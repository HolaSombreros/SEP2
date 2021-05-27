package client.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import server.model.domain.chat.Message;
import server.model.domain.chat.ReadStatus;
import server.model.domain.user.Patient;

public class MessageTableDataViewModel {
    private StringProperty cpr;
    private StringProperty name;
    private StringProperty date;
    private StringProperty status;
    
    public MessageTableDataViewModel(Patient patient) {
        cpr = new SimpleStringProperty(patient.getCpr());
        name = new SimpleStringProperty(patient.getFullName());
        if (patient.getChatLog().size() > 0) {
            Message lastMessage = patient.getChatLog().get(patient.getChatLog().size() - 1);
            date = new SimpleStringProperty(lastMessage.getDate().toString());
            if (lastMessage.getAdministrator() == null) {
                status = new SimpleStringProperty(lastMessage.getStatus().toString());
            }
            else {
                status = new SimpleStringProperty(new ReadStatus().toString());
            }
        }
        else {
            date = new SimpleStringProperty("N/A");
            status = new SimpleStringProperty("N/A");
        }
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
