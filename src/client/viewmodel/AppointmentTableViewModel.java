package client.viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import server.model.domain.appointment.Appointment;
import server.model.domain.appointment.TestAppointment;

public class AppointmentTableViewModel {
    private StringProperty dateProperty;
    private StringProperty timeProperty;
    private StringProperty typeProperty;
    private StringProperty statusProperty;
    private IntegerProperty idProperty;
    private StringProperty cprProperty;
    private StringProperty fullNameProperty;
    private StringProperty resultProperty;
    
    public AppointmentTableViewModel(Appointment appointment) {
        dateProperty = new SimpleStringProperty(appointment.getDate().toString());
        timeProperty = new SimpleStringProperty(appointment.getTimeInterval().toString());
        typeProperty = new SimpleStringProperty(appointment.getType().toString());
        statusProperty = new SimpleStringProperty(appointment.getStatus().toString());
        idProperty = new SimpleIntegerProperty(appointment.getId());
        cprProperty = new SimpleStringProperty(appointment.getPatient().getCpr());
        fullNameProperty = new SimpleStringProperty(appointment.getPatient().getFullName());
        if (appointment instanceof TestAppointment)
            resultProperty = new SimpleStringProperty(((TestAppointment) appointment).getResult().toString());
        else
            resultProperty = new SimpleStringProperty("-");
    }
    
    public StringProperty getDateProperty() {
        return dateProperty;
    }
    
    public StringProperty getTimeProperty() {
        return timeProperty;
    }
    
    public StringProperty getTypeProperty() {
        return typeProperty;
    }
    
    public StringProperty getStatusProperty() {
        return statusProperty;
    }
    
    public IntegerProperty getIdProperty() {
        return idProperty;
    }
    
    public StringProperty getCprProperty() {
        return cprProperty;
    }
    
    public StringProperty getFullNameProperty() {
        return fullNameProperty;
    }
    
    public StringProperty getResultProperty() {
        return resultProperty;
    }
}
