package server.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatientTest {
    private Patient patient1;
    
    @BeforeEach
    void setUp() {
        patient1 = new Patient("220202-1234", "abcdefgh", "Ib", null, "Nielsen", new Address("Vejlevej", "12A", 8700, "Horsens"), "+4512123434", "email@address.com");
    }
    
    @AfterEach
    void tearDown() {
    }
    
    @Test
    void setCprIncludingDash() {
        patient1.setCpr("180594-1234");
        assertEquals("180594-1234", patient1.getCpr());
    }
    
    @Test
    void setCprInvalidLength1() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            patient1.setCpr("1805941-1234");
        });
    }
    
    @Test
    void setCprInvalidLength2() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            patient1.setCpr("180594-12345");
        });
    }
}