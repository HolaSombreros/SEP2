package server.model;

import java.util.ArrayList;
import java.util.List;

public class AppointmentTimeList {
    private List<AppointmentTimeFrame> appointmentTimeFrames;
    
    public AppointmentTimeList() {
        appointmentTimeFrames = new ArrayList<>();
    }
    
    public void add(AppointmentTimeFrame appointmentTimeFrame) {
        appointmentTimeFrames.add(appointmentTimeFrame);
    }
    
    public void add(Appointment appointment, Date date, TimeInterval timeInterval) {
        // is there a timeframe on a certain date?
        // is there a timeinterval in that timeframe?
        // add the appointment there
        
        for (AppointmentTimeFrame appointmentTimeFrame : appointmentTimeFrames) {
            if (appointmentTimeFrame.getDate().equals(date) &&
                appointmentTimeFrame.getTimeInterval().equals(timeInterval)) {
                appointmentTimeFrame.add(appointment);
            }
        }
    }
    
    public AppointmentList getAppointsByUser(User user) {
        AppointmentList appointments = new AppointmentList();
        for (AppointmentTimeFrame appointmentTimeFrame : appointmentTimeFrames) {
            appointments.addAll(appointmentTimeFrame.getAppointmentsByUser(user).getAppointments());
        }
        return appointments;
    }
    
    public Appointment getAppointmentById(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("Please enter an id higher than 0");
        }
        for (AppointmentTimeFrame appointmentTimeFrame : appointmentTimeFrames) {
            for (Appointment appointment : appointmentTimeFrame.getAppointmentList()) {
                if (appointment.getId() == id) {
                    return appointment;
                }
            }
        }
        return null;
    }
    
    public TimeIntervalList getAvailableTimeIntervals(Date date) {
        TimeIntervalList list = new TimeIntervalList();
        for (AppointmentTimeFrame appointmentTimeFrame : appointmentTimeFrames) {
            if (appointmentTimeFrame.getDate().equals(date) &&
                appointmentTimeFrame.size() < appointmentTimeFrame.getMaxAppointmentCount()) {
                list.add(appointmentTimeFrame.getTimeInterval());
            }
        }
        return list;
    }
}
