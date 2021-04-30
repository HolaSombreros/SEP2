package server.database;

import server.model.domain.user.Administrator;
import server.model.domain.user.NotApprovedStatus;
import server.model.domain.user.Patient;
import server.model.domain.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdministratorManager extends DatabaseManager {
    private PatientManager patientManager;


    public AdministratorManager() {
        patientManager = new PatientManager();
    }

    public void addAdministrator(Administrator administrator) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO administrator VALUES (?,?)");
            insertStatement.setString(1, administrator.getCpr());
            insertStatement.setString(2, administrator.getEmployeeId());
            patientManager.addPatient(new Patient(administrator.getCpr(), administrator.getPassword(), administrator.getFirstName(), administrator.getMiddleName(), administrator.getLastName(), administrator.getAddress(), administrator.getPhone(), administrator.getEmail(), new NotApprovedStatus()));
            insertStatement.executeUpdate();
        }
    }

    public Administrator getAdministratorByCpr(String cpr) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM administrator WHERE cpr=?");
            statement.setString(1, cpr);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String employeeId = resultSet.getString("employee_id");
                User patient = patientManager.getPatientByCpr(cpr);
                return new Administrator(patient.getCpr(), patient.getPassword(), patient.getFirstName(), patient.getMiddleName(), patient.getLastName(), patient.getAddress(), patient.getPhone(),
                        patient.getEmail(), employeeId);
            } else
                throw new IllegalArgumentException("No existing registered administrator with this CPR");
        }
    }

    public ArrayList<Administrator> getAllAdministrators() throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM administrator");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Administrator> result = new ArrayList<>();
            while (resultSet.next()) {
                String cpr = resultSet.getString("cpr");
                String employeeId = resultSet.getString("employee_id");
                User patient = patientManager.getPatientByCpr(cpr);
                result.add(new Administrator(cpr, patient.getPassword(), patient.getFirstName(), patient.getMiddleName(), patient.getLastName(), patient.getAddress(), patient.getPhone(),
                        patient.getEmail(), employeeId));
            }
            return result;
        }
    }

    public void removeAdministrator(Administrator administrator) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM administrator WHERE cpr = ?");
            statement.setString(1, administrator.getCpr());
            statement.executeQuery();
        }
    }

    public boolean isAdmin(Administrator administrator) throws SQLException {
        try (Connection connection = getConnection()) {
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
        try (Connection connection = getConnection()) {
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
