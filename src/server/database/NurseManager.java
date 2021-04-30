package server.database;

import server.model.domain.user.NotApprovedStatus;
import server.model.domain.user.Nurse;
import server.model.domain.user.Patient;
import server.model.domain.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NurseManager extends DatabaseManager {
  private PatientManager patientManager;


  public NurseManager() {
    patientManager = new PatientManager();
  }

  public void addNurse(Nurse nurse) throws SQLException {
    try (Connection connection = getConnection()) {
      PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO nurse VALUES (?,?)");
      insertStatement.setString(1, nurse.getCpr());
      insertStatement.setString(2, nurse.getEmployeeId());
      patientManager.addPatient(new Patient(nurse.getCpr(), nurse.getPassword(), nurse.getFirstName(), nurse.getMiddleName(), nurse.getLastName(), nurse.getAddress(), nurse.getPhone(), nurse.getEmail(), new NotApprovedStatus()));
      insertStatement.executeUpdate();
    }
  }

  public Nurse getNurseByCPR(String cpr) throws SQLException {
    try (Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM nurse WHERE cpr=?");
      statement.setString(1, cpr);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        String employeeId = resultSet.getString("employee_id");
        User patient = patientManager.getPatientByCpr(cpr);
        return new Nurse(patient.getCpr(), patient.getPassword(), patient.getFirstName(), patient.getMiddleName(), patient.getLastName(), patient.getAddress(), patient.getPhone(),
                patient.getEmail(), employeeId);
      } else
        throw new IllegalArgumentException("No existing registered nurse with this CPR");
    }
  }

  public ArrayList<Nurse> getAllNurses() throws SQLException {
    try (Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM nurse");
      ResultSet resultSet = statement.executeQuery();
      ArrayList<Nurse> result = new ArrayList<>();
      while (resultSet.next()) {
        String cpr = resultSet.getString("cpr");
        String employeeId = resultSet.getString("employee_id");
        User patient = patientManager.getPatientByCpr(cpr);
        result.add(new Nurse(cpr, patient.getPassword(), patient.getFirstName(), patient.getMiddleName(), patient.getLastName(), patient.getAddress(), patient.getPhone(),
                patient.getEmail(), employeeId));
      }
      return result;
    }
  }

  public void removeNurse(Nurse nurse) throws SQLException {
    try (Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("DELETE FROM nurse WHERE cpr = ?");
      statement.setString(1, nurse.getCpr());
      statement.executeQuery();
    }
  }

  public boolean isNurse(Nurse nurse) throws SQLException {
    try (Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM nurse WHERE cpr = ?");
      statement.setString(1, nurse.getCpr());
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next())
        return true;
      else
        return false;
    }
  }

  public boolean isNurse(String cpr) throws SQLException {
    try (Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM nurse WHERE cpr = ?");
      statement.setString(1, cpr);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next())
        return true;
      else
        return false;
    }
  }

}
