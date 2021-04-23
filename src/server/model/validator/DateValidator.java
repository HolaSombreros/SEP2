package server.model.validator;

import server.model.domain.Date;

import java.time.LocalDate;

public class DateValidator {

    /**
     * Throws exception if the year is a negative integer
     *
     * @param year
     */
    public static void setYear(int year) {
        if (year < 0)
            throw new IllegalArgumentException("Invalid year");
    }
    /**
     * Throws exception if the month is a negative integer or is bigger than 12
     *
     * @param month
     */
    public static void setMonth(int month) {
        if (month < 1 || month > 12)
            throw new IllegalArgumentException("Invalid month");

    }
    /**
     * Throws exception if day does not correspond to the nr of days in the month
     *
     * @param date, day
     */
    public static void setDay(Date date, int day) {
        if (day < 1 || day > date.numberOfDaysInMonth())
            throw new IllegalArgumentException("Invalid day");
    }
    /**
     * Throws exception if a date is not provided
     *
     * @param date
     */
    public static void constructorValidator(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Please input a date");
        }

    }

}
