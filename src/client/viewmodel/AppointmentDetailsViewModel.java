package client.viewmodel;

import client.model.AppointmentModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import server.model.domain.appointment.*;
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
    
    private AppointmentModel appointmentModel;
    private ViewState viewState;
    
    public AppointmentDetailsViewModel(AppointmentModel appointmentModel, ViewState viewState) {
        this.appointmentModel = appointmentModel;
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
        loadTimeIntervals();
        loadAppointmentDetails();
    }

    @Override
    public void loadTimeIntervals(){
        listOfTimeIntervals.clear();
        if(date.get() != null){
            listOfTimeIntervals.addAll(appointmentModel.getAvailableTimeIntervals(date.get()).getTimeIntervals());
            // TODO - revisit this
            if(listOfTimeIntervals.size() > 0){
                timeInterval.set(listOfTimeIntervals.get(0));
                errorLabel.set("");
            }
            else{
                timeInterval.set(null);
                errorLabel.set("No time intervals available this day");
            }
        }
    }
    
    private void loadAppointmentDetails() {
        Appointment appointment = appointmentModel.getAppointmentById(viewState.getSelectedAppointment());
        if(appointment != null)
        {
            date.set(LocalDate.of(appointment.getDate().getYear(), appointment.getDate().getMonth(), appointment.getDate().getDayOfMonth()));
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
    private boolean typeOfConfirmation(int number){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = null;
        switch (number)
        {
            case 1:
                alert.setTitle("Confirm cancelling the appointment");
                alert.setHeaderText("Are you sure you want to cancel this appointment? \n\n" +
                        "Type: " + type.get() + "\n" +
                        "Date: " + date.get() + "\n" +
                        "Status: " + status.get() + "\n" +
                        "Time interval: " + timeInterval.get());
                result = alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Confirm rescheduling the appointment");
                alert.setHeaderText("Are you sure you want to reschedule this appointment? \n\n" +
                        "Type: " + type.get() + "\n" +
                        "Date: " + date.get() + "\n" +
                        "Status: " + status.get() + "\n" +
                        "Time interval: " + timeInterval.get());
                result = alert.showAndWait();
                break;
        }
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
        Appointment appointment = appointmentModel.getAppointmentById(viewState.getSelectedAppointment());
        if((appointment.getStatus() instanceof UpcomingAppointment)) {
            if (typeOfConfirmation(1)) {
                appointmentModel.cancelAppointment(viewState.getSelectedAppointment());
                errorLabel.set("Appointment has been cancelled");
                loadAppointmentDetails();
            }
        }
        else {
            try{
                appointmentModel.cancelAppointment(appointment.getId());
            }
            catch (Exception e){
                errorLabel.set(e.getMessage());
            }
        }
    }
    
    @Override
    public void rescheduleAppointment() {
        Appointment appointment = appointmentModel.getAppointmentById(viewState.getSelectedAppointment());
        if((appointment.getStatus() instanceof UpcomingAppointment)){
            if(typeOfConfirmation(2)) {
                try{
                    appointmentModel.rescheduleAppointment(appointment.getId(), date.get(), timeInterval.get());
                    errorLabel.set("Appointment has been rescheduled");
                    loadAppointmentDetails();
                }
                catch (IllegalStateException e){
                    errorLabel.set(e.getMessage());
                }
            }
        }
        else{
            try{
                appointmentModel.rescheduleAppointment(appointment.getId(), date.get(), timeInterval.get());
            }
            catch (Exception e){
                errorLabel.set(e.getMessage());
            }
        }
    }

}
