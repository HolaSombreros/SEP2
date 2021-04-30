package client.view;

import client.viewmodel.PersonalDataViewModelInterface;
import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class PersonalDataViewController extends ViewController
{
    @FXML
    private TextField firstName;
    @FXML private TextField middleName;
    @FXML private TextField lastName;
    @FXML private TextField cpr;
    @FXML private TextField email;
    @FXML private TextField phoneNumber;
    @FXML private TextField street;
    @FXML private TextField city;
    @FXML private TextField number;
    @FXML private Label errorLabel;

    private PersonalDataViewModelInterface viewModel;


    public PersonalDataViewController(){}


    @Override
    protected void init() {
        viewModel = getViewModelFactory().getPersonalDataViewModel();
        firstName.textProperty().bindBidirectional(viewModel.getFirstName());
        middleName.textProperty().bindBidirectional(viewModel.getMiddleName());
        lastName.textProperty().bindBidirectional(viewModel.getLastName());
        cpr.textProperty().bindBidirectional(viewModel.getCpr());
        email.textProperty().bindBidirectional(viewModel.getEmail());
        phoneNumber.textProperty().bindBidirectional(viewModel.getPhoneNumber());
        street.textProperty().bindBidirectional(viewModel.getStreet());
        city.textProperty().bindBidirectional(viewModel.getCity());
        number.textProperty().bindBidirectional(viewModel.getNumber());
        errorLabel.textProperty().bindBidirectional(viewModel.getErrorLabel());
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
