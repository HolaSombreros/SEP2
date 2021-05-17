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
import util.ObservableClock;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

import java.time.LocalDate;
import java.time.LocalTime;
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
        username.set(viewState.getUser().getFirstName());
        AppointmentList appointmentList = model.getAppointmentsByUser(viewState.getPatient());
        Patient patient = viewState.getPatient();
        accessVisibility.set(false);
        vaccinationLabel.set(patient.getVaccineStatus().toString());
        if (patient.getVaccineStatus() instanceof PendingStatus || patient.getVaccineStatus() instanceof ApprovedStatus)
            disableButton.set(true);

        nextAppointment.set("You do not have any upcoming appointments");
        AppointmentList list = new AppointmentList();
        for (Appointment appointment : appointmentList.getAppointments()) {
            if (appointment.getStatus() instanceof UpcomingAppointment)
                list.add(appointment);
        }

        Appointment recentAppointment = null;

        for (int i = 0; i < list.getAppointments().size()-1; i++) {
            if ((list.get(i).getDate().isBefore(list.get(i+1).getDate())) || (list.get(i).getDate().isBefore(list.get(i+1).getDate()) &&
                    (list.get(i).getTimeInterval().getFrom().isBefore(list.get(i+1).getTimeInterval().getFrom())))) {
                recentAppointment = list.get(i);
            }
        }

        if(recentAppointment != null) {
            long daysBetween = DAYS.between( recentAppointment.getDate(), LocalDate.now());
            if (daysBetween < 1)
                nextAppointment.set("Your next appointment is in less than a day");
            else {
                nextAppointment.set("Your next appointment is in " + daysBetween + " day");
                if (daysBetween > 1)
                    nextAppointment.set(nextAppointment.get() + "s");
            }
        }
    }

    
    @Override
    public void logout() {
        model.logout(viewState.getUser());
        viewState.removeUser();
        viewState.removePatient();
    }
    
    @Override
    public void applyForVaccination() {
        Patient patient = viewState.getPatient();
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
