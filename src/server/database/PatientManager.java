package server.database;

import server.model.Address;
import server.model.Patient;
import server.model.UserList;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientManager extends DatabaseManager{

    private AddressManager addressManager;
    private UserList registeredUsers;

    public PatientManager(){
        addressManager = new AddressManager();
        registeredUsers = new UserList();
    }

    public UserList getRegisteredUsers() {
        return registeredUsers;
    }

    public Patient getPatientByCpr(String cpr) throws SQLException {
        Patient patient = null;
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM patient WHERE cpr=?");
            statement.setString(1, cpr);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String cprResult = resultSet.getString("cpr");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String middleName = resultSet.getString("middleName");
                String street = resultSet.getString("street");
                String number = resultSet.getString("number");
                int zipcode = resultSet.getInt("zip_code");
                String city = addressManager.getCityByZipcode(zipcode);
                Address address = new Address(street,number,zipcode,city);
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                boolean validForVaccination = resultSet.getBoolean("valid_for_vaccine");
                patient = new Patient(cprResult, password, firstName, middleName, lastName, address, phone, email,validForVaccination);
                return patient;
            }
            else {
                throw new IllegalStateException("No existing registered Patient with this CPR");
            }
        }
    }

    public UserList getAllPatients() throws RemoteException, SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM patients");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String cprResult = resultSet.getString("cpr");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String middleName = resultSet.getString("middleName");
                String street = resultSet.getString("street");
                String number = resultSet.getString("number");
                int zipcode = resultSet.getInt("zip_code");
                Address address = new Address(street,number,zipcode,addressManager.getCityByZipcode(zipcode));
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                boolean validForVaccination = resultSet.getBoolean("valid_for_vaccine");
                registeredUsers.getUsersList().add(new Patient(cprResult, password, firstName, middleName, lastName, address, phone, email,validForVaccination));
                return registeredUsers;
            }
            else {
                throw new IllegalStateException("Not loaded from DBS");
            }
        }
    }

    public void addPatient(Patient patient) throws SQLException {
        try(Connection connection = getConnection()){
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO patient VALUES (?,?,?,?,?,?,?,?,?,?,?)");
            insertStatement.setString(1,patient.getCpr());
            insertStatement.setString(2,patient.getPassword());
            insertStatement.setString(3,patient.getFirstName());
            insertStatement.setString(4,patient.getMiddleName());
            insertStatement.setString(5,patient.getLastName());
            insertStatement.setString(6,patient.getPhone());
            insertStatement.setString(7,patient.getEmail());
            insertStatement.setString(8,patient.getAddress().getStreet());
            insertStatement.setString(9,patient.getAddress().getNumber());
            insertStatement.setInt(10,patient.getAddress().getZipcode());
            insertStatement.setBoolean(11,patient.isValidForVaccine());
            addressManager.addAddress(patient.getAddress());
            //execute the statement
            insertStatement.executeUpdate();
        }
    }



}
