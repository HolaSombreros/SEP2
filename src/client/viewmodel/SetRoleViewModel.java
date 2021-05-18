package client.viewmodel;

import client.model.Model;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class SetRoleViewModel implements SetRoleViewModelInterface
{
  private Model model;
  private ViewState viewState;

  public SetRoleViewModel(Model model, ViewState viewState) {
    this.model = model;
    this.viewState = viewState;
  }

  @Override public void setToNurse() {
    if (confirmEditing("Nurse"))
      model.setRole(viewState.getUser(),"Nurse");
  }

  @Override public void setToAdmin() {
    if (confirmEditing("Administrator"))
      model.setRole(viewState.getUser(),"Administrator");
  }

  private boolean confirmEditing(String role) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirm editing");
    alert.setHeaderText("Are you sure you want to change the user's role? \n\n" + "Role: " + role);
    Optional<ButtonType> result = alert.showAndWait();
    return result.isPresent() && result.get() == ButtonType.OK;
  }
}
