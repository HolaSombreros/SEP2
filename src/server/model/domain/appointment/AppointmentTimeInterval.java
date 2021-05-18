package server.model.domain.appointment;

import server.model.domain.user.Nurse;
import server.model.domain.user.Patient;
import server.model.domain.user.User;
import server.model.domain.user.VaccineStatus;

import java.io.Serializable;
import java.time.LocalDate;

public class AppointmentTimeInterval implements Serializable {
    private AppointmentList appointmentList;
    private LocalDate date;
    private TimeInterval timeInterval;
    public static final int MAX_APPOINTMENT_COUNT = 3;
    
    public AppointmentTimeInterval(LocalDate date, TimeInterval timeInterval) {
        this.appointmentList = new AppointmentList();
        this.date = date;
        this.timeInterval = timeInterval;
    }
    
    public int getMaxAppointmentCount() {
        return MAX_APPOINTMENT_COUNT;
    }
    
    public AppointmentList getAppointmentList() {
        return appointmentList;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public TimeInterval getTimeInterval() {
        return timeInterval;
    }
    
    public void add(Appointment appointment) {
        if (size() >= getMaxAppointmentCount()) {
            throw new IllegalStateException("This time interval is already fully occupied");
        }
        
        if (!contains(appointment)) {
            appointmentList.add(appointment);
        }
        else {
            throw new IllegalArgumentException("You cannot book another appointment in this time interval");
        }
    }
    
    public AppointmentList getAppointmentsByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("The user cannot be null");
        }
        
        AppointmentList appointments = new AppointmentList();
        for (Appointment appointment : appointmentList.getAppointments()) {
            if (appointment.getPatient().equals(user) && user instanceof Patient) {
                appointments.add(appointment);
            }
            else if (appointment.getNurse().equals(user) && user instanceof Nurse) {
                appointments.add(appointment);
            }
        }
        return appointments;
    }
    
    public AppointmentList filterAppointmentsByNameAndCpr(String criteria, boolean showFinished, String appointmentType) {
        AppointmentList appointments = appointmentList;
        for (Appointment appointment : appointments.getAppointments()) {
            
            // Filter by finished appointments
            if (showFinished) {
                if (!(appointment.getStatus() instanceof FinishedAppointment)) {
                    appointments.remove(appointment);
                }
            }
    
            // Filter by type of appointment
            if (appointments.contains(appointment)) {
                switch (appointmentType.toLowerCase()) {
                    case "test":
                        if (appointment instanceof TestAppointment) {
                            appointments.remove(appointment);
                        }
                        break;
                    case "vaccine":
                        if (appointment instanceof VaccineAppointment) {
                            appointments.remove(appointment);
                        }
                        break;
                }
            }
            
            // Filter by search criteria
            if (appointments.contains(appointment) && criteria != null && !criteria.isEmpty()) {
                criteria = criteria.toLowerCase();
                if (appointment.getPatient().getCpr().contains(criteria) || appointment.getPatient().getFirstName().toLowerCase().contains(criteria) || appointment.getPatient().getLastName()
                    .toLowerCase().contains(criteria)) {
                    appointments.remove(appointment);
                }
            }
        }
        return appointments;
    }
    
    public int size() {
        return appointmentList.size();
    }
    
    /**
     * Only counts appointments that are not Cancelled.
     *
     * @return The amount of (not-Cancelled) appointments in the list.
     */
    public int getAppointmentCount() {
        int count = 0;
        for (Appointment appointment : appointmentList.getAppointments()) {
            if (!(appointment.getStatus() instanceof CancelledAppointment)) {
                count++;
            }
        }
        return count;
    }
    
    public boolean contains(Appointment appointment) {
        if (appointment.getStatus() instanceof CancelledAppointment) {
            return false;
        }
        return appointmentList.contains(appointment);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AppointmentTimeInterval))
            return false;
        
        AppointmentTimeInterval other = (AppointmentTimeInterval) obj;
        return appointmentList.equals(other.appointmentList) && date.equals(other.date) && timeInterval.equals(other.timeInterval);
    }
    
    @Override
    public String toString() {
        String result = "Date: " + date + " | Time interval: " + timeInterval;
        for (Appointment appointment : appointmentList.getAppointments()) {
            result += "\n\t" + appointment;
        }
        return result;
    }
}
