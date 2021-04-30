package server.model;

import server.model.domain.user.User;
import server.model.domain.user.UserList;

public interface ServerUserModel
{
    User login(String cpr, String password);
    void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip, String city);
    void logout(User user);
    UserList getUserList();
    UserList getPatients();
    UserList getNurses();
    UserList getAdministrators();
}