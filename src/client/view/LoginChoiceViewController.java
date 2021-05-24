package client.view;

import client.viewmodel.LoginChoiceViewModelInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoginChoiceViewController extends ViewController{

    @FXML private Label roleLabel;
    private LoginChoiceViewModelInterface viewModel;


    public LoginChoiceViewController() {

    }

    @Override
    protected void init() {
        viewModel = getViewModelFactory().getLoginChoiceViewModel();
        roleLabel.textProperty().bind(viewModel.roleProperty());
        viewModel.reset();
    }

    @Override
    public void reset() {
        viewModel.reset();
    }

    @FXML private void loginPatient() {
        viewModel.logPatient();
        getViewHandler().openView(View.DASHBOARD);
    }

    @FXML private void loginSpecialRole() {
        if(viewModel.roleProperty().get().equals("Administrator"))
            getViewHandler().openView(View.USERLIST);
        else
            getViewHandler().openView(View.NURSEDASHBOARD);
    }
}
