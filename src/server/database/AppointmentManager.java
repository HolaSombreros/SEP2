package server.database;

import server.model.Appointment;
import server.model.Nurse;
import server.model.TestAppointment;

import java.sql.*;

public class AppointmentManager extends DatabaseManager
{

  public AppointmentManager(){}

  public void addAppointment(Appointment appointment) throws SQLException {
    try(Connection connection = getConnection()){
      PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO appointment VALUES (?,?,?,?,?,?,?,?,?)");
      insertStatement.setDate(1,Date.valueOf(appointment.getDate().getDateSQL()));
      insertStatement.setTime(2, Time.valueOf(appointment.getTimeInterval().getFrom().toString()+":00"));
      insertStatement.setTime(3, Time.valueOf(appointment.getTimeInterval().getTo().toString()+":00"));
      insertStatement.setString(4,appointment.getPatient().getCpr());
      insertStatement.setString(5,appointment.getNurse().getCpr());
      insertStatement.setString(6,((Nurse)appointment.getNurse()).getEmployeeId());
      insertStatement.setString(7,appointment.getType().toString());
      insertStatement.setString(8,appointment.getStatus().toString());
      if(appointment.getType().equals(Appointment.Type.TEST)) {
        insertStatement.setString(9, ((TestAppointment) appointment).getResult().toString());
      }
      else
        insertStatement.setString(9,null);
      insertStatement.executeUpdate();
    }
  }

  //TODO: get appointment by patient, nurse, time, status.
}
