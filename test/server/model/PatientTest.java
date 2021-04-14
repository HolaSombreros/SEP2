package server.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatientTest {
    private Patient patient1;
    
    @BeforeEach
    void setUp() {
        patient1 = new Patient("220202-1234", "abcdefgh", "Ib", null, "Nielsen", new Address("Vejlevej", "12A", 8700, "Horsens"), "+4512123434", "email@address.com", false);
    }
    
    @AfterEach
    void tearDown() {
    }
    
    @Test
    void setCprIncludingDash() {
        patient1.setCpr("180594-1234");
        assertEquals("1805941234", patient1.getCpr());
    }
    
    @Test
    void setCprTooLong1() {
        assertThrows(IllegalArgumentException.class, () -> {
            patient1.setCpr("1805941-1234");
        });
    }
    
    @Test
    void setCprTooLong2() {
        assertThrows(IllegalArgumentException.class, () -> {
            patient1.setCpr("180594-12345");
        });
    }
    
    @Test
    void setCprTooLong3() {
        assertThrows(IllegalArgumentException.class, () -> {
            patient1.setCpr("1805941234567");
        });
    }
    
    @Test
    void setCprTooShort1() {
        assertThrows(IllegalArgumentException.class, () -> {
            patient1.setCpr("180594-123");
        });
    }
    
    @Test
    void setCprTooShort2() {
        assertThrows(IllegalArgumentException.class, () -> {
            patient1.setCpr("18059-1234");
        });
    }
    
    @Test
    void setCprInvalidDay() {
        assertThrows(IllegalArgumentException.class, () -> {
            patient1.setCpr("320594-1234");
        });
    }
    
    @Test
    void setCprInvalidMonth() {
        assertThrows(IllegalArgumentException.class, () -> {
            patient1.setCpr("181394-1234");
        });
    }
    
    @Test
    void setCprNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            patient1.setCpr(null);
        });
    }
    
    @Test
    void setCprEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            patient1.setCpr("");
        });
    }
    
    @Test
    void setPasswordTooShort() {
        assertThrows(IllegalArgumentException.class, () -> {
            patient1.setPassword("1234");
        });
    }
    
    @Test
    void setPasswordTooLong() {
        assertThrows(IllegalArgumentException.class, () -> {
            patient1.setPassword("123456789012345678901234567890");
        });
    }
    
    @Test
    void setInvalidEmail1() {
        assertThrows(IllegalArgumentException.class, () -> {
            patient1.setEmail("123@com.d");
        });
    }
    
    @Test
    void setInvalidEmail2() {
        assertThrows(IllegalArgumentException.class, () -> {
            patient1.setEmail(" @asd.com");
        });
    }
    
    @Test
    void setInvalidEmail3() {
        assertThrows(IllegalArgumentException.class, () -> {
            patient1.setEmail("asd@ .com");
        });
    }
    
    @Test
    void setInvalidEmail4() {
        assertThrows(IllegalArgumentException.class, () -> {
            patient1.setEmail("d@d.c");
        });
    }
}