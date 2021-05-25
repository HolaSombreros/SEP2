package client.mediator;

import client.model.Model;
import server.model.domain.appointment.*;
import server.model.domain.faq.Category;
import server.model.domain.faq.FAQ;
import server.model.domain.faq.FAQList;
import server.model.domain.user.*;
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

public class Client implements Model, RemoteListener<FAQ,FAQ> {
    public static final String HOST = "localhost";
    private RemoteModel server;
    private PropertyChangeAction<FAQ, FAQ> faqProperty;
    
    public Client(String host) throws RemoteException, NotBoundException, MalformedURLException {
        server = (RemoteModel) Naming.lookup("rmi://" + host + ":1099/AppointmentSystem");
        UnicastRemoteObject.exportObject(this, 0);
        server.addListener(this);
        faqProperty = new PropertyChangeProxy<>(this, true);
    }
    
    public Client() throws RemoteException, NotBoundException, MalformedURLException {
        this(HOST);
    }

    @Override
    public void changeResult(int id,Result result) {
        try{
            server.changeResult(id,result);
        }
        catch (RemoteException e){
            throw new IllegalStateException(getExceptionMessage(e),e);
        }
    }
    
    @Override
    public AppointmentList getUpcomingAppointments(Patient patient) {
        try {
            return server.getUpcomingAppointments(patient);
        }
        catch (RemoteException e) {
            throw new IllegalStateException(getExceptionMessage(e), e);
        }
    }
    
    @Override
    public Patient getPatient(String cpr) {
        try{
            return server.getPatient(cpr);
        }
        catch (RemoteException e){
            throw new IllegalStateException(getExceptionMessage(e),e);
        }
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
            e.printStackTrace();
        return null;}
    }

    @Override
    public UserList getUsersByCprAndName(String criteria, String typeOfList)
    {
        try {
            return server.getUsersByCprAndName(criteria, typeOfList);
        }
        catch (RemoteException e) {
            throw new IllegalStateException(getExceptionMessage(e), e);
        }
    }

    @Override
    public UserList getUserList()
    {
        try {
            return server.getUserList();
        }
        catch (RemoteException e)
        {
            throw new IllegalStateException(getExceptionMessage(e),e);
        }
    }

    @Override
    public TimeIntervalList getTimeIntervalList() {
        try{
            return server.getTimeIntervalList();
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
        //     faqProperty.firePropertyChange("NewAppointment", appointment.getPatient(), appointment);
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
    public AppointmentList filterAppointmentsByNameAndCpr(String criteria, boolean showFinished, String appointmentType)
    {
        try {
            return server.filterAppointmentsByNameAndCpr(criteria, showFinished, appointmentType);
        }
        catch (RemoteException e) {
            throw new IllegalStateException(getExceptionMessage(e), e);
        }
    }

    @Override
    public void cancelAppointment(int id)
    {
        try {
            server.cancelAppointment(id);
        }
        catch (RemoteException e){
            throw new IllegalStateException(getExceptionMessage(e),e);
        }
    }

    @Override
    public void rescheduleAppointment(int id, LocalDate date, TimeInterval timeInterval)
    {
        try {
            server.rescheduleAppointment(id, date, timeInterval);
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
/*
    @Override
    public void removeUser(User user) {
        try {
            server.removeUser(user);
        }
        catch (RemoteException e) {
            throw new IllegalStateException(getExceptionMessage(e), e);
        }
    }*/

    @Override public UserList getPatients()
    {
        try {
            return server.getPatients();
        }
        catch (RemoteException e) {
            throw new IllegalStateException(getExceptionMessage(e), e);
        }
    }

    @Override public UserList getNurses()
    {
        try {
            return server.getNurses();
        }
        catch (RemoteException e) {
            throw new IllegalStateException(getExceptionMessage(e), e);
        }
    }

    @Override public UserList getAdministrators()
    {
        try {
            return server.getAdministrators();
        }
        catch (RemoteException e) {
            throw new IllegalStateException(getExceptionMessage(e), e);
        }
    }

    @Override
    public User editUserInformation(User user, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip)
    {
        try{
            return server.editUserInformation(user, password, firstName, middleName, lastName, phone, email, street, number, zip);
        }
        catch (RemoteException e){
            throw new IllegalStateException(getExceptionMessage(e), e);
        }
    }

    @Override
    public VaccineStatus applyForVaccination(Patient patient) {
        try {
            return server.applyForVaccination(patient);
        }
        catch (RemoteException e) {
            throw new IllegalStateException(getExceptionMessage(e), e);
        }
    }
    @Override
    public VaccineStatus updateVaccineStatus(Patient patient) {
        try {
            return server.updateVaccineStatus(patient);
        }
        catch (RemoteException e){
            throw new IllegalStateException(getExceptionMessage(e),e);
        }
    }

    @Override public void setRole(User user, String role) {
        try {
            server.setRole(user,role);
        }
        catch (RemoteException e) {
            throw new IllegalStateException(getExceptionMessage(e),e);
        }
    }

    @Override public void removeRole(User user) {
        try {
            server.removeRole(user);
        }
        catch (RemoteException e) {
            throw new IllegalStateException(getExceptionMessage(e),e);
        }
    }

    @Override public void editSchedule(Nurse nurse, LocalDate dateFrom, int shiftId) {
        try {
            server.editSchedule(nurse, dateFrom, shiftId);
        }
        catch (RemoteException e) {
            throw new IllegalStateException(getExceptionMessage(e),e);
        }
    }

    @Override public void addFAQ(String question, String answer, Category category, Administrator creator) {
        try {
            server.addFAQ(question, answer, category, creator);
        }
        catch (RemoteException e) {
            throw new IllegalStateException(getExceptionMessage(e),e);
        }
    }

    @Override public void editFAQ(String oldQuestion, String oldAnswer, String question, String answer, Category category) {
        try {
            server.editFAQ(oldQuestion, oldAnswer, question, answer, category);
        }
        catch (RemoteException e) {
            throw new IllegalStateException(getExceptionMessage(e),e);
        }
    }

    @Override
    public FAQList getFAQList() {
        try {
            return server.getFAQList();
        }
        catch (RemoteException e) {
            throw new IllegalStateException(getExceptionMessage(e), e);
        }
    }

    @Override
    public void removeFAQ(String question, String answer)
    {
        try {
            server.removeFAQ(question, answer);
        }
        catch (RemoteException e) {
            throw new IllegalStateException(getExceptionMessage(e), e);
        }
    }

    @Override
    public void close() {
        try {
            UnicastRemoteObject.unexportObject(this, true);
            faqProperty.close();
        }
        catch (Exception e) {
            throw new IllegalStateException("Cannot unexport object RMI", e);
        }
    }

    private String getExceptionMessage(Exception e) {
        String message = e.getMessage();
        if (message != null) {
            message = message.split(";")[0];
        }
        return message;
    }

    @Override public void propertyChange(ObserverEvent<FAQ, FAQ> event) throws RemoteException {
        faqProperty.firePropertyChange(event);
    }

    @Override public boolean addListener(GeneralListener<FAQ, FAQ> listener, String... propertyNames) {
        return faqProperty.addListener(listener, propertyNames);
    }

    @Override public boolean removeListener(GeneralListener<FAQ, FAQ> listener, String... propertyNames) {
        return faqProperty.removeListener(listener, propertyNames);
    }
}
