package client.viewmodel;

import client.model.Model;
import javafx.beans.property.*;
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
    private BooleanProperty choiceBox;
    private BooleanProperty changeButton;

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
        choiceBox = new SimpleBooleanProperty(false);
        changeButton = new SimpleBooleanProperty(false);
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


    private void loadAppointmentDetails() {
        TestAppointment appointment = (TestAppointment) model.getAppointmentById(viewState.getSelectedAppointment());
        if (appointment != null) {
            date.set(LocalDate.of(appointment.getDate().getYear(), appointment.getDate().getMonth(), appointment.getDate().getDayOfMonth()).toString());
            timeInterval.set(appointment.getTimeInterval().toString());
            status.set(appointment.getStatus().toString());
            patientName.set(appointment.getPatient().getFullName());
            patientCpr.set(appointment.getPatient().getCpr());
            if(!status.get().equals("Finished")){
                choiceBox.set(true);
                changeButton.set(true);
            }
            else {
                choiceBox.set(false);
                changeButton.set(false);
            }
        }
        else
            result.set("");
    }

    private void changeResult(){
        TestAppointment appointment = (TestAppointment) model.getAppointmentById(viewState.getSelectedAppointment());
        model.changeResult(appointment.getId(),Result.fromString(result.get()));

    }
    private boolean confirmation(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to edit the result? \n\n" +
                        "Result: " + result.get());
        Optional<ButtonType> resultButton = alert.showAndWait();
        return resultButton.isPresent() && resultButton.get() == ButtonType.OK;
    }

    public void back(){
        viewState.removeSelectedAppointment();
    }

    @Override
    public void saveChanges() {
        if(confirmation()) {
            changeResult();
            errorProperty.set("Result changed successfully!");
        }
        else
            errorProperty.set("No changes were saved");
    }

    public BooleanProperty changeButtonProperty() {
        return changeButton;
    }

    public BooleanProperty choiceBoxProperty() {
        return choiceBox;
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
