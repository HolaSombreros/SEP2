package client.model;

import server.model.Address;
import server.model.Patient;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class ModelManager implements Model {
    private PropertyChangeSupport property;
    
    public ModelManager() {
        property = new PropertyChangeSupport(this);
    }
    
    @Override
    public void register(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email) {
        // TODO: establish communication with server, if server response is an error, throw an exception that the viewmodel catches and updates error label
        System.out.println("Registered patient (" + cpr + ")");
    }
    
    @Override
    public void login(String cpr, String password) {
        // TODO: establish communication with server, if server response is an error, throw an exception that the viewmodel catches and updates error label
        System.out.println(cpr + " logged in...");
    }
    
    @Override
    public void addListener(PropertyChangeListener listener) {
        property.addPropertyChangeListener(listener);
    }
    
    @Override
    public void removeListener(PropertyChangeListener listener) {
        property.removePropertyChangeListener(listener);
    }
}
