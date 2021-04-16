package client.model;

import server.model.Address;
import server.model.Patient;
import server.model.ServerMessage;
import server.model.User;
import utility.observer.subject.LocalSubject;

public interface Model extends LocalSubject<User, ServerMessage> {
  void register(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email);
  User login(String cpr, String password);
}
