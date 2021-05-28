package client.view.user;

import client.view.View;
import client.view.ViewController;
import client.viewmodel.user.DashBoardViewModelInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DashboardViewController extends ViewController
{
    private DashBoardViewModelInterface viewModel;

    @FXML private Label usernameLabel;
    @FXML private Label timeLabel;
    @FXML private Label dateLabel;
    @FXML private Label vaccinationLabel;
    @FXML private Button applyButton;
    @FXML private Label nextAppointmentLabel;
    @FXML private Label notificationLabel;
    @FXML private Button notificationButton;

    public DashboardViewController() {
        // empty - called by FXMLLoader
    }

    @Override
    protected void init() {
        viewModel = getViewModelFactory().getDashBoardViewModel();
        usernameLabel.textProperty().bind(viewModel.getUsernameProperty());
        timeLabel.textProperty().bind(viewModel.getTimeProperty());
        dateLabel.textProperty().bind(viewModel.getDateProperty());
        vaccinationLabel.textProperty().bind(viewModel.getVaccinationLabelProperty());
        viewModel.getDisableButtonProperty().addListener((obs, oldVal, newVal) -> applyButton.setDisable(newVal));
        nextAppointmentLabel.textProperty().bind(viewModel.getNextAppointmentProperty());
        notificationLabel.textProperty().bind(viewModel.getNotificationMessageProperty());
        notificationLabel.visibleProperty().bind(viewModel.getNotificationMessageVisibleProperty());
        notificationButton.visibleProperty().bind(viewModel.getNotificationButtonVisibleProperty());
        
        reset();
    }

    @Override
    public void reset() {
        viewModel.reset();
    }

    @FXML
    private void logout() {
        viewModel.logout();
        getViewHandler().openView(View.LOGIN);
    }

    @FXML
    private void applyForVaccination() {
        viewModel.applyForVaccination();
    }

    @FXML
    private void myAppointments() {
        getViewHandler().openView(View.APPOINTMENTLIST);
    }

    @FXML
    private void personalData() {
        getViewHandler().openView(View.PERSONALDATA);
    }

    @FXML
    private void viewFAQ() {
        getViewHandler().openView(View.FAQ);
    }

    @FXML
    private void enterChat() {
        viewModel.enterChat();
        getViewHandler().openView(View.PATIENTCHAT);
    }
    
    @FXML
    private void bookAppointment() {
        getViewHandler().openView(View.ADDAPPOINTMENT);
    }
}
