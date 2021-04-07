package client.view;

import client.viewmodel.RegisterViewModel;
import javafx.fxml.FXML;

public class RegisterViewController extends ViewController {
    private RegisterViewModel viewModel;
    
    public RegisterViewController() {
    
    }
    
    @Override
    protected void init() {
        viewModel = getViewModelFactory().getRegisterViewModel();
    }
    
    @Override
    public void reset() {
        viewModel.reset();
    }
    
    @FXML
    private void register() {
    
    }
    
    @FXML
    private void login() {
    }
}
