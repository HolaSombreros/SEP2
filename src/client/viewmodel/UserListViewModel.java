package client.viewmodel;

import client.model.Model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.model.domain.user.User;
import server.model.domain.user.UserList;

public class UserListViewModel implements UserListViewModelInterface
{
  private ObservableList<UserTableViewModel> users;
  private ObjectProperty<UserTableViewModel> selectedUser;
  private ViewState viewState;
  private Model model;
  private StringProperty roleProperty;
  private StringProperty errorProperty;

  public UserListViewModel(Model model, ViewState viewState)
  {
    this.model = model;
    this.viewState = viewState;
    this.users = FXCollections.observableArrayList();
    this.errorProperty = new SimpleStringProperty();
    this.roleProperty = new SimpleStringProperty();
    this.selectedUser = new SimpleObjectProperty<>();
  }

  @Override public void reset()
  {
    viewState.removeSelectedUser();
    errorProperty.set("");
    users.clear();
    updateList("patients");
  }

  private void updateList(String role)
  {
    users.clear();
    UserList userList = new UserList();
    switch (role)
    {
      case "patients":
        userList = model.getPatients();
        roleProperty.set("Patient List");
        break;
      case "nurses":
        userList = model.getNurses();
        roleProperty.set("Nurse List");
        break;
      case "admins":
        userList = model.getAdministrators();
        roleProperty.set("Administrator List");
        break;
    }
    for (User user : userList.getUsers())
      users.add(new UserTableViewModel(user));

  }

  @Override public void logout()
  {
    model.logout(viewState.getUser());
    viewState.removeUser();
  }

  @Override public boolean seeDetails()
  {
    if (selectedUser.get() != null)
    {
      viewState.setSelectedUser(model.getUserList().getUserByCpr(selectedUser.get().getCprProperty().get()));
      return true;
    }
    else
    {
      viewState.removeSelectedUser();
      errorProperty.set("Please select an user");
      return false;
    }
  }

  @Override public void setSelectedUser(UserTableViewModel selectedUser)
  {
    this.selectedUser.set(selectedUser);
  }

  @Override public ObservableList<UserTableViewModel> getUsers()
  {
    return users;
  }

  @Override public StringProperty getRoleProperty()
  {
    return roleProperty;
  }

  @Override public StringProperty getErrorProperty()
  {
    return errorProperty;
  }

  @Override public void seePatients()
  {
    updateList("patients");
  }

  @Override public void seeNurses()
  {
    updateList("nurses");
  }

  @Override public void seeAdmins()
  {
    updateList("admins");
  }
}
