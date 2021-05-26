package server.model.domain.chat;

import server.model.domain.user.Administrator;
import server.model.domain.user.Patient;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Message implements Serializable {
    private int id;
    private String message;
    private LocalDate date;
    private LocalTime time;
    private MessageStatus status;
    private Patient patient;
    private Administrator administrator;
    
    public Message(int id, String message, Patient patient, Administrator administrator) {
        this.id = id;
        this.message = message;
        date = LocalDate.now();
        time = LocalTime.now();
        status = new UnreadStatus();
        this.patient = patient;
        this.administrator = administrator;
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
    
    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Message))
            return false;
        Message other = (Message) obj;
        // TODO - if admin is null this will scream :<
        return other.id == id && other.message.equals(message) && other.date.equals(date) && other.time.equals(time) && other.administrator.equals(administrator)
                && other.patient.equals(patient);
    }
    
    @Override
    public String toString() {
        return "Message{" + "id=" + id + ", message='" + message + '\'' + ", date=" + date + ", time=" + time + ", status=" + status + ", patient=" + patient + ", administrator=" + administrator
            + '}';
    }
}
