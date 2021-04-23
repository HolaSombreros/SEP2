package server.model.domain;

import server.model.validator.TimeValidator;

import java.io.Serializable;

public class Time implements Serializable {
    private int hour;
    private int minute;
    
    public Time(int hour, int minute) {
        setHour(hour);
        setMinute(minute);
    }

    public void setMinute(int minute) {
        TimeValidator.setMinute(minute);
        this.minute = minute;
    }
    
    public void setHour(int hour) {
        TimeValidator.setHour(hour);
        this.hour = hour;
    }

    public boolean isBefore(Time other){
        if(hour > other.hour){
            return false;
        }
        else if(hour == other.hour){
            if(minute > other.minute){
                return false;
            }
            else if(minute == other.minute){
                return false;
            }
        }
        return true;
    }
    
    public int getMinute() {
        return minute;
    }

    public int getHour() {
        return hour;
    }

    public Time copy() {
        return new Time(hour, minute);
    }
    
    public boolean equals(Object obj) {
        if (!(obj instanceof Time))
            return false;
        Time other = (Time) obj;
        return hour == other.hour && minute == other.minute;
    }

    public String toString() {
        return String.format("%02d:%02d", hour, minute);
    }
}
