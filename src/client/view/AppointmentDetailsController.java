package client.view;

import client.viewmodel.AppointmentDetailsViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import server.model.TimeInterval;

public class AppointmentDetailsController extends ViewController
{
    @FXML private Label type;
    @FXML private Label status;
    @FXML private DatePicker date;
    @FXML private ChoiceBox<TimeInterval> timeInterval;
    @FXML private Label result;
    @FXML private Label errorLabel;

    private AppointmentDetailsViewModel viewModel;

    public AppointmentDetailsController(){}


    @Override
    protected void init()
    {
        viewModel = getViewModelFactory().getAppointmentDetailsViewModel();
        type.textProperty().bind(viewModel.getTypeProperty());
        status.textProperty().bind(viewModel.statusProperty());
        date.valueProperty().bindBidirectional(viewModel.dateProperty());
        timeInterval.valueProperty().bindBidirectional(viewModel.getTimeInterval());
        timeInterval.setItems(viewModel.getListOfTimeIntervals());
        result.textProperty().bind(viewModel.resultProperty());
        errorLabel.textProperty().bind(viewModel.errorLabelProperty());
    }

    @Override
    public void reset()
    {
        viewModel.reset();
    }
    @FXML private void cancelAppointment(){
        viewModel.cancelAppointment();
    }
    @FXML private void rescheduleAppointment(){
        viewModel.rescheduleAppointment();
    }
    @FXML private void goBack(){
        getViewHandler().openView(View.APPOINTMENTLIST);
    }
}
