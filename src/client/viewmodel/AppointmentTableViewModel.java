package client.viewmodel;

import client.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class AppointmentTableViewModel implements LocalListener<Appointment, String>
{
    private ObservableList<AppointmentViewModel> appointments;
    private ViewState viewState;
    private Model model;
    private StringProperty errorProperty;

    public AppointmentTableViewModel(Model model, ViewState viewState){
        this.model = model;
        this.viewState = viewState;
        this.appointments = FXCollections.observableArrayList();
        this.errorProperty = new SimpleStringProperty();

    }
    public ObservableList<AppointmentViewModel> getAppointments(){
        return appointments;
    }
    public void reset(){
        appointments.clear();
    }
    public StringProperty  getErrorProperty(){
        return errorProperty;
    }


}
