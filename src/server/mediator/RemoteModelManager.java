package server.mediator;

import server.model.*;
import server.model.domain.*;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;

public class RemoteModelManager implements RemoteModel, LocalListener<User, Appointment> {
    
    private ServerModel serverModel;
    private PropertyChangeAction<User, Appointment> property;
    
    public RemoteModelManager(ServerModel serverModel) throws RemoteException, MalformedURLException {
        this.serverModel = serverModel;
        property = new PropertyChangeProxy<>(this, true);
        serverModel.addListener(this);
        startRegistry();
        startServer();
    }
    
    @Override
    public User login(String cpr, String password) throws RemoteException {
        return serverModel.login(cpr, password);
    }
    
    @Override
    public void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip, String city)
        throws RemoteException {
        serverModel.register(cpr, password, firstName, middleName, lastName, phone, email, street, number, zip, city);
    }

    @Override
    public UserList getUserList()
    {
        return serverModel.getUserList();
    }
    
    @Override
    public Appointment addAppointment(LocalDate date, TimeInterval timeInterval, Type type, Patient patient) throws RemoteException {
        return serverModel.addAppointment(date, timeInterval, type, patient);
    }
    
    @Override
    public AppointmentList getAppointmentsByUser(User patient) throws RemoteException {
        return serverModel.getAppointmentsByUser(patient);
    }
    
    @Override
    public TimeIntervalList getAvailableTimeIntervals(LocalDate date) throws RemoteException {
        return serverModel.getAvailableTimeIntervals(date);
    }
    
    @Override
    public Appointment getAppointmentById(int id) throws RemoteException {
        return serverModel.getAppointmentById(id);
    }
    
    @Override
    public void logout(User user) throws RemoteException {
        serverModel.logout(user);
    }
    
    private void startRegistry() throws RemoteException {
        try {
            LocateRegistry.createRegistry(1099);
            System.out.println("Registry started...");
        }
        catch (ExportException e) {
            System.out.println("Registry already started - " + e.getMessage());
        }
    }
    
    private void startServer() throws RemoteException, MalformedURLException {
        UnicastRemoteObject.exportObject(this, 0);
        Naming.rebind("AppointmentSystem", this);
        System.out.println("Server started...");
    }
    
    public void close() {
        property.close();
        try {
            UnicastRemoteObject.unexportObject(this, true);
        }
        catch (Exception e) {
            // Do nothing
        }
    }
    
    @Override
    public void propertyChange(ObserverEvent<User, Appointment> event) {
        property.firePropertyChange(event.getPropertyName(), event.getValue1(), event.getValue2());
    }
    
    @Override
    public boolean addListener(GeneralListener<User, Appointment> listener, String... propertyNames) throws RemoteException {
        return property.addListener(listener, propertyNames);
    }
    
    @Override
    public boolean removeListener(GeneralListener<User, Appointment> listener, String... propertyNames) throws RemoteException {
        return property.removeListener(listener, propertyNames);
    }
}
