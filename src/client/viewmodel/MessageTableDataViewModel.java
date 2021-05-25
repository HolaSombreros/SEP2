package client.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import server.model.domain.user.Patient;

public class MessageTableDataViewModel {
    private StringProperty cpr;
    private StringProperty name;
    private StringProperty date;
    private StringProperty status;
    
    public MessageTableDataViewModel(Patient patient) {
        cpr = new SimpleStringProperty(patient.getCpr());
        name = new SimpleStringProperty(patient.getFullName());
        Message lastMessage = patient.getChat().get(patient.getgetChat.size() - 1);
        date = new SimpleStringProperty(lastMessage.getSendDate().toString());
        status = new SimpleStringProperty(message.getStatus().toString());
    }
    
    public StringProperty getCprProperty() {
        return cpr;
    }
    
    public StringProperty getNameProperty() {
        return name;
    }
    
    public StringProperty getDateProperty() {
        return date;
    }
    
    public StringProperty getStatusProperty() {
        return status;
    }
}
