package server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AppointmentList implements Serializable {
    private List<Appointment> appointments;
    
    public AppointmentList() {
        appointments = new ArrayList<>();
    }
    
    public void add(Appointment appointment) {
        appointments.add(appointment);
    }

    public List<Appointment> getAppointments(){
        return appointments;
    }
    public void addAll(List<Appointment> appointmentList){
        appointments.addAll(appointmentList);
    }
}
