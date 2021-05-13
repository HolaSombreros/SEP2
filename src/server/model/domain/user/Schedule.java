package server.model.domain.user;

import server.model.domain.appointment.TimeInterval;

import java.io.Serializable;
import java.time.LocalDate;

public class Schedule implements Serializable
{
  private LocalDate day;
  private TimeInterval timeInterval;

  public Schedule (LocalDate day, TimeInterval timeInterval)
  {
    if (day.isBefore(LocalDate.now()))
      throw new IllegalArgumentException("Not valid date");
    this.day = day;
    this.timeInterval = timeInterval;
  }

  public LocalDate getDay()
  {
    return day;
  }

  public void setDay(LocalDate day)
  {
    this.day = day;
  }

  public TimeInterval getTimeInterval()
  {
    return timeInterval;
  }

  public void setTimeInterval(TimeInterval timeInterval)
  {
    this.timeInterval = timeInterval;
  }

  public String toString()
  {
    return day.toString() + " " + timeInterval.toString();
  }
}
