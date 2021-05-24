package client.viewmodel;

import client.model.Model;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import server.model.domain.appointment.*;
import server.model.domain.user.ApprovedStatus;
import server.model.domain.user.Patient;
import server.model.domain.user.PendingStatus;
import server.model.domain.user.User;
import util.ObservableClock;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.*;

public class DashBoardViewModel implements DashBoardViewModelInterface, LocalListener<String, String>
{
    private Model model;
    private ViewState viewState;
    private ObservableClock observableClock;
    private StringProperty username;
    private StringProperty access;
    private BooleanProperty accessVisibility;
    private StringProperty time;
    private StringProperty date;
    private StringProperty vaccinationLabel;
    private BooleanProperty disableButton;
    private StringProperty nextAppointment;

    public DashBoardViewModel(Model model, ViewState viewState)
    {
        this.model = model;
        this.viewState = viewState;
        observableClock = new ObservableClock();
        observableClock.addListener(this, "ObservableClock");
        Thread timer = new Thread(observableClock);
        timer.setDaemon(true);
        timer.start();

        this.username = new SimpleStringProperty();
        this.access = new SimpleStringProperty();
        this.accessVisibility = new SimpleBooleanProperty();
        this.time = new SimpleStringProperty();
        this.date = new SimpleStringProperty();
        this.vaccinationLabel = new SimpleStringProperty();
        this.disableButton = new SimpleBooleanProperty(false);
        nextAppointment = new SimpleStringProperty();
    }

    @Override
    public void reset()
    {
        username.set(((User)viewState.getUser()).getFirstName());
        AppointmentList appointmentList = model.getAppointmentsByUser((Patient)viewState.getUser());
        Patient patient = (Patient) viewState.getUser();
        accessVisibility.set(false);
        vaccinationLabel.set(patient.getVaccineStatus().toString());
        disableButton.set(patient.getVaccineStatus() instanceof PendingStatus || patient.getVaccineStatus() instanceof ApprovedStatus);

        AppointmentList list = new AppointmentList();
        for (Appointment appointment : appointmentList.getAppointments()) {
            if (appointment.getStatus() instanceof UpcomingAppointment)
                list.add(appointment);
        }

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
        }
        else {
            nextAppointment.set("You do not have any upcoming appointments");
        }
    }

    
    @Override
    public void logout() {
        model.logout((User)viewState.getUser());
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
    public StringProperty getUsernameProperty() {
        return username;
    }
    
    @Override
    public StringProperty getAccessProperty() {
        return access;
    }
    
    @Override
    public BooleanProperty getAccessVisibilityProperty() {
        return accessVisibility;
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
