package server.database;


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

  private void setSchemaPrivileges() throws SQLException {
    try (Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SET SCHEMA 'sep2'");
      statement.executeUpdate();
      PreparedStatement privilegeStatement = connection.prepareStatement("GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA sep2 TO sep2admin");
      privilegeStatement.executeUpdate();
    }
  }
  public DatabaseManager(){
    this(URL,USERNAME,PASSWORD);
    try {
      setSchemaPrivileges();
    }
    catch (SQLException e){
      e.printStackTrace();
    }

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