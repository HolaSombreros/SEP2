package client.model;

import server.model.domain.appointment.Appointment;
import server.model.domain.user.Patient;
import server.model.domain.user.User;
import server.model.domain.user.UserList;
import server.model.domain.user.VaccineStatus;
import utility.observer.subject.LocalSubject;

public interface UserModel extends LocalSubject<User, Appointment>
{
    void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip, String city);
    User login(String cpr, String password);
    void logout(User user);
    UserList getUserList();
    UserList getPatients();
    UserList getNurses();
    UserList getAdministrators();
    User editUserInformation(User user, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip);
   VaccineStatus applyForVaccination(Patient patient);

}
