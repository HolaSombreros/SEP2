package client.view;

public enum View {
    REGISTER("user/RegisterView.fxml"),
    LOGIN("user/LoginView.fxml"),
    LOGINCHOICE("user/LoginChoiceView.fxml"),
    APPOINTMENTLIST("appointment/AppointmentListView.fxml"),
    ADDAPPOINTMENT("appointment/AddAppointmentView.fxml"),
    APPOINTMENTDETAILS("appointment/AppointmentDetailsView.fxml"),
    DASHBOARD("user/DashBoardView.fxml"),
    USERLIST("user/UserListView.fxml"),
    PERSONALDATA("user/PersonalDataView.fxml"),
    STAFFDETAILS("user/StaffDetailsView.fxml"),
    NURSEDASHBOARD("user/NurseDashBoardView.fxml"),
    NURSETEST("appointment/NurseTestAppointmentView.fxml"),
    FAQ("faq/FAQView.fxml"),
    ADDEDITFAQ("faq/AddEditFAQView.fxml"),
    SETROLE("user/SetRoleView.fxml"),
    ADMINMESSAGELIST("chat/AdminMessageListView.fxml"),
    PATIENTCHAT("chat/PatientChatView.fxml"),
    MYSCHEDULE("user/MyScheduleView.fxml");

    private String fxmlFile;

    View(String fxmlFile) {
        this.fxmlFile = fxmlFile;
    }

    public String getFxmlFile() {
        return fxmlFile;
    }
}
