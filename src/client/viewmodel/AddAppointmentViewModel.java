package client.viewmodel;

import client.model.Model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import server.model.*;

import java.time.LocalDate;

public class AddAppointmentViewModel {
    private Model model;
    private ViewState viewState;
    private ObjectProperty<LocalDate> date;
    private ObservableList<Appointment.Type> types;
    private ObjectProperty<Appointment.Type> type;
    private ObservableList<TimeInterval> timeIntervals;
    private ObjectProperty<TimeInterval> timeInterval;
    private StringProperty error;
    private ObjectProperty<Paint> errorFill;
    
    public AddAppointmentViewModel(Model model, ViewState viewState) {
        this.model = model;
        this.viewState = viewState;
        date = new SimpleObjectProperty<>();
        types = FXCollections.observableArrayList();
        type = new SimpleObjectProperty<>(Appointment.Type.values()[0]);
        timeIntervals = FXCollections.observableArrayList();
        timeInterval = new SimpleObjectProperty<>();
        loadTypes();
        loadTimeIntervals();
        error = new SimpleStringProperty();
        errorFill = new SimpleObjectProperty<>(Color.RED);
    }
    
    private void loadTypes() {
        types.clear();
        types.addAll(Appointment.Type.values());
        type.set(types.get(0));
    }
    
    private void loadTimeIntervals() {
        // TODO: Get available time intervals from the server
    }
    
    public void reset() {
        error.set("");
        resetInputs();
    }
    
    private void resetInputs() {
        date.set(null);
        loadTypes();
    }
    
    public ObjectProperty<LocalDate> getDateProperty() {
        return date;
    }
    
    public ObservableList<Appointment.Type> getAllTypes() {
        return types;
    }
    
    public ObjectProperty<Appointment.Type> getTypeProperty() {
        return type;
    }
    
    public ObservableList<TimeInterval> getAvailableTimeIntervals() {
        return timeIntervals;
    }
    
    public ObjectProperty<TimeInterval> getTimeIntervalProperty() {
        return timeInterval;
    }
    
    public StringProperty getErrorProperty() {
        return error;
    }
    
    public ObjectProperty<Paint> getErrorFillProperty() {
        return errorFill;
    }
    
    public void createAppointment() {
        try {
            model.addAppointment(new Date(date.get()), timeInterval.get(), type.get(), viewState.getUser());
            errorFill.set(Color.GREEN);
            error.set("Appointment at " + date.get() + " " + timeInterval.get() + " successfully created!");
            resetInputs();
        }
        catch (Exception e) {
            errorFill.set(Color.RED);
            error.set(e.getMessage());
        }
    }
}
