package server.model.domain.chat;

import server.model.domain.user.Administrator;
import server.model.domain.user.Patient;
import server.model.domain.user.User;

import java.time.LocalDate;
import java.time.LocalTime;

public class Message {

    private int id;
    private String message;
    private LocalDate date;
    private LocalTime time;
    private MessageStatus status;
    private Patient patient;
    private Administrator administrator;
    private User sender;

    public Message(int id, String message, LocalDate date, LocalTime time, MessageStatus status, Patient patient, Administrator administrator, User sender) {
        this.id = id;
        this.message = message;
        this.date = date;
        this.time = time;
        this.status = status;
        this.patient = patient;
        this.administrator = administrator;
        this.sender = sender;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public Patient getPatient() {
        return patient;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public User getSender() {
        return sender;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public boolean equals(Object obj){
        if(!(obj instanceof Message))
            return false;
        Message other = (Message) obj;
        return other.id == id && other.message.equals(message) && other.date.equals(date) && other.time.equals(time) && other.administrator.equals(administrator)
                && other.patient.equals(patient) && other.sender.equals(sender);
    }

    public String toString(){
        return id + ". " + message + " sent by: " + sender.getCpr() + " at: " + time.toString() + ", " + date.toString() + " status: " + status.toString();
    }
}