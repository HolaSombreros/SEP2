package server.model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class LoginModelManager implements LoginModel
{
    private UsersList users;
    private PropertyChangeSupport property;

    public LoginModelManager(){
        property = new PropertyChangeSupport(this);
        users = new UsersList();
    }

    @Override
    public void login(Patient user)
    {
        ServerMessage message;
        if(users.getUsersList().contains(user)){
            message = new ServerMessage("error", null, null,"Username already exists");
            property.firePropertyChange("error", null, message);
        }
        else{
            users.addUser(user);
            message = new ServerMessage("login", user.getCpr(), user.getPassword(), "Successfully connected to the server");
            property.firePropertyChange("login",null, message);
        }
    }


    @Override
    public void sendServerMessage(ServerMessage message)
    {
        property.firePropertyChange("message", null, message);
    }

    @Override
    public UsersList getOnlineUsers()
    {
        return users;
    }
}
