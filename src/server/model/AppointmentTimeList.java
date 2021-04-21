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
    
    public void add(Appointment appointment, TimeInterval timeInterval) {
        for (AppointmentTimeFrame appointmentTimeFrame : appointmentTimeFrames) {
            if (appointmentTimeFrame.getTimeInterval().equals(timeInterval)) {
                System.out.println("Added appointment");
                appointmentTimeFrame.add(appointment);
            }
        }
    }
    
    public AppointmentList getAppointsByUser(User user) {
        AppointmentList appointments = new AppointmentList();
        for (AppointmentTimeFrame appointmentTimeFrame : appointmentTimeFrames) {
            appointments.addAll(appointmentTimeFrame.getAppointmentsByUser(user));
        }
        return appointments;
    }
    
    public Appointment getAppointmentById(int id) {
        for (AppointmentTimeFrame appointmentTimeFrame : appointmentTimeFrames) {
            return appointmentTimeFrame.getAppointmentById(id);
        }
        return null;
    }
    
    public TimeIntervalList getAvailableTimeIntervals() {
        TimeIntervalList list = new TimeIntervalList();
        for (AppointmentTimeFrame appointmentTimeFrame : appointmentTimeFrames) {
            list.add(appointmentTimeFrame.getTimeInterval());
        }
        return list;
    }
}
