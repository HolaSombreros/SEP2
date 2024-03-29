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
    
    public int getNextId() throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(appointment_id) FROM appointment;");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") + 1;
            }
            else {
                throw new SQLException("Something went very wrong...");
            }
        }
    }
    
    public void addAppointment(Appointment appointment) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO appointment (date,time_from,time_to,patient_cpr,nurse_cpr,type,status,result) VALUES (?,?,?,?,?,?,?,?)");
            insertStatement.setDate(1, Date.valueOf(appointment.getDate()));
            insertStatement.setTime(2, Time.valueOf(appointment.getTimeInterval().getFrom()));
            insertStatement.setTime(3, Time.valueOf(appointment.getTimeInterval().getTo()));
            insertStatement.setString(4, appointment.getPatient().getCpr());
            insertStatement.setString(5, appointment.getNurse().getCpr());
            insertStatement.setString(6, appointment.getType().toString());
            insertStatement.setString(7, appointment.getStatus().toString());
            if (appointment.getType().equals(Type.TEST)) {
                insertStatement.setString(8, (Result.NORESULTSAVAILABLE.toString()));
            }
            else {
                insertStatement.setString(8, null);
            }
            insertStatement.executeUpdate();
        }
    }
    
    public AppointmentTimeIntervalList getAllAppointments() throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            AppointmentTimeIntervalList appointmentTimeIntervalList = new AppointmentTimeIntervalList();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM appointment ORDER BY appointment_id;");
            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
                LocalDate date = rs.getDate("date").toLocalDate();
                LocalTime from = rs.getTime("time_from").toLocalTime();
                LocalTime to = rs.getTime("time_to").toLocalTime();
                TimeInterval timeInterval = new TimeInterval(from, to);
                
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
            PreparedStatement statement = connection.prepareStatement("SELECT date, time_to, appointment_id FROM appointment WHERE status = 'Upcoming';");
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
            PreparedStatement statement = connection.prepareStatement("UPDATE appointment SET date = ?, time_from = ?, time_to = ? WHERE appointment_id = ?;");
            statement.setDate(1, Date.valueOf(date));
            statement.setTime(2, Time.valueOf(timeInterval.getFrom()));
            statement.setTime(3, Time.valueOf(timeInterval.getTo()));
            statement.setInt(4, id);
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
    
    // TODO - remove all this...
    
    //    public AppointmentList getAppointmentsByPatient(Patient patient) throws SQLException {
    //        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
    //            PreparedStatement statement = connection.prepareStatement("SELECT * FROM appointment WHERE patient_cpr=?");
    //            statement.setString(1, patient.getCpr());
    //            ResultSet resultSet = statement.executeQuery();
    //            AppointmentList list = new AppointmentList();
    //            while (resultSet.next()) {
    //                Date dateSQL = resultSet.getDate("date");
    //                Time timeFromSQL = resultSet.getTime("time_from");
    //                Time timeToSQL = resultSet.getTime("time_to");
    //                String patientCpr = resultSet.getString("patient_cpr");
    //                String nurseCpr = resultSet.getString("nurse_cpr");
    //                String nurseId = resultSet.getString("nurse_id");
    //                String typeSQL = resultSet.getString("type");
    //                String statusSQL = resultSet.getString("status");
    //                String resultSQL = resultSet.getString("result");
    //                LocalDate date = LocalDate.of(dateSQL.getYear(), dateSQL.getMonth(), dateSQL.getDay());
    //                LocalTime timeFrom = LocalTime.of(timeFromSQL.getHours(), timeFromSQL.getMinutes());
    //                LocalTime timeTo = LocalTime.of(timeFromSQL.getHours(), timeFromSQL.getMinutes());
    //                Type type = Type.valueOf(typeSQL);
    //                //        Appointment.Status status = Appointment.Status.valueOf(statusSQL);
    //                Nurse nurse = userManager.getNurse(nurseCpr);
    //                if (type.equals(Type.TEST)) {
    //                    Result result = Result.valueOf(resultSQL);
    //                    Appointment appointment = new TestAppointment(date, new TimeInterval(timeFrom, timeTo), type, patient, nurse);
    //                    ((TestAppointment) appointment).setResult(result);
    //                    //          appointment.setStatus(status);
    //                    list.add(appointment);
    //                }
    //                else {
    //                    Appointment appointment = new VaccineAppointment(date, new TimeInterval(timeFrom, timeTo), type, patient, nurse);
    //                    //          appointment.setStatus(status);
    //                    list.add(appointment);
    //                }
    //            }
    //            return list;
    //        }
    //    }
    //
    //    public AppointmentList getAppointmentsByNurse(Nurse nurse) throws SQLException {
    //        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
    //            PreparedStatement statement = connection.prepareStatement("SELECT * FROM appointment WHERE nurse_cpr=?");
    //            statement.setString(1, nurse.getCpr());
    //            ResultSet resultSet = statement.executeQuery();
    //            AppointmentList list = new AppointmentList();
    //            while (resultSet.next()) {
    //                Date dateSQL = resultSet.getDate("date");
    //                Time timeFromSQL = resultSet.getTime("time_from");
    //                Time timeToSQL = resultSet.getTime("time_to");
    //                String patientCpr = resultSet.getString("patient_cpr");
    //                String nurseCpr = resultSet.getString("nurse_cpr");
    //                String nurseId = resultSet.getString("nurse_id");
    //                String typeSQL = resultSet.getString("type");
    //                String statusSQL = resultSet.getString("status");
    //                String resultSQL = resultSet.getString("result");
    //                LocalDate date = LocalDate.of(dateSQL.getYear(), dateSQL.getMonth(), dateSQL.getDay());
    //                LocalTime timeFrom = LocalTime.of(timeFromSQL.getHours(), timeFromSQL.getMinutes());
    //                LocalTime timeTo = LocalTime.of(timeToSQL.getHours(), timeToSQL.getMinutes());
    //                Type type = Type.valueOf(typeSQL);
    //                //        String status = Appointment.Status.valueOf(statusSQL);
    //                User patient = userManager.getPatient(patientCpr);
    //                if (type.equals(Type.TEST)) {
    //                    Result result = Result.valueOf(resultSQL);
    //                    Appointment appointment = new TestAppointment(date, new TimeInterval(timeFrom, timeTo), type, (Patient) patient, nurse);
    //                    ((TestAppointment) appointment).setResult(result);
    //                    //          appointment.setStatus(status);
    //                    list.add(appointment);
    //                }
    //                else {
    //                    Appointment appointment = new VaccineAppointment(date, new TimeInterval(timeFrom, timeTo), type, (Patient) patient, nurse);
    //                    //          appointment.setStatus(status);
    //                    list.add(appointment);
    //                }
    //            }
    //            return list;
    //        }
    //    }
    
    //  public AppointmentList getAppointmentsByNurseAndStatus(Nurse nurse, Appointment.Status status) throws SQLException
    //  {
    //    AppointmentList list = new AppointmentList();
    //    for (Appointment appointment : getAppointmentsByNurse(nurse).getAppointments())
    //      if (appointment.getStatus().equals(status))
    //        list.add(appointment);
    //    return list;
    //  }
    //
    //  public AppointmentList getAppointmentsByPatientAndStatus(Patient patient, Appointment.Status status) throws SQLException
    //  {
    //    AppointmentList list = new AppointmentList();
    //    for (Appointment appointment : getAppointmentsByPatient(patient).getAppointments())
    //      if (appointment.getStatus().equals(status))
    //        list.add(appointment);
    //    return list;
    //  }
    
}


