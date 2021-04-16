package server.model;

public abstract class Appointment {
    public enum Type {
        TEST,
        VACCINE;
    }
    
    public enum Status {
        PREVIOUS,
        UPCOMING,
        CANCELLED;
    }
    
    private Date date;
    private TimeInterval timeInterval;
    private Type type;
    private Status status;
    private User patient;
    
    public Appointment(Date date, TimeInterval timeInterval, Type type, User patient) {
        this.date = date;
        this.timeInterval = timeInterval;
        this.type = type;
        this.status = Status.UPCOMING;
        this.patient = patient;
    }
    
    public Date getDate() {
        return date.copy();
    }
    
    public TimeInterval getTimeInterval() {
        return timeInterval;
    }
    
    public Type getType() {
        return type;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public User getPatient() {
        return patient;
    }
}
