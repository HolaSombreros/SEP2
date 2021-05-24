package server.model.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.model.domain.appointment.Appointment;
import server.model.domain.appointment.TimeInterval;
import server.model.domain.appointment.Type;
import server.model.domain.appointment.VaccineAppointment;
import server.model.domain.user.Address;
import server.model.domain.user.NotAppliedStatus;
import server.model.domain.user.Nurse;
import server.model.domain.user.Patient;

import java.time.LocalDate;
import java.time.LocalTime;


import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {
    private Patient patient;
    private Nurse nurse;
    private Appointment appointment;
    
    @BeforeEach
    void setUp() {
        patient = new Patient("1204560000", "testpassword", "Test", "Person", new Address("TestStreet", "0", 8700, "Horsens"), "12345678", "test@email.com", new NotAppliedStatus());
        nurse = new Nurse("1205561111", "testpassword", "Test", "Person", new Address("TestStreet", "0", 8700, "Horsens"), "12345678", "test@email.com", "emp1");
        appointment = new VaccineAppointment(1, LocalDate.now(), new TimeInterval(1, LocalTime.of(8, 0), LocalTime.of(8, 20)), Type.VACCINE, patient, nurse);
    }

    @Test
    void setDateNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            appointment.setDate(null);
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