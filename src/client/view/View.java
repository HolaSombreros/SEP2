package client.view;

public enum View {
    REGISTER("RegisterView.fxml"),
    LOGIN("LoginView.fxml"),
    LOGINCHOICE("LoginChoiceView.fxml"),
    APPOINTMENTLIST("AppointmentListView.fxml"),
    ADDAPPOINTMENT("AddAppointmentView.fxml"),
    APPOINTMENTDETAILS("AppointmentDetailsView.fxml"),
    DASHBOARD("DashBoardView.fxml"),
    USERLIST("UserListView.fxml"),
    PERSONALDATA("PersonalData.fxml"),
    NURSEDETAILS("NurseDetailsView.fxml"),
    NURSEDASHBOARD("NurseDashBoardView.fxml"),
    NURSETEST("NurseTestAppointmentView.fxml");
    
    private String fxmlFile;
    
    View(String fxmlFile) {
        this.fxmlFile = fxmlFile;
    }
    
    public String getFxmlFile() {
        return fxmlFile;
    }
}
