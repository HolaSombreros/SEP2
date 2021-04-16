package server.model;

import utility.observer.subject.LocalSubject;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;


public interface ServerModel extends LocalSubject<User, ServerMessage>
{
    User login(String cpr, String password);
    void sendServerMessage(ServerMessage message);
    User register(User user);
    void close();
    Appointment addAppointment(Appointment appointment);
}
