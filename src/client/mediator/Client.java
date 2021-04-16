package client.mediator;

import server.model.*;
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

public class Client implements LocalClientModel, RemoteListener<User, ServerMessage> {
    private RemoteModel server;
    private PropertyChangeAction<User, ServerMessage> property;
    
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
    public User login(String cpr, String password) throws RemoteException {
        return server.login(cpr, password);
    }

    @Override
    public User register(User user) throws RemoteException {
        return server.register(user);
    }

    @Override
    public Appointment addAppointment(User user,Appointment appointment) throws RemoteException
    {
        return server.addAppointment(user, appointment);
    }

    @Override
    public AppointmentList getAppointmentsByUser(User user) throws RemoteException
    {
        return server.getAppointmentsByUser(user);
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
    public void propertyChange(ObserverEvent<User, ServerMessage> event) throws RemoteException
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
