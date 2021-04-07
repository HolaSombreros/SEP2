package client.viewmodel;

import client.model.Model;

public class ViewModelFactory {
    private RegisterViewModel registerViewModel;
        private LoginViewModel loginViewModel;
    
    public ViewModelFactory(Model model) {
        registerViewModel = new RegisterViewModel(model);
        loginViewModel = new LoginViewModel(model);
    }
    
    public RegisterViewModel getRegisterViewModel() {
        return registerViewModel;
    }
    
    public LoginViewModel getLoginViewModel() {
        return loginViewModel;
    }
}
