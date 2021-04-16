package server.model;

import utility.observer.listener.GeneralListener;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

public class ServerModelManager implements ServerModel
{
    //private DatabaseManager databaseManager;
    private UserList userList;
    private PropertyChangeAction<User, ServerMessage> property;

    public ServerModelManager(){
        property = new PropertyChangeProxy<>(this);
        //databaseManager = new DatabaseManager();
        userList = new UserList();
        userList.addUser(new Patient("1204560000", "testpassword", "Test", null, "Person",
            new Address("TestStreet", "0", 8700, "Horsens"
        ), "12345678", "test@email.com", false));
        userList.addUser(new Nurse("1205561111", "testpassword", "Test", null, "Person",
            new Address("TestStreet", "0", 8700, "Horsens"
            ), "12345678", "test@email.com", "emp1"));
        userList.addUser(new Administrator("1211562222", "testpassword", "Test", null, "Person",
            new Address("TestStreet", "0", 8700, "Horsens"
            ), "12345678", "test@email.com", "emp2"));
    }
   //mocked login
    @Override
    public User login(String cpr, String password)
    {
        for(User user : userList.getUsersList()){
            if(user.getCpr().equals(cpr)){
                if(user.getPassword().equals(password)){
                    System.out.println("Logged in as a: " + user.getCpr());
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public void sendServerMessage(ServerMessage message)
    {
        property.firePropertyChange("message", null, message);
    }

    @Override public User register(User user)
    {
        if (userList.contains(user)) {
            return null;
        }
        userList.addUser(user);
        System.out.println("Registered patient: " + user.getCpr());
        return user;
    }

    @Override
    public void close()
    {
        property.close();
    }

    @Override
    public Appointment addAppointment(Appointment appointment)
    {
        return null;
    }

    @Override
    public boolean addListener(GeneralListener<User, ServerMessage> listener, String... propertyNames)
    {
        return property.addListener(listener, propertyNames);
    }

    @Override
    public boolean removeListener(GeneralListener<User, ServerMessage> listener, String... propertyNames)
    {
        return property.removeListener(listener, propertyNames);
    }
}
