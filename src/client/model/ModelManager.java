package client.model;

import client.mediator.Client;
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

import java.time.LocalDate;

public class ModelManager implements Model, LocalListener<FAQ, FAQ> {
    
    private PropertyChangeAction<FAQ, FAQ> faqProperty;
    private Client client;
    
    public ModelManager() {
        try {
            client = new Client();
            client.addListener(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        faqProperty = new PropertyChangeProxy<>(this);
    }
    
    @Override
    public void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip, String city) {
        client.register(cpr, password, firstName, middleName, lastName, phone, email, street, number, zip, city);
    }
    
    @Override
    public void changeResult(int id, Result result) {
        client.changeResult(id, result);
    }
    
    @Override
    public Patient getPatient(String cpr) {
        return client.getPatient(cpr);
    }
    
    @Override
    public User login(String cpr, String password) {
        return client.login(cpr, password);
    }
    
    @Override
    public UserList getUsersByCprAndName(String criteria, String typeOfList) {
        return client.getUsersByCprAndName(criteria, typeOfList);
    }
    
    @Override
    public UserList getUserList() {
        return client.getUserList();
    }
    
    @Override
    public void addAppointment(LocalDate date, TimeInterval timeInterval, Type type, Patient patient) {
        client.addAppointment(date, timeInterval, type, patient);
    }
    
    @Override
    public AppointmentList getAppointmentsByUser(User patient) {
        return client.getAppointmentsByUser(patient);
    }
    
    @Override
    public TimeIntervalList getAvailableTimeIntervals(LocalDate date) {
        return client.getAvailableTimeIntervals(date);
    }
    
    @Override
    public Appointment getAppointmentById(int id) {
        return client.getAppointmentById(id);
    }
    
    @Override
    public AppointmentList filterAppointmentsByNameAndCpr(String criteria, boolean showFinished, String appointmentType) {
        return client.filterAppointmentsByNameAndCpr(criteria, showFinished, appointmentType);
    }
    
    @Override
    public void cancelAppointment(int id) {
        client.cancelAppointment(id);
    }
    
    @Override
    public void rescheduleAppointment(int id, LocalDate date, TimeInterval timeInterval) {
        client.rescheduleAppointment(id, date, timeInterval);
    }
    
    @Override
    public UserList getPatients() {
        return client.getPatients();
    }
    
    @Override
    public UserList getNurses() {
        return client.getNurses();
    }
    
    @Override
    public UserList getAdministrators() {
        return client.getAdministrators();
    }
    
    @Override
    public TimeIntervalList getTimeIntervalList() {
        return client.getTimeIntervalList();
    }
    
    @Override
    public User editUserInformation(User user, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip) {
        return client.editUserInformation(user, password, firstName, middleName, lastName, phone, email, street, number, zip);
    }
    
    @Override
    public VaccineStatus applyForVaccination(Patient patient) {
        return client.applyForVaccination(patient);
    }
    
    @Override
    public void editSchedule(Nurse nurse, LocalDate dateFrom, int shiftId) {
        client.editSchedule(nurse, dateFrom, shiftId);
    }

    @Override
    public VaccineStatus updateVaccineStatus(Patient patient) {
        return client.updateVaccineStatus(patient);
    }

    @Override public void setRole(User user, String role) {
        client.setRole(user,role);
    }

    @Override public void removeRole(User user) {
        client.removeRole(user);
    }

    @Override
    public void logout(User user) {
        client.logout(user);
    }

    @Override public void addFAQ(String question, String answer, Category category, Administrator creator) {
        client.addFAQ(question, answer, category, creator);
    }

    @Override
    public FAQList getFAQList() {
        return client.getFAQList();
    }

    @Override
    public void removeFAQ(String question, String answer)
    {
        client.removeFAQ(question, answer);
    }

    @Override
    public void close() {
        faqProperty.close();
        client.close();
    }

    @Override public void propertyChange(ObserverEvent<FAQ, FAQ> event)
    {
        faqProperty.firePropertyChange(event.getPropertyName(), event.getValue1(), event.getValue2());
    }

    @Override public boolean addListener(GeneralListener<FAQ, FAQ> listener, String... propertyNames)
    {
        return faqProperty.addListener(listener, propertyNames);
    }

    @Override public boolean removeListener(GeneralListener<FAQ, FAQ> listener, String... propertyNames)
    {
        return faqProperty.removeListener(listener, propertyNames);
    }
}
