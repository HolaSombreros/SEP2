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

public class ModelManager implements Model, LocalListener<Patient, ServerMessage> {
    private PropertyChangeAction<Patient, ServerMessage> property;
    private LocalClientModel client;
    
    public ModelManager() {
        property = new PropertyChangeProxy<>(this);
        client = new Client();
        client.addListener(this);
    }
    
    @Override
    public void register(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email) {
        try {
            Patient patient = new Patient(cpr, password, firstName, middleName, lastName, address, phone, email, true);
            client.register(patient);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public Patient login(String cpr, String password) {
        try {
            return client.login(cpr, password);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public void propertyChange(ObserverEvent<Patient, ServerMessage> event) {
        property.firePropertyChange(event);
    }
    
    @Override
    public boolean addListener(GeneralListener<Patient, ServerMessage> listener, String... propertyNames) {
        return property.addListener(listener, propertyNames);
    }
    
    @Override
    public boolean removeListener(GeneralListener<Patient, ServerMessage> listener, String... propertyNames) {
        return property.removeListener(listener, propertyNames);
    }
}
