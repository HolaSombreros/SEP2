package server.model;

import server.model.domain.appointment.*;
import server.model.domain.user.User;
import utility.observer.subject.LocalSubject;

public interface ServerModel extends ServerAppointmentModel, ServerUserModel, LocalSubject<User, Appointment>  {
    void close();
}
