package client.model;

import server.model.Address;
import server.model.Patient;
import server.model.ServerMessage;
import utility.observer.subject.LocalSubject;

public interface Model extends LocalSubject<Patient, ServerMessage> {
  void register(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email);
  void login(String cpr, String password);
}
