package server.mediator;

import server.model.*;
import utility.observer.subject.RemoteSubject;
import java.rmi.RemoteException;

public interface RemoteModel extends RemoteSubject<User, Appointment>
{
    void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip, String city) throws RemoteException;
    User login(String cpr, String password) throws RemoteException;
    void addAppointment(Date date, TimeInterval timeInterval, Appointment.Type type, User patient) throws RemoteException;
    AppointmentList getAppointmentsByUser(User patient) throws RemoteException;
    TimeIntervalList getAvailableTimeIntervals() throws RemoteException;
}
