package client.mediator;

import server.model.domain.appointment.*;
import server.model.domain.user.Patient;
import server.model.domain.user.User;
import server.model.domain.user.UserList;
import utility.observer.subject.LocalSubject;

import java.time.LocalDate;

public interface LocalClientModel extends LocalSubject<User, Appointment> {
    void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip, String city);
    User login(String cpr, String password);
    UserList getUserList();
    void addAppointment(LocalDate date, TimeInterval timeInterval, Type type, Patient patient);
    AppointmentList getAppointmentsByUser(User patient);
    TimeIntervalList getAvailableTimeIntervals(LocalDate date);
    Appointment getAppointmentById(int id);
    void logout(User user);
    UserList getPatients();
    UserList getNurses();
    UserList getAdministrators();
    void close();
}
