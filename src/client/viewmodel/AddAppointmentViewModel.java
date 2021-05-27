package client.viewmodel;

import client.model.AppointmentModel;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Callback;
import server.model.domain.user.ApprovedStatus;
import server.model.domain.user.Patient;
import server.model.domain.appointment.TimeInterval;
import server.model.domain.appointment.Type;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class AddAppointmentViewModel implements AddAppointmentViewModelInterface
{
    private AppointmentModel appointmentModel;
    private ViewState viewState;
    private ObjectProperty<LocalDate> date;
    private ObservableList<Type> types;
    private ObjectProperty<Type> type;
    private ObservableList<TimeInterval> timeIntervals;
    private ObjectProperty<TimeInterval> timeInterval;
    private StringProperty error;
    private ObjectProperty<Paint> errorFill;

    public AddAppointmentViewModel(AppointmentModel appointmentModel, ViewState viewState) {
        this.appointmentModel = appointmentModel;
        this.viewState = viewState;
        date = new SimpleObjectProperty<>();
        types = FXCollections.observableArrayList();
        type = new SimpleObjectProperty<>(Type.values()[0]);
        timeIntervals = FXCollections.observableArrayList();
        timeInterval = new SimpleObjectProperty<>();
        error = new SimpleStringProperty();
        errorFill = new SimpleObjectProperty<>(Color.RED);
    }

    private void loadTypes() {
        types.clear();
        for (Type type : Type.values()) {
            if (type == Type.VACCINE) {
                if (((Patient) viewState.getUser()).getVaccineStatus() instanceof ApprovedStatus) {
                    types.add(type);
                }
            } else {
                types.add(type);
            }
        }
        type.set(types.get(0));
    }

    @Override
    public void loadTimeIntervals() {
        timeIntervals.clear();
        if (date.get() != null) {
            timeIntervals.addAll(appointmentModel.getAvailableTimeIntervals((date.get())).getTimeIntervals());
            if (timeIntervals.size() > 0) {
                timeInterval.set(timeIntervals.get(0));
                error.set("");
            } else {
                timeInterval.set(null);
                errorFill.set(Color.RED);
                error.set("No time intervals available this day");
            }
        }
    }

    @Override
    public void reset() {
        error.set("");
        loadTypes();
        date.set(null);
        timeIntervals.clear();
        timeInterval.set(null);
    }

    @Override
    public void disableDays(DatePicker datePicker) {
        Callback<DatePicker, DateCell> callB = new Callback<>() {
            @Override
            public DateCell call(final DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        LocalDate today = LocalDate.now();
                        if (item.compareTo(today) < 0 || appointmentModel.getAvailableTimeIntervals(item).getTimeIntervals().size() == 0)
                            setDisable(true);
                    }
                };
            }
        };
        datePicker.setDayCellFactory(callB);
    }

    @Override
    public ObjectProperty<LocalDate> getDateProperty() {
        return date;
    }

    @Override
    public ObservableList<Type> getAllTypes() {
        return types;
    }

    @Override
    public ObjectProperty<Type> getTypeProperty() {
        return type;
    }

    @Override
    public ObservableList<TimeInterval> getAvailableTimeIntervals() {
        return timeIntervals;
    }

    @Override
    public ObjectProperty<TimeInterval> getTimeIntervalProperty() {
        return timeInterval;
    }

    @Override
    public StringProperty getErrorProperty() {
        return error;
    }

    @Override
    public ObjectProperty<Paint> getErrorFillProperty() {
        return errorFill;
    }

    @Override
    public boolean createAppointment() {
        try {
            appointmentModel.addAppointment((date.get()), timeInterval.get(), type.get(), (Patient) viewState.getUser());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            errorFill.set(Color.RED);
            error.set(e.getMessage());
            return false;
        }
    }
}
