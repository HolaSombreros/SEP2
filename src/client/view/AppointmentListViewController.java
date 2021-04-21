package client.view;

import client.viewmodel.AppointmentListViewModel;
import client.viewmodel.AppointmentTableViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;



public class AppointmentListViewController extends ViewController
{
    @FXML private TableView<AppointmentTableViewModel> appointmentTable;
    @FXML private TableColumn<AppointmentTableViewModel, String> dateColumn;
    @FXML private TableColumn<AppointmentTableViewModel, String > resultColumn;
    @FXML private TableColumn<AppointmentTableViewModel, String> timeColumn;
    @FXML private TableColumn<AppointmentTableViewModel, String> typeColumn;
    @FXML private Label errorLabel;

    private AppointmentListViewModel viewModel;

    public AppointmentListViewController(){

    }
    @Override
    protected void init()
    {
        viewModel = getViewModelFactory().getAppointmentListViewModel();
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().datePropertyProperty());
        resultColumn.setCellValueFactory(cellData -> cellData.getValue().resultPropertyProperty());
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().timePropertyProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typePropertyProperty());
        errorLabel.textProperty().bind(viewModel.getErrorProperty());
        appointmentTable.setItems(viewModel.getAppointments());
        
        // TODO: maybe?
        reset();
    }
    @FXML private void seeDetails(){
    }
    @FXML private void bookAppointment(){
        getViewHandler().openView(View.ADDAPPOINTMENT);
    }
    @FXML private void backButton(){
        //TODO: go back to the main page
    }

    @Override
    public void reset()
    {
        viewModel.reset();
    }
}