package server.mediator;

import server.model.*;
import utility.observer.subject.RemoteSubject;
import java.rmi.RemoteException;

public interface RemoteModel extends RemoteSubject<User, ServerMessage>
{
    User login(String cpr, String password) throws RemoteException;
    User register(User user) throws RemoteException;
    Appointment addAppointment(User user, Appointment appointment) throws RemoteException;
    AppointmentList getAppointmentsByUser(User user) throws RemoteException;
    void close() throws RemoteException;
}
