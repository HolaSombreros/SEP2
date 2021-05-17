package client.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import server.model.domain.user.Patient;
import server.model.domain.user.User;

public class UserTableViewModel
{
  private StringProperty cprProperty;
  private StringProperty nameProperty;
  private StringProperty phoneProperty;
  private StringProperty emailProperty;
  private StringProperty vaccineProperty;

  public UserTableViewModel(User user) {
    cprProperty = new SimpleStringProperty(user.getCpr());
    nameProperty = new SimpleStringProperty(user.getFullName());
    phoneProperty = new SimpleStringProperty(user.getPhone());
    emailProperty = new SimpleStringProperty(user.getEmail());
    if (user instanceof Patient)
      vaccineProperty = new SimpleStringProperty(((Patient) user).getVaccineStatus().toString());
    else
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

  public StringProperty getVaccineProperty()
  {
    return vaccineProperty;
  }
}