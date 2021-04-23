package server.model.domain;

import server.model.validator.DateValidator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.GregorianCalendar;

public class Date implements Serializable {
    private int year;
    private int month;
    private int day;
    
    public Date(int day, int month, int year) {
        set(day, month, year);
    }
    
    /**
     * @return Date object storing the current date
     */
    public static Date today() {
        GregorianCalendar currentDate = new GregorianCalendar();
        int currentDay = currentDate.get(GregorianCalendar.DATE);
        int currentMonth = currentDate.get(GregorianCalendar.MONTH) + 1;
        int currentYear = currentDate.get(GregorianCalendar.YEAR);
        return new Date(currentDay, currentMonth, currentYear);
    }
    
    public Date(LocalDate date) {
        DateValidator.constructorValidator(date);
        set(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
    }
    
    public void set(int day, int month, int year) {
        setYear(year);
        setMonth(month);
        setDay(day);
    }
    
    public void setYear(int year) {
        DateValidator.setYear(year);
        this.year = year;
    }
    
    public void setMonth(int month) {
        DateValidator.setMonth(month);
        this.month = month;
    }
    
    public void setDay(int day) {
        DateValidator.setDay(this, day);
        this.day = day;
    }
    
    public int getYear() {
        return year;
    }
    
    public int getMonth() {
        return month;
    }
    
    public int getDay() {
        return day;
    }

    public int numberOfDaysInMonth() {
        if (month == 2)
            if (isLeapYear())
                return 29;
            else
                return 28;
        else if (month == 4 || month == 6 || month == 9 || month == 11)
            return 30;
        else
            return 31;
    }
    public boolean isLeapYear() {
        return (((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) && year >= 1752);
    }

    public boolean isBefore(Date other) {
        if (year > other.year)
            return false;
        else if (year == other.year)
            if (month > other.month)
                return false;
            else if (month == other.month)
                if (day >= other.day)
                    return false;
        return true;
    }
    
    public Date copy() {
        return new Date(day, month, year);
    }
    
    public boolean equals(Object obj) {
        if (!(obj instanceof Date))
            return false;
        Date other = (Date) obj;
        return day == other.day && month == other.month && year == other.year;
    }
    
    @Override
    public String toString() {
        return String.format("%02d-%02d-%d", day, month, year);
    }

    public String getDateSQL() {
        return year + "-" + month + "-" + day;
    }
}
