package server.model.domain.appointment;

import server.model.domain.user.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class AppointmentTimeInterval implements Serializable {
    private AppointmentList appointmentList;
    private LocalDate date;
    private TimeInterval timeInterval;
    public static final int maxAppointmentCount = 3;
    
    public AppointmentTimeInterval(LocalDate date, TimeInterval timeInterval) {
        this.appointmentList = new AppointmentList();
        this.date = date;
        this.timeInterval = timeInterval;
    }
    
    public int getMaxAppointmentCount() {
        return maxAppointmentCount;
    }
    
    public AppointmentList getAppointmentList() {
        return appointmentList;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public TimeInterval getTimeInterval() {
        return timeInterval;
    }
    
    public void add(Appointment appointment) {
        if (size() >= maxAppointmentCount) {
            throw new IllegalStateException("This time interval is unavailable");
        }
        appointmentList.add(appointment);
    }
    
    public AppointmentList getAppointmentsByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("The user cannot be null");
        }
        AppointmentList appointments = new AppointmentList();
        for (Appointment appointment : appointmentList.getAppointments()) {
            if (appointment.getPatient().equals(user) || appointment.getNurse().equals(user)) {
                appointments.add(appointment);
            }
        }
        return appointments;
    }
    
    public int size() {
        return appointmentList.size();
    }
    
    @Override
    public String toString() {
        String result = "";
        for (Appointment appointment : appointmentList.getAppointments()) {
            result += appointment + " ";
        }
        return result;
    }
}
