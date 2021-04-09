package server.model;

import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.sql.*;

public class ServerModelManager implements ServerModel
{
    private UsersList usersRegistered;
    private PropertyChangeSupport property;

    public ServerModelManager(){
        property = new PropertyChangeSupport(this);
        usersRegistered = new UsersList();
    }

    @Override
    public void login(Patient user)
    {
        ServerMessage message;
        if(!usersRegistered.getUsersList().contains(user)){
            message = new ServerMessage("error", null, null,"Not registered in the system");
            property.firePropertyChange("error", null, message);

        }
        else{
            usersRegistered.addUser(user);
            message = new ServerMessage("login", user.getCpr(), user.getPassword(), "Successfully connected to the server");
            property.firePropertyChange("login",null, message);
        }
        // TODO: LOGIN based on DBS
    }


    @Override
    public void sendServerMessage(ServerMessage message)
    {
        property.firePropertyChange("message", null, message);
    }

    @Override
    public UsersList getRegisteredUsers()
    {
        return usersRegistered;
    }

    @Override
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

    @Override
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
                usersRegistered.getUsersList().add( new Patient(cprResult, password, firstName, middleName, lastName, address, phone, email));
                return usersRegistered;
            }
            else{
                throw new IllegalStateException("Not loaded from DBS");
            }
        }
    }

    @Override public void register(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email)
    {
        //TODO check for CRP
        usersRegistered.addUser(new Patient(cpr, password, firstName, middleName, lastName, address, phone, email));
        System.out.println("Registered patient!");
    }

    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=jdbc", "postgres", "admin");

    }
}
