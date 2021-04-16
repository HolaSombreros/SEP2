package server.model;

public abstract class Appointment {
    public enum Type {
        TEST("Test"),
        VACCINE("Vaccine");
        
        private String type;
        
        Type(String type) {
            this.type = type;
        }
        
        @Override
        public String toString() {
            return type;
        }
    }
    
    public enum Status {
        PREVIOUS("Previous"),
        UPCOMING("Upcoming"),
        CANCELLED("Cancelled");
    
        private String status;
    
        Status(String status) {
            this.status = status;
        }
    
        @Override
        public String toString() {
            return type;
        }
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
