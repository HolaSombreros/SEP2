package server.model.domain;

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
    private Type type;
    private Status status;
    private Patient patient;
    private Nurse nurse;
    private Date date;
    private TimeInterval timeInterval;
    
    public Appointment(Date date, TimeInterval timeInterval, Type type, Patient patient, Nurse nurse) {
        if (timeInterval == null) {
            throw new IllegalArgumentException("Please specify a time interval");
        }
        if (patient == null) {
            throw new IllegalArgumentException("Please specify a patient");
        }
        if (nurse == null) {
            throw new IllegalArgumentException("Please specify a nurse");
        }
        id = idCounter;
        this.type = type;
        this.status = Status.UPCOMING;
        this.patient = patient;
        this.nurse = nurse;
        this.date = date.copy();
        this.timeInterval = timeInterval;
        idCounter++;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Type getType() {
        return type;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public Nurse getNurse() {
        return nurse;
    }
    
    public Date getDate() {
        return date.copy();
    }
    
    public TimeInterval getTimeInterval() {
        return timeInterval;
    }
    
    public void setTimeInterval(TimeInterval timeInterval) {
        this.timeInterval = timeInterval;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Appointment)) {
            return false;
        }
        Appointment appointment = (Appointment) obj;
        return id == appointment.id &&
            type.equals(appointment.type) &&
            status.equals(appointment.status) &&
            patient.equals(appointment.patient) &&
            nurse.equals(appointment.nurse);
    }
    
    @Override
    public String toString() {
        return String.format("#%d: %s (%s) - %s\n", id, patient, type, status);
    }
}