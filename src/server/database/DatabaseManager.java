package server.database;

import server.model.Address;
import server.model.Patient;
import server.model.UserList;

import java.rmi.RemoteException;
import java.sql.*;

public class DatabaseManager {
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


  public String requestPassword(String cpr) throws RemoteException, SQLException {
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