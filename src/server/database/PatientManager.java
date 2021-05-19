package server.database;

import server.model.domain.user.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientManager {
    public PatientManager() {
    }
    
    public void addPatient(User patient) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO patient VALUES (?,?)");
            insertStatement.setString(1, patient.getCpr());
            insertStatement.setString(2, new NotAppliedStatus().toString());
            insertStatement.executeUpdate();
        }
    }
    
    public VaccineStatus getVaccineStatus(String cpr) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM patient WHERE cpr=?");
            statement.setString(1, cpr);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                String vaccineStatus = resultSet.getString("vaccine_status");
                VaccineStatus status = null;
                switch (vaccineStatus) {
                    case "Not Applied":
                        status = new NotAppliedStatus();
                        break;
                    case "Approved":
                        status = new ApprovedStatus();
                        break;
                    case "Not Approved":
                        status = new NotApprovedStatus();
                        break;
                    case "Pending":
                        status = new PendingStatus();
                        break;
                }
                return status;
            }
            throw new IllegalStateException("User not in database");
        }
    }
    
    public void setVaccineStatus(String cpr, VaccineStatus status) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE patient set vaccine_status = ? WHERE cpr = ?");
            statement.setString(1, status.toString());
            statement.setString(2, cpr);
            statement.executeUpdate();
        }
    }
    
    public boolean isPatient(String cpr) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM patient WHERE cpr = ?");
            statement.setString(1, cpr);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return true;
            else
                return false;
        }
    }

    public void removePatient(Patient patient) throws SQLException {
        if(patient != null) {
            try (Connection connection = DatabaseManager.getInstance().getConnection()) {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM patient WHERE cpr = ?;");
                statement.setString(1, patient.getCpr());
                statement.executeUpdate();
            }
        }
        else
            throw new IllegalArgumentException("You cannot remove a null patient");
    }
}
