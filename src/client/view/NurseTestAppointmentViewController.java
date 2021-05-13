package client.view;


import client.viewmodel.NurseTestAppointmentViewModelInterface;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;


public class NurseTestAppointmentViewController extends ViewController {

   @FXML private Label patientNameLabel;
   @FXML private Label patientCprLabel;
   @FXML private Label statusLabel;
   @FXML private ChoiceBox<String> resultChoiceBox;
   @FXML private Label dateLabel;
   @FXML private Label timeIntervalLabel;
   @FXML private Label errorLabel;


   private NurseTestAppointmentViewModelInterface viewModel;


    public NurseTestAppointmentViewController() {

    }

    @Override
    protected void init() {
        this.viewModel = getViewModelFactory().getNurseTestViewModel();
        patientCprLabel.textProperty().bind(viewModel.patientCprProperty());
        patientNameLabel.textProperty().bind(viewModel.getPatientNameProperty());
        errorLabel.textProperty().bind(viewModel.errorPropertyProperty());
        dateLabel.textProperty().bind(viewModel.dateProperty());
        statusLabel.textProperty().bind(viewModel.statusProperty());
        timeIntervalLabel.textProperty().bind(viewModel.timeIntervalProperty());
        resultChoiceBox.setItems(viewModel.getResultList());
        resultChoiceBox.valueProperty().bindBidirectional(viewModel.resultProperty());
        reset();
    }

    @Override
    public void reset() {
        viewModel.reset();
    }


    @FXML private void saveChangesAppointment() {
    }

    @FXML private void goBack() {
        getViewHandler().openView(View.NURSEDASHBOARD);
    }
}
