package client.view;

import client.viewmodel.RegisterViewModelInterface;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import util.IntStringConverter;

public class RegisterViewController extends ViewController {
    private RegisterViewModelInterface viewModel;

    @FXML
    private TextField firstNameInputField;
    @FXML
    private TextField middleNameInputField;
    @FXML
    private TextField lastNameInputField;
    @FXML
    private TextField cprInputField;
    @FXML
    private TextField passwordInputField;
    @FXML
    private TextField streetInputField;
    @FXML
    private TextField numberInputField;
    @FXML
    private TextField zipCodeInputField;
    @FXML
    private TextField cityInputField;
    @FXML
    private TextField phoneInputField;
    @FXML
    private TextField emailInputField;
    @FXML
    private Label errorLabel;

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
        streetInputField.textProperty().bindBidirectional(viewModel.getStreetProperty());
        numberInputField.textProperty().bindBidirectional(viewModel.getNumberProperty());
        Bindings.bindBidirectional(zipCodeInputField.textProperty(), viewModel.getZipCodeProperty(), new IntStringConverter());
        cityInputField.textProperty().bindBidirectional(viewModel.getCityProperty());
        phoneInputField.textProperty().bindBidirectional(viewModel.getPhoneProperty());
        emailInputField.textProperty().bindBidirectional(viewModel.getEmailProperty());
        errorLabel.textProperty().bind(viewModel.getErrorProperty());
    }

    @Override
    public void reset() {
        viewModel.reset();
    }

    @FXML
    private void register() {
        if (viewModel.register()) {
            login();
        }
    }

    @FXML
    private void login() {
        getViewHandler().openView(View.LOGIN);
    }

    @FXML
    private void onEnter(Event event) {
        if (event.getSource() == firstNameInputField) {
            middleNameInputField.requestFocus();
        } else if (event.getSource() == middleNameInputField) {
            lastNameInputField.requestFocus();
        } else if (event.getSource() == lastNameInputField) {
            cprInputField.requestFocus();
        } else if (event.getSource() == cprInputField) {
            passwordInputField.requestFocus();
        } else if (event.getSource() == passwordInputField) {
            streetInputField.requestFocus();
        } else if (event.getSource() == streetInputField) {
            numberInputField.requestFocus();
        } else if (event.getSource() == numberInputField) {
            zipCodeInputField.requestFocus();
        } else if (event.getSource() == zipCodeInputField) {
            cityInputField.requestFocus();
        } else if (event.getSource() == cityInputField) {
            phoneInputField.requestFocus();
        } else if (event.getSource() == phoneInputField) {
            emailInputField.requestFocus();
        } else if (event.getSource() == emailInputField) {
            register();
        }
    }
}
