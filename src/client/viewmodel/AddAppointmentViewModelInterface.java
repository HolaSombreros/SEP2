package client.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.paint.Paint;
import server.model.Appointment;
import server.model.TimeInterval;

import java.time.LocalDate;

public interface AddAppointmentViewModelInterface {
    void loadTimeIntervals();
    void reset();
    void createAppointment();
    
    ObjectProperty<LocalDate> getDateProperty();
    ObservableList<Appointment.Type> getAllTypes();
    ObjectProperty<Appointment.Type> getTypeProperty();
    ObservableList<TimeInterval> getAvailableTimeIntervals();
    ObjectProperty<TimeInterval> getTimeIntervalProperty();
    StringProperty getErrorProperty();
    ObjectProperty<Paint> getErrorFillProperty();
}
