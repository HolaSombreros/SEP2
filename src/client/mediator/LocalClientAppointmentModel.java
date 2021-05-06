package client.mediator;

import server.model.domain.appointment.*;
import server.model.domain.user.Patient;
import server.model.domain.user.User;
import utility.observer.subject.LocalSubject;
import java.time.LocalDate;

public interface LocalClientAppointmentModel extends LocalSubject<User, Appointment>
{
    void addAppointment(LocalDate date, TimeInterval timeInterval, Type type, Patient patient);
    AppointmentList getAppointmentsByUser(User patient);
    TimeIntervalList getAvailableTimeIntervals(LocalDate date);
    Appointment getAppointmentById(int id);
    void cancelAppointment(int id);
    void rescheduleAppointment(int id, LocalDate date, TimeInterval timeInterval);
}
