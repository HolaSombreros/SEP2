package server.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.model.domain.appointment.TimeInterval;
import server.model.domain.appointment.Type;
import server.model.domain.user.*;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ServerModelManagerTest
{
    private ServerModelManagerServer serverModelManager;

    @BeforeEach
    void setUp() throws RemoteException {
        serverModelManager = new ServerModelManagerServer();
    }

    @AfterEach
    void tearDown() {}

    //Login a user with matched combination of password and username
    @Test
    void loginOne() {
        assertEquals(serverModelManager.getUserList().getUserByCpr("2003045698"),serverModelManager.login("2003045698", "password"));
    }

    //Login a null user
    @Test
    void loginZero() {
        assertThrows(IllegalArgumentException.class, () ->
                serverModelManager.login(null, null));
    }

    /*Login a user with unmatched combination of password and username
    E from Zomb+e */
    @Test
    void loginUserWithBadCombination() {
        assertThrows(IllegalArgumentException.class, () ->
                serverModelManager.login("2003045698", "password2"));
    }

    //E from Zomb+e
    @Test
    void loginUserAlreadyLoggedIn() {
        serverModelManager.login("2003045698", "password");
        assertThrows(IllegalArgumentException.class, () ->
                serverModelManager.login("2003045698", "password2"));
    }

    @Test
    void register() throws RemoteException {
        serverModelManager.register("2807048574", "password",
                "Lines", null, "Echbert",
                "789716123","line8@email.com", "Slotsvej",
                "78B",8700, "Horsens");
        User user = serverModelManager.getUserList().getUserByCpr("2807048574");
        assertEquals(user, serverModelManager.getUserList().getUserByCpr("2807048574"));
    }

    @Test
    void registerAUserAlreadyRegistered() throws RemoteException {
        assertThrows(IllegalStateException.class, () ->
                serverModelManager.register("2504012368", "password",
                "Lines", null, "Echbert", "789716123",
                        "line8@email.com", "Slotsvej","78B",8700, "Horsens"));
    }

    @Test
    void editUserInformation() throws RemoteException {
        User user = serverModelManager.getUserList().getUserByCpr("2504012368");
        serverModelManager.editUserInformation(user,"password2",
                "Lines", null, "Echbert", "97161113",
                "line78@email.com", "Slotsvej","78B",8700);
        assertEquals("2504012368 password2 Lines Echbert 8700 - Horsens: Slotsvej 78B 97161113 " +
                        "line78@email.com | Vaccine Status: Not Applied",
                user.toString());
    }

    // a patient that has not applied before for vaccination
    @Test
    void applyForVaccination1() throws RemoteException {
        Patient patient = serverModelManager.getPatientList().getPatient("2504012368");
        VaccineStatus pendingStatus = serverModelManager.applyForVaccination(patient);
        assertEquals(pendingStatus.toString(),patient.getVaccineStatus().toString());
    }

    @Test
    void editSchedule() throws RemoteException {
        Nurse nurse = serverModelManager.getNurseList().getNurse("1302026584");
        LocalDate date = LocalDate.of(2021,5,24);
        serverModelManager.editSchedule(nurse, date, 2);
        assertEquals("2021-05-24 - 2021-05-30 2 : 14:00 : 20:00", nurse.getSchedule(date).toString());
    }

    @Test
    void addAppointment() throws RemoteException {
        LocalDate date = LocalDate.of(2021,5,24);
        Patient patient = serverModelManager.getPatientList().getPatient("2504012368");
        TimeInterval time = new TimeInterval(25,
                LocalTime.of(10,0), LocalTime.of(10, 20));
        assertEquals("#8: 2504012368 password2 Lines Echbert 8700 - Horsens: Slotsvej " +
                "78B 97161113 line78@email.com | Vaccine Status: Pending (Test) - " +
                "Upcoming, Date: 2021-05-24, Time 10:00 - 10:20 " +
                "No results available", serverModelManager.addAppointment(date, time, Type.TEST, patient).toString());
    }

    //cancel a finished appointment
    @Test
    void cancelAppointment1() throws RemoteException {
        assertThrows(IllegalStateException.class, () -> serverModelManager.cancelAppointment(3));
    }
}