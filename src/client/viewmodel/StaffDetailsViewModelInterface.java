package client.viewmodel;

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
  StringProperty getNameProperty();
  StringProperty getCprProperty();
  StringProperty getIdProperty();
  StringProperty getPhoneProperty();
  StringProperty getEmailProperty();
  ObjectProperty<LocalDate> getDateProperty();
  ObjectProperty<Boolean> getShift0();
  ObjectProperty<Boolean> getShift1();
  ObjectProperty<Boolean> getShift2();
  StringProperty getErrorProperty();
  ObjectProperty<Boolean> getDisplayProperty();
}
