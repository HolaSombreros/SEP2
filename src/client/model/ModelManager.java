package client.model;

import client.mediator.Client;
import server.model.domain.appointment.*;
import server.model.domain.chat.Message;
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
import java.util.List;

public class ModelManager implements Model, LocalListener<Object, Object> {
    private PropertyChangeAction<Object, Object> property;
    private Client client;

    public ModelManager() {
        try {
            client = new Client();
            client.addListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        property = new PropertyChangeProxy<>(this);
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
    public AppointmentList getUpcomingAppointments(Patient patient) {
        return client.getUpcomingAppointments(patient);
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

    @Override
    public void setRole(User user, String role) {
        client.setRole(user, role);
    }

    @Override
    public void removeRole(User user) {
        client.removeRole(user);
    }

    @Override public NotificationList getNotifications(Patient patient) {
        return client.getNotifications(patient);
    }

    @Override public void disableNotification(Notification notification) {
        client.disableNotification(notification);
    }

    @Override
    public void logout(User user) {
        client.logout(user);
    }

    @Override
    public void addFAQ(String question, String answer, Category category, Administrator creator) {
        client.addFAQ(question, answer, category, creator);
    }

    @Override
    public void editFAQ(FAQ faq, String question, String answer, Category category) {
        client.editFAQ(faq, question, answer, category);
    }

    @Override
    public FAQList getFAQList() {
        return client.getFAQList();
    }

    @Override
    public void removeFAQ(String question, String answer) {
        client.removeFAQ(question, answer);
    }

    @Override
    public void sendMessage(Patient patient, String message, Administrator administrator) {
        client.sendMessage(patient, message, administrator);
    }

    @Override
    public List<Message> getUnreadMessages(Patient patient) {
        return client.getUnreadMessages(patient);
    }
    
    @Override
    public boolean isPatientChatBeingViewed(String cpr) {
        return client.isPatientChatBeingViewed(cpr);
    }
    
    @Override
    public void lockChat(String cpr, boolean locked) {
        client.lockChat(cpr, locked);
    }
    
    @Override
    public void close() {
        property.close();
        client.close();
    }

    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {
        property.firePropertyChange(event.getPropertyName(), event.getValue1(), event.getValue2());
    }

    @Override
    public boolean addListener(GeneralListener<Object, Object> listener, String... propertyNames) {
        return property.addListener(listener, propertyNames);
    }

    @Override
    public boolean removeListener(GeneralListener<Object, Object> listener, String... propertyNames) {
        return property.removeListener(listener, propertyNames);
    }
}
