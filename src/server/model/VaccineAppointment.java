package server.model;

public class VaccineAppointment extends Appointment {
    public VaccineAppointment(Date date, TimeInterval timeInterval, Type type, User patient,User nurse) {
        super(date, timeInterval, type, patient, nurse);
    }
}
