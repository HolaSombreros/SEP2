package client.view;

public enum View {
    REGISTER("RegisterView.fxml"),
    LOGIN("LoginView.fxml"),
    LOGINCHOICE("LoginChoiceView.fxml"),
    APPOINTMENTLIST("AppointmentList.fxml"),
    ADDAPPOINTMENT("AddAppointmentView.fxml"),
    APPOINTMENTDETAILS("AppointmentDetails.fxml");
    
    private String fxmlFile;
    
    View(String fxmlFile) {
        this.fxmlFile = fxmlFile;
    }
    
    public String getFxmlFile() {
        return fxmlFile;
    }
}
