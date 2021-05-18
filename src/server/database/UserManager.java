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

    public UserManager(AddressManager addressManager, PatientManager patientManager, NurseManager nurseManager, AdministratorManager administratorManager) {
        this.addressManager = addressManager;
        this.patientManager = patientManager;
        this.nurseManager = nurseManager;
        this.administratorManager = administratorManager;
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
            patientManager.addPatient((Patient)user);
        }
    }

    public Patient getPatient(String cpr) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person JOIN city USING(zip_code) WHERE cpr=?");
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
                String city = resultSet.getString("city");
                Address address = new Address(street, number, zipcode, city);
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                return new Patient(cprResult, password, firstName, middleName, lastName, address, phone, email, patientManager.getVaccineStatus(cpr));
            }
            else {
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

    public void addNurse(Nurse nurse) throws SQLException{
        if(!isUser(nurse.getCpr()))
            addPerson(nurse);
        nurseManager.addNurse(nurse);
    }

    public void addAdministrator(Administrator administrator) throws SQLException{
        if(!isUser(administrator.getCpr()))
            addPerson(administrator);
        administratorManager.addAdministrator(administrator);
    }

    public void updateUserInformation(User user, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE person set password = ?, firstname = ?, middlename = ?, lastname = ?, phone = ?, email = ?, street = ?, number = ?, zip_code = ? where cpr = ?");
            statement.setString(1, password);
            statement.setString(2, firstName);
            statement.setString(3, middleName);
            statement.setString(4, lastName);
            statement.setString(5, phone);
            statement.setString(6, email);
            statement.setString(7, street);
            statement.setString(8, number);
            statement.setInt(9, zip);
            statement.setString(10, user.getCpr());
            statement.executeUpdate();
            //TODO: zipcode and city
        }
    }
    public void removeUser(User user) throws SQLException {
        if(user != null) {
            try (Connection connection = DatabaseManager.getInstance().getConnection()) {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM person WHERE cpr = ?;");
                statement.setString(1, user.getCpr());
                statement.executeUpdate();
            }
        }
        else
            throw new IllegalArgumentException("You cannot remove a null user");
    }
    
    public UserList getAllUsers() throws SQLException {
        UserList users = new UserList();
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
           // PreparedStatement statement = connection.prepareStatement("SELECT person.*, city.city, patient.vaccine_status, nurse.employee_id AS nurse_id, administrator.employee_id AS admin_id FROM person LEFT JOIN city USING (zip_code) LEFT JOIN patient USING(cpr) LEFT JOIN nurse ON person.cpr = nurse.cpr LEFT JOIN administrator ON person.cpr = administrator.cpr;");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String cpr = rs.getString("cpr");
                String password = rs.getString("password");
                String firstName = rs.getString("firstName");
                String middleName = rs.getString("middleName");
                String lastName = rs.getString("lastName");
                String street = rs.getString("street");
                String number = rs.getString("number");
                int zipcode = rs.getInt("zip_code");
                String city = rs.getString("city");
                Address address = new Address(street, number, zipcode, city);
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String vaccine = rs.getString("vaccine_status");
                String nurse_id = rs.getString("nurse_id");
                String admin_id = rs.getString("admin_id");
                VaccineStatus status = null;
                switch (vaccine){
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
                if (nurse_id != null) {
                    users.add(new Nurse(cpr, password, firstName, middleName, lastName, address, phone, email, nurse_id));
                }
                else if (admin_id != null) {
                    users.add(new Administrator(cpr, password, firstName, middleName, lastName, address, phone, email, admin_id));
                }
                else {
                    users.add(new Patient(cpr, password, firstName, middleName, lastName, address, phone, email, status));
                }
            }
            return users;
        }
    }
    
    public UserList getAllPatients() throws SQLException {
        UserList registeredUsers = new UserList();
        try (Connection connection = DatabaseManager.getInstance().getConnection())
        {
            //PreparedStatement statement = connection.prepareStatement("SELECT * FROM person JOIN city USING(zip_code) JOIN patient USING(cpr)");
            PreparedStatement statement = connection.prepareStatement("Select * from patients");
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
                String city = resultSet.getString("city");
                Address address = new Address(street, number, zipcode, city);
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String vaccine = resultSet.getString("vaccine_status");
                VaccineStatus status = null;
                switch (vaccine){
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
                if(patientManager.isPatient(cprResult))
                    registeredUsers.getUsers().add(new Patient(cprResult, password, firstName, middleName, lastName, address, phone, email, status));
            }
            return registeredUsers;
        }
    }

    public UserList getAllNurses() throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            //PreparedStatement statement = connection.prepareStatement("SELECT * FROM nurse");
            PreparedStatement statement = connection.prepareStatement("Select * from nurses");
            ResultSet resultSet = statement.executeQuery();
            UserList result = new UserList();
            while (resultSet.next()) {
                String cpr = resultSet.getString("cpr");
                Nurse user = getNurse(cpr);
                result.add(user);
            }
            return result;
        }
    }

    public UserList getAllAdministrators() throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            //PreparedStatement statement = connection.prepareStatement("SELECT * FROM administrator");
            PreparedStatement statement = connection.prepareStatement("Select * from administrator");
            ResultSet resultSet = statement.executeQuery();
            UserList result = new UserList();
            while (resultSet.next()) {
                String cpr = resultSet.getString("cpr");
                Administrator user = getAdministrator(cpr);
                result.add(user);
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
}
