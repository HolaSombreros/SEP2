package server.mediator;

import server.model.domain.appointment.*;
import server.model.domain.user.Patient;
import server.model.domain.user.User;
import utility.observer.subject.RemoteSubject;

import java.rmi.RemoteException;
import java.time.LocalDate;

public interface RemoteAppointmentModel extends RemoteSubject<User, Appointment>
{
    Appointment addAppointment(LocalDate date, TimeInterval timeInterval, Type type, Patient patient) throws RemoteException;
    AppointmentList getAppointmentsByUser(User patient) throws RemoteException;
    AppointmentList filterAppointmentsByNameAndCpr(String criteria, boolean showFinished, String appointmentType) throws RemoteException;
    TimeIntervalList getAvailableTimeIntervals(LocalDate date) throws RemoteException;
    Appointment getAppointmentById(int id) throws RemoteException;
    TimeIntervalList getTimeIntervalList() throws RemoteException;
    void cancelAppointment(int id) throws RemoteException;
    void rescheduleAppointment(int id, LocalDate date, TimeInterval timeInterval) throws RemoteException;
    void changeResult(int id,Result result) throws RemoteException;
}
