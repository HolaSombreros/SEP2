package server.model;

import server.model.domain.*;
import utility.observer.subject.LocalSubject;

import java.time.LocalDate;

public interface ServerModel extends ServerAppointmentModel, ServerUsersModel, LocalSubject<User, Appointment> {
    void close();
}
