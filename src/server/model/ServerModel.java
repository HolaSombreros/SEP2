package server.model;

import utility.observer.subject.LocalSubject;



public interface ServerModel extends LocalSubject<User, ServerMessage>
{
    User login(String cpr, String password);
    void sendServerMessage(ServerMessage message);
    User register(User user);
    void close();
    Appointment addAppointment(User user, Appointment appointment);
    AppointmentList getAppointmentsByUser(User user);
}
