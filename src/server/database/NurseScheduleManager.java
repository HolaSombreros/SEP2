package server.database;

import server.model.domain.user.*;
import server.model.domain.appointment.TimeInterval;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class NurseScheduleManager {
    public NurseScheduleManager() {
    }

    //shift manager
    public Shift addShift(TimeInterval timeInterval) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO shift (time_from,time_to) VALUES (?,?);", Statement.RETURN_GENERATED_KEYS);
            statement.setTime(1, Time.valueOf(timeInterval.getFrom()));
            statement.setTime(2, Time.valueOf(timeInterval.getTo()));
            if (!isShift(timeInterval)) {
                statement.executeUpdate();
                ResultSet keys = statement.getGeneratedKeys();
                if (keys.next())
                    return new Shift(keys.getInt("shift_id"), timeInterval);
                else {
                    throw new SQLException("No keys were generated");
                }
            }
        }
        return null;
    }

    public boolean isShift(TimeInterval timeInterval) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM shift WHERE time_from = ?, time_ to = ?");
            statement.setTime(1, Time.valueOf(timeInterval.getFrom()));
            statement.setTime(2, Time.valueOf(timeInterval.getTo()));
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }

    public ShiftList getAllShifts() throws SQLException {
        try(Connection connection = DatabaseManager.getInstance().getConnection()){
            ShiftList list = new ShiftList();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM shift_view");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                LocalTime time_to = resultSet.getTime("time_to").toLocalTime();
                LocalTime time_from = resultSet.getTime("time_from").toLocalTime();
                int id = resultSet.getInt("shift_id");
                int time_interval_id = resultSet.getInt("time_interval_id");
                list.add(new Shift(id, new TimeInterval(time_interval_id,time_from, time_to)));
            }
            return list;
        }
    }

    //TODO: schedules

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
                    return new Schedule(keys.getInt("shift_id"), dateFrom, dateTo, shift);
                else {
                    throw new SQLException("No keys were generated");
                }
            }
        }
        return null;
    }

    public boolean isSchedule(LocalDate dateFrom, LocalDate dateTo, Shift shift) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM schedule WHERE date_from = ?, date_to = ?, shift_id = ?");
            statement.setDate(1, Date.valueOf(dateFrom.toString()));
            statement.setDate(2, Date.valueOf(dateTo.toString()));
            statement.setInt(3,shift.getId());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }


    public ScheduleList getAllSchedules() throws SQLException{
        try(Connection connection = DatabaseManager.getInstance().getConnection()){
            ScheduleList list = new ScheduleList();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM schedule JOIN shift USING(shift_id);");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                LocalTime time_to = resultSet.getTime("time_to").toLocalTime();
                LocalTime time_from = resultSet.getTime("time_from").toLocalTime();
                LocalDate date_from = resultSet.getDate("date_from").toLocalDate();
                LocalDate date_to = resultSet.getDate("date_to").toLocalDate();
                int shift_id = resultSet.getInt("shift_id");
                int id = resultSet.getInt("schedule_id");
                int time_interval_id = resultSet.getInt("time_interval_id");
                list.add(new Schedule(id, date_from,date_to,new Shift(shift_id, new TimeInterval(time_interval_id,time_from, time_to))));
            }
            return list;
        }
    }

    public void addNurseSchedule(Nurse nurse, Schedule schedule) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO nurse_schedule (nurse_cpr,schedule_id) VALUES(?,?);");
            insertStatement.setString(1, nurse.getCpr());
            insertStatement.setInt(2,schedule.getId());
            addSchedule(schedule.getDateFrom(),schedule.getDateTo(),schedule.getShift());
            insertStatement.executeUpdate();
        }
    }

    public void editNurseSchedule(Nurse nurse, Schedule schedule) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement insertStatement = connection.prepareStatement("UPDATE nurse_schedule SET schedule_id = ? WHERE nurse_cpr = ?;");
            insertStatement.setInt(1,schedule.getId());
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


    //TODO: list of nurse schedules
    public void getAllNurseSchedules() throws SQLException {
        List<Schedule> schedules = new ArrayList<>();
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM nurse_schedule;");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String cpr = resultSet.getString("nurse_cpr");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                LocalTime from = resultSet.getTime("time_from").toLocalTime();
                LocalTime to = resultSet.getTime("time_to").toLocalTime();
            }
        }
    }
}
