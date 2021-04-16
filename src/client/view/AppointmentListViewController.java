package client.view;

import client.viewmodel.AppointmentTableViewModel;
import client.viewmodel.AppointmentViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;



public class AppointmentListViewController extends ViewController
{
    @FXML private TableView<AppointmentViewModel> appointmentTable;
    @FXML private TableColumn<AppointmentViewModel, String> dateColumn;
    @FXML private TableColumn<AppointmentViewModel, String > resultColumn;
    @FXML private TableColumn<AppointmentViewModel, String> timeColumn;
    @FXML private TableColumn<AppointmentViewModel, String> typeColumn;
    @FXML private Label errorLabel;

    private AppointmentTableViewModel viewModel;

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
    }
    @FXML public void seeDetails(ActionEvent event){

    }
    @FXML public void backButton(ActionEvent event){
        //TODO: go back to the main page
    }

    @Override
    public void reset()
    {
        viewModel.reset();
    }
}
