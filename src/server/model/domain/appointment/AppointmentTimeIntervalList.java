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
        if (!contains(appointmentTimeInterval))
            appointmentTimeIntervals.add(appointmentTimeInterval);
    }
    
    public void add(Appointment appointment, LocalDate date, TimeInterval timeInterval) {
        for (AppointmentTimeInterval appointmentTimeInterval : appointmentTimeIntervals) {
            if (appointmentTimeInterval.getDate().equals(date) && appointmentTimeInterval.getTimeInterval().equals(timeInterval)) {
                appointmentTimeInterval.add(appointment);
            }
        }
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
        // is the specified date before the current date, return an empty list
        if (date.isBefore(LocalDate.now())) {
            return list;
        }
        for (AppointmentTimeInterval appointmentTimeInterval : appointmentTimeIntervals) {
            // if the date is the current date, filter out based on current time to not show past time intervals
            if (date.equals(LocalDate.now())) {
                if (appointmentTimeInterval.getDate().equals(date) && !appointmentTimeInterval.getTimeInterval().getFrom().isBefore(LocalTime.now())) {
                    
                    // filter based on max appointments per time interval (which is filtered by cancelled appointments):
                    if (appointmentTimeInterval.getAppointmentCount() < appointmentTimeInterval.getMaxAppointmentCount()) {
                        list.add(appointmentTimeInterval.getTimeInterval());
                    }
                }
            }
            // otherwise, add all time intervals for that date
            else {
                if (appointmentTimeInterval.getDate().equals(date) && appointmentTimeInterval.getAppointmentCount() < appointmentTimeInterval.getMaxAppointmentCount()) {
                    list.add(appointmentTimeInterval.getTimeInterval());
                }
            }
        }
        return list;
    }
    
    public boolean contains(AppointmentTimeInterval appointmentTimeInterval) {
        for (AppointmentTimeInterval a : appointmentTimeIntervals) {
            if (a.getDate().equals(appointmentTimeInterval.getDate()) && a.getTimeInterval().equals(appointmentTimeInterval.getTimeInterval())) {
                return true;
            }
        }
        return false;
    }
    
    public int size() {
        return appointmentTimeIntervals.size();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AppointmentTimeIntervalList)) {
            return false;
        }
        
        AppointmentTimeIntervalList atil = (AppointmentTimeIntervalList) obj;
        if (appointmentTimeIntervals.size() != atil.size()) {
            return false;
        }
        
        for (int i = 0; i < appointmentTimeIntervals.size(); i++) {
            if (!appointmentTimeIntervals.get(i).equals(atil.appointmentTimeIntervals.get(i))) {
                return false;
            }
        }
        return true;
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
