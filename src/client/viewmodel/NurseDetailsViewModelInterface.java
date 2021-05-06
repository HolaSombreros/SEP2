package client.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import server.model.domain.appointment.TimeInterval;

import java.time.LocalDate;

public interface NurseDetailsViewModelInterface
{
  void reset();
  void confirm();
  void back();
  StringProperty getNameProperty();
  StringProperty getCprProperty();
  StringProperty getIdProperty();
  StringProperty getPhoneProperty();
  StringProperty getEmailProperty();
  ObjectProperty<LocalDate> getDateProperty();
  StringProperty getFromProperty();
  StringProperty getToProperty();
  StringProperty getErrorProperty();
}
