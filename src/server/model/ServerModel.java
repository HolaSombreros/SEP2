package server.model;

import utility.observer.subject.LocalSubject;



public interface ServerModel extends LocalSubject<User, Appointment>
{
    User login(String cpr, String password);
    void logout(User user);
    void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip, String city);
    void addAppointment(Date date, TimeInterval timeInterval, Appointment.Type type, User patient);
    AppointmentList getAppointmentsByUser(User user);
    TimeIntervalList getAvailableTimeIntervals();
    void close();
}
