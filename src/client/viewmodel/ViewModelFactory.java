package client.viewmodel;

import client.model.Model;

public class ViewModelFactory {
    private RegisterViewModelInterface registerViewModel;
    private LoginViewModelInterface loginViewModel;
    private LoginChoiceViewModelInterface loginChoiceViewModel;
    private AppointmentListViewModelInterface appointmentListViewModel;
    private AddAppointmentViewModelInterface addAppointmentViewModel;
    private AppointmentDetailsViewModelInterface appointmentDetailsViewModel;
    private DashBoardViewModelInterface dashBoardViewModel;
    private UserListViewModelInterface userListViewModel;
    private ViewState viewState;
    
    public ViewModelFactory(Model model) {
        this.viewState = new ViewState();
        registerViewModel = new RegisterViewModel(model);
        loginViewModel = new LoginViewModel(model, viewState);
        loginChoiceViewModel = new LoginChoiceViewModel(model, viewState);
        appointmentListViewModel = new AppointmentListViewModel(model, viewState);
        addAppointmentViewModel = new AddAppointmentViewModel(model, viewState);
        appointmentDetailsViewModel = new AppointmentDetailsViewModel(model, viewState);
        dashBoardViewModel = new DashBoardViewModel(model, viewState);
        userListViewModel = new UserListViewModel(model,viewState);
    }
    
    public ViewState getViewState() {
        return viewState;
    }
    
    public RegisterViewModelInterface getRegisterViewModel() {
        return registerViewModel;
    }
    
    public LoginViewModelInterface getLoginViewModel() {
        return loginViewModel;
    }
    
    public LoginChoiceViewModelInterface getLoginChoiceViewModel() {
        return loginChoiceViewModel;
    }
    
    public AppointmentListViewModelInterface getAppointmentListViewModel() {
        return appointmentListViewModel;
    }
    
    public AddAppointmentViewModelInterface getAddAppointmentViewModel() {
        return addAppointmentViewModel;
    }
    
    public AppointmentDetailsViewModelInterface getAppointmentDetailsViewModel() {
        return appointmentDetailsViewModel;
    }
    
    public DashBoardViewModelInterface getDashBoardViewModel() {
        return dashBoardViewModel;
    }

    public UserListViewModelInterface getUserListViewModel()
    {
        return userListViewModel;
    }
}
