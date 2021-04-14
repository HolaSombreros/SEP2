package server.model;

public class Time
{
  private int hour;
  private int minute;
  private int second;

  public Time(int hour, int minute, int second)
  {
    setHour(hour);
    setMinute(minute);
    setSecond(second);
  }

  public Time(int hour, int minute)
  {
    setHour(hour);
    setMinute(minute);
  }

  public Time(int seconds)
  {
    setTimeInSeconds(seconds);
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

  public int getSecond()
  {
    return second;
  }

  public void setSecond(int second)
  {
    if (second >= 60 || second < 0)
      throw new IllegalArgumentException("Invalid time");
    this.second = second;
  }

  public void setTimeInSeconds(int seconds)
  {
    if (seconds >= 24 * 60 * 60 || seconds < 0)
      throw new IllegalArgumentException("Invalid time");
    hour = minute = second = 0;
    while (seconds >= 3600)
    {
      hour++;
      seconds -= 3600;
    }
    while (seconds >= 60)
    {
      minute++;
      seconds -= 60;
    }
    second = seconds;
  }

  public int TimeInSeconds()
  {
    return hour * 3600 + minute * 60 + second;
  }

  public Time copy()
  {
    return new Time(hour, minute, second);
  }

  public boolean equals(Object obj)
  {
    if (!(obj instanceof Time))
      return false;
    Time other = (Time) obj;
    return hour == other.hour && minute == other.minute && second == other.second;
  }

  public String toString()
  {
    return String.format("%02d:%02d:%02d", hour, minute, second);
  }
}
