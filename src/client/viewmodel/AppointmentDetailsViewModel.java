package client.viewmodel;

import client.model.Model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.model.Appointment;
import server.model.TimeInterval;

import java.time.LocalDate;

public class AppointmentDetailsViewModel
{
    private StringProperty type;
    private ObjectProperty<LocalDate> date;
    private StringProperty status;
    private StringProperty errorLabel;
    private ObservableList<TimeInterval> listOfTimeIntervals;
    private ObjectProperty<TimeInterval> timeInterval;
    private StringProperty result;

    private Model model;
    private ViewState viewState;

    public AppointmentDetailsViewModel(Model model, ViewState viewState){
        this.model = model;
        this.viewState = viewState;
        date = new SimpleObjectProperty<>();
        type = new SimpleStringProperty();
        result = new SimpleStringProperty();
        status = new SimpleStringProperty();
        timeInterval = new SimpleObjectProperty<>();
        listOfTimeIntervals = FXCollections.observableArrayList();
        errorLabel = new SimpleStringProperty();
        reset();
    }
    public void reset(){
      //  Appointment appointment = model.getAppointmentById(viewState.getSelectedAppointment());
    //    type.set(appointment.getType().toString());

        errorLabel.set("");
        date.set(null);
    }

    public StringProperty getTypeProperty()
    {
        return type;
    }

    public ObjectProperty<LocalDate> dateProperty()
    {
        return date;
    }

    public StringProperty statusProperty()
    {
        return status;
    }

    public StringProperty errorLabelProperty()
    {
        return errorLabel;
    }

    public ObservableList<TimeInterval> getListOfTimeIntervals()
    {
        return listOfTimeIntervals;
    }
    public ObjectProperty<TimeInterval> getTimeInterval(){
        return timeInterval;
    }

    public StringProperty resultProperty()
    {
        return result;
    }
    public void cancelAppointment(){
        //TODO: Next sprints
    }
    public void rescheduleAppointment(){
        //TODO: Next sprints
    }
}
