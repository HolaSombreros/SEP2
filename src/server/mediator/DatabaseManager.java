package server.mediator;

import server.model.Address;
import server.model.Patient;
import server.model.UserList;

import java.rmi.RemoteException;
import java.sql.*;

public class DatabaseManager
{
  private UserList registeredUsers;

  public DatabaseManager()
  {
    registeredUsers = new UserList();
  }

  public Connection getConnection() throws SQLException
  {
    return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=sep2", "sep2admin", "admin");
  }

  public UserList getRegisteredUsers()
  {
    return registeredUsers;
  }

  public UserList loadFromDatabasePatients() throws RemoteException, SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users");
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
        Address address = new Address(street,number,zipcode,null); //TODO: Get city from database (new method)
        String phone = resultSet.getString("phone");
        String email = resultSet.getString("email");
        boolean validForVaccination = resultSet.getBoolean("valid_for_vaccine");
        registeredUsers.getUsersList().add(new Patient(cprResult, password, firstName, middleName, lastName, address, phone, email,validForVaccination));
        return registeredUsers;
      }
      else
      {
        throw new IllegalStateException("Not loaded from DBS");
      }
    }
  }

  public Patient readByCpr(String cpr) throws RemoteException, SQLException
  {
    Patient patient = null;
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE cpr=?");
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
        Address address = new Address(street,number,zipcode,null); //TODO: Get city from database (new method)
        String phone = resultSet.getString("phone");
        String email = resultSet.getString("email");
        boolean validForVaccination = resultSet.getBoolean("valid_for_vaccine");
        patient = new Patient(cprResult, password, firstName, middleName, lastName, address, phone, email,validForVaccination);
        return patient;
      }
      else
      {
        throw new IllegalStateException("No existing registered Patient with this CPR");
      }
    }

  }

  public String requestPassword(String cpr) throws RemoteException, SQLException
  {
    String password = null;
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT password FROM Users WHERE cpr=?");
      statement.setString(1, cpr);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next())
      {
        password = resultSet.getString("password");
        return password;
      }
      else
        throw new IllegalArgumentException("No existing registered Patient with this CPR");
    }
  }
}