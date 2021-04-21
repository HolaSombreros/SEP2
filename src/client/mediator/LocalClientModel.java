package client.mediator;

import server.model.*;
import utility.observer.subject.LocalSubject;

public interface LocalClientModel extends LocalSubject<User, Appointment> {
    void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip, String city);
    User login(String cpr, String password);
    void addAppointment(Date date, TimeInterval timeInterval, Appointment.Type type, User patient);
    AppointmentList getAppointmentsByUser(User patient);
    TimeIntervalList getAvailableTimeIntervals();
    Appointment getAppointmentById(int id);
    void close();
}
