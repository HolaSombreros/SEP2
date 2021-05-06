package client.mediator;

import server.model.domain.appointment.*;
import server.model.domain.user.User;
import utility.observer.subject.LocalSubject;


public interface LocalClientModel extends LocalClientAppointmentModel, LocalClientUserModel, LocalSubject<User, Appointment> {
    void close();
}
