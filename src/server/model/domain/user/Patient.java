package server.model.domain.user;

import server.model.domain.chat.Chat;

public class Patient extends User {
    private VaccineStatus vaccineStatus;
    private Chat chat;
    
    // TODO - PASS CHAT IN CONSTRUCTORS
    public Patient(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email, VaccineStatus validForVaccine) {
        super(cpr, password, firstName, middleName, lastName, address, phone, email);
        setVaccineStatus(validForVaccine);
        chat = new Chat();
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
    
    public Chat getChat() {
        return chat;
    }
    
    public void setChat(Chat chat) {
        this.chat = chat;
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
