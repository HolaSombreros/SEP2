package server.model;

public class TimeInterval {
    private Time from;
    private Time to;
    private User nurse;
    
    public TimeInterval(Time from, Time to, User nurse) {
        this.from = from.copy();
        this.to = to.copy();
        this.nurse = nurse;
    }
    
    public Time getFrom() {
        return from.copy();
    }
    
    public Time getTo() {
        return to.copy();
    }
    
    public User getNurse() {
        return nurse;
    }
}
