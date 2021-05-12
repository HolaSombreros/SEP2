package client.viewmodel;

import client.model.Model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import server.model.domain.appointment.Appointment;
import server.model.domain.appointment.CancelledAppointment;
import server.model.domain.appointment.TestAppointment;
import server.model.domain.appointment.TimeInterval;

import java.time.LocalDate;
import java.util.Optional;

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
        errorLabel.set("");
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
    private boolean confirmEditing()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm cancelling the appointment");
            alert.setHeaderText("Are you sure you want to cancel this appointment? \n\n" +
                    "Type: " + type.get() + "\n" +
                    "Date: " + date.get() + "\n" +
                    "Status: " + status.get() + "\n"+
                    "Time interval: " + timeInterval.get());
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
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
        Appointment appointment = model.getAppointmentById(viewState.getSelectedAppointment());
        if(!(appointment.getStatus() instanceof CancelledAppointment)) {
            if (confirmEditing()) {
                model.cancelAppointment(viewState.getSelectedAppointment());
                errorLabel.set("Appointment has been cancelled");
                loadAppointmentDetails();
            }
        }
        else
            errorLabel.set("This appointment is already cancelled!");
    }
    
    @Override
    public void rescheduleAppointment() {
        //TODO: Next sprints
    }
}
