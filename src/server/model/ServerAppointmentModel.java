package server.model;

import server.model.domain.appointment.*;
import server.model.domain.user.Patient;
import server.model.domain.user.User;

import java.rmi.RemoteException;
import java.time.LocalDate;

public interface ServerAppointmentModel
{
    Appointment addAppointment(LocalDate date, TimeInterval timeInterval, Type type, Patient patient) throws RemoteException;
    AppointmentList getAppointmentsByUser(User user);
    AppointmentList filterAppointmentsByNameAndCpr(String criteria, boolean showFinished, String appointmentType);
    Appointment getAppointmentById(int id);
    TimeIntervalList getAvailableTimeIntervals(LocalDate date);
    TimeIntervalList getTimeIntervalList();
    void cancelAppointment(int id) throws RemoteException;
    void rescheduleAppointment(int id, LocalDate date, TimeInterval timeInterval) throws RemoteException;
    void changeResult(int id ,Result result) throws RemoteException;
}
