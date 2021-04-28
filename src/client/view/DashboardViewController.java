package client.view;

import client.viewmodel.DashBoardViewModelInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardViewController extends ViewController {
    private DashBoardViewModelInterface viewModel;
    
    @FXML private Label usernameLabel;
    @FXML private Label accessLabel;
    @FXML private Label timeLabel;
    @FXML private Label dateLabel;
    
    @Override
    protected void init() {
        viewModel = getViewModelFactory().getDashBoardViewModel();
        usernameLabel.textProperty().bind(viewModel.getUsernameProperty());
        accessLabel.textProperty().bind(viewModel.getAccessProperty());
        accessLabel.visibleProperty().bind(viewModel.getAccessVisibilityProperty());
        timeLabel.textProperty().bind(viewModel.getTimeProperty());
        dateLabel.textProperty().bind(viewModel.getDateProperty());
        
        reset();
    }
    
    @Override
    public void reset() {
        viewModel.reset();
    }
    
    @FXML
    private void logout() {
        viewModel.logout();
        getViewHandler().openView(View.LOGIN);
    }
    
    @FXML
    private void myAppointments() {
        getViewHandler().openView(View.APPOINTMENTLIST);
    }
}
