package client.viewmodel.appointment;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import server.model.domain.appointment.TimeInterval;

import java.time.LocalDate;

public interface AppointmentDetailsViewModelInterface {
    void reset();

    void cancelAppointment();

    void rescheduleAppointment();

    void loadTimeIntervals();

    StringProperty getTypeProperty();

    ObjectProperty<LocalDate> dateProperty();

    StringProperty statusProperty();

    StringProperty errorLabelProperty();

    ObservableList<TimeInterval> getListOfTimeIntervals();

    ObjectProperty<TimeInterval> getTimeInterval();

    StringProperty resultProperty();

    StringProperty resultLabelProperty();

}
