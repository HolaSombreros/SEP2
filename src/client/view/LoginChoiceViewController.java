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
    }

    @Override
    public void reset() {
        viewModel.reset();
    }

    public void loginPatient() {
        //TODO: implement View for patient
    }

    public void loginSpecialRole() {
        //TODO: implement View for Admin/Nurse
    }
}
