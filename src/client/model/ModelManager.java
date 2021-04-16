package client.model;

import client.mediator.Client;
import client.mediator.LocalClientModel;
import server.model.*;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.rmi.RemoteException;

public class ModelManager implements Model, LocalListener<User, ServerMessage> {

    private PropertyChangeAction<User, ServerMessage> property;
    private LocalClientModel client;
    
    public ModelManager() {
        property = new PropertyChangeProxy<>(this);
        client = new Client();
        client.addListener(this);
    }
    
    @Override
    public void register(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email) {
        try {
            Patient patient = new Patient(cpr, password, firstName, middleName, lastName, address, phone, email, false);
            User patient1 = client.register(patient);
            if(patient1 == null){
                throw new IllegalArgumentException("CPR already exists in the system");
            }
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public User login(String cpr, String password) {
        try {
            return client.login(cpr, password);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void propertyChange(ObserverEvent<User, ServerMessage> event)
    {
        property.firePropertyChange(event);
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
