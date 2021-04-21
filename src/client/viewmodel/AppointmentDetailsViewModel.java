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
    private ObjectProperty<Appointment.Status> status;
    private StringProperty errorLabel;
    private ObservableList<TimeInterval> timeInterval;
    private StringProperty result;

    private Model model;
    private ViewState viewState;

    public AppointmentDetailsViewModel(Model model, ViewState viewState){
        this.model = model;
        this.viewState = viewState;
        date = new SimpleObjectProperty<>();
        type = new SimpleStringProperty();
        result = new SimpleStringProperty();
       // status = new SimpleObjectProperty<>(Appointment.Type.values()[0]);
        timeInterval = FXCollections.observableArrayList();
        errorLabel = new SimpleStringProperty();
    }
}
