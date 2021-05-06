package client.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.paint.Paint;

public interface NurseDashBoardViewModelInterface {
    void reset();
    boolean updateViewState();
    void logout();
    void setSelectedAppointment(AppointmentTableViewModel appointmentTableViewModel);
    void filterTable();
    
    StringProperty getUsernameProperty();
    StringProperty getRoleProperty();
    StringProperty getTimeProperty();
    StringProperty getDateProperty();
    StringProperty getSearchBarProperty();
    BooleanProperty showFinishedAppointmentsProperty();
    StringProperty getErrorProperty();
    ObjectProperty<Paint> getErrorFillProperty();
    StringProperty getFilterButtonTextProperty();
    ObservableList<AppointmentTableViewModel> getAppointments();
}
