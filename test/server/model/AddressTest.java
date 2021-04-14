package server.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest
{
  private Address address1;

  @BeforeEach void setUp()
  {
    address1 = new Address("Vejlevej", "12A", 8700, "Horsens");
  }

  @AfterEach void tearDown()
  {

  }

  @Test void setInvalidZipcode1()
  {
    assertThrows(IllegalArgumentException.class, () -> {
      address1.setZipcode(10000);
    });
  }

  @Test void setInvalidZipcode2()
  {
    assertThrows(IllegalArgumentException.class, () -> {
      address1.setZipcode(999);
    });
  }

  @Test void setNullStreet()
  {
    assertThrows(IllegalArgumentException.class, () -> {
      address1.setStreet(null);
    });
  }
  
  @Test void setEmptyStreet()
  {
    assertThrows(IllegalArgumentException.class, () -> {
      address1.setStreet("");
    });
  }

  @Test void setNullCity()
  {
    assertThrows(IllegalArgumentException.class, () -> {
      address1.setCity(null);
    });
  }
  
  @Test void setEmptyCity()
  {
    assertThrows(IllegalArgumentException.class, () -> {
      address1.setCity("");
    });
  }

  @Test void setNullNumber()
  {
    assertThrows(IllegalArgumentException.class, () -> {
      address1.setNumber(null);
    });
  }
  
  @Test void setEmptyNumber()
  {
    assertThrows(IllegalArgumentException.class, () -> {
      address1.setNumber("");
    });
  }
}