package server.model;

import server.mediator.DatabaseManager;
import utility.observer.listener.GeneralListener;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;



public class ServerModelManager implements ServerModel
{
    //private DatabaseManager databaseManager;
    private UsersList usersList;
    private PropertyChangeAction<Patient, ServerMessage> property;

    public ServerModelManager(){
        property = new PropertyChangeProxy<>(this);
        //databaseManager = new DatabaseManager();
        usersList = new UsersList();
        usersList.addUser(new Patient("1234560000", "testpassword", "Test", null, "Person",
            new Address("TestStreet", "0", 8700, "Horsens"
        ), "12345678", "test@email.com"));
        usersList.addUser(new Nurse("1234561111", "testpassword", "Test", null, "Person",
            new Address("TestStreet", "0", 8700, "Horsens"
            ), "12345678", "test@email.com", "emp1"));
        usersList.addUser(new Administrator("1234562222", "testpassword", "Test", null, "Person",
            new Address("TestStreet", "0", 8700, "Horsens"
            ), "12345678", "test@email.com", "emp2"));
    }
   //mocked login
    @Override
    public Patient login(String cpr, String password)
    {
        for(Patient patient : usersList.getUsersList()){
            if(patient.getCpr().equals(cpr)){
                if(patient.getPassword().equals(password)){
                    System.out.println("Logged in as a: " + patient.getCpr());
                    return patient;
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
        if (usersList.contains(patient)) {
            return null;
        }
        usersList.addUser(patient);
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
