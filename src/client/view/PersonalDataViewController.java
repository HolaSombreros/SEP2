package client.view;

import client.viewmodel.PersonalDataViewModelInterface;
import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import util.IntStringConverter;


public class PersonalDataViewController extends ViewController
{
    @FXML private TextField firstName;
    @FXML private TextField middleName;
    @FXML private TextField lastName;
    @FXML private TextField cpr;
    @FXML private TextField password;
    @FXML private TextField email;
    @FXML private TextField phoneNumber;
    @FXML private TextField street;
    @FXML private TextField zipCode;
    @FXML private TextField number;
    @FXML private Label errorLabel;
    @FXML private Label vaccineStatus;

    private PersonalDataViewModelInterface viewModel;

    public PersonalDataViewController(){}

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
        reset();
    }

    @Override
    public void reset() {
        viewModel.reset();
    }
    @FXML private void editDetails(){
        viewModel.editDetails();
    }
    @FXML private void backButton(){
        if (viewModel.isLoggedInAsNurse()) {
            getViewHandler().openView(View.NURSEDASHBOARD);
        }
        else {
            getViewHandler().openView(View.DASHBOARD);
        }
    }
}
