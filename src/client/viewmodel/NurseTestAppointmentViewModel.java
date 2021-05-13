package client.viewmodel;

import client.model.Model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.model.domain.appointment.Result;
import server.model.domain.appointment.TestAppointment;

import java.time.LocalDate;


public class NurseTestAppointmentViewModel implements NurseTestAppointmentViewModelInterface {

    private StringProperty patientName;
    private StringProperty patientCpr;
    private StringProperty status;
    private StringProperty errorProperty;
    private ObservableList<String> resultList;
    private ObjectProperty<String> result;
    private StringProperty timeInterval;
    private StringProperty date;

    private Model model;
    private ViewState viewState;

    public NurseTestAppointmentViewModel(Model model, ViewState viewState) {
        this.model = model;
        this.viewState = viewState;
        status = new SimpleStringProperty();
        errorProperty = new SimpleStringProperty();
        patientCpr = new SimpleStringProperty();
        patientName = new SimpleStringProperty();
        resultList = FXCollections.observableArrayList();
        result = new SimpleObjectProperty<>();
        timeInterval = new SimpleStringProperty();
        date = new SimpleStringProperty();
    }

    @Override
    public void reset() {
        errorProperty.set("");
        result.set(Result.NORESULTSAVAILABLE.toString());
        loadAppointmentDetails();
        loadResultTypes();
    }

    public void loadResultTypes() {
        resultList.clear();
        resultList.add(Result.INCONCLUSIVE.toString());
        resultList.add(Result.NEGATIVE.toString());
        resultList.add(Result.NORESULTSAVAILABLE.toString());
        resultList.add(Result.POSITIVE.toString());

    }

    //TODO: make it so only when status is finished you can edit the result
    private void loadAppointmentDetails() {
        TestAppointment appointment = (TestAppointment) model.getAppointmentById(viewState.getSelectedAppointment());
        if (appointment != null) {
            date.set(LocalDate.of(appointment.getDate().getYear(), appointment.getDate().getMonth(), appointment.getDate().getDayOfMonth()).toString());
            timeInterval.set(appointment.getTimeInterval().toString());
            status.set(appointment.getStatus().toString());
            patientName.set(appointment.getPatient().getFullName());
            patientCpr.set(appointment.getPatient().getCpr());
        } else {
            result.set("");
        }

    }

    public StringProperty getPatientNameProperty() {
        return patientName;
    }

    public StringProperty patientCprProperty() {
        return patientCpr;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty errorPropertyProperty() {
        return errorProperty;
    }

    public ObservableList<String> getResultList() {
        return resultList;
    }

    public ObjectProperty<String> resultProperty() {
        return result;
    }

    public StringProperty timeIntervalProperty() {
        return timeInterval;
    }

    public StringProperty dateProperty() {
        return date;
    }
}
