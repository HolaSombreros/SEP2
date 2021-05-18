package server.mediator;

import server.model.*;
import server.model.domain.appointment.*;
import server.model.domain.faq.Category;
import server.model.domain.faq.FAQList;
import server.model.domain.user.*;
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
    public AppointmentList filterAppointmentsByNameAndCpr(String criteria) throws RemoteException
    {
        return serverModel.filterAppointmentsByNameAndCpr(criteria);
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
    public void cancelAppointment(int id) throws RemoteException
    {
        serverModel.cancelAppointment(id);
    }

    @Override
    public void rescheduleAppointment(int id, LocalDate date, TimeInterval timeInterval) throws RemoteException
    {
        serverModel.rescheduleAppointment(id, date, timeInterval);
    }

    @Override
    public void logout(User user) throws RemoteException {
        serverModel.logout(user);
    }

    @Override
    public UserList getUsersByCprAndName(String criteria) throws RemoteException
    {
        return serverModel.getUsersByCprAndName(criteria);
    }

    @Override public UserList getPatients() throws RemoteException
    {
       return serverModel.getPatientList();
    }

    @Override public UserList getNurses() throws RemoteException
    {
        return serverModel.getNurseList();
    }

    @Override public UserList getAdministrators() throws RemoteException
    {
        return serverModel.getAdministratorList();
    }

    @Override
    public User editUserInformation(User user, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip) throws RemoteException
    {
        return serverModel.editUserInformation(user, password, firstName, middleName, lastName, phone, email, street, number, zip);
    }

    @Override
    public VaccineStatus applyForVaccination(Patient patient) throws RemoteException {
        return serverModel.applyForVaccination(patient);
    }
    
    @Override
    public Patient getPatient(String cpr) throws RemoteException {
        return serverModel.getPatient(cpr);
    }

    @Override public void addSchedule(Nurse nurse, Schedule schedule) throws RemoteException
    {
        serverModel.addSchedule(nurse,schedule);
    }

    @Override public void removeSchedule(Nurse nurse, Schedule schedule) throws RemoteException {
        serverModel.removeSchedule(nurse, schedule);
    }
    
    @Override
    public void changeResult(int id,Result result) throws RemoteException {
        serverModel.changeResult(id,result);
    }

    @Override public void addFAQ(String question, String answer, Category category, Administrator creator) throws RemoteException {
        serverModel.addFAQ(question, answer, category, creator);
    }

    @Override
    public FAQList getFAQList() throws RemoteException {
        return serverModel.getFAQList();
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
