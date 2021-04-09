package client.model;

import server.model.Address;
import server.model.Patient;
import util.UnnamedPropertyChangeSubject;

import java.util.ArrayList;

public interface Model extends UnnamedPropertyChangeSubject {
  void register(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email);
  ArrayList<Patient> getPatients();
  Patient getPatientByCpr(String CPR);
}
