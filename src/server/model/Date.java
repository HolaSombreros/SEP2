package server.model;

import java.util.GregorianCalendar;

public class Date {
    private int year;
    private int month;
    private int day;

    public Date(int day, int month, int year) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Sets the date to the current date
     * */
    public Date(){
        today();
    }

    /**
     * @return Date object storing the current date
     * */
    private static Date today(){
        GregorianCalendar currentDate = new GregorianCalendar();
        int currentDay = currentDate.get(GregorianCalendar.DATE);
        int currentMonth = currentDate.get(GregorianCalendar.MONTH)+1;
        int currentYear = currentDate.get(GregorianCalendar.YEAR);
        Date today = new Date(currentDay,currentMonth,currentYear);
        return today;
    }

    public void set(int day, int month, int year){
        this.day = day;
        this.year = year;
        this.month = month;
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

    public boolean isLeapYear(){
        if( (year % 4 == 0) && ((year % 100 == 0) || (year % 400 == 0 )))
            return true; //is leap year
        else
            return false; //is not leap year
    }

    public int numberOfDaysInMonth(){
        if(month == 2 )
            if(isLeapYear())
                return 29;
            else return 28;
        else if(month == 4 || month == 6 || month == 9 || month == 11)
            return 30;
        else
            return 31;
    }

    public boolean isBefore(Date other){
        if(year > other.year)
            return false;
        else
        if(year == other.year)
            if(month > other.month)
                return false;
            else
            if(month == other.month)
                if(day >= other.day)
                    return false;
                else
                    return true;
            else
                return true;
        else
            return true;

    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%04d", day, month, year);
    }
}
