package client.view;

import client.viewmodel.AddAppointmentViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import server.model.Appointment;
import server.model.TimeInterval;

public class AddAppointmentViewController extends ViewController {
    private AddAppointmentViewModel viewModel;
    
    @FXML private DatePicker dateDatePicker;
    @FXML private ChoiceBox<TimeInterval> timeIntervalChoiceBox;
    @FXML private ChoiceBox<Appointment.Type> typeChoiceBox;
    @FXML private Label errorLabel;
    
    @Override
    protected void init() {
        viewModel = getViewModelFactory().getAddAppointmentViewModel();
        dateDatePicker.valueProperty().bindBidirectional(viewModel.getDateProperty());
        timeIntervalChoiceBox.valueProperty().bindBidirectional(viewModel.getTimeIntervalProperty());
        timeIntervalChoiceBox.setItems(viewModel.getAvailableTimeIntervals());
        typeChoiceBox.valueProperty().bindBidirectional(viewModel.getTypeProperty());
        typeChoiceBox.setItems(viewModel.getAllTypes());
        errorLabel.textProperty().bind(viewModel.getErrorProperty());
        errorLabel.textFillProperty().bind(viewModel.getErrorFillProperty());
    }
    
    @Override
    public void reset() {
        viewModel.reset();
    }
    
    @FXML
    private void createAppointment() {
        viewModel.createAppointment();
    }
    
    @FXML
    private void cancel() {
        getViewHandler().openView(View.APPOINTMENTLIST);
    }
}
