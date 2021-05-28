package server.model.domain.user;

public class Notification {

    private int id;
    private String text;
    private boolean status;
    private Patient patient;

    public Notification(int id, String text, boolean status, Patient patient) {
        this.id = id;
        this.text = text;
        this.status = status;
        this.patient = patient;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isStatus() {
        return status;
    }

    public Patient getPatient() {
        return patient;
    }


    public Notification setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public String toString(){
        return id + " : " + text + " " + (!status ?"Read" : "Unread") + patient.getCpr();
    }
}
