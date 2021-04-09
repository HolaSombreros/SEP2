package server.model;

import org.junit.jupiter.api.Test;

class PatientTest {
    
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        Patient patient1 = new Patient("220202-1234", "abcdefgh", "Ib", null, "Nielsen", new Address("Vejlevej", "12A", 8700, "Horsens"), "+45 12 12 3434", "email@address.com")
    }
    
    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }
    
    @Test
    void setCpr() {
    }
    
    @Test
    void setPassword() {
    }
    
    @Test
    void setFirstName() {
    }
    
    @Test
    void setMiddleName() {
    }
    
    @Test
    void setLastName() {
    }
    
    @Test
    void setAddress() {
    }
    
    @Test
    void setPhone() {
    }
    
    @Test
    void setEmail() {
    }
}