package server.database;

import server.model.domain.user.Nurse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NurseManager {
    public NurseManager() {}
    
    public void addNurse(Nurse nurse) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO nurse VALUES (?, ?, ?)");
            insertStatement.setString(1, nurse.getCpr());
            insertStatement.setString(2, nurse.getEmployeeId());
            insertStatement.setBoolean(3, true);
            insertStatement.executeUpdate();
        }
    }
    public void removeNurse(Nurse nurse) throws SQLException {
        if(nurse != null)
        {
            try (Connection connection = DatabaseManager.getInstance().getConnection())
            {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM nurse WHERE cpr = ?");
                statement.setString(1, nurse.getCpr());
                statement.executeUpdate();
            }
        }
        else throw new IllegalArgumentException("You cannot remove a non existing nurse");
    }
    
    public boolean isNurse(Nurse nurse) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM nurse WHERE cpr = ?");
            statement.setString(1, nurse.getCpr());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }
    public boolean hasAccess(Nurse nurse) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT has_access FROM nurse WHERE cpr = ?;");
            statement.setString(1, nurse.getCpr());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.first();
        }
    }
    public void updateAccess(Nurse nurse) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE has_access FROM nurse WHERE cpr = ?;");
            statement.setString(1, nurse.getCpr());
            statement.executeUpdate();
        }
    }
    
}
