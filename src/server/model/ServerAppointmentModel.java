package server.model;

import server.model.domain.appointment.*;
import server.model.domain.user.Patient;
import server.model.domain.user.User;

import java.time.LocalDate;

public interface ServerAppointmentModel
{
    Appointment addAppointment(LocalDate date, TimeInterval timeInterval, Type type, Patient patient);
    AppointmentList getAppointmentsByUser(User user);
    Appointment getAppointmentById(int id);
    TimeIntervalList getAvailableTimeIntervals(LocalDate date);
}
