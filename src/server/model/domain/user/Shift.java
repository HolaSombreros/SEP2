package server.model.domain.user;

import java.io.Serializable;
import java.time.LocalTime;

public class Shift implements Serializable {

    private int id;
    private LocalTime timeFrom;
    private LocalTime timeTo;


    public Shift(int id, LocalTime timeFrom, LocalTime timeTo) {
        set(id,timeFrom,timeTo);
    }

    public void set(int id,LocalTime timeFrom, LocalTime timeTo) {
        this.id = id;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
    }

    public int getId() {
        return id;
    }

    public LocalTime getTimeFrom() {
        return timeFrom;
    }

    public LocalTime getTimeTo() {
        return timeTo;
    }

    public boolean equals(Object obj){
        if(!(obj instanceof Shift))
            return false;
        Shift shift = (Shift) obj;
        return shift.id == id && shift.timeTo.equals(timeTo) && shift.timeFrom.equals(timeFrom);
    }

    public String toString(){
        return id + " : " + timeFrom.toString() + " : " + timeTo.toString();
    }

}
