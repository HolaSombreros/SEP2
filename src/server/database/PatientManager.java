package server.database;

import server.model.domain.user.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientManager extends DatabaseManager {

  private AddressManager addressManager;
  private UserList registeredUsers;

  public PatientManager()
  {
    addressManager = new AddressManager();
    registeredUsers = new UserList();
  }


  public User getPatientByCpr(String cpr) throws SQLException {
    User patient = null;
    try (Connection connection = getConnection())
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
        patient = new Patient(cprResult, password, firstName, middleName, lastName, address, phone, email, new NotApprovedStatus());
        return patient;
      }
      else
      {
        throw new IllegalStateException("User not in database");
      }
    }
  }

  public UserList getAllPatients() throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM patient");
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next())
      {
        String cprResult = resultSet.getString("cpr");
        String password = resultSet.getString("password");
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        String middleName = resultSet.getString("middleName");
        String street = resultSet.getString("street");
        String number = resultSet.getString("number");
        int zipcode = resultSet.getInt("zip_code");
        Address address = new Address(street, number, zipcode, addressManager.getCityByZipcode(zipcode));
        String phone = resultSet.getString("phone");
        String email = resultSet.getString("email");
        String validForVaccination = resultSet.getString("valid_for_vaccine");
        registeredUsers.getUsersList().add(new Patient(cprResult, password, firstName, middleName, lastName, address, phone, email, new NotApprovedStatus()));
      }
      return registeredUsers;
    }
  }

  public void addPatient(User patient) throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO patient VALUES (?,?,?,?,?,?,?,?,?,?,?)");
      insertStatement.setString(1, patient.getCpr());
      insertStatement.setString(2, patient.getPassword());
      insertStatement.setString(3, patient.getFirstName());
      insertStatement.setString(4, patient.getMiddleName());
      insertStatement.setString(5, patient.getLastName());
      insertStatement.setString(6, patient.getPhone());
      insertStatement.setString(7, patient.getEmail());
      insertStatement.setString(8, patient.getAddress().getStreet());
      insertStatement.setString(9, patient.getAddress().getNumber());
      insertStatement.setInt(10, patient.getAddress().getZipcode());
      insertStatement.setString(11, "change me");
      if (!addressManager.isAddress(patient.getAddress().getStreet(), patient.getAddress().getNumber(), patient.getAddress().getZipcode()))
        addressManager.addAddress(patient.getAddress());
      insertStatement.executeUpdate();
    }
  }

  public String getPassword(String cpr) throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement selectStatement = connection.prepareStatement("SELECT password FROM patient WHERE cpr = ?");
      selectStatement.setString(1, cpr);
      ResultSet resultSet = selectStatement.executeQuery();
      if (resultSet.next())
      {
        String password = resultSet.getString("password");
        return password;
      }
      else
      {
        throw new IllegalStateException("User not in database");
      }
    }
  }

  public void removePatient(User patient) throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("DELETE FROM patient WHERE cpr = ?");
      statement.setString(1, patient.getCpr());
      statement.executeQuery();
    }
  }

  public boolean isPatient(User patient) throws SQLException{
    try(Connection connection = getConnection()) {
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
    try(Connection connection = getConnection()) {
      PreparedStatement statement= connection.prepareStatement("SELECT * FROM patient WHERE cpr = ?");
      statement.setString(1,cpr);
      ResultSet resultSet = statement.executeQuery();
      if(resultSet.next())
        return true;
      else
        return false;
    }
  }

}
