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
    private Type type;
    private Status status;
    private User patient;
    private User nurse;
    
    public Appointment(Type type, User patient, User nurse) {
        id = idCounter;
        this.type = type;
        this.status = Status.UPCOMING;
        this.patient = patient;
        this.nurse = nurse;
        idCounter++;
    }
    
    public int getId() {
        return id;
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
            type.equals(appointment.type) &&
            status.equals(appointment.status) &&
            patient.equals(appointment.patient);
    }
    
    @Override
    public String toString() {
        return String.format("#%d: %s (%s) - %s\n", id, patient, type, status);
    }
}
