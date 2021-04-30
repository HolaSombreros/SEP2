package server.database;


import server.model.domain.*;
import java.sql.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentManager extends DatabaseManager {
  private NurseManager nurseManager;
  private PatientManager patientManager;

  public AppointmentManager()
  {
    nurseManager = new NurseManager();
    patientManager = new PatientManager();
  }

  public void addAppointment(Appointment appointment) throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO appointment VALUES (?,?,?,?,?,?,?,?,?)");
      insertStatement.setDate(1, Date.valueOf(LocalDate.of(appointment.getDate().getYear(), appointment.getDate().getMonth(), appointment.getDate().getDayOfMonth())));
      insertStatement.setTime(2, Time.valueOf(LocalTime.of(appointment.getTimeInterval().getFrom().getHour(), appointment.getTimeInterval().getFrom().getMinute())));
      insertStatement.setTime(3, Time.valueOf(LocalTime.of(appointment.getTimeInterval().getTo().getHour(), appointment.getTimeInterval().getTo().getMinute())));
      insertStatement.setString(4, appointment.getPatient().getCpr());
      insertStatement.setString(5, appointment.getNurse().getCpr());
      insertStatement.setString(6, ((Nurse) appointment.getNurse()).getEmployeeId());
      insertStatement.setString(7, appointment.getType().toString());
      insertStatement.setString(8, appointment.getStatus().toString());
      if (appointment.getType().equals(Type.TEST))
      {
        insertStatement.setString(9, ((TestAppointment) appointment).getResult().toString());
      }
      else
        insertStatement.setString(9, null);
      insertStatement.executeUpdate();
    }
  }

  public AppointmentList getAppointmentsByPatient(Patient patient) throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM appointment WHERE patient_cpr=?");
      statement.setString(1, patient.getCpr());
      ResultSet resultSet = statement.executeQuery();
      AppointmentList list = new AppointmentList();
      while (resultSet.next())
      {
        Date dateSQL = resultSet.getDate("date");
        Time timeFromSQL = resultSet.getTime("time_from");
        Time timeToSQL = resultSet.getTime("time_to");
        String patientCpr = resultSet.getString("patient_cpr");
        String nurseCpr = resultSet.getString("nurse_cpr");
        String nurseId = resultSet.getString("nurse_id");
        String typeSQL = resultSet.getString("type");
        String statusSQL = resultSet.getString("status");
        String resultSQL = resultSet.getString("result");
        LocalDate date = LocalDate.of(dateSQL.getYear(), dateSQL.getMonth(), dateSQL.getDay());
        LocalTime timeFrom = LocalTime.of(timeFromSQL.getHours(), timeFromSQL.getMinutes());
        LocalTime timeTo = LocalTime.of(timeFromSQL.getHours(), timeFromSQL.getMinutes());
        Type type = Type.valueOf(typeSQL);
        Appointment.Status status = Appointment.Status.valueOf(statusSQL);
        Nurse nurse = nurseManager.getNurseByCPR(nurseCpr);
        if (type.equals(Type.TEST))
        {
          Result result = Result.valueOf(resultSQL);
          Appointment appointment = new TestAppointment(date, new TimeInterval(timeFrom, timeTo), type, patient, nurse);
          ((TestAppointment) appointment).setResult(result);
          appointment.setStatus(status);
          list.add(appointment);
        }
        else
        {
          Appointment appointment = new VaccineAppointment(date, new TimeInterval(timeFrom, timeTo), type, patient, nurse);
          appointment.setStatus(status);
          list.add(appointment);
        }
      }
      return list;
    }
  }

  public AppointmentList getAppointmentsByNurse(Nurse nurse) throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM appointment WHERE nurse_cpr=?");
      statement.setString(1, nurse.getCpr());
      ResultSet resultSet = statement.executeQuery();
      AppointmentList list = new AppointmentList();
      while (resultSet.next())
      {
        Date dateSQL = resultSet.getDate("date");
        Time timeFromSQL = resultSet.getTime("time_from");
        Time timeToSQL = resultSet.getTime("time_to");
        String patientCpr = resultSet.getString("patient_cpr");
        String nurseCpr = resultSet.getString("nurse_cpr");
        String nurseId = resultSet.getString("nurse_id");
        String typeSQL = resultSet.getString("type");
        String statusSQL = resultSet.getString("status");
        String resultSQL = resultSet.getString("result");
        LocalDate date = LocalDate.of(dateSQL.getYear(), dateSQL.getMonth(), dateSQL.getDay());
        LocalTime timeFrom = LocalTime.of(timeFromSQL.getHours(), timeFromSQL.getMinutes());
        LocalTime timeTo = LocalTime.of(timeToSQL.getHours(), timeToSQL.getMinutes());
        Type type = Type.valueOf(typeSQL);
        Appointment.Status status = Appointment.Status.valueOf(statusSQL);
        User patient = patientManager.getPatientByCpr(patientCpr);
        if (type.equals(Type.TEST))
        {
          Result result = Result.valueOf(resultSQL);
          Appointment appointment = new TestAppointment(date, new TimeInterval(timeFrom, timeTo), type, (Patient)patient, nurse);
          ((TestAppointment) appointment).setResult(result);
          appointment.setStatus(status);
          list.add(appointment);
        }
        else
        {
          Appointment appointment = new VaccineAppointment(date, new TimeInterval(timeFrom, timeTo), type, (Patient)patient, nurse);
          appointment.setStatus(status);
          list.add(appointment);
        }
      }
      return list;
    }
  }

  public AppointmentList getAppointmentsByNurseAndStatus(Nurse nurse, Appointment.Status status) throws SQLException
  {
    AppointmentList list = new AppointmentList();
    for (Appointment appointment : getAppointmentsByNurse(nurse).getAppointments())
      if (appointment.getStatus().equals(status))
        list.add(appointment);
    return list;
  }

  public AppointmentList getAppointmentsByPatientAndStatus(Patient patient, Appointment.Status status) throws SQLException
  {
    AppointmentList list = new AppointmentList();
    for (Appointment appointment : getAppointmentsByPatient(patient).getAppointments())
      if (appointment.getStatus().equals(status))
        list.add(appointment);
    return list;
  }


}


