package server.mediator;

import server.model.domain.UserList;


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


}