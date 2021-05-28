package server.model.domain.user;

public class Notification {

    private int id;
    private String text;
    private boolean seen;
    private Patient patient;

    public Notification(int id, String text, boolean seen, Patient patient) {
        this.id = id;
        this.text = text;
        this.seen = seen;
        this.patient = patient;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isSeen() {
        return seen;
    }

    public Patient getPatient() {
        return patient;
    }


    public Notification setSeen(boolean seen) {
        this.seen = seen;
        return this;
    }

    public String toString(){
        return id + " : " + text + " " + (!seen ?"Read" : "Unread") + patient.getCpr();
    }
}
