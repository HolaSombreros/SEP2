package server.model.validator;

import server.model.domain.*;

public class AppointmentValidator {

    public static void appointmentValidator(Date date, TimeInterval timeInterval, Appointment.Type type, Patient patient, Nurse nurse) {
        if (timeInterval == null) {
            throw new IllegalArgumentException("Please specify a time interval");
        }
        if (patient == null) {
            throw new IllegalArgumentException("Please specify a patient");
        }
        if (nurse == null) {
            throw new IllegalArgumentException("Please specify a nurse");
        }
    }
}
