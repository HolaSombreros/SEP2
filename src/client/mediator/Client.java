package client.mediator;

import client.model.Model;
import server.model.Patient;
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

public class Client implements LocalClientModel, RemoteListener<Patient, String> {
    private RemoteModel server;
    private Model model;
    private PropertyChangeAction<Patient, String> property;
    
    public Client(Model model) {
        this.model = model;
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
    public boolean login(String cpr, String password) throws RemoteException {
        return server.login(cpr, password);
    }
    
    @Override
    public boolean register(Patient patient) throws RemoteException {
        return server.register(patient);
    }
    
    @Override
    public void close() {
        try {
            UnicastRemoteObject.unexportObject(this, true);
        }
        catch (NoSuchObjectException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void propertyChange(ObserverEvent<Patient, String> event) throws RemoteException {
        property.firePropertyChange(event);
    }
    
    @Override
    public boolean addListener(GeneralListener<Patient, String> listener, String... propertyNames) {
        return property.addListener(listener, propertyNames);
    }
    
    @Override
    public boolean removeListener(GeneralListener<Patient, String> listener, String... propertyNames) {
        return property.removeListener(listener, propertyNames);
    }
}
