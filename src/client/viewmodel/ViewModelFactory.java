package client.viewmodel;

import client.model.Model;

public class ViewModelFactory {
    private RegisterViewModel registerViewModel;
    private LoginViewModel loginViewModel;
    private LoginChoiceViewModel loginChoiceViewModel;
    private AppointmentListViewModel appointmentListViewModel;
    private AddAppointmentViewModel addAppointmentViewModel;
    private AppointmentDetailsViewModel appointmentDetailsViewModel;
    private ViewState viewState;

    
    public ViewModelFactory(Model model) {
        this.viewState = new ViewState();
        registerViewModel = new RegisterViewModel(model);
        loginViewModel = new LoginViewModel(model,viewState);
        loginChoiceViewModel = new LoginChoiceViewModel(model,viewState);
        appointmentListViewModel = new AppointmentListViewModel(model, viewState);
        addAppointmentViewModel = new AddAppointmentViewModel(model, viewState);
        appointmentDetailsViewModel = new AppointmentDetailsViewModel(model, viewState);
    }
    public ViewState getViewState(){ return viewState;}
    public RegisterViewModel getRegisterViewModel() {
        return registerViewModel;
    }
    
    public LoginViewModel getLoginViewModel() {
        return loginViewModel;
    }

    public LoginChoiceViewModel getLoginChoiceViewModel() {
        return loginChoiceViewModel;
    }
    public AppointmentListViewModel getAppointmentListViewModel(){
        return appointmentListViewModel;
    }

    public AddAppointmentViewModel getAddAppointmentViewModel()
    {
        return addAppointmentViewModel;
    }
    public AppointmentDetailsViewModel getAppointmentDetailsViewModel(){ return appointmentDetailsViewModel;}
}
