package server.model.domain.user;

import server.model.domain.chat.ChatLog;

public class Patient extends User {
    private VaccineStatus vaccineStatus;
<<<<<<< HEAD
    private ChatLog chatLog;
    
=======
    private Chat chat;

>>>>>>> fd72e1b089a1f57d38ec0804c40164e05a5855f5
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
<<<<<<< HEAD
    
    public ChatLog getChatLog() {
        return chatLog;
    }
    
    public void setChatLog(ChatLog chatLog) {
        this.chatLog = chatLog;
=======

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
>>>>>>> fd72e1b089a1f57d38ec0804c40164e05a5855f5
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Patient)) {
            return false;
        }
        Patient patient = (Patient) obj;
        return super.equals(obj) && vaccineStatus.equals(patient.vaccineStatus);
    }
<<<<<<< HEAD
    
    @Override
    public String toString() {
        return super.toString() + " | Vaccine Status: " + vaccineStatus.toString();
    }
=======

//    @Override
//    public String toString() {
//        return super.toString() + " | Vaccine Status: " + vaccineStatus.toString();
//    }
>>>>>>> fd72e1b089a1f57d38ec0804c40164e05a5855f5
}
