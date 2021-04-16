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

    public AppointmentList getAppointmentListByUser(User user) {
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
        //TODO : CONTAINS WITH TIME INTERVAL AND BLA BLA
        return false;
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
