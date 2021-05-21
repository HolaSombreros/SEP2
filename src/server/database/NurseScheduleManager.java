package server.database;

import server.model.domain.user.*;
import server.model.domain.appointment.TimeInterval;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class NurseScheduleManager {
    public NurseScheduleManager() {}

    //shift manager
    public Shift addShift(LocalTime timeFrom, LocalTime timeTo) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO shift (time_from,time_to) VALUES (?,?);", Statement.RETURN_GENERATED_KEYS);
            statement.setTime(1, Time.valueOf(timeFrom));
            statement.setTime(2, Time.valueOf(timeTo));
            if (!isShift(timeFrom,timeTo)) {
                statement.executeUpdate();
                ResultSet keys = statement.getGeneratedKeys();
                if (keys.next())
                    return new Shift(keys.getInt("shift_id"), timeFrom,timeTo);
                else {
                    throw new SQLException("No keys were generated");
                }
            }
        }
        return null;
    }

    public boolean isShift(LocalTime timeFrom, LocalTime timeTo) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM shift WHERE time_from = ? AND time_to = ?");
            statement.setTime(1, Time.valueOf(timeFrom));
            statement.setTime(2, Time.valueOf(timeTo));
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }

    public ShiftList getAllShifts() throws SQLException {
        try(Connection connection = DatabaseManager.getInstance().getConnection()){
            ShiftList list = new ShiftList();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM shift");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                LocalTime time_to = resultSet.getTime("time_to").toLocalTime();
                LocalTime time_from = resultSet.getTime("time_from").toLocalTime();
                int id = resultSet.getInt("shift_id");
                list.add(new Shift(id,time_from,time_to));
            }
            return list;
        }
    }


    public Schedule addSchedule(LocalDate dateFrom, LocalDate dateTo, Shift shift) throws SQLException{
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO schedule (date_from,date_to,shift_id) VALUES (?,?,?);", Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, Date.valueOf(dateFrom.toString()));
            statement.setDate(2, Date.valueOf(dateTo.toString()));
            statement.setInt(3,shift.getId());
            if (!isSchedule(dateFrom, dateTo, shift)) {
                statement.executeUpdate();
                ResultSet keys = statement.getGeneratedKeys();
                if (keys.next())
                    return new Schedule(keys.getInt("schedule_id"), dateFrom, dateTo, shift);
                else {
                    throw new SQLException("No keys were generated");
                }
            }
            return getSchedule(dateFrom, shift);
        }
    }

    public boolean isSchedule(LocalDate dateFrom, LocalDate dateTo, Shift shift) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM schedule WHERE date_from = ? AND date_to = ? AND shift_id = ?");
            statement.setDate(1, Date.valueOf(dateFrom.toString()));
            statement.setDate(2, Date.valueOf(dateTo.toString()));
            statement.setInt(3,shift.getId());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }

    public Schedule getSchedule(LocalDate dateFrom, Shift shift) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM schedule WHERE date_from = ? AND shift_id = ?");
            statement.setDate(1, Date.valueOf(dateFrom.toString()));
            statement.setInt(2,shift.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                LocalDate date_from = resultSet.getDate("date_from").toLocalDate();
                LocalDate date_to = resultSet.getDate("date_to").toLocalDate();
                int shift_id = resultSet.getInt("shift_id");
                int id = resultSet.getInt("schedule_id");
                return new Schedule(id, date_from,date_to, getAllShifts().getById(shift_id));
            }
            return null;
        }
    }


    public ScheduleList getAllSchedules() throws SQLException{
        try(Connection connection = DatabaseManager.getInstance().getConnection()){
            ScheduleList list = new ScheduleList();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM schedule_view JOIN shift USING(shift_id);");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                LocalTime time_to = resultSet.getTime("time_to").toLocalTime();
                LocalTime time_from = resultSet.getTime("time_from").toLocalTime();
                LocalDate date_from = resultSet.getDate("date_from").toLocalDate();
                LocalDate date_to = resultSet.getDate("date_to").toLocalDate();
                int shift_id = resultSet.getInt("shift_id");
                int id = resultSet.getInt("schedule_id");
                list.add(new Schedule(id, date_from,date_to,new Shift(shift_id, time_from,time_to)));
            }
            return list;
        }
    }

    public ScheduleList getAllSchedulesForNurse(Nurse nurse) throws SQLException{
        try(Connection connection = DatabaseManager.getInstance().getConnection()){
            ScheduleList list = new ScheduleList();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM schedule_view WHERE nurse_cpr=?;");
            statement.setString(1,nurse.getCpr());
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                LocalTime time_to = resultSet.getTime("time_to").toLocalTime();
                LocalTime time_from = resultSet.getTime("time_from").toLocalTime();
                LocalDate date_from = resultSet.getDate("date_from").toLocalDate();
                LocalDate date_to = resultSet.getDate("date_to").toLocalDate();
                int shift_id = resultSet.getInt("shift_id");
                int id = resultSet.getInt("schedule_id");
                list.add(new Schedule(id, date_from,date_to,new Shift(shift_id, time_from,time_to)));
            }
            return list;
        }
    }

    public void addNurseSchedule(Nurse nurse, Schedule schedule) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO nurse_schedule (nurse_cpr,schedule_id) VALUES(?,?);");
            insertStatement.setString(1, nurse.getCpr());
            insertStatement.setInt(2,schedule.getId());
            //adds the schedule, only if it doesn't exist
            addSchedule(schedule.getDateFrom(),schedule.getDateTo(),schedule.getShift());
            insertStatement.executeUpdate();
        }
    }

    public void editNurseSchedule(Nurse nurse, Schedule schedule) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement insertStatement = connection.prepareStatement("UPDATE nurse_schedule SET schedule_id = ? WHERE nurse_cpr = ?;");
            insertStatement.setInt(1,schedule.getId());
            insertStatement.setString(2,nurse.getCpr());
            addSchedule(schedule.getDateFrom(),schedule.getDateTo(),schedule.getShift());
            insertStatement.executeUpdate();
        }
    }

    public void removeNurseSchedule(Nurse nurse, Schedule schedule) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement insertStatement = connection.prepareStatement("DELETE FROM nurse_schedule WHERE nurse_cpr = ? AND schedule_id = ?;");
            insertStatement.setString(1, nurse.getCpr());
            insertStatement.setInt(2,schedule.getId());
            insertStatement.executeUpdate();
        }
    }


    //TODO: list of nurse schedules from schedule_view

}
