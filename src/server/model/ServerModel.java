package server.model;

import server.model.domain.*;
import utility.observer.subject.LocalSubject;

import java.time.LocalDate;

public interface ServerModel extends LocalSubject<User, Appointment> {
    User login(String cpr, String password);
    void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip, String city);
    UserList getUserList();
    Appointment addAppointment(LocalDate date, TimeInterval timeInterval, Type type, Patient patient);
    AppointmentList getAppointmentsByUser(User user);
    Appointment getAppointmentById(int id);
    TimeIntervalList getAvailableTimeIntervals(LocalDate date);
    void logout(User user);
    void close();
}
