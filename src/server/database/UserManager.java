package server.database;

import server.model.domain.user.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserManager {

    private AddressManager addressManager;
    private PatientManager patientManager;
    private NurseManager nurseManager;
    private AdministratorManager administratorManager;

    public UserManager() {
        addressManager = new AddressManager();
        patientManager = new PatientManager();
        nurseManager = new NurseManager();
        administratorManager = new AdministratorManager();
    }

    public void addPerson(User user) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO person VALUES (?,?,?,?,?,?,?,?,?,?)");
            insertStatement.setString(1, user.getCpr());
            insertStatement.setString(2, user.getPassword());
            insertStatement.setString(3, user.getFirstName());
            insertStatement.setString(4, user.getMiddleName());
            insertStatement.setString(5, user.getLastName());
            insertStatement.setString(6, user.getPhone());
            insertStatement.setString(7, user.getEmail());
            insertStatement.setString(8, user.getAddress().getStreet());
            insertStatement.setString(9, user.getAddress().getNumber());
            insertStatement.setInt(10, user.getAddress().getZipcode());
            if (!addressManager.isAddress(user.getAddress().getStreet(), user.getAddress().getNumber(), user.getAddress().getZipcode()))
                addressManager.addAddress(user.getAddress());
            insertStatement.executeUpdate();
            patientManager.addPatient(user);
        }
    }



    public User getUser(String cpr) throws SQLException {
        User user = null;
        if (nurseManager.isNurse(cpr))
            user = getNurse(cpr);
        else if (administratorManager.isAdmin(cpr))
            user = getAdministrator(cpr);
        else
            user = getPatient(cpr);
        return user;
    }


    public User getPatient(String cpr) throws SQLException {
        User patient = null;
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE cpr=?");
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
                Address address = new Address(street, number, zipcode, city);
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                patient = new Patient(cprResult, password, firstName, middleName, lastName, address, phone, email, patientManager.getVaccineStatus(cpr));
                return patient;
            } else {
                throw new IllegalStateException("User not in database");
            }
        }
    }

    public Nurse getNurse(String cpr) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM nurse WHERE cpr=?");
            statement.setString(1, cpr);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String employeeId = resultSet.getString("employee_id");
                User user = getPatient(cpr);
                return new Nurse(user.getCpr(), user.getPassword(), user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getAddress(), user.getPhone(),
                        user.getEmail(), employeeId);
            } else
                throw new IllegalArgumentException("No existing registered nurse with this CPR");
        }
    }

    public Administrator getAdministrator(String cpr) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM administrator WHERE cpr=?");
            statement.setString(1, cpr);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String employeeId = resultSet.getString("employee_id");
                User user = getPatient(cpr);
                return new Administrator(user.getCpr(), user.getPassword(), user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getAddress(), user.getPhone(),
                        user.getEmail(), employeeId);
            } else
                throw new IllegalArgumentException("No existing registered administrator with this CPR");
        }
    }


    public void addNurse(User user) throws SQLException{
        if(isUser(user.getCpr()))
            nurseManager.addNurse((Nurse)user);
        else{
            addPerson(user);
            nurseManager.addNurse((Nurse)user);
        }
    }

    public void addAdministrtor(User user) throws SQLException{
        if(isUser(user.getCpr()))
            administratorManager.addAdministrator((Administrator) user);
        else{
            addPerson(user);
            administratorManager.addAdministrator((Administrator) user);
        }
    }

    public String getPassword(String cpr) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement selectStatement = connection.prepareStatement("SELECT password FROM person WHERE cpr = ?");
            selectStatement.setString(1, cpr);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                String password = resultSet.getString("password");
                return password;
            } else {
                throw new IllegalStateException("User not in database");
            }
        }
    }

    public void updateEmail(User user) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE person set email = ? where cpr = ?");
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getCpr());
            statement.executeUpdate();
        }
    }

    public void updatePhone(User user) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE person set phone = ? where cpr = ?");
            statement.setString(1, user.getPhone());
            statement.setString(2, user.getCpr());
            statement.executeUpdate();
        }
    }

    public void updateAddress(User user) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE person set street = ?, number = ?, zip_code = ? where cpr = ?");
            statement.setString(1, user.getAddress().getStreet());
            statement.setString(2, user.getAddress().getNumber());
            statement.setInt(3, user.getAddress().getZipcode());
            statement.setString(4, user.getCpr());
            statement.executeUpdate();
        }
    }

    public void updateVaccineStatus(Patient patient) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE person set valid_for_vaccine = ? where cpr = ?");
            statement.setString(1, patient.getVaccineStatus().toString());
            statement.setString(2, patient.getCpr());
            statement.executeUpdate();
        }
    }

    public void updatePassword(User user) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE person set password = ?  where cpr = ?");
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getCpr());
            statement.executeUpdate();
        }
    }

    public void updateName(User user) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE person set firstname = ?, middlename = ?, lastname = ? where cpr = ?");
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getMiddleName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getCpr());
            statement.executeUpdate();
        }
    }

    public UserList getAllPatients() throws SQLException {
        UserList registeredUsers = new UserList();
        try (Connection connection = DatabaseManager.getInstance().getConnection())
        {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person JOIN patient USING(cpr)");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                String cprResult = resultSet.getString("cpr");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String middleName = resultSet.getString("middleName");
                String street = resultSet.getString("street");
                String number = resultSet.getString("number");
                int zipcode = resultSet.getInt("zip_code");
                Address address = new Address(street, number, zipcode, addressManager.getCityByZipcode(zipcode));
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String vaccine = resultSet.getString("vaccine_status");
                VaccineStatus status = null;
                switch (vaccine){
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
                if(patientManager.isPatient(cprResult))
                    registeredUsers.getUsersList().add(new Patient(cprResult, password, firstName, middleName, lastName, address, phone, email, status));
            }
            return registeredUsers;
        }
    }

    public UserList getAllNurses() throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM nurse");
            ResultSet resultSet = statement.executeQuery();
            UserList result = new UserList();
            while (resultSet.next()) {
                String cpr = resultSet.getString("cpr");
                Nurse user = getNurse(cpr);
                result.addUser(user);
            }
            return result;
        }
    }

    public UserList getAllAdministrators() throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM administrator");
            ResultSet resultSet = statement.executeQuery();
            UserList result = new UserList();
            while (resultSet.next()) {
                String cpr = resultSet.getString("cpr");
                Administrator user = getAdministrator(cpr);
                result.addUser(user);
            }
            return result;
        }
    }

    public boolean isUser(String cpr) throws SQLException{
        try(Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE cpr = ?");
            statement.setString(1,cpr);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
                return true;
            else
                return false;
        }
    }

    //TODO: add methods for remove

}
