package server.mediator;

import server.model.domain.*;
import utility.observer.subject.RemoteSubject;
import java.rmi.RemoteException;
import java.time.LocalDate;

public interface RemoteModel extends RemoteSubject<User, Appointment>
{
    void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip, String city) throws RemoteException;
    User login(String cpr, String password) throws RemoteException;
    UserList getUserList() throws RemoteException;
    Appointment addAppointment(LocalDate date, TimeInterval timeInterval, Type type, Patient patient) throws RemoteException;
    AppointmentList getAppointmentsByUser(User patient) throws RemoteException;
    TimeIntervalList getAvailableTimeIntervals(LocalDate date) throws RemoteException;
    Appointment getAppointmentById(int id) throws RemoteException;
    void logout(User user) throws RemoteException;
    UserList getPatients() throws RemoteException;
    UserList getNurses() throws RemoteException;
    UserList getAdministrators() throws RemoteException;
}
