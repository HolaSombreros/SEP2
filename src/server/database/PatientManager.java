package server.database;

import server.model.domain.*;
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
        patient = new Patient(cprResult, password, firstName, middleName, lastName, address, phone, email,Patient.VaccineStatus.fromString(validForVaccination));
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
        registeredUsers.getUsersList().add(new Patient(cprResult, password, firstName, middleName, lastName, address, phone, email, Patient.VaccineStatus.fromString(validForVaccination)));
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
      insertStatement.setString(11, Patient.VaccineStatus.NOTAPPLIED.toString());
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
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM patient WHERE cpr = ?");
      statement.setString(1,cpr);
      ResultSet resultSet = statement.executeQuery();
      if(resultSet.next())
        return true;
      else
        return false;
    }
  }


  public void updateEmail(String email) throws SQLException{
    try(Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("UPDATE patient set email = ?");
      statement.setString(1,email);
      statement.executeUpdate();
    }
  }

  public void updatePhone(String phone) throws SQLException{
    try(Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("UPDATE patient set phone = ?");
      statement.setString(1,phone);
      statement.executeUpdate();
    }
  }

  public void updateAddress(Address address) throws SQLException{
    try(Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("UPDATE patient set street = ?, number = ?, zip_code = ?");
      statement.setString(1,address.getStreet());
      statement.setString(2,address.getNumber());
      statement.setInt(3,address.getZipcode());
      statement.executeUpdate();
    }
  }

  public void updateVaccineStatus(Patient.VaccineStatus vaccineStatus)throws SQLException{
    try(Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("UPDATE patient set valid_for_vaccine = ?");
      statement.setString(1,vaccineStatus.toString());
      statement.executeUpdate();
    }
  }

  public void updatePassword(String password) throws SQLException {
    try(Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("UPDATE patient set password = ?");
      statement.setString(1,password);
      statement.executeUpdate();
    }
  }

  public void updateName(String firstName, String middleName, String lastName) throws SQLException{
    try(Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("UPDATE patient set firstname = ?, middlename = ?, lastname = ?");
      statement.setString(1,firstName);
      statement.setString(2,middleName);
      statement.setString(3,lastName);
      statement.executeUpdate();
    }
  }

}
