package server.model;

import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.sql.*;

public class LoginModelManager implements LoginModel
{
    private UsersList usersLoggedIn;
    private PropertyChangeSupport property;

    public LoginModelManager(){
        property = new PropertyChangeSupport(this);
        usersLoggedIn = new UsersList();
    }

    @Override
    public void login(Patient user)
    {
        ServerMessage message;
        if(usersLoggedIn.getUsersList().contains(user)){
            message = new ServerMessage("error", null, null,"Username already exists");
            property.firePropertyChange("error", null, message);

        }
        else{
            usersLoggedIn.addUser(user);
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
    public UsersList getOnlineUsers()
    {
        return usersLoggedIn;
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
    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=jdbc", "postgres", "admin");

    }
}
