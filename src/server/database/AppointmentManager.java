package server.database;

import server.model.domain.appointment.*;
import server.model.domain.user.Nurse;
import server.model.domain.user.Patient;

import java.sql.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentManager {
    private UserManager userManager;
    
    public AppointmentManager(UserManager userManager) {
        this.userManager = userManager;
        try{
            updateAppointments();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    //adds only if not in the table
    public TimeInterval addTimeInterval(LocalTime timeFrom, LocalTime timeTo) throws SQLException{
        try(Connection connection = DatabaseManager.getInstance().getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO time_interval (time_from,time_to) VALUES (?,?);",Statement.RETURN_GENERATED_KEYS);
            statement.setTime(1,Time.valueOf(timeFrom));
            statement.setTime(2,Time.valueOf(timeTo));
            if(!isTimeInterval(timeFrom,timeTo)) {
                statement.executeUpdate();
                ResultSet keys = statement.getGeneratedKeys();
                if (keys.next())
                    return new TimeInterval(keys.getInt("time_interval_id"),timeFrom,timeTo);
                else {
                    throw new SQLException("No keys were generated");
                }
            }
        }
        return null;
    }

    //checks if is in the table
    public boolean isTimeInterval(LocalTime timeFrom, LocalTime timeTo) throws SQLException{
        try(Connection connection = DatabaseManager.getInstance().getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM time_interval WHERE time_from = ? AND time_to = ?");
            statement.setTime(1,Time.valueOf(timeFrom));
            statement.setTime(2,Time.valueOf(timeTo));
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }

    //gets the list of timeintervals from the table
    public TimeIntervalList getTimeIntervals() throws SQLException{
        try(Connection connection = DatabaseManager.getInstance().getConnection()){
            TimeIntervalList list = new TimeIntervalList();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM time_interval");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                LocalTime time_to = resultSet.getTime("time_to").toLocalTime();
                LocalTime time_from = resultSet.getTime("time_from").toLocalTime();
                int id = resultSet.getInt("time_interval_id");
                list.add(new TimeInterval(id, time_from, time_to));
            }
            return list;
        }
    }

    public Appointment addAppointment(LocalDate date, TimeInterval timeInterval, Type type, Patient patient, Nurse nurse) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO appointment (date, time_interval_id, patient_cpr, nurse_cpr, type, status, result) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, Date.valueOf(date));
            statement.setInt(2, timeInterval.getId());
            statement.setString(3, patient.getCpr());
            statement.setString(4, nurse.getCpr());
            statement.setString(5, type.toString());
            statement.setString(6, "Upcoming");
            if (type.equals(Type.TEST)) {
                statement.setString(7, (Result.NORESULTSAVAILABLE.toString()));
            }
            else {
                statement.setString(7, null);
            }
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                switch (type) {
                    case TEST:
                        return new TestAppointment(keys.getInt("appointment_id"), date, timeInterval, type, patient, nurse);
                    case VACCINE:
                        return new VaccineAppointment(keys.getInt("appointment_id"), date, timeInterval, type, patient, nurse);
                    default:
                        throw new IllegalStateException("Invalid appointment type");
                }
            }
            else {
                throw new SQLException("No keys were generated");
            }
        }
    }
    
    public AppointmentTimeIntervalList getAllAppointments() throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            AppointmentTimeIntervalList appointmentTimeIntervalList = new AppointmentTimeIntervalList();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM appointment_view ORDER BY appointment_id;");
            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
                LocalDate date = rs.getDate("date").toLocalDate();
                LocalTime from = rs.getTime("time_from").toLocalTime();
                LocalTime to = rs.getTime("time_to").toLocalTime();
                int time_interval_id = rs.getInt("time_interval_id");
                TimeInterval timeInterval = new TimeInterval(time_interval_id,from, to);
                
                // Will only happen if the specified date and time interval doesn't already exist in the system
                appointmentTimeIntervalList.add(new AppointmentTimeInterval(date, timeInterval));
                
                int id = rs.getInt("appointment_id");
                Patient patient = userManager.getPatient(rs.getString("patient_cpr"));
                Nurse nurse = userManager.getNurse(rs.getString("nurse_cpr"));
                Type type = Type.fromString(rs.getString("type"));
                Result result = Result.fromString(rs.getString("result"));
                
                // switching on this for now - might not be the proper way of doing this (:
                Status status = null;
                switch (rs.getString("status")) {
                    case "Finished":
                        status = new FinishedAppointment();
                        break;
                    case "Results given":
                        status = new ResultGivenAppointment();
                        break;
                    case "Cancelled":
                        status = new CancelledAppointment();
                        break;
                }
                
                Appointment appointment = null;
                switch (type) {
                    case TEST:
                        if (rs.getString("status").equals("Upcoming")) {
                            appointment = new TestAppointment(id, date, timeInterval, Type.TEST, patient, nurse, result);
                        }
                        else {
                            appointment = new TestAppointment(id, date, timeInterval, Type.TEST, patient, nurse, result, status);
                        }
                        break;
                    case VACCINE:
                        if (rs.getString("status").equals("Upcoming")) {
                            appointment = new VaccineAppointment(id, date, timeInterval, Type.VACCINE, patient, nurse);
                        }
                        else {
                            appointment = new VaccineAppointment(id, date, timeInterval, Type.VACCINE, patient, nurse, status);
                        }
                        break;
                }
    
                appointmentTimeIntervalList.add(appointment, date, timeInterval);
            }
            return appointmentTimeIntervalList;
        }
    }
    
    public void cancelStatus(int id) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE appointment SET status = ? WHERE appointment_id = ?;");
            statement.setString(1, new CancelledAppointment().toString());
            statement.setInt(2, id);
            statement.executeUpdate();
        }
    }
    public void updateAppointments() throws SQLException{
        try (Connection connection = DatabaseManager.getInstance().getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT date, time_to, appointment_id FROM appointment_view WHERE status = 'Upcoming';");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                LocalDate date = rs.getDate("date").toLocalDate();
                LocalTime timeTo = rs.getTime("time_to").toLocalTime();
                int id = rs.getInt("appointment_id");
                if(date.isBefore(LocalDate.now()) || (date.equals(LocalDate.now()) && timeTo.isBefore(LocalTime.now()))){
                    PreparedStatement statement1 = connection.prepareStatement("UPDATE appointment SET status = ? WHERE appointment_id = ?;");
                    statement1.setString(1, new FinishedAppointment().toString());
                    statement1.setInt(2, id);
                    statement1.executeUpdate();
                }
            }
        }
    }
    
    public void rescheduleAppointment(int id, LocalDate date, TimeInterval timeInterval) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE appointment SET date = ?, time_interval_id = ? WHERE appointment_id = ?;");
            statement.setDate(1, Date.valueOf(date));
            statement.setInt(2,timeInterval.getId());
            statement.setInt(3, id);
            statement.executeUpdate();
        }
    }

    public void changeResult(TestAppointment appointment) throws SQLException{
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE appointment SET result = ? WHERE appointment_id = ?;");
            statement.setString(1,appointment.getResult().toString());
            statement.setInt(2,appointment.getId());
            statement.executeUpdate();
        }
    }

    
}


