package server.model.domain.user;

import server.model.domain.appointment.TimeInterval;

import java.io.Serializable;

public class Shift implements Serializable {

    private int id;
    private TimeInterval timeInterval;

    public Shift(int id, TimeInterval timeInterval) {
        set(id,timeInterval);
    }

    public void set(int id, TimeInterval timeInterval) {
        this.id = id;
        this.timeInterval = timeInterval;
    }

    public int getId() {
        return id;
    }

    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    public boolean equals(Object obj){
        if(!(obj instanceof Shift))
            return false;
        Shift shift = (Shift) obj;
        return shift.id == id && shift.timeInterval.equals(timeInterval);
    }

    public String toString(){
        return id + " : " + timeInterval.toString();
    }

}
