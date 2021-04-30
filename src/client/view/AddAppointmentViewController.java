package client.view;

import client.viewmodel.AddAppointmentViewModelInterface;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import server.model.domain.Appointment;
import server.model.domain.TimeInterval;
import server.model.domain.Type;

public class AddAppointmentViewController extends ViewController {
    private AddAppointmentViewModelInterface viewModel;
    
    @FXML private DatePicker datePicker;
    @FXML private ChoiceBox<TimeInterval> timeIntervalChoiceBox;
    @FXML private ChoiceBox<Type> typeChoiceBox;
    @FXML private Label errorLabel;
    
    public AddAppointmentViewController() {
    
    }
    
    @Override
    protected void init() {
        viewModel = getViewModelFactory().getAddAppointmentViewModel();
        datePicker.valueProperty().bindBidirectional(viewModel.getDateProperty());
        datePicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            viewModel.loadTimeIntervals();
        });
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
