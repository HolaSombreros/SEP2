package server.model.domain.user;

import server.model.domain.chat.ChatLog;

public class Patient extends User {
    private VaccineStatus vaccineStatus;
    private ChatLog chatLog;
    
    // TODO - PASS CHAT IN CONSTRUCTORS
    public Patient(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email, VaccineStatus validForVaccine) {
        super(cpr, password, firstName, middleName, lastName, address, phone, email);
        setVaccineStatus(validForVaccine);
        chatLog = new ChatLog();
    }
    
    public Patient(String cpr, String password, String firstName, String lastName, Address address, String phone, String email, VaccineStatus validForVaccine) {
        this(cpr, password, firstName, null, lastName, address, phone, email, validForVaccine);
    }
    
    public VaccineStatus getVaccineStatus() {
        return vaccineStatus;
    }
    
    public void setVaccineStatus(VaccineStatus vaccineStatus) {
        this.vaccineStatus = vaccineStatus;
    }
    
    public ChatLog getChatLog() {
        return chatLog;
    }
    
    public void setChatLog(ChatLog chatLog) {
        this.chatLog = chatLog;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Patient)) {
            return false;
        }
        Patient patient = (Patient) obj;
        return super.equals(obj) && vaccineStatus.equals(patient.vaccineStatus);
    }
    
    @Override
    public String toString() {
        return super.toString() + " | Vaccine Status: " + vaccineStatus.toString();
    }
}
