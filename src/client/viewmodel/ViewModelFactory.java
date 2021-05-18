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
    private PersonalDataViewModelInterface personalDataViewModel;
    private UserListViewModelInterface userListViewModel;
    private StaffDetailsViewModelInterface nurseDetailsViewModel;
    private NurseDashBoardViewModelInterface nurseDashBoardViewModel;
    private NurseTestAppointmentViewModelInterface nurseTestViewModel;
    private SetRoleViewModelInterface setRoleViewModel;


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
        personalDataViewModel = new PersonalDataViewModel(model,viewState);
        userListViewModel = new UserListViewModel(model,viewState);
        nurseDetailsViewModel = new StaffDetailsViewModel(model,viewState);
        nurseDashBoardViewModel = new NurseDashBoardViewModel(model, viewState);
        nurseTestViewModel = new NurseTestAppointmentViewModel(model, viewState);
        setRoleViewModel = new SetRoleViewModel(model,viewState);
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

    public PersonalDataViewModelInterface getPersonalDataViewModel(){
        return personalDataViewModel;
    }

    public UserListViewModelInterface getUserListViewModel()
    {
        return userListViewModel;
    }

    public StaffDetailsViewModelInterface getNurseDetailsViewModel() {
        return nurseDetailsViewModel;
    }

    public NurseDashBoardViewModelInterface getNurseDashBoardViewModel() {
        return nurseDashBoardViewModel;
    }

    public NurseTestAppointmentViewModelInterface getNurseTestViewModel() {
        return nurseTestViewModel;
    }

    public SetRoleViewModelInterface getSetRoleViewModel() {
        return setRoleViewModel;
    }
}
