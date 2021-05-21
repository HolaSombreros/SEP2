package client.model;

import server.model.domain.appointment.Appointment;
import server.model.domain.user.*;
import utility.observer.subject.LocalSubject;

import java.time.LocalDate;

public interface UserModel
{
    void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip, String city);
    User login(String cpr, String password);
    UserList getUsersByCprAndName(String criteria, String typeOfList);
    void logout(User user);
    UserList getUserList();
    UserList getPatients();
    UserList getNurses();
    UserList getAdministrators();
    User editUserInformation(User user, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip);
    VaccineStatus applyForVaccination(Patient patient);
    Patient getPatient(String cpr);
    void editSchedule(Nurse nurse, LocalDate dateFrom, int shiftId);
    VaccineStatus updateVaccineStatus(Patient patient);
    void setRole (User user, String role);
    void removeRole(User user);
}
