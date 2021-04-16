package client.model;

import server.model.*;
import utility.observer.subject.LocalSubject;

public interface Model extends LocalSubject<User, ServerMessage> {
  void register(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email);
  User login(String cpr, String password);
  Appointment addAppointment(Appointment appointment);
  AppointmentList getAppointmentsByUser(User user);
}
