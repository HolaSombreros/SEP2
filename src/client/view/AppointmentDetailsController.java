package client.view;

import client.viewmodel.AppointmentDetailsViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import server.model.Appointment;
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

    }

    @Override
    public void reset()
    {

    }
}
