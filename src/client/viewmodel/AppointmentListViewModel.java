package client.viewmodel;

import client.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.model.Appointment;
import server.model.AppointmentList;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;


public class AppointmentListViewModel implements LocalListener<Appointment, Appointment>
{
    private ObservableList<AppointmentTableViewModel> appointments;
    private ViewState viewState;
    private Model model;
    private StringProperty errorProperty;

    public AppointmentListViewModel(Model model, ViewState viewState){
        this.model = model;
        this.viewState = viewState;
        this.appointments = FXCollections.observableArrayList();
        this.errorProperty = new SimpleStringProperty();
    }
    public ObservableList<AppointmentTableViewModel> getAppointments(){
        return appointments;
    }
    public void reset(){
        appointments.clear();
        loadFromModel();
    }
    public StringProperty  getErrorProperty(){
        return errorProperty;
    }
    
    private void loadFromModel() {
        appointments.clear();
        AppointmentList appointmentList = model.getAppointmentsByUser(viewState.getUser());
        for (Appointment appointment : appointmentList.getAppointmentList()) {
            appointments.add(new AppointmentTableViewModel(appointment));
        }
    }

    @Override
    public void propertyChange(ObserverEvent<Appointment, Appointment> event)
    {
    
    }
}
