package client.mediator;

import server.model.Patient;
import server.model.ServerMessage;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.RemoteListener;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import server.mediator.RemoteModel;

public class Client implements LocalClientModel, RemoteListener<Patient, ServerMessage> {
    private RemoteModel server;
    private PropertyChangeAction<Patient, ServerMessage> property;
    
    public Client() {
        try {
            server = (RemoteModel) Naming.lookup("rmi://localhost:1099/AppointmentSystem");
            UnicastRemoteObject.exportObject(this, 0);
            server.addListener(this);
            property = new PropertyChangeProxy<>(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public Patient login(String cpr, String password) throws RemoteException {
        return server.login(cpr, password);
    }
    
    @Override
    public Patient register(Patient patient) throws RemoteException {
        return server.register(patient);
    }
    
    @Override
    public void close() {
        try {
            property.close();
            UnicastRemoteObject.unexportObject(this, true);
        }
        catch (NoSuchObjectException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void propertyChange(ObserverEvent<Patient, ServerMessage> event) throws RemoteException {
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
