package client.mediator;

import server.model.domain.appointment.*;
import server.model.domain.user.Patient;
import server.model.domain.user.User;
import server.model.domain.user.UserList;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.RemoteListener;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;

import server.mediator.RemoteModel;

public class Client implements LocalClientModel, RemoteListener<User, Appointment> {
    public static final String HOST = "localhost";
    private RemoteModel server;
    private PropertyChangeAction<User, Appointment> property;
    
    public Client(String host) throws RemoteException, NotBoundException, MalformedURLException {
        server = (RemoteModel) Naming.lookup("rmi://" + host + ":1099/AppointmentSystem");
        UnicastRemoteObject.exportObject(this, 0);
        server.addListener(this);
        property = new PropertyChangeProxy<>(this, true);
    }
    
    public Client() throws RemoteException, NotBoundException, MalformedURLException {
        this(HOST);
    }
    
    @Override
    public void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip, String city) {
        try {
            server.register(cpr, password, firstName, middleName, lastName, phone, email, street, number, zip, city);
        }
        catch (RemoteException e) {
            throw new IllegalStateException(getExceptionMessage(e), e);
        }
    }
    
    @Override
    public User login(String cpr, String password) {
        try {
            return server.login(cpr, password);
        }
        catch (RemoteException e) {
            throw new IllegalStateException(getExceptionMessage(e), e);
        }
    }

    @Override
    public UserList getUserList()
    {
        try
        {
            return server.getUserList();
        }
        catch (RemoteException e)
        {
            throw new IllegalStateException(getExceptionMessage(e),e);
        }
    }
    
    @Override
    public void addAppointment(LocalDate date, TimeInterval timeInterval, Type type, Patient patient) {
        try {
            Appointment appointment = server.addAppointment(date, timeInterval, type, patient);
            property.firePropertyChange("NewAppointment", appointment.getPatient(), appointment);
        }
        catch (RemoteException e) {
            throw new IllegalStateException(getExceptionMessage(e), e);
        }
    }
    
    @Override
    public AppointmentList getAppointmentsByUser(User patient) {
        try {
            return server.getAppointmentsByUser(patient);
        }
        catch (RemoteException e) {
            throw new IllegalStateException(getExceptionMessage(e), e);
        }
    }
    
    @Override
    public TimeIntervalList getAvailableTimeIntervals(LocalDate date) {
        try {
            return server.getAvailableTimeIntervals(date);
        }
        catch (RemoteException e) {
            throw new IllegalStateException(getExceptionMessage(e), e);
        }
    }
    
    @Override
    public Appointment getAppointmentById(int id) {
        try {
            return server.getAppointmentById(id);
        }
        catch (RemoteException e) {
            throw new IllegalStateException(getExceptionMessage(e), e);
        }
    }
    
    @Override
    public void logout(User user) {
        try {
            server.logout(user);
        }
        catch (RemoteException e) {
            throw new IllegalStateException(getExceptionMessage(e), e);
        }
    }

    @Override public UserList getPatients()
    {
        try
        {
            return server.getPatients();
        }
        catch (RemoteException e)
        {
            throw new IllegalStateException("Cannot unexport RMI object", e);
        }
    }

    @Override public UserList getNurses()
    {
        try
        {
            return server.getNurses();
        }
        catch (RemoteException e)
        {
            throw new IllegalStateException("Cannot unexport RMI object", e);
        }
    }

    @Override public UserList getAdministrators()
    {
        try
        {
            return server.getAdministrators();
        }
        catch (RemoteException e)
        {
            throw new IllegalStateException("Cannot unexport RMI object", e);
        }
    }

    @Override
    public void close() {
        try {
            UnicastRemoteObject.unexportObject(this, true);
            property.close();
        }
        catch (Exception e) {
            throw new IllegalStateException("Cannot unexport RMI object", e);
        }
    }
    
    @Override
    public synchronized void propertyChange(ObserverEvent<User, Appointment> event) throws RemoteException
    {
        property.firePropertyChange(event);
    }
    
    @Override
    public boolean addListener(GeneralListener<User, Appointment> listener, String... propertyNames)
    {
        return property.addListener(listener, propertyNames);
    }
    
    @Override
    public boolean removeListener(GeneralListener<User, Appointment> listener, String... propertyNames)
    {
        return property.removeListener(listener, propertyNames);
    }
    
    private String getExceptionMessage(Exception e) {
        String message = e.getMessage();
        if (message != null) {
            message = message.split(";")[0];
        }
        return message;
    }
}
