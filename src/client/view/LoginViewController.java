package client.view;


import client.viewmodel.LoginViewModel;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
        errorLabel.textProperty().bind(viewModel.getErrorProperty());
    }

    @Override
    public void reset() {
        viewModel.reset();
    }

    public void register() {
        getViewHandler().openView(View.REGISTER);
    }

    public void login() {
        if (viewModel.login())
            getViewHandler().openView(View.LOGINCHOICE);
       //TODO: else open view for Patient

    }
}
