package server.model.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {
    private Patient patient;
    private Nurse nurse;
    private Appointment appointment;
    
    @BeforeEach
    void setUp() {
        patient = new Patient("1204560000", "testpassword", "Test", "Person", new Address("TestStreet", "0", 8700, "Horsens"), "12345678", "test@email.com", false);
        nurse = new Nurse("1205561111", "testpassword", "Test", "Person", new Address("TestStreet", "0", 8700, "Horsens"), "12345678", "test@email.com", "emp1");
        appointment = new VaccineAppointment(Date.today(), new TimeInterval(new Time(8, 0), new Time(8, 20)), Appointment.Type.VACCINE, patient, nurse);
    }
    
    @Test
    void setDateNull() {
        assertThrows(IllegalArgumentException.class, () -> {
//            appointment.setDate(null);
        });
    }
    
    @Test
    void setTimeIntervalNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            appointment.setTimeInterval(null);
        });
    }
    
    @Test
    void setPatientNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            appointment.setPatient(null);
        });
    }

    @Test
    void setNurseNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            appointment.setNurse(null);
        });
    }
}