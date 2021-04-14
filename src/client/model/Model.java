package client.model;

import server.model.Address;
import server.model.Patient;
import server.model.UsersList;
import util.UnnamedPropertyChangeSubject;

public interface Model extends UnnamedPropertyChangeSubject {
  void register(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email);
  void login(String cpr, String password);
  UsersList getPatients();
  Patient getPatientByCpr(String CPR);
}
