package server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AppointmentList implements Serializable
{
    private List<Appointment> appointments;

    public AppointmentList() {
        this.appointments = new ArrayList<>();
    }

    public List<Appointment> getAppointmentList() {
        return appointments;
    }

    public void add(Appointment appointment) {
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

    public AppointmentList getAppointmentListByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("The user cannot be null");
        }
        AppointmentList list = new AppointmentList();
        for (Appointment appointment : appointments) {
            if (user.equals(appointment.getPatient())) {
                 list.add(appointment);
            }
        }
        return list;
    }


    public int size() {
        return appointments.size();
    }

    public boolean contains(Appointment appointment) {
        return getAppointmentById(appointment.getId()) != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AppointmentList))
            return false;
        AppointmentList other = (AppointmentList) obj;
        if (appointments.size() != other.size()) {
            return false;
        }
        for (int index = 0; index < appointments.size(); index++) {
            if (!appointments.get(index).equals(other.appointments.get(index))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String totalAppointments = "";
        for (Appointment appointment1 : appointments) {
            totalAppointments += appointment1+ " ";
        }
        return totalAppointments;
    }
}
