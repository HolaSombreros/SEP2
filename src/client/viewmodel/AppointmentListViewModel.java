package client.viewmodel;

import client.model.Model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.model.domain.Appointment;
import server.model.domain.AppointmentList;

public class AppointmentListViewModel implements AppointmentListViewModelInterface {
    private ObservableList<AppointmentTableViewModel> appointments;
    private ObjectProperty<AppointmentTableViewModel> selectedAppointment;
    private ViewState viewState;
    private Model model;
    private StringProperty errorProperty;
    
    public AppointmentListViewModel(Model model, ViewState viewState) {
        this.model = model;
        this.viewState = viewState;
        this.appointments = FXCollections.observableArrayList();
        this.errorProperty = new SimpleStringProperty();
        this.selectedAppointment = new SimpleObjectProperty<>();
    }
    
    public ObservableList<AppointmentTableViewModel> getAppointments() {
        return appointments;
    }
    
    @Override
    public void reset() {
        viewState.setSelectedAppointment(-1);
        errorProperty.set("");
        appointments.clear();
        updateList();
    }
    
    @Override
    public StringProperty getErrorProperty() {
        return errorProperty;
    }
    
    private void updateList() {
        appointments.clear();
        AppointmentList appointmentList = model.getAppointmentsByUser(viewState.getUser());
        for (Appointment appointment : appointmentList.getAppointments()) {
            appointments.add(new AppointmentTableViewModel(appointment));
        }
    }
    
    @Override
    public void setSelectedAppointment(AppointmentTableViewModel selectedAppointment) {
        this.selectedAppointment.set(selectedAppointment);
    }
    
    @Override
    public boolean seeDetails() {
        if (selectedAppointment.get() != null) {
            viewState.setSelectedAppointment(selectedAppointment.get().getIdProperty().get());
            return true;
        }
        else {
            viewState.setSelectedAppointment(-1);
            errorProperty.set("Please select an appointment first");
            return false;
        }
    }
}
