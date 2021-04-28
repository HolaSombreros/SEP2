package client.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import server.model.domain.User;

public class UserTableViewModel
{
  private StringProperty cprProperty;
  private StringProperty nameProperty;
  private StringProperty phoneProperty;
  private StringProperty emailProperty;
  private StringProperty roleProperty;
  private StringProperty vaccineProperty;

  public UserTableViewModel(User user)
  {
    cprProperty = new SimpleStringProperty(user.getCpr());
    nameProperty = new SimpleStringProperty(user.getFullName());
    phoneProperty = new SimpleStringProperty(user.getPhone());
    emailProperty = new SimpleStringProperty(user.getEmail());
    roleProperty = new SimpleStringProperty(user.getClass().getSimpleName());
    // if (user instanceof Patient)
    // vaccineProperty = new SimpleStringProperty(((Patient)user).isValidForVaccine());
    // else
    vaccineProperty = new SimpleStringProperty("-");
  }

  public StringProperty getCprProperty()
  {
    return cprProperty;
  }

  public StringProperty getNameProperty()
  {
    return nameProperty;
  }

  public StringProperty getPhoneProperty()
  {
    return phoneProperty;
  }

  public StringProperty getEmailProperty()
  {
    return emailProperty;
  }

  public StringProperty getRoleProperty()
  {
    return roleProperty;
  }

  public StringProperty getVaccineProperty()
  {
    return vaccineProperty;
  }
}