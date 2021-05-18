package client.viewmodel;

import client.model.Model;

public class SetRoleViewModel implements SetRoleViewModelInterface
{
  private Model model;
  private ViewState viewState;

  public SetRoleViewModel(Model model, ViewState viewState) {
    this.model = model;
    this.viewState = viewState;
  }

  @Override public void setToNurse() {

  }

  @Override public void setToAdmin() {

  }
}
