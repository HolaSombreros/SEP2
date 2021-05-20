package server.mediator;

import server.model.*;
import server.model.domain.appointment.*;
import server.model.domain.faq.Category;
import server.model.domain.faq.FAQ;
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

public class RemoteModelManager implements RemoteModel, LocalListener<FAQ, FAQ> {
    private ServerModel serverModel;
    private PropertyChangeAction<FAQ, FAQ> faqProperty;
    
    public RemoteModelManager(ServerModel serverModel) throws RemoteException, MalformedURLException {
        this.serverModel = serverModel;
        faqProperty = new PropertyChangeProxy<>(this, true);
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
    public AppointmentList filterAppointmentsByNameAndCpr(String criteria, boolean showFinished, String appointmentType) throws RemoteException
    {
        return serverModel.filterAppointmentsByNameAndCpr(criteria, showFinished, appointmentType);
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
/*

    public void removeUser(User user) throws RemoteException
    {
        serverModel.removeUser(user);
    }*/

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

    @Override public void editSchedule(Nurse nurse, LocalDate dateFrom, int shiftId) throws RemoteException
    {
        serverModel.editSchedule(nurse, dateFrom, shiftId);
    }

    @Override
    public TimeIntervalList getTimeIntervalList() throws RemoteException {
        return serverModel.getTimeIntervalList();
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

    @Override
    public void removeFAQ(String question, String answer) throws RemoteException
    {
        serverModel.removeFAQ(question, answer);
    }

    @Override
    public VaccineStatus updateVaccineStatus(Patient patient) throws RemoteException {
        return serverModel.updateVaccineStatus(patient);
    }

    @Override public void setRole(User user, String role) throws RemoteException {
        serverModel.setRole(user,role);
    }

    @Override public void removeRole(User user) throws RemoteException {
        serverModel.RemoveRole(user);
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
        faqProperty.close();
        try {
            UnicastRemoteObject.unexportObject(this, true);
        }
        catch (Exception e) {
            // Do nothing
        }
    }
    
    @Override
    public void propertyChange(ObserverEvent<FAQ, FAQ> event) {
        faqProperty.firePropertyChange(event.getPropertyName(), event.getValue1(), event.getValue2());
    }
    
    @Override
    public boolean addListener(GeneralListener<FAQ, FAQ> listener, String... propertyNames) throws RemoteException {
        return faqProperty.addListener(listener, propertyNames);
    }
    
    @Override
    public boolean removeListener(GeneralListener<FAQ, FAQ> listener, String... propertyNames) throws RemoteException {
        return faqProperty.removeListener(listener, propertyNames);
    }
}
