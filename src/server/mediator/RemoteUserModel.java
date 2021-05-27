package server.mediator;

import server.model.domain.user.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;

public interface RemoteUserModel extends Remote {
    void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip, String city) throws RemoteException;

    User login(String cpr, String password) throws RemoteException;

    void logout(User user) throws RemoteException;

    UserList getUsersByCprAndName(String criteria, String typeOfList) throws RemoteException;

    UserList getUserList() throws RemoteException;

    UserList getPatients() throws RemoteException;

    UserList getNurses() throws RemoteException;

    UserList getAdministrators() throws RemoteException;

    User editUserInformation(User user, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip) throws RemoteException;

    VaccineStatus applyForVaccination(Patient patient) throws RemoteException;

    void editSchedule(Nurse nurse, LocalDate dateFrom, int shiftId) throws RemoteException;

    Patient getPatient(String cpr) throws RemoteException;

    VaccineStatus updateVaccineStatus(Patient patient) throws RemoteException;

    void setRole(User user, String role) throws RemoteException;

    void removeRole(User user) throws RemoteException;

}
