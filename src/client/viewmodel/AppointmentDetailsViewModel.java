package client.viewmodel;

import client.model.Model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.model.domain.appointment.Appointment;
import server.model.domain.appointment.TestAppointment;
import server.model.domain.appointment.TimeInterval;

import java.time.LocalDate;

public class AppointmentDetailsViewModel implements AppointmentDetailsViewModelInterface {
    private StringProperty type;
    private ObjectProperty<LocalDate> date;
    private StringProperty status;
    private StringProperty errorLabel;
    private ObservableList<TimeInterval> listOfTimeIntervals;
    private ObjectProperty<TimeInterval> timeInterval;
    private StringProperty result;
    private StringProperty resultLabel;
    
    private Model model;
    private ViewState viewState;
    
    public AppointmentDetailsViewModel(Model model, ViewState viewState) {
        this.model = model;
        this.viewState = viewState;
        date = new SimpleObjectProperty<>();
        type = new SimpleStringProperty();
        result = new SimpleStringProperty();
        status = new SimpleStringProperty();
        timeInterval = new SimpleObjectProperty<>();
        listOfTimeIntervals = FXCollections.observableArrayList();
        errorLabel = new SimpleStringProperty();
        resultLabel = new SimpleStringProperty("Result");
    }
    
    @Override
    public void reset() {
        listOfTimeIntervals.clear();
        loadAppointmentDetails();
    }
    
    private void loadAppointmentDetails() {
        Appointment appointment = model.getAppointmentById(viewState.getSelectedAppointment());
        if(appointment != null)
        {
            date.set(LocalDate.of(appointment.getDate().getYear(), appointment.getDate().getMonth(), appointment.getDate().getDayOfMonth()));
            listOfTimeIntervals.addAll(model.getAvailableTimeIntervals(date.get()).getTimeIntervals());
            timeInterval.set(appointment.getTimeInterval());
            type.set(appointment.getType().toString());
            status.set(appointment.getStatus().toString());
            if (appointment instanceof TestAppointment) {
                TestAppointment testAppointment = (TestAppointment) appointment;
                result.set(testAppointment.getResult().toString());
            }
            else {
                resultLabel.set("");
                result.set("");
            }
        }
    }
    
    @Override
    public StringProperty getTypeProperty() {
        return type;
    }
    
    @Override
    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }
    
    @Override
    public StringProperty statusProperty() {
        return status;
    }
    
    @Override
    public StringProperty errorLabelProperty() {
        return errorLabel;
    }
    
    @Override
    public ObservableList<TimeInterval> getListOfTimeIntervals() {
        return listOfTimeIntervals;
    }
    
    @Override
    public ObjectProperty<TimeInterval> getTimeInterval() {
        return timeInterval;
    }
    
    @Override
    public StringProperty resultProperty() {
        return result;
    }
    
    @Override
    public StringProperty resultLabelProperty() {
        return resultLabel;
    }
    
    @Override
    public void cancelAppointment() {
        //TODO: Next sprints
    }
    
    @Override
    public void rescheduleAppointment() {
        //TODO: Next sprints
    }
}
