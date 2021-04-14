package client.view;

import client.viewmodel.LoginChoiceViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoginChoiceViewController extends ViewController{

    @FXML private Label roleLabel;
    private LoginChoiceViewModel viewModel;


    public LoginChoiceViewController() {

    }

    @Override
    protected void init() {
        viewModel = getViewModelFactory().getLoginChoiceViewModel();
        roleLabel.textProperty().bind(viewModel.roleProperty());
        viewModel.updateRoleProperty();
    }

    @Override
    public void reset() {
        viewModel.reset();
    }

    @FXML public void loginPatient() {
        System.out.println("Logged in as Patient");
        //TODO: implement View for patient
    }

    @FXML public void loginSpecialRole() {
        System.out.println("Logged in as " + roleLabel.textProperty().get());
        //TODO: implement View for Admin/Nurse
    }
}
