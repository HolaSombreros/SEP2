package server.mediator;

import server.model.*;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class RemoteModelManager implements RemoteModel, LocalListener<User, ServerMessage>
{

    private ServerModel serverModel;
    private PropertyChangeAction<User, ServerMessage> property;

    public RemoteModelManager(ServerModel serverModel) throws RemoteException, MalformedURLException
    {
        this.serverModel = serverModel;
        this.property = new PropertyChangeProxy<>(this, true);
        //this.serverModel.addListener(this);
        startRegistry();
        startServer();

    }
    private void startRegistry() throws RemoteException
    {
        try
        {
            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("Registry started...");
        }
        catch (java.rmi.server.ExportException e)
        {
            System.out.println("Registry did not start "+ e.getMessage());
        }
    }
    private void startServer() throws RemoteException, MalformedURLException
    {
        UnicastRemoteObject.exportObject(this, 0);
        Naming.rebind("AppointmentSystem", this);
        System.out.println("Server started...");
    }

    @Override
    public User login(String cpr, String password) throws RemoteException
    {
        return serverModel.login(cpr, password);
    }

    @Override
    public User register(User user) throws RemoteException
    {
        return serverModel.register(user);
    }

    @Override
    public Appointment addAppointment(User user, Appointment appointment) throws RemoteException
    {
        return serverModel.addAppointment(user, appointment);
    }

    @Override
    public AppointmentList getAppointmentsByUser(User user) throws RemoteException {
        return serverModel.getAppointmentsByUser(user);
    }

    @Override
    public void close()
    {
        property.close();
        try { UnicastRemoteObject.unexportObject(this, true); }
        catch (Exception e) {}
    }

    @Override
    public void propertyChange(ObserverEvent<User, ServerMessage> event)
    {
        property.firePropertyChange(event.getPropertyName(), event.getValue1(), event.getValue2());
    }

    @Override
    public boolean addListener(GeneralListener<User, ServerMessage> listener, String... propertyNames) throws RemoteException
    {
        return property.addListener(listener, propertyNames);
    }

    @Override
    public boolean removeListener(GeneralListener<User, ServerMessage> listener, String... propertyNames) throws RemoteException
    {
        return property.removeListener(listener, propertyNames);
    }
}
