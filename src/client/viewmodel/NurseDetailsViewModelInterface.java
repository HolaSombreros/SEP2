package client.viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public interface NurseDetailsViewModelInterface
{
  void reset();
  void confirm();
  void back();
  void loadTimeInterval();
  StringProperty getNameProperty();
  StringProperty getCprProperty();
  StringProperty getIdProperty();
  StringProperty getPhoneProperty();
  StringProperty getEmailProperty();
  ObjectProperty<LocalDate> getDateProperty();
  IntegerProperty getFromHourProperty();
  IntegerProperty getFromMinuteProperty();
  IntegerProperty getToHourProperty();
  IntegerProperty getToMinuteProperty();
  StringProperty getErrorProperty();
}
