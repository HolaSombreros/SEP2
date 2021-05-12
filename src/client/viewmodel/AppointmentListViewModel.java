package client.viewmodel;

import client.model.Model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.model.domain.appointment.Appointment;
import server.model.domain.appointment.AppointmentList;
import server.model.domain.user.Nurse;
import server.model.domain.user.Patient;

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
        viewState.removeSelectedAppointment();
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
        AppointmentList appointmentList = model.getAppointmentsByUser(viewState.getPatient());
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
            viewState.removeSelectedAppointment();
            errorProperty.set("Please select an appointment first");
            return false;
        }
    }
}
