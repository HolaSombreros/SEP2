package server.model;

import server.model.domain.user.*;

public interface ServerUserModel
{
    User login(String cpr, String password);
    void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip, String city);
    void logout(User user);
//    void removeUser(User user);
    UserList getUserList();
    UserList getPatientList();
    UserList getNurseList();
    UserList getAdministratorList();
    User editUserInformation(User user, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip);
    VaccineStatus applyForVaccination(Patient patient);
    Patient getPatient(String cpr);
    void addSchedule(Nurse nurse, Schedule schedule);
    void removeSchedule(Nurse nurse, Schedule schedule);
    VaccineStatus updateVaccineStatus(Patient patient);
    void setRole (User user, String role);
    void RemoveRole(User user);
}
