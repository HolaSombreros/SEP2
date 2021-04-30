package server.database;

import server.model.domain.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientManager{

  private AddressManager addressManager;


  public PatientManager() {
    addressManager = new AddressManager();
  }

  public void addPatient(User patient) throws SQLException {
    try (Connection connection = DatabaseManager.getInstance().getConnection()) {
      PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO user VALUES (?,?)");
      insertStatement.setString(1, patient.getCpr());
      insertStatement.setString(2,Patient.VaccineStatus.NOTAPPLIED.toString());
    }
  }

  public User getPatientByCpr(String cpr) throws SQLException {
    User patient = null;
    try (Connection connection = DatabaseManager.getInstance().getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM patient WHERE cpr=?");
      statement.setString(1, cpr);
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next())
      {
        String cprResult = resultSet.getString("cpr");
        String password = resultSet.getString("password");
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        String middleName = resultSet.getString("middleName");
        String street = resultSet.getString("street");
        String number = resultSet.getString("number");
        int zipcode = resultSet.getInt("zip_code");
        String city = addressManager.getCityByZipcode(zipcode);
        Address address = new Address(street, number, zipcode, city);
        String phone = resultSet.getString("phone");
        String email = resultSet.getString("email");
        String validForVaccination = resultSet.getString("valid_for_vaccine");
        System.out.println(validForVaccination);
        patient = new Patient(cprResult, password, firstName, middleName, lastName, address, phone, email,Patient.VaccineStatus.fromString(validForVaccination));
        return patient;
      }
      else
      {
        throw new IllegalStateException("User not in database");
      }
    }
  }

  public Patient.VaccineStatus getVaccineStatus(String cpr)throws SQLException {
    try (Connection connection = DatabaseManager.getInstance().getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM patient WHERE cpr=?");
      statement.setString(1, cpr);
      ResultSet resultSet = statement.executeQuery();
      if(resultSet.next()){
        String vaccineStatus = resultSet.getString("valid_for_vaccine");
        return Patient.VaccineStatus.fromString(vaccineStatus);
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
