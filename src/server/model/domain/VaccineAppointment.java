package server.model.domain;

public class VaccineAppointment extends Appointment {
    public VaccineAppointment(Date date, TimeInterval timeInterval, Type type, Patient patient, Nurse nurse) {
        super(date, timeInterval, type, patient, nurse);
    }
    
    public String toString() {
        return super.toString();
    }
}
