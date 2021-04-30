package server.model.domain.appointment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AppointmentList implements Serializable {
    private List<Appointment> appointments;
    
    public AppointmentList() {
        appointments = new ArrayList<>();
    }
    
    public List<Appointment> getAppointments() {
        return appointments;
    }
    
    public void add(Appointment appointment) {
        appointments.add(appointment);
    }
    
    public void addAll(List<Appointment> appointments) {
        this.appointments.addAll(appointments);
    }
    
    public int size() {
        return appointments.size();
    }
}
