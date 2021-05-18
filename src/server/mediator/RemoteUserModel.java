package server.mediator;

import server.model.domain.user.*;

import java.rmi.RemoteException;

public interface RemoteUserModel
{
    void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip, String city) throws RemoteException;
    User login(String cpr, String password) throws RemoteException;
    void logout(User user) throws RemoteException;
    UserList getUsersByCprAndName(String criteria) throws RemoteException;
//    void removeUser(User user) throws RemoteException;
    UserList getUserList() throws RemoteException;
    UserList getPatients() throws RemoteException;
    UserList getNurses() throws RemoteException;
    UserList getAdministrators() throws RemoteException;
    User editUserInformation(User user, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip) throws RemoteException;
    VaccineStatus applyForVaccination(Patient patient) throws RemoteException;
    void addSchedule(Nurse nurse, Schedule schedule) throws RemoteException;
    void removeSchedule(Nurse nurse, Schedule schedule) throws RemoteException;
    Patient getPatient(String cpr) throws RemoteException;
    VaccineStatus updateVaccineStatus(Patient patient) throws RemoteException;
    void setRole (User user, String role) throws RemoteException;
    void removeRole(User user) throws RemoteException;
}
