package server.model.domain;

import java.util.ArrayList;
import java.util.List;

public class AppointmentTimeIntervalList {
    private List<AppointmentTimeInterval> appointmentTimeIntervals;
    
    public AppointmentTimeIntervalList() {
        appointmentTimeIntervals = new ArrayList<>();
    }
    
    public void add(AppointmentTimeInterval appointmentTimeInterval) {
        appointmentTimeIntervals.add(appointmentTimeInterval);
    }
    
    public void add(Appointment appointment, Date date, TimeInterval timeInterval) {
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
    public boolean checkAppointmentOnTimeInterval(TimeInterval timeInterval, Date date, Patient patient){
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
            for (Appointment appointment : appointmentTimeInterval.getAppointmentList()) {
                if (appointment.getId() == id) {
                    return appointment;
                }
            }
        }
        return null;
    }
    
    public TimeIntervalList getAvailableTimeIntervals(Date date) {
        TimeIntervalList list = new TimeIntervalList();
        if(date.isBefore(Date.today())){
            return list;
        }
        for (AppointmentTimeInterval appointmentTimeInterval : appointmentTimeIntervals) {
            if (appointmentTimeInterval.getDate().equals(date) && appointmentTimeInterval.size() < appointmentTimeInterval.getMaxAppointmentCount()) {
                list.add(appointmentTimeInterval.getTimeInterval());
            }
        }
        return list;
    }
}
