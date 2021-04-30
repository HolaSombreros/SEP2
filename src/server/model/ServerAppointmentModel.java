package server.model;

import server.model.domain.*;

import java.time.LocalDate;

public interface ServerAppointmentModel
{
    Appointment addAppointment(LocalDate date, TimeInterval timeInterval, Type type, Patient patient);
    AppointmentList getAppointmentsByUser(User user);
    Appointment getAppointmentById(int id);
    TimeIntervalList getAvailableTimeIntervals(LocalDate date);
}
