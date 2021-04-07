package client.view;

import client.viewmodel.RegisterViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RegisterViewController extends ViewController {
    private RegisterViewModel viewModel;
    
    @FXML private TextField firstNameInputField;
    @FXML private TextField middleNameInputField;
    @FXML private TextField lastNameInputField;
    @FXML private TextField cprInputField;
    @FXML private TextField passwordInputField;
    @FXML private TextField confirmPasswordInputField;
    @FXML private TextField steetInputField;
    @FXML private TextField numberInputField;
    @FXML private TextField zipCodeInputField;
    @FXML private TextField cityInputField;
    @FXML private TextField phoneInputField;
    @FXML private TextField emailInputField;
    
    public RegisterViewController() {
    
    }
    
    @Override
    protected void init() {
        viewModel = getViewModelFactory().getRegisterViewModel();
        firstNameInputField.textProperty().bindBidirectional(viewModel.getFirstNameProperty());
        middleNameInputField.textProperty().bindBidirectional(viewModel.getMiddleNameProperty());
        lastNameInputField.textProperty().bindBidirectional(viewModel.getLastNameProperty());
        cprInputField.textProperty().bindBidirectional(viewModel.getCPRProperty());
        passwordInputField.textProperty().bindBidirectional(viewModel.getPasswordProperty());
        confirmPasswordInputField.textProperty().bindBidirectional(viewModel.getConfirmPasswordProperty());
        steetInputField.textProperty().bindBidirectional(viewModel.getStreetProperty());
        numberInputField.textProperty().bindBidirectional(viewModel.getNumberProperty());
        zipCodeInputField.textProperty().bindBidirectional(viewModel.getZipCodeProperty());
        cityInputField.textProperty().bindBidirectional(viewModel.getCityProperty());
        phoneInputField.textProperty().bindBidirectional(viewModel.getPhoneProperty());
        emailInputField.textProperty().bindBidirectional(viewModel.getEmailProperty());
    }
    
    @Override
    public void reset() {
        viewModel.reset();
    }
    
    @FXML
    private void register() {
        viewModel.register();
    }
    
    @FXML
    private void login() {
        getViewHandler().openView(View.LOGIN);
    }
}
