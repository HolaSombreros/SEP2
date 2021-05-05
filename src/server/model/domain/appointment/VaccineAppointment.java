package server.model.domain.appointment;

import server.model.domain.user.Nurse;
import server.model.domain.user.Patient;

import java.time.LocalDate;

public class VaccineAppointment extends Appointment {
    public VaccineAppointment(int id, LocalDate date, TimeInterval timeInterval, Type type, Patient patient, Nurse nurse) {
        super(id, date, timeInterval, type, patient, nurse);
    }
    
    public String toString() {
        return super.toString();
    }
}
