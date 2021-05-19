package client.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;

public interface StaffDetailsViewModelInterface
{
  void reset();
  void confirm();
  void back();
  void loadShift();
  void disableDays(DatePicker week);
  boolean removeRole();
  StringProperty getNameProperty();
  StringProperty getCprProperty();
  StringProperty getIdProperty();
  StringProperty getPhoneProperty();
  StringProperty getEmailProperty();
  ObjectProperty<LocalDate> getDateProperty();
  BooleanProperty getShift0();
  BooleanProperty getShift1();
  BooleanProperty getShift2();
  StringProperty getErrorProperty();
  BooleanProperty getDisplayProperty();
}
