package client.mediator;

import server.model.*;
import utility.observer.subject.LocalSubject;

import java.rmi.RemoteException;

public interface LocalClientModel extends LocalSubject<User, ServerMessage> {
    User login(String cpr, String password) throws RemoteException;
    User register(User patient) throws RemoteException;
    Appointment addAppointment(User user, Appointment appointment) throws RemoteException;
    AppointmentList getAppointmentsByUser(User user) throws RemoteException;
    void close() throws RemoteException;
}
