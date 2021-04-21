package client.view;


import client.viewmodel.LoginViewModel;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.Event;
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
        usernameField.textProperty().bindBidirectional(viewModel.getCprProperty());
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

    @FXML
    private void onEnter(Event event) {
        if(event.getSource() == usernameField){
            passwordField.requestFocus();
        }
        if(event.getSource() == passwordField){
            login();
        }
    }
    public void login() {
        switch (viewModel.login()) {
            case 0:
                // Could not log in - nothing happens
                break;
            case 1:
                // Account is 'Patient' only
                getViewHandler().openView(View.APPOINTMENTLIST);
                break;
            case 2:
                // Account is either Admin or Nurse
                getViewHandler().openView(View.LOGINCHOICE);
                break;
        }
    }
}
