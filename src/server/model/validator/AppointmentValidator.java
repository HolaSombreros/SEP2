package server.model.validator;

import server.model.domain.*;

public class AppointmentValidator {
    public static void appointmentValidator(Date date, TimeInterval timeInterval, Patient patient, Nurse nurse) {
        validateDate(date);
        validateTimeInterval(timeInterval);
        validatePatient(patient);
        validateNurse(nurse);
    }
    
    public static void validatePatient(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Please specify a patient");
        }
    }
    
    public static void validateNurse(Nurse nurse) {
        if (nurse == null) {
            throw new IllegalArgumentException("Please specify a nurse");
        }
    }
    
    public static void validateDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Please specify a date");
        }
        if (date.isBefore(Date.today())) {
            throw new IllegalArgumentException("You cannot book an appointment for the previous days");
        }
    }
    
    public static void validateTimeInterval(TimeInterval timeInterval) {
        if (timeInterval == null) {
            throw new IllegalArgumentException("Please specify a time interval");
        }
    }
}
