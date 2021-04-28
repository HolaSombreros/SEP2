package server.model.domain;

import java.io.Serializable;
import java.util.List;

public class AppointmentTimeInterval implements Serializable {
    private AppointmentList appointmentList;
    private Date date;
    private TimeInterval timeInterval;
    public static final int maxAppointmentCount = 3;
    
    public AppointmentTimeInterval(Date date, TimeInterval timeInterval) {
        this.appointmentList = new AppointmentList();
        this.date = date.copy();
        this.timeInterval = timeInterval;
    }
    
    public int getMaxAppointmentCount() {
        return maxAppointmentCount;
    }
    
    public List<Appointment> getAppointmentList() {
        return appointmentList.getAppointments();
    }
    
    public Date getDate() {
        return date.copy();
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
