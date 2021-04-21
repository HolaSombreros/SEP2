package client.model;

import client.mediator.Client;
import client.mediator.LocalClientModel;
import server.model.*;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

public class ModelManager implements Model, LocalListener<User, Appointment> {
    
    private PropertyChangeAction<User, Appointment> property;
    private LocalClientModel client;
    
    public ModelManager() {
        try {
            client = new Client();
            client.addListener(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        property = new PropertyChangeProxy<>(this);
    }
    
    @Override
    public void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip, String city) {
        client.register(cpr, password, firstName, middleName, lastName, phone, email, street, number, zip, city);
    }
    
    @Override
    public User login(String cpr, String password) {
        return client.login(cpr, password);
    }
    
    @Override
    public void addAppointment(Date date, TimeInterval timeInterval, Appointment.Type type, User patient) {
        client.addAppointment(date, timeInterval, type, patient);
        property.firePropertyChange("new", null, null);
    }
    
    @Override
    public AppointmentList getAppointmentsByUser(User patient) {
        return client.getAppointmentsByUser(patient);
    }
    
    @Override
    public TimeIntervalList getAvailableTimeIntervals(Date date) {
        return client.getAvailableTimeIntervals(date);
    }
    
    @Override
    public Appointment getAppointmentById(int id) {
        return client.getAppointmentById(id);
    }
    
    @Override
    public void close() {
        property.close();
        client.close();
    }
    
    @Override
    public void propertyChange(ObserverEvent<User, Appointment> event) {
        property.firePropertyChange(event);
    }
    
    @Override
    public boolean addListener(GeneralListener<User, Appointment> listener, String... propertyNames) {
        return property.addListener(listener, propertyNames);
    }
    
    @Override
    public boolean removeListener(GeneralListener<User, Appointment> listener, String... propertyNames) {
        return property.removeListener(listener, propertyNames);
    }
}
