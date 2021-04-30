package server.model.domain;

import server.model.domain.appointment.Status;
import server.model.domain.appointment.UpcomingStatus;
import server.model.validator.AppointmentValidator;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Appointment implements Serializable {
    public static int idCounter = 1;
    private int id;
    private Type type;
    private Status status;
    private Patient patient;
    private Nurse nurse;
    private LocalDate date;
    private TimeInterval timeInterval;
    
    public Appointment(LocalDate date, TimeInterval timeInterval, Type type, Patient patient, Nurse nurse) {
        AppointmentValidator.appointmentValidator(date, timeInterval, patient, nurse);
        id = idCounter;
        this.type = type;
        this.status = new UpcomingStatus(this);
        this.patient = patient;
        this.nurse = nurse;
        this.date = date;
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

    public void setNurse(Nurse nurse){
        AppointmentValidator.validateNurse(nurse);
        this.nurse = nurse;
    }
    public void setPatient(Patient patient){
        AppointmentValidator.validatePatient(patient);
        this.patient = patient;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public Nurse getNurse() {
        return nurse;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        AppointmentValidator.validateDate(date);
        this.date = date;
    }
    
    public TimeInterval getTimeInterval() {
        return timeInterval;
    }
    
    public void setTimeInterval(TimeInterval timeInterval) {
        AppointmentValidator.validateTimeInterval(timeInterval);
        this.timeInterval = timeInterval;
    }
    
    public void cancel() {
        status.cancel(this);
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
