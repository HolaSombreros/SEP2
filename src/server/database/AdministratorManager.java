package server.database;

import server.model.domain.user.Administrator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministratorManager {
    public AdministratorManager() {
    
    }
    
    public void addAdministrator(Administrator administrator) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO administrator VALUES (?,?)");
            insertStatement.setString(1, administrator.getCpr());
            insertStatement.setString(2, administrator.getEmployeeId());
            insertStatement.executeUpdate();
        }
    }
    
    public void removeAdministrator(Administrator administrator) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM administrator WHERE cpr = ?");
            statement.setString(1, administrator.getCpr());
            statement.executeQuery();
        }
    }

    
    public boolean isAdmin(Administrator administrator) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM administrator WHERE cpr = ?");
            statement.setString(1, administrator.getCpr());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return true;
            else
                return false;
        }
    }
    
    public boolean isAdmin(String cpr) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM administrator WHERE cpr = ?");
            statement.setString(1, cpr);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return true;
            else
                return false;
        }
    }
}
