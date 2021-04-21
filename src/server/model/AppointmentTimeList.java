package server.model;

import java.util.ArrayList;
import java.util.List;

public class AppointmentTimeList {
    private List<AppointmentList> appointmentLists;
    
    public AppointmentTimeList() {
        appointmentLists = new ArrayList<>();
    }
    
    public void add(AppointmentList appointmentList) {
        appointmentLists.add(appointmentList);
    }
    
    public void add(Appointment appointment, TimeInterval timeInterval) {
        for (AppointmentList appointmentList : appointmentLists) {
            if (appointmentList.getTimeInterval().equals(timeInterval)) {
                appointmentList.add(appointment);
            }
        }
    }
    
    public List<Appointment> getAppointsByUser(User user) {
        for (AppointmentList appointmentList : appointmentLists) {
        
        }
    }
}
