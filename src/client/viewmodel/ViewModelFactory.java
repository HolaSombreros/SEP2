package client.viewmodel;

import client.model.Model;

public class ViewModelFactory {
    private RegisterViewModel registerViewModel;
    private LoginViewModel loginViewModel;
    private LoginChoiceViewModel loginChoiceViewModel;
    private AppointmentTableViewModel appointmentTableViewModel;
    private AddAppointmentViewModel addAppointmentViewModel;

    
    public ViewModelFactory(Model model,ViewState viewState) {
        registerViewModel = new RegisterViewModel(model);
        loginViewModel = new LoginViewModel(model,viewState);
        loginChoiceViewModel = new LoginChoiceViewModel(model,viewState);
        appointmentTableViewModel = new AppointmentTableViewModel(model, viewState);
        addAppointmentViewModel = new AddAppointmentViewModel(model);
    }
    
    public RegisterViewModel getRegisterViewModel() {
        return registerViewModel;
    }
    
    public LoginViewModel getLoginViewModel() {
        return loginViewModel;
    }

    public LoginChoiceViewModel getLoginChoiceViewModel() {
        return loginChoiceViewModel;
    }
    public AppointmentTableViewModel getAppointmentListViewModel(){
        return appointmentTableViewModel;
    }

    public AddAppointmentViewModel getAddAppointmentViewModel()
    {
        return addAppointmentViewModel;
    }
}
