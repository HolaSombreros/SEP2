package server.database;

import server.model.domain.user.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientManager{

  private AddressManager addressManager;


  public PatientManager() {
    addressManager = new AddressManager();
  }

  public void addPatient(Patient patient) throws SQLException {
    try (Connection connection = DatabaseManager.getInstance().getConnection()) {
      PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO patient VALUES (?,?)");
      insertStatement.setString(1, patient.getCpr());
      insertStatement.setString(2,patient.getVaccineStatus().toString());
    }
  }



  public VaccineStatus getVaccineStatus(String cpr)throws SQLException {
    try (Connection connection = DatabaseManager.getInstance().getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM patient WHERE cpr=?");
      statement.setString(1, cpr);
      ResultSet resultSet = statement.executeQuery();

      if(resultSet.next()){
        String vaccineStatus = resultSet.getString("valid_for_vaccine");
        VaccineStatus status = null;
        switch (vaccineStatus){
          case "Approved":
            status = new ApprovedStatus();
            break;
          case "Not Approved":
            status = new NotApprovedStatus();
            break;
          case "Pending":
            status = new PendingStatus();
            break;
        }
        return status;
      }
      throw new IllegalStateException("User not in database");

    }
  }


  public void removePatient(User patient) throws SQLException
  {
    try (Connection connection = DatabaseManager.getInstance().getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("DELETE FROM patient WHERE cpr = ?");
      statement.setString(1, patient.getCpr());
      statement.executeQuery();
    }
  }

  public boolean isPatient(User patient) throws SQLException{
    try(Connection connection = DatabaseManager.getInstance().getConnection()) {
      PreparedStatement statement= connection.prepareStatement("SELECT * FROM patient WHERE cpr = ?");
      statement.setString(1,patient.getCpr());
      ResultSet resultSet = statement.executeQuery();
      if(resultSet.next())
        return true;
      else
        return false;
    }
  }

  public boolean isPatient(String cpr) throws SQLException{
    try(Connection connection = DatabaseManager.getInstance().getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM patient WHERE cpr = ?");
      statement.setString(1,cpr);
      ResultSet resultSet = statement.executeQuery();
      if(resultSet.next())
        return true;
      else
        return false;
    }
  }





}
