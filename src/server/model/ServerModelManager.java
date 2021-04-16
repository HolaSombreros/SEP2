package server.model;

import utility.observer.listener.GeneralListener;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;



public class ServerModelManager implements ServerModel
{
    //private DatabaseManager databaseManager;
    private UserList userList;
    private PropertyChangeAction<Patient, ServerMessage> property;

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
    public Patient login(String cpr, String password)
    {
        for(User user : userList.getUsersList()){
            if(user.getCpr().equals(cpr)){
                if(user.getPassword().equals(password)){
                    System.out.println("Logged in as a: " + user.getCpr());
                    return (Patient) user;
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

    @Override public Patient register(Patient patient)
    {
        if (userList.contains(patient)) {
            return null;
        }
        userList.addUser(patient);
        System.out.println("Registered patient: " + patient.getCpr());
        return patient;
    }

    @Override
    public void close()
    {
        property.close();
    }

    @Override
    public boolean addListener(GeneralListener<Patient, ServerMessage> listener, String... propertyNames)
    {
        return property.addListener(listener, propertyNames);
    }

    @Override
    public boolean removeListener(GeneralListener<Patient, ServerMessage> listener, String... propertyNames)
    {
        return property.removeListener(listener, propertyNames);
    }
}
