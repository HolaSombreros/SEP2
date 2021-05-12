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
    
    public Appointment get(int index) {
        if (index < 0 || index > size()) {
            throw new IllegalArgumentException("Please specify a valid index");
        }
        return appointments.get(index);
    }
    
    public int size() {
        return appointments.size();
    }

    public boolean contains(Appointment appointment){
        for(Appointment a : appointments)
            if(a.equals(appointment))
                return true;
        return false;
    }

    public boolean equals(Object obj){
        if(!(obj instanceof AppointmentList))
            return false;
        AppointmentList other = (AppointmentList) obj;
        if(other.size() != size())
            return false;
        for(Appointment appointment: appointments)
            if(!other.getAppointments().contains(appointment))
                return false;
        return true;
    }
}
