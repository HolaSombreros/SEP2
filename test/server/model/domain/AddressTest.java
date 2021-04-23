package server.model.domain;

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
      address1.setZipcode(12345);
    });
  }

  @Test void setInvalidZipcode2()
  {
    assertThrows(IllegalArgumentException.class, () -> {
      address1.setZipcode(123);
    });
  }

  @Test void setNullStreet()
  {
    assertThrows(IllegalArgumentException.class, () -> {
      address1.setStreet(null);
    });
  }

  @Test void setNullCity()
  {
    assertThrows(IllegalArgumentException.class, () -> {
      address1.setCity(null);
    });
  }

  @Test void setNullNumber()
  {
    assertThrows(IllegalArgumentException.class, () -> {
      address1.setNumber(null);
    });
  }
}