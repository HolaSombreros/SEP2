package server.model.domain.appointment;

import server.model.domain.user.Nurse;
import server.model.domain.user.Patient;
import server.model.validator.AppointmentValidator;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;

public abstract class Appointment implements Serializable {
    private int id;
    private Type type;
    private Status status;
    private Patient patient;
    private Nurse nurse;
    private LocalDate date;
    private TimeInterval timeInterval;
    
    public Appointment(int id, LocalDate date, TimeInterval timeInterval, Type type, Patient patient, Nurse nurse) {
        AppointmentValidator.appointmentValidator(date, timeInterval, patient, nurse);
        this.id = id;
        this.type = type;
        this.status = new UpcomingAppointment(this);
        this.patient = patient;
        this.nurse = nurse;
        this.date = date;
        this.timeInterval = timeInterval;
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
    
    public boolean cancel() {
        status.cancel(this);
        return status instanceof CanceledAppointment;
    }
    public void rescheduleAppointment(LocalDate localDate, TimeInterval timeInterval){
        //TODO: DOUBLE CHECK IT
       setDate(localDate);
       setTimeInterval(timeInterval);
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
        return String.format("#%d: %s (%s) - %s, Date: %s, Time %s\n", id, patient, type, status, date, timeInterval);
    }


}
