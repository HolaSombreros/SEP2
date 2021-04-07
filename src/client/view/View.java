package client.view;

public enum View {
    REGISTER("RegisterView.fxml"),
    LOGIN("LoginView.fxml");
    
    private String fxmlFile;
    
    View(String fxmlFile) {
        this.fxmlFile = fxmlFile;
    }
    
    public String getFxmlFile() {
        return fxmlFile;
    }
}
