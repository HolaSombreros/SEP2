package server.model;

import server.model.domain.User;
import server.model.domain.UserList;

public interface ServerUsersModel
{
    User login(String cpr, String password);
    void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip, String city);
    void logout(User user);
    UserList getUserList();
    UserList getPatients();
    UserList getNurses();
    UserList getAdministrators();
}
