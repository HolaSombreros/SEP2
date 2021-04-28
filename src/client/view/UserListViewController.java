package client.view;

import client.viewmodel.UserTableViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UserListViewController extends ViewController
{
  //private ViewModelInterface viewModel;
  @FXML private TableView<UserTableViewModel> userTable;
  @FXML private TableColumn<UserTableViewModel, String> cprColumn;
  @FXML private TableColumn<UserTableViewModel, String> nameColumn;
  @FXML private TableColumn<UserTableViewModel, String> phoneColumn;
  @FXML private TableColumn<UserTableViewModel, String> emailColumn;
  @FXML private TableColumn<UserTableViewModel, String> roleColumn;
  @FXML private TableColumn<UserTableViewModel, String> vaccineColumn;
  @FXML private Label errorLabel;

  public UserListViewController(){}

  @Override protected void init()
  {
    //viewModel = ...
    cprColumn.setCellValueFactory(cellData -> cellData.getValue().getCprProperty());
    nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
    phoneColumn.setCellValueFactory(cellData -> cellData.getValue().getPhoneProperty());
    emailColumn.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());
    roleColumn.setCellValueFactory(cellData -> cellData.getValue().getRoleProperty());
    vaccineColumn.setCellValueFactory(cellData -> cellData.getValue().getVaccineProperty());
    //errorLabel.textProperty().bind(viewModel.getErrorProperty());
    //userTable.setItems(viewModel.getUsers);
    //userTable.getSelectionModel().selectedItemProperty().addListener((obs,oldValue,newValue) -> viewModel.setSelectedUser(newValue));
    reset();
  }

  @Override public void reset()
  {
    // viewModel.reset();
  }

  @FXML public void goToChat()
  {
    // TODO open the chat view
  }

  @FXML public void seeDetails()
  {
    // TODO
    //boolean openWindow = viewModel.seeDetails();
    //if (openWindow)
      //getViewHandler().openView(View.....);
  }

  @FXML public void logout()
  {
    //viewModel.logout();
  }
}
