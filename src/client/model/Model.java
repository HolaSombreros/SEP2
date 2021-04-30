package client.model;

import server.model.domain.*;
import utility.observer.subject.LocalSubject;

import java.time.LocalDate;

public interface Model extends LocalSubject<User, Appointment> {
  void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip, String city);
  User login(String cpr, String password);
  UserList getUserList();
  void addAppointment(LocalDate date, TimeInterval timeInterval, Type type, Patient patient);
  AppointmentList getAppointmentsByUser(User patient);
  TimeIntervalList getAvailableTimeIntervals(LocalDate date);
  Appointment getAppointmentById(int id);
  
  void logout(User user);
  void close();
}
