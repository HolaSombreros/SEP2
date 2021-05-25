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
    STAFFDETAILS("StaffDetailsView.fxml"),
    NURSEDASHBOARD("NurseDashBoardView.fxml"),
    NURSETEST("NurseTestAppointmentView.fxml"),
    FAQ("FAQView.fxml"),
    ADDFAQ("AddFAQView.fxml"),
    SETROLE("SetRoleView.fxml"),
    ADMINMESSAGELIST("AdminMessageListView.fxml"),
    PATIENTCHAT("PatientChat.fxml");

    private String fxmlFile;
    
    View(String fxmlFile) {
        this.fxmlFile = fxmlFile;
    }
    
    public String getFxmlFile() {
        return fxmlFile;
    }
}
