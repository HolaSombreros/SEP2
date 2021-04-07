package client.viewmodel;

import client.model.Model;

public class ViewModelFactory {

    private LoginViewModel loginViewModel;

    public ViewModelFactory(Model model) {
        loginViewModel = new LoginViewModel(model);
    }

    public LoginViewModel getLoginViewModel() {
        return loginViewModel;
    }
}
