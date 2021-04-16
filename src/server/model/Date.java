package server.model;

import java.time.LocalDate;
import java.util.GregorianCalendar;

public class Date
{
  private int year;
  private int month;
  private int day;

  public Date(int day, int month, int year)
  {
    set(day, month, year);
  }

  /**
   * Sets the date to the current date
   */
  public Date()
  {
    today();
  }

  /**
   * @return Date object storing the current date
   */
  private static Date today()
  {
    GregorianCalendar currentDate = new GregorianCalendar();
    int currentDay = currentDate.get(GregorianCalendar.DATE);
    int currentMonth = currentDate.get(GregorianCalendar.MONTH) + 1;
    int currentYear = currentDate.get(GregorianCalendar.YEAR);
    Date today = new Date(currentDay, currentMonth, currentYear);
    return today;
  }
  
  public Date(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("Please input a date");
    }
    set(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
  }

  public void set(int day, int month, int year)
  {
    setYear(year);
    setMonth(month);
    setDay(day);
  }

  public void setYear(int year)
  {
    if (year < 0)
      throw new IllegalArgumentException("Invalid year");
    this.year = year;
  }

  public void setMonth(int month)
  {
    if (month < 1 || month > 12)
      throw new IllegalArgumentException("Invalid month");
    this.month = month;
  }

  public void setDay(int day)
  {
    if (day < 1 || day > numberOfDaysInMonth())
      throw new IllegalArgumentException("Invalid day");
    this.day = day;
  }

  public int getYear()
  {
    return year;
  }

  public int getMonth()
  {
    return month;
  }

  public int getDay()
  {
    return day;
  }

  public boolean isLeapYear()
  {
    if ((year % 4 == 0) && ((year % 100 == 0) || (year % 400 == 0)))
      return true;
    else
      return false;
  }

  public int numberOfDaysInMonth()
  {
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

  public boolean isBefore(Date other)
  {
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

  public Date copy()
  {
    return new Date(day, month, year);
  }

  public boolean equals(Object obj)
  {
    if (!(obj instanceof Date))
      return false;
    Date other = (Date) obj;
    return day == other.day && month == other.month && year == other.year;
  }

  @Override public String toString()
  {
    return String.format("%02d:%02d:%04d", day, month, year);
  }
}
