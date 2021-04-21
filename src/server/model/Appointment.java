package server.model;

import java.io.Serializable;

public abstract class Appointment implements Serializable {
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
            return status;
        }
    }
    
    public static int idCounter = 1;
    private int id;
    private Date date;
    private TimeInterval timeInterval;
    private Type type;
    private Status status;
    private User patient;
    
    public Appointment(Date date, TimeInterval timeInterval, Type type, User patient) {
        id = idCounter;
        this.date = date;
        this.timeInterval = timeInterval;
        this.type = type;
        this.status = Status.UPCOMING;
        this.patient = patient;
        idCounter++;
    }
    
    public int getId() {
        return id;
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
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Appointment)) {
            return false;
        }
        Appointment appointment = (Appointment) obj;
        return id == appointment.id &&
            date.equals(appointment.date) &&
            timeInterval.equals(appointment.timeInterval) &&
            type.equals(appointment.type) &&
            status.equals(appointment.status) &&
            patient.equals(appointment.patient);
    }
    
    @Override
    public String toString() {
        return String.format("#%d: %s %s | %s | %s | %s\n", id, date, timeInterval, type, status, patient);
    }
}
