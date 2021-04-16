package server.model;

public class VaccineAppointment extends Appointment {
    public VaccineAppointment(Date date, TimeInterval timeInterval, Type type, User patient) {
        super(date, timeInterval, type, patient);
    }
}
