package server.model.domain.appointment;

import server.model.domain.user.Patient;
import server.model.domain.user.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentTimeIntervalList {
    private List<AppointmentTimeInterval> appointmentTimeIntervals;
    
    public AppointmentTimeIntervalList() {
        appointmentTimeIntervals = new ArrayList<>();
    }
    
    public List<AppointmentTimeInterval> getAppointmentTimeIntervals() {
        return appointmentTimeIntervals;
    }
    
    public void add(AppointmentTimeInterval appointmentTimeInterval) {
        appointmentTimeIntervals.add(appointmentTimeInterval);
    }
    
    public void add(Appointment appointment, LocalDate date, TimeInterval timeInterval) {
        if(!checkAppointmentOnTimeInterval(timeInterval, date, appointment.getPatient())){
            for (AppointmentTimeInterval appointmentTimeInterval : appointmentTimeIntervals)
            {
                if (appointmentTimeInterval.getDate().equals(date) && appointmentTimeInterval.getTimeInterval().equals(timeInterval))
                {
                    appointmentTimeInterval.add(appointment);
                }
            }
        }
            else throw new IllegalArgumentException("You cannot book another appointment in this time interval");
    }
    
    public boolean checkAppointmentOnTimeInterval(TimeInterval timeInterval, LocalDate date, Patient patient){
        for(Appointment appointment : getAppointmentsByUser(patient).getAppointments()){
            if(appointment.getTimeInterval().equals(timeInterval) && appointment.getDate().equals(date)){
                return true;
            }
        }
        return false;
    }
    
    public AppointmentList getAppointmentsByUser(User user) {
        AppointmentList appointments = new AppointmentList();
        for (AppointmentTimeInterval appointmentTimeInterval : appointmentTimeIntervals) {
            appointments.addAll(appointmentTimeInterval.getAppointmentsByUser(user).getAppointments());
        }
        return appointments;
    }
    
    public Appointment getAppointmentById(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("Please enter an id higher than 0");
        }
        for (AppointmentTimeInterval appointmentTimeInterval : appointmentTimeIntervals) {
            for (Appointment appointment : appointmentTimeInterval.getAppointmentList().getAppointments()) {
                if (appointment.getId() == id) {
                    return appointment;
                }
            }
        }
        return null;
    }
    
    public TimeIntervalList getAvailableTimeIntervals(LocalDate date) {
        TimeIntervalList list = new TimeIntervalList();
        if(date.isBefore(LocalDate.now())){
            return list;
        }
        for (AppointmentTimeInterval appointmentTimeInterval : appointmentTimeIntervals) {
            // if the date is the current date, filter out based on current time to not show past time intervals
            if (date.equals(LocalDate.now())) {
                if (appointmentTimeInterval.getDate().equals(date) && appointmentTimeInterval.size() < appointmentTimeInterval.getMaxAppointmentCount()
                    && !appointmentTimeInterval.getTimeInterval().getFrom().isBefore(LocalTime.now())) {
                    list.add(appointmentTimeInterval.getTimeInterval());
                }
            }
            // otherwise, add all time intervals for that date
            else {
                if (appointmentTimeInterval.getDate().equals(date) && appointmentTimeInterval.size() < appointmentTimeInterval.getMaxAppointmentCount()) {
                    list.add(appointmentTimeInterval.getTimeInterval());
                }
            }
        }
        return list;
    }
    
    @Override
    public String toString() {
        String str = "";
        for (AppointmentTimeInterval appointmentTimeInterval : appointmentTimeIntervals) {
            str += "\n" + appointmentTimeInterval.toString();
        }
        return str;
    }
}
