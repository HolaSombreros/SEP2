package client.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import server.model.domain.TimeInterval;

import java.time.LocalDate;

public interface AppointmentDetailsViewModelInterface {
    void reset();
    void cancelAppointment();
    void rescheduleAppointment();
    
    StringProperty getTypeProperty();
    ObjectProperty<LocalDate> dateProperty();
    StringProperty statusProperty();
    StringProperty errorLabelProperty();
    ObservableList<TimeInterval> getListOfTimeIntervals();
    ObjectProperty<TimeInterval> getTimeInterval();
    StringProperty resultProperty();
    StringProperty resultLabelProperty();
}
