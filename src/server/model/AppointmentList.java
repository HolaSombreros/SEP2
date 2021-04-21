package server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AppointmentList implements Serializable {
    private List<Appointment> appointments;
    
    public AppointmentList() {
        appointments = new ArrayList<>();
    }
    
    public List<Appointment> getAppointments(){
        return appointments;
    }
    
    public void add(Appointment appointment) {
        appointments.add(appointment);
    }
    
    public void addAll(AppointmentList appointmentList){
        appointments.addAll(appointmentList.getAppointments());
    }
    
    public int size() {
        return appointments.size();
    }
}
