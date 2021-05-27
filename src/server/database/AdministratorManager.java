package server.database;

import server.model.domain.user.Administrator;
import server.model.domain.user.Nurse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministratorManager {
    public AdministratorManager() {
    }

    public void addAdministrator(Administrator administrator) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            if (!isAdmin(administrator)) {
                PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO administrator VALUES (?,?,?)");
                insertStatement.setString(1, administrator.getCpr());
                insertStatement.setString(2, administrator.getEmployeeId());
                insertStatement.setBoolean(3, true);
                insertStatement.executeUpdate();
            } else updateAccess(administrator, true);
        }
    }

    public void removeAdministrator(Administrator administrator) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM administrator WHERE cpr = ?");
            statement.setString(1, administrator.getCpr());
            statement.executeUpdate();
        }
    }

    public boolean isAdmin(Administrator administrator) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM administrator WHERE cpr = ?");
            statement.setString(1, administrator.getCpr());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }

    public boolean hasAccess(Administrator administrator) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT has_access FROM administrator WHERE cpr = ?;");
            statement.setString(1, administrator.getCpr());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.first();
        }
    }

    public void updateAccess(Administrator administrator, boolean access) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE administrator SET has_access = ? WHERE cpr = ?;");
            statement.setBoolean(1, access);
            statement.setString(2, administrator.getCpr());
            statement.executeUpdate();

        }
    }
}
