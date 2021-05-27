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
    PERSONALDATA("PersonalDataView.fxml"),
    STAFFDETAILS("StaffDetailsView.fxml"),
    NURSEDASHBOARD("NurseDashBoardView.fxml"),
    NURSETEST("NurseTestAppointmentView.fxml"),
    FAQ("FAQView.fxml"),
    ADDEDITFAQ("AddEditFAQView.fxml"),
    SETROLE("SetRoleView.fxml"),
    ADMINMESSAGELIST("AdminMessageListView.fxml"),
    PATIENTCHAT("PatientChatView.fxml");

    private String fxmlFile;

    View(String fxmlFile) {
        this.fxmlFile = fxmlFile;
    }

    public String getFxmlFile() {
        return fxmlFile;
    }
}
