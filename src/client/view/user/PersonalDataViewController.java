package client.view.user;

import client.view.View;
import client.view.ViewController;
import client.viewmodel.user.PersonalDataViewModelInterface;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import util.IntStringConverter;


public class PersonalDataViewController extends ViewController
{
    @FXML
    private TextField firstName;
    @FXML
    private TextField middleName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField cpr;
    @FXML
    private TextField password;
    @FXML
    private TextField email;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField street;
    @FXML
    private TextField zipCode;
    @FXML
    private TextField number;
    @FXML
    private Label errorLabel;
    @FXML
    private Label vaccineStatus;
    @FXML
    private Label vaccineLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Button approveButton;
    @FXML
    private Button declineButton;
    @FXML
    private Button changeRole;


    private PersonalDataViewModelInterface viewModel;

    public PersonalDataViewController() {
    }

    @Override
    protected void init() {
        viewModel = getViewModelFactory().getPersonalDataViewModel();
        firstName.textProperty().bindBidirectional(viewModel.getFirstNameProperty());
        middleName.textProperty().bindBidirectional(viewModel.getMiddleNameProperty());
        lastName.textProperty().bindBidirectional(viewModel.getLastNameProperty());
        password.textProperty().bindBidirectional(viewModel.getPasswordProperty());
        cpr.textProperty().bindBidirectional(viewModel.getCprProperty());
        email.textProperty().bindBidirectional(viewModel.getEmailProperty());
        phoneNumber.textProperty().bindBidirectional(viewModel.getPhoneNumberProperty());
        street.textProperty().bindBidirectional(viewModel.getStreetProperty());
        zipCode.textProperty().bindBidirectional(viewModel.getZipCodeProperty(), new IntStringConverter());
        number.textProperty().bindBidirectional(viewModel.getNumberProperty());
        errorLabel.textProperty().bindBidirectional(viewModel.getErrorLabelProperty());
        vaccineStatus.textProperty().bind(viewModel.getVaccineStatusProperty());
        vaccineStatus.visibleProperty().bind(viewModel.vaccineStatusVisibilityProperty());
        vaccineLabel.visibleProperty().bind(viewModel.vaccineLabelVisibilityProperty());
        changeRole.visibleProperty().bind(viewModel.changeRoleProperty());
        titleLabel.textProperty().bind(viewModel.titleProperty());
        approveButton.visibleProperty().bind(viewModel.approveButtonProperty());
        declineButton.visibleProperty().bind(viewModel.declineButtonProperty());
        reset();
    }

    @Override
    public void reset() {
        viewModel.reset();
    }
    @FXML
    private void editDetails() {
        viewModel.editDetails();
    }

    @FXML
    private void backButton() {
        viewModel.back();
        if (viewModel.isLoggedInAsNurse())
            getViewHandler().openView(View.NURSEDASHBOARD);
        else if (viewModel.isAdmin())
            getViewHandler().openView(View.USERLIST);
        else getViewHandler().openView(View.DASHBOARD);
    }

    @FXML
    private void approve() {
        viewModel.approve();
    }

    @FXML
    private void decline() {
        viewModel.decline();
    }

    @FXML
    private void changeRole() {
        if (viewModel.changeRole())
            getViewHandler().openView(View.SETROLE);
    }

}
