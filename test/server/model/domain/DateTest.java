package server.model.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateTest {
    private Date date;
    
    @BeforeEach
    void setUp() {
        date = new Date (8, 8, 2000);
    }
    
    private String stringVal() {
        return date.toString();
    }
    
    @Test
    void setDayZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            date.setDay(0);
        });
    }
    
    @Test
    void setDayOne() {
        date.setDay(1);
        assertEquals(stringVal(), "01-08-2000");
    }
    
    @Test
    void setDayMany() {
        date.setDay(5);
        assertEquals(stringVal(), "05-08-2000");
        
        date.setDay(22);
        assertEquals(stringVal(), "22-08-2000");
    }
    
    @Test
    void setDayBoundary() {
        // lower left done in Zero
    
        // upper left done in One
    
        // lower right
        date.setDay(31);
        assertEquals(stringVal(), "31-08-2000");
    
        // upper right
        assertThrows(IllegalArgumentException.class, () -> date.setDay(32));
    }
    
    @Test
    void setDayException() {
        // any negative value
        assertThrows(IllegalArgumentException.class, () -> date.setDay(-1));
    }
    
    @Test
    void setMonthZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            date.setMonth(0);
        });
    }
    
    @Test
    void setMonthOne() {
        date.setMonth(1);
        assertEquals(stringVal(), "08-01-2000");
    }
    
    @Test
    void setMonthMany() {
        date.setMonth(5);
        assertEquals(stringVal(), "08-05-2000");
        
        date.setMonth(10);
        assertEquals(stringVal(), "08-10-2000");
    }
    
    @Test
    void setMonthBoundary() {
        // lower left done in Zero
    
        // upper left done in One
    
        // lower right
        date.setMonth(12);
        assertEquals(stringVal(), "08-12-2000");
    
        // upper right
        assertThrows(IllegalArgumentException.class, () -> date.setMonth(13));
    }
    
    @Test
    void setMonthException() {
        // any negative value
        assertThrows(IllegalArgumentException.class, () -> date.setMonth(-1));
    }
    
    @Test
    void setYearZero() {
        date.setYear(0);
        assertEquals(stringVal(), "08-08-0");
    }
    
    @Test
    void setYearOne() {
        date.setYear(1);
        assertEquals(stringVal(), "08-08-1");
    }
    
    @Test
    void setYearMany() {
        date.setYear(612);
        assertEquals(stringVal(), "08-08-612");
        
        date.setYear(1994);
        assertEquals(stringVal(), "08-08-1994");
    }
    
    @Test
    void setYearBoundary() {
        assertThrows(IllegalArgumentException.class, () -> date.setYear(-1));
        
        // upper left done in Zero
        
        // lower right and upper right don't exist
    }
    
    @Test
    void setYearException() {
        // dont in Boundary()
    }
    
    @Test
    void isLeapYearZero() {
        // not possible - year cannot be 0
    }
    
    @Test
    void isLeapYearOne() {
        date.setYear(1);
        assertFalse(date.isLeapYear());
    }
    
    @Test
    void isLeapYearMany() {
        date.setYear(2000);
        assertTrue(date.isLeapYear());
        
        date.setYear(1818);
        assertFalse(date.isLeapYear());
        
        date.setYear(1920);
        assertTrue(date.isLeapYear());
        
        date.setYear(1902);
        assertFalse(date.isLeapYear());
    }
    
    @Test
    void isLeapYearBoundary() {
        // lower left
        date.setYear(1751);
        assertFalse(date.isLeapYear());
        
        // upper left - first leap year in 'modern sense'
        date.setYear(1752);
        assertTrue(date.isLeapYear());
        
        // lower right and upper right don't exist
    }
    
    @Test
    void isLeapYearException() {
        // no exceptions thrown but there are special cases
        // anything lower than year 1752 should return false - 1751 is checked in Boundary()
        date.setYear(400);
        assertFalse(date.isLeapYear());
    }
    
    @Test
    void numberOfDaysInMonth() {
        date.setMonth(8);
        assertEquals(31, date.numberOfDaysInMonth());
        
        date.setMonth(4);
        assertEquals(30, date.numberOfDaysInMonth());
        
        date.setMonth(2);
        date.setYear(1998);
        assertEquals(28, date.numberOfDaysInMonth());
        
        date.setYear(2000);
        date.setMonth(2);
        assertEquals(29, date.numberOfDaysInMonth());
    }
    
    @Test
    void isBeforeSameDay() {
        Date date2 = new Date(8, 8, 2000);
        assertFalse(date.isBefore(date2));
    }
    
    @Test
    void isBeforeDayIsBefore() {
        Date date2 = new Date(9, 8, 2000);
        assertTrue(date.isBefore(date2));
    }
    
    @Test
    void isBeforeDayIsAfter() {
        Date date2 = new Date(7, 8, 2000);
        assertFalse(date.isBefore(date2));
    }
    
    @Test
    void isBeforeMonthIsBefore() {
        Date date2 = new Date(8, 9, 2000);
        assertTrue(date.isBefore(date2));
    }
    
    @Test
    void isBeforeMonthIsAfter() {
        Date date2 = new Date(8, 7, 2000);
        assertFalse(date.isBefore(date2));
    }
    
    @Test
    void isBeforeYearIsBefore() {
        Date date2 = new Date(8, 8, 1999);
        assertFalse(date.isBefore(date2));
    }
    
    @Test
    void isBeforeYearIsAfter() {
        Date date2 = new Date(8, 8, 2001);
        assertTrue(date.isBefore(date2));
    }
}