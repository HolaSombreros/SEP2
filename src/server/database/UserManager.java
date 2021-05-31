package server.database;

import server.model.domain.user.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {
    private AddressManager addressManager;


    public UserManager(AddressManager addressManager) {
        this.addressManager = addressManager;
    }

    public void addPerson(User user) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO person VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
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
            insertStatement.setString(11, new NotAppliedStatus().toString());
            if (user instanceof Staff)
                insertStatement.setString(12, ((Staff) user).getEmployeeId());
            else
                insertStatement.setString(12, null);
            if (user instanceof Nurse)
                insertStatement.setString(13, Nurse.class.getSimpleName());
            else if (user instanceof Administrator)
                insertStatement.setString(13, Administrator.class.getSimpleName());
            else
                insertStatement.setString(13, Patient.class.getSimpleName());
            if(!isUser(user.getCpr()))
                insertStatement.executeUpdate();
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
                return new Patient(cprResult, password, firstName, middleName, lastName, address, phone, email, status);
            } else {
                throw new IllegalStateException("User not in database");
            }
        }
    }

    public Nurse getNurse(String cpr) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person JOIN city USING(zip_code) WHERE cpr = ? AND role = ?");
            statement.setString(1, cpr);
            statement.setString(2, Nurse.class.getSimpleName());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String employeeId = resultSet.getString("employee_id");
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
                return new Nurse(cpr, password, firstName, middleName, lastName, address, phone, email, employeeId);
            } else
                throw new IllegalArgumentException("No existing registered nurse with this CPR");
        }
    }

    public Administrator getAdministrator(String cpr) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person JOIN city USING(zip_code)WHERE cpr = ? AND role = ?");
            statement.setString(1, cpr);
            statement.setString(2, Administrator.class.getSimpleName());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String employeeId = resultSet.getString("employee_id");
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
                return new Administrator(cpr, password, firstName, middleName, lastName, address, phone, email, employeeId);
            } else
                throw new IllegalArgumentException("No existing registered administrator with this CPR");
        }
    }

    public void addNurse(Nurse nurse) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE person set role = ?, employee_id = ? WHERE cpr = ?");
            statement.setString(1, Nurse.class.getSimpleName());
            statement.setString(2, nurse.getEmployeeId());
            statement.setString(3, nurse.getCpr());
            statement.executeUpdate();
        }
    }

    public void addAdministrator(Administrator administrator) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE person set role = ?, employee_id = ? WHERE cpr = ?");
            statement.setString(1, Administrator.class.getSimpleName());
            statement.setString(2, administrator.getEmployeeId());
            statement.setString(3, administrator.getCpr());
            statement.executeUpdate();
        }
    }

    public void updateUserInformation(User user, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE person Set password = ?, firstname = ?,middlename = ?, lastname = ?, phone = ?, email = ?, street = ?, number = ?, zip_code = ? WHERE cpr = ?");
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
            if (!addressManager.isAddress(street, number, zip))
                addressManager.addAddress(new Address(street, number, zip, addressManager.getCity(zip)));
            statement.executeUpdate();
        }
    }

    public UserList getAllUsers() throws SQLException {
        UserList users = new UserList();
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person JOIN city USING(zip_code);");
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
                String employee_id = rs.getString("employee_id");
                String role = rs.getString("role");
                VaccineStatus status = null;
                if (vaccine != null)
                    switch (vaccine) {
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
                if (role.equals(Nurse.class.getSimpleName())) {
                    users.add(new Nurse(cpr, password, firstName, middleName, lastName, address, phone, email, employee_id));
                    users.add(new Patient(cpr, password, firstName, middleName, lastName, address, phone, email, status));
                } else if (role.equals(Administrator.class.getSimpleName())) {
                    users.add(new Administrator(cpr, password, firstName, middleName, lastName, address, phone, email, employee_id));
                    users.add(new Patient(cpr, password, firstName, middleName, lastName, address, phone, email, status));
                } else {
                    users.add(new Patient(cpr, password, firstName, middleName, lastName, address, phone, email, status));
                }
            }
            return users;
        }
    }

    public boolean isUser(String cpr) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE cpr = ?");
            statement.setString(1, cpr);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }

    public void updateAccess(Staff user) throws SQLException{
        try (Connection connection = DatabaseManager.getInstance().getConnection()){
            PreparedStatement statement = connection.prepareStatement("UPDATE person SET role = ? WHERE cpr = ?");
            statement.setString(1,Patient.class.getSimpleName());
            statement.setString(2, user.getCpr());
            statement.executeUpdate();
        }
    }

    public boolean isNurse(String cpr) throws SQLException{
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE cpr = ? AND role = ? ");
            statement.setString(1, cpr);
            statement.setString(2,Nurse.class.getSimpleName());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }

    public boolean isAdmin(String cpr) throws SQLException{
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE cpr = ? AND role = ? ");
            statement.setString(1, cpr);
            statement.setString(2,Administrator.class.getSimpleName());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }

    public void setVaccineStatus(String cpr, VaccineStatus status) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE person set vaccine_status = ? WHERE cpr = ?");
            statement.setString(1, status.toString());
            statement.setString(2, cpr);
            statement.executeUpdate();
        }
    }

}