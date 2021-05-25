package server.model.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.model.domain.user.Address;
import server.model.domain.user.NotAppliedStatus;
import server.model.domain.user.Patient;


import static org.junit.jupiter.api.Assertions.*;

class PatientTest {
    private Patient patient1;

    @BeforeEach
    void setUp() {
        patient1 = new Patient("121256-1234", "password", "Test", null, "Person", new Address("Street", "1", 8700, "Horsens"), "12345678", "email@address.com", new NotAppliedStatus());
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
            patient1.setCpr("021394-1234");
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
    void setPasswordNull() {
        assertThrows(IllegalArgumentException.class, () -> {
           patient1.setPassword(null);
        });
    }

    @Test
    void setPasswordEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            patient1.setPassword("");
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
    void setEmailNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            patient1.setEmail(null);
        });
    }

    @Test
    void setEmailEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            patient1.setEmail("");
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

    @Test
    void setInvalidEmail5() {
        assertThrows(IllegalArgumentException.class, () -> {
            patient1.setEmail("dd.c");
        });
    }

    @Test
    void setInvalidEmail6() {
        assertThrows(IllegalArgumentException.class, () -> {
            patient1.setEmail("dd@c");
        });
    }

    @Test
    void setPhoneNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            patient1.setPhone(null);
        });
    }

    @Test
    void setPhoneEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            patient1.setPhone("");
        });
    }

    @Test
    void setPhoneWithLetters() {
        assertThrows(IllegalArgumentException.class, () -> {
            patient1.setPhone("1234567a");
        });
    }
}