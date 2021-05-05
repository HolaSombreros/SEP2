package client.viewmodel;

import client.model.Model;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import server.model.domain.user.Patient;
import server.model.domain.user.Staff;
import util.ObservableClock;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DashBoardViewModel implements DashBoardViewModelInterface, LocalListener<String, String> {
    private Model model;
    private ViewState viewState;
    private ObservableClock observableClock;
    private StringProperty username;
    private StringProperty access;
    private BooleanProperty accessVisibility;
    private StringProperty time;
    private StringProperty date;
    private StringProperty vaccinationLabel;
    
    public DashBoardViewModel(Model model, ViewState viewState) {
        this.model = model;
        this.viewState = viewState;
        observableClock = new ObservableClock();
        observableClock.addListener(this, "ObservableClock");
        Thread timer = new Thread(observableClock);
        timer.setDaemon(true);
        timer.start();
        
        username = new SimpleStringProperty();
        access = new SimpleStringProperty();
        accessVisibility = new SimpleBooleanProperty();
        time = new SimpleStringProperty();
        date = new SimpleStringProperty();
        this.vaccinationLabel = new SimpleStringProperty();
    }
    
    @Override
    public void reset() {
        username.set(viewState.getUser().getFirstName());
        vaccinationLabel.set(((Patient)viewState.getUser()).getVaccineStatus().toString());
        if (viewState.getUser() instanceof Staff) {
            access.set("Logged in as: " + viewState.getUser().getClass().getSimpleName());
            accessVisibility.set(true);
        }
        else {
            accessVisibility.set(false);
        }
        // time and date are updated in propertyChange()
    }
    
    @Override
    public void logout() {
        model.logout(viewState.getUser());
        viewState.removeUser();
    }

    @Override
    public void applyForVaccination()
    {

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
    public StringProperty getVaccinationLabelProperty()
    {
        return vaccinationLabel;
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
