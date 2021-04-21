package server.database;

import server.model.Address;
import server.model.Patient;
import server.model.UserList;

import java.rmi.RemoteException;
import java.sql.*;

public class DatabaseManager
{


  private String url;
  private String username;
  private String password;
  private static String URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=sep2";
  private static String USERNAME = "sep2admin";
  private static String PASSWORD = "admin";


  public DatabaseManager(String url, String username, String password) {

    this.password = password;
    this.url = url;
    this.username = username;

  }

  public DatabaseManager(){
    this(URL,USERNAME,PASSWORD);
  }

  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(url,username,password);
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