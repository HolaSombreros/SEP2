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

public class RemoteModelManager implements RemoteModel, LocalListener<Patient, ServerMessage>
{

    private ServerModel serverModel;
    private PropertyChangeAction<Patient, ServerMessage> property;

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
    public Patient login(String cpr, String password) throws RemoteException
    {
        return serverModel.login(cpr, password);
    }

    @Override
    public Patient register(Patient patient) throws RemoteException
    {
        return serverModel.register(patient);
    }

    @Override
    public void close()
    {
        property.close();
        try { UnicastRemoteObject.unexportObject(this, true); }
        catch (Exception e) {}
    }

    @Override
    public boolean addListener(GeneralListener<Patient, ServerMessage> listener, String... propertyNames) throws RemoteException
    {
        return property.addListener(listener, propertyNames);
    }

    @Override
    public boolean removeListener(GeneralListener<Patient, ServerMessage> listener, String... propertyNames) throws RemoteException
    {
        return property.removeListener(listener, propertyNames);
    }

    @Override
    public void propertyChange(ObserverEvent<Patient, ServerMessage> event)
    {
        property.firePropertyChange(event.getPropertyName(), event.getValue1(), event.getValue2());
    }
}
