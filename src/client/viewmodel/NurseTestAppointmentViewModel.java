package client.viewmodel;

import client.model.Model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import server.model.domain.appointment.Result;
import server.model.domain.appointment.TestAppointment;

import java.time.LocalDate;
import java.util.Optional;


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

        loadAppointmentDetails();
        loadResultTypes();
        result.set(((TestAppointment)model.getAppointmentById(viewState.getSelectedAppointment())).getResult().toString());
    }

    public void loadResultTypes() {
        resultList.clear();
        resultList.add(Result.NORESULTSAVAILABLE.toString());
        resultList.add(Result.INCONCLUSIVE.toString());
        resultList.add(Result.NEGATIVE.toString());
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

    public void changeResult(){
        if(!status.get().equals("Finished")) {
            errorProperty.set("Appointment has to be finished first!");
        }
        else{
            TestAppointment appointment = (TestAppointment) model.getAppointmentById(viewState.getSelectedAppointment());
            model.changeResult(appointment.getId(),Result.fromString(result.get()));

        }

    }
    private boolean typeOfConfirmation(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> resultButton = null;
        alert.setHeaderText("Are you sure you want to edit the result? \n\n" +
                        "Result: " + result.get());
        resultButton = alert.showAndWait();
        return resultButton.isPresent() && resultButton.get() == ButtonType.OK;
    }

    @Override
    public void saveChanges() {
        typeOfConfirmation();
        changeResult();
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
