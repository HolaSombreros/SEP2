package server.model;

import java.beans.PropertyChangeSupport;

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
}
