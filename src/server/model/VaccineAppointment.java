package server.model;

public class VaccineAppointment extends Appointment {
    public VaccineAppointment(Date date, TimeInterval timeInterval, Type type, Patient patient, Nurse nurse) {
        super(date, timeInterval, type, patient, nurse);
    }
}
