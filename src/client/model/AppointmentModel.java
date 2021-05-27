package client.model;

import server.model.domain.appointment.*;
import server.model.domain.user.Patient;
import server.model.domain.user.User;
import utility.observer.subject.LocalSubject;

import java.rmi.RemoteException;
import java.time.LocalDate;

public interface AppointmentModel extends LocalSubject<Object, Object>
{
    void addAppointment(LocalDate date, TimeInterval timeInterval, Type type, Patient patient);
    AppointmentList getAppointmentsByUser(User patient);
    TimeIntervalList getAvailableTimeIntervals(LocalDate date);
    Appointment getAppointmentById(int id);
    AppointmentList filterAppointmentsByNameAndCpr(String criteria, boolean showFinished, String appointmentType);
    TimeIntervalList getTimeIntervalList();
    void cancelAppointment( int id);
    void rescheduleAppointment(int id, LocalDate date, TimeInterval timeInterval);
    void changeResult(int id,Result result);
    AppointmentList getUpcomingAppointments(Patient patient);
}
