package client.viewmodel;

import client.model.Model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserListViewModel implements UserListViewModelInterface
{
  private ObservableList<UserTableViewModel> users;
  private ObjectProperty<UserTableViewModel> selectedUser;
  private ViewState viewState;
  private Model model;
  private StringProperty errorProperty;

  public UserListViewModel(Model model, ViewState viewState)
  {
    this.model = model;
    this.viewState = viewState;
    this.users = FXCollections.observableArrayList();
    this.errorProperty = new SimpleStringProperty();
    this.selectedUser = new SimpleObjectProperty<>();
  }

  @Override public void reset()
  {
    viewState.removeSelectedAppointment();
    errorProperty.set("");
    users.clear();
    updateList();
  }

  private void updateList()
  {
    users.clear();
    //TODO
  }

  @Override public void logout()
  {
    model.logout(viewState.getUser());
    viewState.removeUser();
  }

  @Override public boolean seeDetails()
  {
    if (selectedUser.get()!=null)
    {
      // TODO
      //viewState.setSelectedUser(model.get);
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

  @Override public StringProperty getErrorProperty()
  {
    return errorProperty;
  }
}
