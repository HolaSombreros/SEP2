package server.model.domain;

import java.time.LocalDate;

public class VaccineAppointment extends Appointment {
    public VaccineAppointment(LocalDate date, TimeInterval timeInterval, Type type, Patient patient, Nurse nurse) {
        super(date, timeInterval, type, patient, nurse);
    }
    
    public String toString() {
        return super.toString();
    }
}
