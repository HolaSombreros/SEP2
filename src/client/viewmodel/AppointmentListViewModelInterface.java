package client.viewmodel;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public interface AppointmentListViewModelInterface {
    void reset();
    void setSelectedAppointment(AppointmentTableViewModel selectedAppointment);
    void seeDetails();
    
    ObservableList<AppointmentTableViewModel> getAppointments();
    StringProperty getErrorProperty();
}
