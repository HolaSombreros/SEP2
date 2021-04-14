package server.mediator;

import server.model.Address;
import server.model.Patient;
import server.model.UsersList;

import java.rmi.RemoteException;
import java.sql.*;

public class DatabaseManager
{
    private UsersList registeredUsers;

    public DatabaseManager(){
        registeredUsers = new UsersList();
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=jdbc", "postgres", "admin");
    }
    public UsersList getRegisteredUsers(){
        return registeredUsers;
    }

    public UsersList loadFromDatabasePatients() throws RemoteException, SQLException
    {
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users");
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                String cprResult =resultSet.getString("cpr");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String middleName = resultSet.getString("middleName");
                Address address = resultSet.getObject("address", Address.class); //TODO: Address is going to be split up in the DBS :(
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                registeredUsers.getUsersList().add( new Patient(cprResult, password, firstName, middleName, lastName, address, phone, email));
                return registeredUsers;
            }
            else{
                throw new IllegalStateException("Not loaded from DBS");
            }
        }
    }
    public Patient readByCpr(String cpr) throws RemoteException, SQLException
    {
        Patient patient = null;
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE cpr=?");
            statement.setString(1,cpr);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                String cprResult =resultSet.getString("cpr");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String middleName = resultSet.getString("middleName");
                Address address = resultSet.getObject("address", Address.class); //TODO: Address is going to be split up in the DBS :(
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                patient = new Patient(cprResult, password, firstName, middleName, lastName, address, phone, email);
                return patient;
            }
            else{
                throw new IllegalStateException("No existing registered Patient with this CPR");
            }
        }

    }


}