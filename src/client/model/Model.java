package client.model;

import server.model.domain.appointment.*;
import server.model.domain.user.User;
import utility.observer.subject.LocalSubject;


public interface Model extends AppointmentModel, UserModel, LocalSubject<User, Appointment> {

  void close();
}
