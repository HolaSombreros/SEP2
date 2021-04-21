package server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AppointmentList implements Serializable {
    private List<Appointment> appointments;
    private Date date;
    private TimeInterval timeInterval;
    private static final int maxAppointmentCount = 3;
    
    public AppointmentList(Date date, TimeInterval timeInterval) {
        this.appointments = new ArrayList<>();
        this.date = date.copy();
        this.timeInterval = timeInterval;
    }
    
    public List<Appointment> getAppointmentList() {
        return appointments;
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
        appointments.add(appointment);
    }
    
    public Appointment getAppointmentById(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("Please enter an id higher than 0");
        }
        for (Appointment appointment : appointments) {
            if (appointment.getId() == id) {
                return appointment;
            }
        }
        return null;
    }
    
    public List<Appointment> getAppointmensByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("The user cannot be null");
        }
        List<Appointment> appointments = new ArrayList<>();
        for (Appointment appointment : this.appointments) {
            if (user.getCpr().equals(appointment.getPatient().getCpr())) {
                appointments.add(appointment);
            }
        }
        return appointments;
    }
    
    public int size() {
        return appointments.size();
    }
    
    @Override
    public boolean equals(Object obj) {
        return false;
    }
    
    @Override
    public String toString() {
        String result = "";
        for (Appointment appointment : appointments) {
            result += appointment + " ";
        }
        return result;
    }
}
