package client.viewmodel.appointment;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.paint.Paint;
import server.model.domain.appointment.TimeInterval;
import server.model.domain.appointment.Type;

import java.time.LocalDate;

public interface AddAppointmentViewModelInterface {
    void loadTimeIntervals();

    void reset();

    boolean createAppointment();

    void disableDays(DatePicker datePicker);

    ObjectProperty<LocalDate> getDateProperty();

    ObservableList<Type> getAllTypes();

    ObjectProperty<Type> getTypeProperty();

    ObservableList<TimeInterval> getAvailableTimeIntervals();

    ObjectProperty<TimeInterval> getTimeIntervalProperty();

    StringProperty getErrorProperty();

    ObjectProperty<Paint> getErrorFillProperty();
}
