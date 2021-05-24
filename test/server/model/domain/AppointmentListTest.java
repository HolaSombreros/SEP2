package server.model.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.ManagerFactory;
import server.model.domain.appointment.*;
import server.model.domain.user.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentListTest {
    private AppointmentList list;
    ManagerFactory managerFactory = new ManagerFactory();
    UserList userList;

    @BeforeEach
    void setUp() {
        list = new AppointmentList();
        try {
            userList = managerFactory.getUserManager().getAllUsers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
    }

    void addToList() {
        Appointment appointment1 = new TestAppointment(1, LocalDate.now(), new TimeInterval(1, LocalTime.of(8, 0), LocalTime.of(8, 20)), Type.TEST, userList.getPatientList().getPatient("1407026358"), userList.getNurse("1805941234"));
        Appointment appointment2 = new TestAppointment(2, LocalDate.now(), new TimeInterval(1, LocalTime.of(8, 0), LocalTime.of(8, 20)), Type.TEST, userList.getPatientList().getPatient("1407026358"), userList.getNurse("1805941234"));
        Appointment appointment3 = new TestAppointment(3, LocalDate.now(), new TimeInterval(1, LocalTime.of(8, 0), LocalTime.of(8, 20)), Type.TEST, userList.getPatientList().getPatient("3105026358"), userList.getNurse("1805941234"));
        Appointment appointment4 = new TestAppointment(4, LocalDate.now(), new TimeInterval(1, LocalTime.of(8, 0), LocalTime.of(8, 20)), Type.TEST, userList.getPatientList().getPatient("1407026358"), userList.getNurse("1805941234"));
        Appointment appointment5 = new TestAppointment(5, LocalDate.now(), new TimeInterval(1, LocalTime.of(8, 0), LocalTime.of(8, 20)), Type.TEST, userList.getPatientList().getPatient("3105026358"), userList.getNurse("1805941234"));
        list.add(appointment1);
        list.add(appointment2);
        list.add(appointment3);
        list.add(appointment4);
        list.add(appointment5);
    }

    @Test
    void sizeEmpty() {
        assertEquals(0,list.size());
    }

    @Test
    void sizeWithElements() {
        addToList();
        assertEquals(5,list.size());
    }

    @Test
    void addNullElement() {
        assertThrows(IllegalArgumentException.class, ()-> list.add(null));
    }

    @Test void addElement() {

        list.add( new TestAppointment(5, LocalDate.now(), new TimeInterval(1, LocalTime.of(8, 0), LocalTime.of(8, 20)), Type.TEST,userList.getPatient("3105026358"),userList.getNurse("1805941234")));
        assertEquals(1,list.size());
    }

    @Test void removeElementWhenListEmpty(){
        assertThrows(IllegalArgumentException.class, ()->list.remove(1));
    }

    @Test void removeElementWithNegativeIndex(){
        assertThrows(IllegalArgumentException.class, ()->list.remove(-1));
    }

    @Test void removeElementWithIndexHigherThanSize(){
        addToList();
        assertThrows(IllegalArgumentException.class, ()->list.remove(7));
    }

    @Test void removeElement(){
        addToList();
        list.remove(1);
        assertEquals(4,list.size());
    }

    @Test void containsInEmptyList(){
        addToList();
        assertEquals(false,list.contains(new TestAppointment(6, LocalDate.now(), new TimeInterval(1, LocalTime.of(8, 0), LocalTime.of(8, 20)), Type.TEST, userList.getPatient("3105026358"),userList.getNurse("1805941234"))));
    }

    @Test void containsAfterRemove(){
        addToList();
        Appointment appointment = list.getAppointmentById(2);
        list.remove(1);
        assertEquals(false, list.contains(appointment));
    }

    @Test void containsElementInList(){
        addToList();
        Appointment appointment = new TestAppointment(5, LocalDate.now(), new TimeInterval(1, LocalTime.of(8, 0), LocalTime.of(8, 20)), Type.TEST, userList.getPatient("3105026358"),userList.getNurse("1805941234"));
        assertEquals(true,list.contains(appointment));
    }

    @Test void getAppointmentByNegativeId(){
        assertThrows(IllegalArgumentException.class, () -> list.getAppointmentById(-6));
    }

    @Test void getAppointmentByIdZero(){
        assertThrows(IllegalArgumentException.class, () -> list.getAppointmentById(0));
    }

    @Test void getAppointmentByIdOne(){
        addToList();
        Appointment appointment = list.get(1);
        assertEquals(appointment, list.getAppointmentById(2));
    }

    @Test void getAppointmentByIdHigherThanListSize(){
        addToList();
        assertEquals(null, list.getAppointmentById(6));
    }

    @Test void getAppointmentsByUserWithUserNull(){
        addToList();
        assertThrows(IllegalArgumentException.class, () -> list.getAppointmentsByUser(null));
    }

    @Test void getAppointmentsByUserWithNoAppointments(){
        Patient patient = new Patient("2904010987", "password", "Adriana", null, "Grecea", new Address("Clujstreet", "319", 9150, "Romania"), "94735271", "adriana@grecea.net",new NotAppliedStatus());
        assertEquals(0, list.getAppointmentsByUser(patient).size());
    }

    @Test void getAppointmentsByPatient(){
        addToList();
        AppointmentList list2 = new AppointmentList();
        list2.add(new TestAppointment(3, LocalDate.now(), new TimeInterval(1, LocalTime.of(8, 0), LocalTime.of(8, 20)), Type.TEST, userList.getPatientList().getPatient("3105026358"), userList.getNurse("1805941234")));
        list2.add(new TestAppointment(5, LocalDate.now(), new TimeInterval(1, LocalTime.of(8, 0), LocalTime.of(8, 20)), Type.TEST, userList.getPatientList().getPatient("3105026358"), userList.getNurse("1805941234")));
        assertEquals(list2,list.getAppointmentsByUser(userList.getPatient("3105026358")));
    }

    @Test void getAppointmentsByNurse(){
        addToList();
        assertEquals(list,list.getAppointmentsByUser(userList.getNurse("1805941234")));
    }

    @Test void getIndexNegative(){
        assertThrows(IllegalArgumentException.class, () -> list.get(-7));
    }

    @Test void getIndexZero(){
        addToList();
        Appointment appointment = list.getAppointmentById(1);
        assertEquals(appointment, list.get(0));
    }

    @Test void getIndexHigherThanListSize(){
        addToList();
        assertThrows(IllegalArgumentException.class, () -> list.get(7));
    }



}