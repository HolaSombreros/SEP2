package client.viewmodel;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public interface UserListViewModelInterface
{
  void reset();
  void logout();
  boolean seeDetails();
  void setSelectedUser(UserTableViewModel selectedUser);

  ObservableList<UserTableViewModel> getUsers();
  StringProperty getErrorProperty();
}
