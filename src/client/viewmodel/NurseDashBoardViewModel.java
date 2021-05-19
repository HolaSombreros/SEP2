package client.viewmodel;

import client.model.Model;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import server.model.domain.appointment.Appointment;
import server.model.domain.appointment.AppointmentList;
import server.model.domain.user.Staff;
import util.ObservableClock;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NurseDashBoardViewModel implements NurseDashBoardViewModelInterface, LocalListener<String, String> {
    private Model model;
    private ViewState viewState;
    private ObservableClock observableClock;
    
    private StringProperty username;
    private StringProperty role;
    private StringProperty time;
    private StringProperty date;
    private StringProperty searchBar;
    private BooleanProperty showFinishedAppointments;
    private StringProperty error;
    private ObjectProperty<Paint> errorFill;
    private StringProperty filterButtonText;
    private StringProperty filterType;
    private ObservableList<AppointmentTableViewModel> appointmentTable;
    private ObjectProperty<AppointmentTableViewModel> selectedAppointment;
    
    public NurseDashBoardViewModel(Model model, ViewState viewState) {
        this.model = model;
        this.viewState = viewState;
        
        username = new SimpleStringProperty();
        role = new SimpleStringProperty();
        time = new SimpleStringProperty();
        date = new SimpleStringProperty();
        searchBar = new SimpleStringProperty("");
        showFinishedAppointments = new SimpleBooleanProperty(false);
        error = new SimpleStringProperty();
        errorFill = new SimpleObjectProperty<>(Color.RED);
        filterButtonText = new SimpleStringProperty("Show test appointments");
        filterType = new SimpleStringProperty("all");
        
        appointmentTable = FXCollections.observableArrayList();
        selectedAppointment = new SimpleObjectProperty<>();
        
        observableClock = new ObservableClock();
        observableClock.addListener(this, "ObservableClock");
        Thread timer = new Thread(observableClock);
        timer.setDaemon(true);
        timer.start();
    }
    
    private void loadFromModel() {
        appointmentTable.clear();
        for (Appointment appointment : model.getAppointmentsByUser(viewState.getUser()).getAppointments()) {
            appointmentTable.add(new AppointmentTableViewModel(appointment));
        }
    }
    
    @Override
    public void reset() {
        searchBar.set("");
        error.set("");
        username.set(viewState.getUser().getFirstName());
        if (viewState.getUser() instanceof Staff) {
            role.set("Logged in as: " + viewState.getUser().getClass().getSimpleName());
        }
        loadFromModel();
    }
    
    @Override
    public boolean updateViewState() {
        if (selectedAppointment.get() != null) {
            viewState.setSelectedAppointment(selectedAppointment.get().getIdProperty().get());
            return true;
        }
        else {
            errorFill.set(Color.RED);
            error.set("Please select an appointment");
            return false;
        }
    }
    
    @Override
    public void logout() {
        model.logout(viewState.getUser());
        viewState.removeUser();
        viewState.removeNurse();
    }
    
    @Override
    public void setSelectedAppointment(AppointmentTableViewModel appointmentTableViewModel) {
        selectedAppointment.set(appointmentTableViewModel);
    }
    
    @Override
    public void filterAppointments() {
        appointmentTable.clear();
        String search = searchBar.get();
        AppointmentList appointmentList = model.filterAppointmentsByNameAndCpr(search, showFinishedAppointments.get(), filterType.get());
        for (Appointment appointment : appointmentList.getAppointments()) {
            appointmentTable.add(new AppointmentTableViewModel(appointment));
        }
    }
    
    @Override
    public void toggleTypeButton() {
        switch (filterType.get()) {
            case "all":
                filterType.set("test");
                filterButtonText.set("Show vaccine appointments");
                break;
            case "test":
                filterType.set("vaccine");
                filterButtonText.set("Show all appointments");
                break;
            case "vaccine":
                filterButtonText.set("Show test appointments");
                filterType.set("all");
                break;
        }
    }
    
    @Override
    public StringProperty getUsernameProperty() {
        return username;
    }
    
    @Override
    public StringProperty getRoleProperty() {
        return role;
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
    public StringProperty getSearchBarProperty() {
        return searchBar;
    }
    
    @Override
    public BooleanProperty showFinishedAppointmentsProperty() {
        return showFinishedAppointments;
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
    public StringProperty getFilterButtonTextProperty() {
        return filterButtonText;
    }
    
    @Override
    public ObservableList<AppointmentTableViewModel> getAppointments() {
        return appointmentTable;
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
