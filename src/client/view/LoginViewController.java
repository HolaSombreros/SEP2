package client.view;


import client.viewmodel.LoginViewModel;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class LoginViewController extends ViewController {

    private LoginViewModel viewModel;
    @FXML private JFXTextField usernameField;
    @FXML private JFXPasswordField passwordField;
    @FXML private Label errorLabel;


    public LoginViewController() {

    }

    @Override
    protected void init() {
        viewModel = getViewModelFactory().getLoginViewModel();
        usernameField.textProperty().bindBidirectional(viewModel.getUsernameProperty());
        passwordField.textProperty().bindBidirectional(viewModel.getPasswordProperty());
        errorLabel.textProperty().bindBidirectional(viewModel.getErrorProperty());
    }

    @Override
    public void reset() {
        viewModel.reset();
    }

    public void register(MouseEvent mouseEvent) {
    }

    public void login(MouseEvent mouseEvent) {
    }
}
