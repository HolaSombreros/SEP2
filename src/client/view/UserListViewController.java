package client.view;

import client.viewmodel.UserListViewModelInterface;
import client.viewmodel.UserTableViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class UserListViewController extends ViewController
{
  private UserListViewModelInterface viewModel;
  @FXML private Label roleLabel;
  @FXML private TextField searchBar;
  @FXML private TableView<UserTableViewModel> userTable;
  @FXML private TableColumn<UserTableViewModel, String> cprColumn;
  @FXML private TableColumn<UserTableViewModel, String> nameColumn;
  @FXML private TableColumn<UserTableViewModel, String> phoneColumn;
  @FXML private TableColumn<UserTableViewModel, String> emailColumn;
  @FXML private TableColumn<UserTableViewModel, String> vaccineColumn;
  @FXML private Label errorLabel;

  public UserListViewController() {}

  @Override protected void init() {
    viewModel = getViewModelFactory().getUserListViewModel();
    roleLabel.textProperty().bind(viewModel.getRoleProperty());
    searchBar.textProperty().bindBidirectional(viewModel.getSearchBarProperty());
    cprColumn.setCellValueFactory(cellData -> cellData.getValue().getCprProperty());
    nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
    phoneColumn.setCellValueFactory(cellData -> cellData.getValue().getPhoneProperty());
    emailColumn.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());
    vaccineColumn.setCellValueFactory(cellData -> cellData.getValue().getVaccineProperty());
    errorLabel.textProperty().bind(viewModel.getErrorProperty());
    userTable.setItems(viewModel.getUsers());
    userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> viewModel.setSelectedUser(newValue));
    reset();
  }

  @Override public void reset()
  {
    viewModel.reset();
  }

  @FXML private void goToChat()
  {
    getViewHandler().openView(View.FAQ);
  }

  @FXML private void seeDetails() {
    boolean openWindow = viewModel.seeDetails();
    if (openWindow) {
      switch (roleLabel.textProperty().get())
      {
        case "Patient List":
          getViewHandler().openView(View.PERSONALDATA);
          break;
        case "Nurse List":
        case "Administrator List":
          getViewHandler().openView(View.STAFFDETAILS);
          break;
      }
    }
  }

  @FXML private void logout() {
    viewModel.logout();
    getViewHandler().openView(View.LOGIN);
  }

  @FXML private void keyReleased() {
    viewModel.filterUsers();
  }

  @FXML private void seePatients()
  {
    viewModel.seePatients();
  }

  @FXML private void seeNurses()
  {
    viewModel.seeNurses();
  }

  @FXML private void seeAdmins()
  {
    viewModel.seeAdmins();
  }
}
