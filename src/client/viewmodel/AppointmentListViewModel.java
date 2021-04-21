package client.viewmodel;

import client.model.Model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.model.Appointment;

import server.model.AppointmentTimeFrame;


public class AppointmentListViewModel
{
    private ObservableList<AppointmentTableViewModel> appointments;
    private ObjectProperty<AppointmentTableViewModel> selectedAppointment;
    private ViewState viewState;
    private Model model;
    private StringProperty errorProperty;

    public AppointmentListViewModel(Model model, ViewState viewState){
        this.model = model;
        this.viewState = viewState;
        this.appointments = FXCollections.observableArrayList();
        this.errorProperty = new SimpleStringProperty();
        selectedAppointment = new SimpleObjectProperty<>();
    }
    public ObservableList<AppointmentTableViewModel> getAppointments(){
        return appointments;
    }
    public void reset(){
        appointments.clear();
        updateList();
    }
    public StringProperty  getErrorProperty(){
        return errorProperty;
    }
    
    private void updateList() {
        appointments.clear();

//        AppointmentTimeFrame appointmentTimeFrame = model.getAppointmentsByUser(viewState.getUser());
//        for (Appointment appointment : appointmentTimeFrame.getAppointmentList()) {
//            appointments.add(new AppointmentTableViewModel(appointment));
//        }

    }
    public void setSelectedAppointment(AppointmentTableViewModel selectedAppointment){
        this.selectedAppointment.set(selectedAppointment);

    }
    public void seeDetails(){
        if(selectedAppointment != null){
            viewState.setSelectedAppointment(selectedAppointment.get().getIdProperty().get());
        }
        else
            errorProperty.set("Please select an appointment");
    }
}
