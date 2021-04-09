package server.model;

import server.mediator.DatabaseManager;

import java.beans.PropertyChangeSupport;

public class ServerModelManager implements ServerModel
{
    //private DatabaseManager databaseManager;
    private UsersList usersList;
    private PropertyChangeSupport property;

    public ServerModelManager(){
        property = new PropertyChangeSupport(this);
        //databaseManager = new DatabaseManager();
        usersList = new UsersList();
    }
   //mocked login
    @Override
    public void login(Patient user)
    {
        ServerMessage message;
        String successConnection = "Successfully connected to the server";
        if(!usersList.getUsersList().contains(user)){
            message = new ServerMessage("error", null, null,"Not registered in the system");
            property.firePropertyChange("error", null, message);
        }
        else{
            if(user instanceof Nurse){
                message = new ServerMessage("loginNurse", user.getCpr(),user.getPassword(),successConnection);
                property.firePropertyChange("loginNurse", null, message);
            }
            else if(user instanceof Administrator){
                message = new ServerMessage("loginAdministrator", user.getCpr(),user.getPassword(),successConnection);
                property.firePropertyChange("loginAdministrator", null, message);
            }
            else{
                message = new ServerMessage("login", user.getCpr(), user.getPassword(), successConnection);
                property.firePropertyChange("loginPatient",null, message);
            }
            usersList.addUser(user);
        }
        // TODO: LOGIN based on DBS
    }

    @Override
    public void sendServerMessage(ServerMessage message)
    {
        property.firePropertyChange("message", null, message);
    }

    @Override public void register(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email)
    {
        if (usersList.getUserByCpr(cpr)!=null)
          throw new IllegalArgumentException("The CPR is already registered into the system");
        usersList.addUser(new Patient(cpr, password, firstName, middleName, lastName, address, phone, email));
        System.out.println("Registered patient!");
    }

}
