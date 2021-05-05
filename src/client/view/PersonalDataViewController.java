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
        firstName.textProperty().bindBidirectional(viewModel.getFirstName());
        middleName.textProperty().bindBidirectional(viewModel.getMiddleName());
        lastName.textProperty().bindBidirectional(viewModel.getLastName());
        password.textProperty().bindBidirectional(viewModel.getPassword());
        cpr.textProperty().bindBidirectional(viewModel.getCpr());
        email.textProperty().bindBidirectional(viewModel.getEmail());
        phoneNumber.textProperty().bindBidirectional(viewModel.getPhoneNumber());
        street.textProperty().bindBidirectional(viewModel.getStreet());
        zipCode.textProperty().bindBidirectional(viewModel.getZipCode(), new IntStringConverter());
        number.textProperty().bindBidirectional(viewModel.getNumber());
        errorLabel.textProperty().bindBidirectional(viewModel.getErrorLabel());
        vaccineStatus.textProperty().bind(viewModel.getVaccineStatus());
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
        getViewHandler().openView(View.DASHBOARD);
    }

}
