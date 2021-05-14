package server.model.validator;

import server.model.domain.appointment.TimeInterval;
import server.model.domain.user.Nurse;
import server.model.domain.user.Patient;

import java.time.LocalDate;

public class AppointmentValidator {
    public static void validateAppointment(LocalDate date, TimeInterval timeInterval, Patient patient, Nurse nurse) {
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
    
    public static void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Please specify a date");
        }
    }
    
    public static void validateTimeInterval(TimeInterval timeInterval) {
        if (timeInterval == null) {
            throw new IllegalArgumentException("Please specify a time interval");
        }
    }
}
