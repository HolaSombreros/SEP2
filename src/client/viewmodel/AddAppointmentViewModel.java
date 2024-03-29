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
import server.model.domain.user.ApprovedStatus;
import server.model.domain.user.Patient;
import server.model.domain.appointment.TimeInterval;
import server.model.domain.appointment.Type;

import java.time.LocalDate;

public class AddAppointmentViewModel implements AddAppointmentViewModelInterface {
    private Model model;
    private ViewState viewState;
    private ObjectProperty<LocalDate> date;
    private ObservableList<Type> types;
    private ObjectProperty<Type> type;
    private ObservableList<TimeInterval> timeIntervals;
    private ObjectProperty<TimeInterval> timeInterval;
    private StringProperty error;
    private ObjectProperty<Paint> errorFill;
    
    public AddAppointmentViewModel(Model model, ViewState viewState) {
        this.model = model;
        this.viewState = viewState;
        date = new SimpleObjectProperty<>();
        types = FXCollections.observableArrayList();
        type = new SimpleObjectProperty<>(Type.values()[0]);
        timeIntervals = FXCollections.observableArrayList();
        timeInterval = new SimpleObjectProperty<>();
        error = new SimpleStringProperty();
        errorFill = new SimpleObjectProperty<>(Color.RED);
    }
    
    private void loadTypes() {
        types.clear();
        for (Type type : Type.values()) {
            if (type == Type.VACCINE) {
                if (viewState.getPatient().getVaccineStatus() instanceof ApprovedStatus) {
                    types.add(type);
                }
            }
            else {
                types.add(type);
            }
        }
        type.set(types.get(0));
    }
    
    @Override
    public void loadTimeIntervals() {
        timeIntervals.clear();
        if (date.get() != null) {
            timeIntervals.addAll(model.getAvailableTimeIntervals((date.get())).getTimeIntervals());
            if (timeIntervals.size() > 0) {
                timeInterval.set(timeIntervals.get(0));
                error.set("");
            }
            else {
                timeInterval.set(null);
                errorFill.set(Color.RED);
                error.set("No time intervals available this day");
            }
        }
    }
    
    @Override
    public void reset() {
        error.set("");
        resetInputs();
    }
    
    private void resetInputs() {
        loadTypes();
        date.set(null);
        timeIntervals.clear();
        timeInterval.set(null);
    }
    
    @Override
    public ObjectProperty<LocalDate> getDateProperty() {
        return date;
    }
    
    @Override
    public ObservableList<Type> getAllTypes() {
        return types;
    }
    
    @Override
    public ObjectProperty<Type> getTypeProperty() {
        return type;
    }
    
    @Override
    public ObservableList<TimeInterval> getAvailableTimeIntervals() {
        return timeIntervals;
    }
    
    @Override
    public ObjectProperty<TimeInterval> getTimeIntervalProperty() {
        return timeInterval;
    }
    
    @Override
    public StringProperty getErrorProperty() {
        return error;
    }
    
    @Override
    public ObjectProperty<Paint> getErrorFillProperty() {
        return errorFill;
    }
    
    @Override
    public void createAppointment() {
        try {
            model.addAppointment((date.get()), timeInterval.get(), type.get(), viewState.getPatient());
            errorFill.set(Color.GREEN);
            error.set("Appointment booked for " + date.get().toString() + " (" + timeInterval.get() + ")");
            resetInputs();
        }
        catch (Exception e) {
            errorFill.set(Color.RED);
            error.set(e.getMessage());
        }
    }
}
