package server.model.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserListTest {
    private UserList list;
    
    @BeforeEach
    void setUp() {
        list = new UserList();
    }
    
    private void addOneUser() {
        User user = new Patient( "1805941234", "password", "Morten", "Frederik", "Hansen", new Address("Fabrikvej", "4", 8700, "Horsens"), "28800805", "morten.f.hansen@hotmail.com", new NotAppliedStatus());
        list.add(user);
    }
    
    private void addManyUsers() {
        list.add(new Patient( "1805941234", "password", "Morten", "Frederik", "Hansen", new Address("Teststreet", "20", 8700, "Horsens"), "98765432", "test@mail.com", new NotAppliedStatus()));
        list.add(new Patient( "1212120987", "password", "Test",  "Subject", new Address("Teststreet", "20", 8700, "Horsens"), "98765432", "test@mail.com", new NotAppliedStatus()));
        list.add(new Nurse( "0508910185", "password", "Test",  "Subject2", new Address("Teststreet", "20", 8700, "Horsens"), "98765432", "test@mail.com", "emp1"));
        list.add(new Administrator( "1802710395", "password", "Test",  "Subject3", new Address("Teststreet", "20", 8700, "Horsens"), "98765432", "test@mail.com", "emp2"));
        list.add(new Patient( "2809848372", "password", "Other",  "Person", new Address("Teststreet", "20", 8700, "Horsens"), "98765432", "test@mail.com", new NotAppliedStatus()));
    }
    
    @Test
    void getUserByCprZero() {
        assertEquals(null, list.getUserByCpr("1805941234"));
        assertEquals(null, list.getUserByCpr(null));
    }
    
    @Test
    void getUserByCprOne() {
        User user = new Patient( "1805941234", "password", "Morten", "Frederik", "Hansen", new Address("Fabrikvej", "4", 8700, "Horsens"), "28800805", "morten.f.hansen@hotmail.com", new NotAppliedStatus());
        list.add(user);
        assertEquals(user, list.getUserByCpr("1805941234"));
    }
    
    @Test
    void getUserByCprMany() {
        // same as above...
    }
    
    @Test
    void getUserByCprBoundary() {
        // nothing
    }
    
    @Test
    void getUserByCprException() {
        // nothing
    }
    
    // Returns empty list
    @Test
    void getUsersByCprAndNameZero() {
        assertEquals(0, list.getUsersByCprAndName("ten").size());
        assertEquals(0, list.getUsersByCprAndName(null).size());
    }
    
    @Test
    void getUsersByCprAndNameOne() {
        addOneUser();
        assertEquals(1, list.getUsersByCprAndName("Hans").size());
    }
    
    @Test
    void getUsersByCprAndNameMany() {
        addManyUsers();
        assertEquals(3, list.getUsersByCprAndName("ubje").size());
    }
    
    @Test
    void getUsersByCprAndNameBoundary() {
        // already done
    }
    
    @Test
    void getUsersByCprAndNameException() {
        // nothing
    }
    
    @Test
    void removeByCprZero() {
        // nothing
    }
    
    @Test
    void removeByCprOne() {
        addOneUser();
        assertEquals(1, list.size());
        list.removeByCpr("1805941234");
        assertEquals(0, list.size());
    }
    
    @Test
    void removeByCprMany() {
        addManyUsers();
        assertEquals(5, list.size());
        list.removeByCpr("1212120987");
        assertEquals(4, list.size());
    }
    
    @Test
    void removeByCprBoundary() {
        // already done
    }
    
    @Test
    void removeByCprException() {
        // nothing
    }
    
    @Test
    void containsZero() {
        assertFalse(list.contains("1805941234"));
    }
    
    @Test
    void containsOne() {
        addOneUser();
        assertTrue(list.contains("1805941234"));
    }
    
    @Test
    void containsMany() {
        addManyUsers();
        assertTrue(list.contains("1212120987"));
    }
    
    @Test
    void containsBoundary() {
        // already done
    }
    
    @Test
    void containsException() {
        // nothing
    }
    
    @Test
    void getNurseZero() {
        assertEquals(null, list.getNurse("0508910185"));
    }
    
    @Test
    void getNurseOne() {
        Nurse nurse = new Nurse( "0508910185", "password", "Test",  "Subject2", new Address("Teststreet", "20", 8700, "Horsens"), "98765432", "test@mail.com", "emp1");
        list.add(nurse);
        assertEquals(nurse, list.getNurse("0508910185"));
    }
    
    @Test
    void getNurseMany() {
        addManyUsers();
        assertEquals("Subject2", list.getNurse("0508910185").getLastName());
    }
    
    @Test
    void getNurseBoundary() {
        // already done
    }
    
    @Test
    void getNurseException() {
        // nothing
    }
    
    @Test
    void getPatientZero() {
        assertEquals(null, list.getPatient("1805941234"));
    }
    
    @Test
    void getPatientOne() {
        Patient patient = new Patient( "1805941234", "password", "Morten", "Frederik", "Hansen", new Address("Fabrikvej", "4", 8700, "Horsens"), "28800805", "morten.f.hansen@hotmail.com", new NotAppliedStatus());
        list.add(patient);
        assertEquals(patient, list.getPatient("1805941234"));
    }
    
    @Test
    void getPatientMany() {
        addManyUsers();
        assertEquals("Morten", list.getPatient("1805941234").getFirstName());
    }
    
    @Test
    void getPatientBoundary() {
        // already done
    }
    
    @Test
    void getPatientException() {
        // nothing
    }
    
    @Test
    void getAdminListZero() {
        assertEquals(0, list.getAdminList().size());
    }
    
    @Test
    void getAdminListOne() {
        list.add(new Administrator( "1802710395", "password", "Test",  "Subject3", new Address("Teststreet", "20", 8700, "Horsens"), "98765432", "test@mail.com", "emp2"));
        assertEquals(1, list.getAdminList().size());
    }
    
    @Test
    void getAdminListMany() {
        addManyUsers();
        list.add(new Administrator( "2611858274", "password", "Test",  "Subject4", new Address("Teststreet", "20", 8700, "Horsens"), "98765432", "test@mail.com", "emp3"));
        list.add(new Administrator( "0101982638", "password", "Test",  "Subject5", new Address("Teststreet", "20", 8700, "Horsens"), "98765432", "test@mail.com", "emp3"));
        assertEquals(3, list.getAdminList().size());
    }
    
    @Test
    void getAdminListBoundary() {
        // already done
    }
    
    @Test
    void getAdminListException() {
        // nothing
    }
    
    @Test
    void getNurseListZero() {
        assertEquals(0, list.getNurseList().size());
    }
    
    @Test
    void getNurseListOne() {
        list.add(new Nurse( "1802710395", "password", "Test",  "Subject3", new Address("Teststreet", "20", 8700, "Horsens"), "98765432", "test@mail.com", "emp2"));
        assertEquals(1, list.getNurseList().size());
    }
    
    @Test
    void getNurseListMany() {
        addManyUsers();
        list.add(new Nurse( "0912894918", "password", "Test",  "Subject4", new Address("Teststreet", "20", 8700, "Horsens"), "98765432", "test@mail.com", "emp3"));
        list.add(new Nurse( "2611858274", "password", "Test",  "Subject5", new Address("Teststreet", "20", 8700, "Horsens"), "98765432", "test@mail.com", "emp3"));
        assertEquals(3, list.getNurseList().size());
    }
    
    @Test
    void getNurseListBoundary() {
        // already done
    }
    
    @Test
    void getNurseListException() {
        // nothing
    }
    
    @Test
    void getPatientListZero() {
        assertEquals(0, list.getPatientList().size());
    }
    
    @Test
    void getPatientListOne() {
        addOneUser();
        assertEquals(1, list.getPatientList().size());
    }
    
    @Test
    void getPatientListMany() {
        addManyUsers();
        assertEquals(3, list.getPatientList().size());
    }
    
    @Test
    void getPatientListBoundary() {
        // already done
    }
    
    @Test
    void getPatientListException() {
        // nothing
    }
}