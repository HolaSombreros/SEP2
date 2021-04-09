package client.model;

import server.model.Address;
import util.UnnamedPropertyChangeSubject;

public interface Model extends UnnamedPropertyChangeSubject {
    void register(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email);
    void login(String cpr, String password);
}
