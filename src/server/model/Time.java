package server.model;

import java.io.Serializable;

public class Time implements Serializable
{
  private int hour;
  private int minute;

  public Time(int hour, int minute)
  {
    setHour(hour);
    setMinute(minute);
  }

  public int getHour()
  {
    return hour;
  }

  public void setHour(int hour)
  {
    if (hour >= 24 || hour < 0)
      throw new IllegalArgumentException("Invalid time");
    this.hour = hour;
  }

  public int getMinute()
  {
    return minute;
  }

  public void setMinute(int minute)
  {
    if (minute >= 60 || minute < 0)
      throw new IllegalArgumentException("Invalid time");
    this.minute = minute;
  }

  public Time copy()
  {
    return new Time(hour, minute);
  }

  public boolean equals(Object obj)
  {
    if (!(obj instanceof Time))
      return false;
    Time other = (Time) obj;
    return hour == other.hour && minute == other.minute;
  }

  public String toString()
  {
    return String.format("%02d:%02d", hour, minute);
  }
}
