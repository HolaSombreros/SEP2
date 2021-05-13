package client.model;

import server.model.domain.appointment.*;
import server.model.domain.user.Patient;
import server.model.domain.user.User;
import utility.observer.subject.LocalSubject;

import java.time.LocalDate;

public interface AppointmentModel extends LocalSubject<User, Appointment>
{
    void addAppointment(LocalDate date, TimeInterval timeInterval, Type type, Patient patient);
    AppointmentList getAppointmentsByUser(User patient);
    TimeIntervalList getAvailableTimeIntervals(LocalDate date);
    Appointment getAppointmentById(int id);
    void cancelAppointment( int id);
    void rescheduleAppointment(int id, LocalDate date, TimeInterval timeInterval);
    void changeResult(int id,Result result);
}
