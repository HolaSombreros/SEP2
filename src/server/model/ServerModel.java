package server.model;

import server.model.domain.*;
import utility.observer.subject.LocalSubject;

public interface ServerModel extends LocalSubject<User, Appointment> {
    User login(String cpr, String password);
    void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip, String city);
    void addAppointment(Date date, TimeInterval timeInterval, Appointment.Type type, Patient patient);
    AppointmentList getAppointmentsByUser(User user);
    Appointment getAppointmentById(int id);
    TimeIntervalList getAvailableTimeIntervals(Date date);
    void logout(User user);
    void close();
}
