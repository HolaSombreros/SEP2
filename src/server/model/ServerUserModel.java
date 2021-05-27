package server.model;

import server.model.domain.user.*;

import java.rmi.RemoteException;
import java.time.LocalDate;

public interface ServerUserModel {
    User login(String cpr, String password);

    void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip, String city) throws RemoteException;

    void logout(User user);

    UserList getUsersByCprAndName(String criteria, String typeOfList);

    UserList getUserList();

    UserList getPatientList();

    UserList getNurseList();

    UserList getAdministratorList();

    User editUserInformation(User user, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip) throws RemoteException;

    VaccineStatus applyForVaccination(Patient patient) throws RemoteException;

    Patient getPatient(String cpr);

    void editSchedule(Nurse nurse, LocalDate dateFrom, int shiftId) throws RemoteException;

    VaccineStatus updateVaccineStatus(Patient patient) throws RemoteException;

    void setRole(User user, String role) throws RemoteException;

    void RemoveRole(User user) throws RemoteException;
}
