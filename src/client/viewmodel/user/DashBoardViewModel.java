package client.viewmodel.user;

import client.model.Model;
import client.viewmodel.ViewState;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import server.model.domain.appointment.*;
import server.model.domain.user.ApprovedStatus;
import server.model.domain.user.Patient;
import server.model.domain.user.PendingStatus;
import util.ObservableClock;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.*;

public class DashBoardViewModel implements DashBoardViewModelInterface, LocalListener<String, String> {
    private Model model;
    private ViewState viewState;
    private ObservableClock observableClock;
    private StringProperty username;
    private StringProperty time;
    private StringProperty date;
    private StringProperty vaccinationLabel;
    private BooleanProperty disableButton;
    private StringProperty nextAppointment;
    private StringProperty notificationMessageProperty;
    private BooleanProperty notificationVisibleProperty;

    public DashBoardViewModel(Model model, ViewState viewState) {
        this.model = model;
        this.viewState = viewState;
        observableClock = new ObservableClock();
        observableClock.addListener(this, "ObservableClock");
        Thread timer = new Thread(observableClock);
        timer.setDaemon(true);
        timer.start();

        this.username = new SimpleStringProperty();
        this.time = new SimpleStringProperty();
        this.date = new SimpleStringProperty();
        this.vaccinationLabel = new SimpleStringProperty();
        this.disableButton = new SimpleBooleanProperty(false);
        this.nextAppointment = new SimpleStringProperty();
        this.notificationMessageProperty = new SimpleStringProperty();
        this.notificationVisibleProperty = new SimpleBooleanProperty();
    }

    @Override
    public void reset() {
        username.set((viewState.getUser()).getFirstName());
        Patient patient = (Patient) viewState.getUser();
        vaccinationLabel.set(patient.getVaccineStatus().toString());
        disableButton.set(patient.getVaccineStatus() instanceof PendingStatus || patient.getVaccineStatus() instanceof ApprovedStatus);
        notificationVisibleProperty.set(false);
        notificationMessageProperty.set("");

        AppointmentList appointments = model.getUpcomingAppointments((Patient) viewState.getUser());
        if (appointments.size() > 0) {
            Appointment appointment = appointments.get(0);
            long daysBetween = DAYS.between(LocalDate.now(), appointment.getDate());
            if (daysBetween < 1)
                nextAppointment.set("Your next appointment is in less than a day");
            else {
                nextAppointment.set("Your next appointment is in " + daysBetween + " day");
                if (daysBetween > 1)
                    nextAppointment.set(nextAppointment.get() + "s");
            }
        } else {
            nextAppointment.set("You do not have any upcoming appointments");
        }
        if (model.getNotifications((Patient) viewState.getUser()).getNotifications().size() > 0) {
            notificationVisibleProperty.set(true);
            notificationMessageProperty.set(model.getNotifications((Patient) viewState.getUser()).getNotifications().get(0).getText());
        }
    }

    @Override
    public void logout() {
        model.logout(viewState.getUser());
        viewState.removeUser();
    }

    @Override
    public void applyForVaccination() {
        Patient patient = (Patient) viewState.getUser();
        patient.setVaccineStatus(model.applyForVaccination(patient));
        vaccinationLabel.set(patient.getVaccineStatus().toString());
        disableButton.set(true);
    }

    @Override
    public void enterChat() {
        viewState.setSelectedUser(viewState.getUser());
    }

    @Override public void bookAppointment() {
        model.disableNotification(model.getNotifications((Patient) viewState.getUser()).getNotifications().get(0));
    }

    @Override
    public StringProperty getUsernameProperty() {
        return username;
    }

    @Override
    public StringProperty getTimeProperty() {
        return time;
    }

    @Override
    public StringProperty getDateProperty() {
        return date;
    }

    @Override
    public StringProperty getVaccinationLabelProperty() {
        return vaccinationLabel;
    }

    @Override
    public StringProperty getNextAppointmentProperty() {
        return nextAppointment;
    }

    @Override
    public BooleanProperty getDisableButtonProperty() {
        return disableButton;
    }

    @Override public StringProperty getNotificationMessageProperty() {
        return notificationMessageProperty;
    }

    @Override public BooleanProperty getNotificationVisibleProperty() {
        return notificationVisibleProperty;
    }

    @Override
    public void propertyChange(ObserverEvent<String, String> event) {
        Platform.runLater(() -> {
            time.set(event.getValue2());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String date = LocalDate.now().format(formatter);
            if (!date.equals(this.date.get())) {
                this.date.set(date);
            }
        });
    }
}
