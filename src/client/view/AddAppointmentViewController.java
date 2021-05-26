package client.view;

import client.viewmodel.AddAppointmentViewModelInterface;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import server.model.domain.appointment.TimeInterval;
import server.model.domain.appointment.Type;

public class AddAppointmentViewController extends ViewController {
    private AddAppointmentViewModelInterface viewModel;
    
    @FXML private DatePicker datePicker;
    @FXML private ChoiceBox<TimeInterval> timeIntervalChoiceBox;
    @FXML private ChoiceBox<Type> typeChoiceBox;
    @FXML private Label errorLabel;
    
    public AddAppointmentViewController() {}
    
    @Override
    protected void init() {
        viewModel = getViewModelFactory().getAddAppointmentViewModel();
        datePicker.valueProperty().bindBidirectional(viewModel.getDateProperty());
        datePicker.valueProperty().addListener((obs, oldVal, newVal) -> viewModel.loadTimeIntervals());
        timeIntervalChoiceBox.valueProperty().bindBidirectional(viewModel.getTimeIntervalProperty());
        timeIntervalChoiceBox.setItems(viewModel.getAvailableTimeIntervals());
        typeChoiceBox.valueProperty().bindBidirectional(viewModel.getTypeProperty());
        typeChoiceBox.setItems(viewModel.getAllTypes());
        errorLabel.textProperty().bind(viewModel.getErrorProperty());
        errorLabel.textFillProperty().bind(viewModel.getErrorFillProperty());
        viewModel.disableDays(datePicker);
        reset();
    }
    
    @Override
    public void reset() {
        viewModel.reset();
    }
    
    @FXML
    private void createAppointment() {
        if (viewModel.createAppointment()) {
            getViewHandler().openView(View.APPOINTMENTLIST);
        }
    }
    
    @FXML
    private void cancel() {
        getViewHandler().openView(View.APPOINTMENTLIST);
    }
}
