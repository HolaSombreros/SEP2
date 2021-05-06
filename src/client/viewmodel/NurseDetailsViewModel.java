package client.viewmodel;

import client.model.Model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import server.model.domain.appointment.TimeInterval;
import server.model.domain.user.Nurse;

import java.time.LocalDate;
import java.time.LocalTime;

public class NurseDetailsViewModel implements NurseDetailsViewModelInterface
{
  private Model model;
  private ViewState viewState;
  private StringProperty nameProperty;
  private StringProperty cprProperty;
  private StringProperty idProperty;
  private StringProperty phoneProperty;
  private StringProperty emailProperty;
  private ObjectProperty<LocalDate> dateProperty;
  private StringProperty fromProperty;
  private StringProperty toProperty;
  private StringProperty errorProperty;

  public NurseDetailsViewModel(Model model, ViewState viewState)
  {
    this.model = model;
    this.viewState = viewState;
    nameProperty = new SimpleStringProperty();
    cprProperty = new SimpleStringProperty();
    idProperty = new SimpleStringProperty();
    phoneProperty = new SimpleStringProperty();
    emailProperty = new SimpleStringProperty();
    dateProperty = new SimpleObjectProperty<>();
    fromProperty = new SimpleStringProperty();
    toProperty = new SimpleStringProperty();
    errorProperty = new SimpleStringProperty();
  }

  @Override public void reset()
  {
    Nurse nurse = (Nurse) viewState.getSelectedUser();
    nameProperty.set(nurse.getFullName());
    cprProperty.set("CPR: " + nurse.getCpr());
    idProperty.set("ID: " + nurse.getEmployeeId());
    phoneProperty.set("Phone: " + nurse.getPhone());
    emailProperty.set("Email: " + nurse.getEmail());
    dateProperty.set(null);
    fromProperty.set("");
    toProperty.set("");
    errorProperty.set("");
  }

  @Override public void confirm()
  {

  }

  @Override public void back()
  {
    viewState.removeSelectedUser();
  }

  @Override public StringProperty getNameProperty()
  {
    return nameProperty;
  }

  @Override public StringProperty getCprProperty()
  {
    return cprProperty;
  }

  @Override public StringProperty getIdProperty()
  {
    return idProperty;
  }

  @Override public StringProperty getPhoneProperty()
  {
    return phoneProperty;
  }

  @Override public StringProperty getEmailProperty()
  {
    return emailProperty;
  }

  @Override public ObjectProperty<LocalDate> getDateProperty()
  {
    return dateProperty;
  }

  @Override public StringProperty getFromProperty()
  {
    return fromProperty;
  }

  @Override public StringProperty getToProperty()
  {
    return toProperty;
  }

  @Override public StringProperty getErrorProperty()
  {
    return errorProperty;
  }
}
