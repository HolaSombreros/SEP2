package client.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.paint.Paint;
import server.model.domain.Appointment;
import server.model.domain.TimeInterval;
import server.model.domain.Type;

import java.time.LocalDate;

public interface AddAppointmentViewModelInterface {
    void loadTimeIntervals();
    void reset();
    void createAppointment();
    
    ObjectProperty<LocalDate> getDateProperty();
    ObservableList<Type> getAllTypes();
    ObjectProperty<Type> getTypeProperty();
    ObservableList<TimeInterval> getAvailableTimeIntervals();
    ObjectProperty<TimeInterval> getTimeIntervalProperty();
    StringProperty getErrorProperty();
    ObjectProperty<Paint> getErrorFillProperty();
}
