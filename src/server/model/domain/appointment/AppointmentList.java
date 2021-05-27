package server.model.domain.appointment;

import server.model.domain.user.Nurse;
import server.model.domain.user.Patient;
import server.model.domain.user.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
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
        if (appointment != null)
            appointments.add(appointment);
        else
            throw new IllegalArgumentException("Cannot be null");
    }

    public void remove(int index) {
        if (index >= size() || index < 0) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        appointments.remove(index);
    }

    public void remove(Appointment appointment) {
        appointments.remove(appointment);
    }

    public void addAll(List<Appointment> appointments) {
        this.appointments.addAll(appointments);
    }

    public Appointment get(int index) {
        if (index < 0 || index >= size()) {
            throw new IllegalArgumentException("Please specify a valid index");
        }
        return appointments.get(index);
    }

    public int size() {
        return appointments.size();
    }

    public AppointmentList getAppointmentsByUser(User user) {
        if (user == null)
            throw new IllegalArgumentException("User cannot be null");
        AppointmentList list = new AppointmentList();
        for (Appointment appointment : appointments) {
            if (user instanceof Patient && user.getCpr().equals(appointment.getPatient().getCpr()))
                list.add(appointment);
            else if (user instanceof Nurse && user.getCpr().equals(appointment.getNurse().getCpr()))
                list.add(appointment);
        }
        return list;
    }

    public AppointmentList filterAppointmentsByNameAndCpr(String criteria, boolean showFinished, String appointmentType) {
        AppointmentList filteredList = new AppointmentList();

        if (criteria != null && !criteria.isEmpty()) {
            for (Appointment appointment : appointments) {
                criteria = criteria.trim().toLowerCase();
                if (appointment.getPatient().getCpr().contains(criteria) || appointment.getPatient().getFullName().toLowerCase().contains(criteria)) {
                    filteredList.add(appointment);
                }
            }
        } else {
            filteredList.addAll(appointments);
        }

        filteredList = filterAppointmentsByStatus(filteredList, showFinished);
        filteredList = filterAppointmentsByType(filteredList, appointmentType);
        return filteredList;
    }

    public AppointmentList filterAppointmentsByStatus(AppointmentList appointmentList, boolean showFinished) {
        for (int i = appointmentList.getAppointments().size() - 1; i >= 0; i--) {
            Appointment appointment = appointmentList.get(i);
            if (!showFinished && appointment.getStatus() instanceof FinishedAppointment) {
                appointmentList.remove(i);
            }
        }
        return appointmentList;
    }

    public AppointmentList filterAppointmentsByType(AppointmentList appointmentList, String type) {
        for (int i = appointmentList.getAppointments().size() - 1; i >= 0; i--) {
            Appointment appointment = appointmentList.get(i);
            switch (type.toLowerCase()) {
                case "test":
                    if (!(appointment instanceof TestAppointment)) {
                        appointmentList.remove(i);
                    }
                    break;
                case "vaccine":
                    if (!(appointment instanceof VaccineAppointment)) {
                        appointmentList.remove(i);
                    }
                    break;
            }
        }
        return appointmentList;
    }

    public Appointment getAppointmentById(int id) {
        if (id < 1)
            throw new IllegalArgumentException("Please enter an id higher than 0");
        for (Appointment appointment : appointments)
            if (appointment.getId() == id)
                return appointment;
        return null;
    }

    public boolean hasAppointment(User user, LocalDate date, TimeInterval time) {
        AppointmentList list = getAppointmentsByUser(user);
        for (Appointment appointment : list.getAppointments())
            if (appointment.getDate().equals(date) && appointment.getTimeInterval().equals(time) && appointment.getStatus() instanceof UpcomingAppointment)
                return true;
        return false;
    }

    public boolean contains(Appointment appointment) {
        for (Appointment a : appointments) {
            if (a.equals(appointment)) {
                return true;
            }
        }
        return false;
    }

    public AppointmentList getUpcomingAppointments(Patient patient) {
        AppointmentList list = getAppointmentsByUser(patient);
        list = filterAppointmentsByStatus(list, false);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = 1; j < list.size(); j++) {
                    Appointment app1 = list.get(j - 1);
                    Appointment app2 = list.get(j);

                    // one is before the other
                    if (app2.getDate().isBefore(app1.getDate())) {
                        list.set(j - 1, app2);
                        list.set(j, app1);
                    }
                    // same date, now check time
                    else if (app1.getDate().equals(app2.getDate())) {
                        if (app2.getTimeInterval().getFrom().isBefore(app1.getTimeInterval().getFrom())) {
                            list.set(j - 1, app2);
                            list.set(j, app1);
                        }
                    }
                }
            }
        }
        return list;
    }

    public void set(int index, Appointment appointment) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        appointments.set(index, appointment);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AppointmentList))
            return false;

        AppointmentList other = (AppointmentList) obj;
        if (other.size() != size())
            return false;

        for (int i = 0; i < size(); i++) {
            if (!appointments.get(i).equals(other.appointments.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String str = "";
        for (Appointment appointment : appointments) {
            str += "\n" + appointment.toString();
        }
        return str;
    }
}
