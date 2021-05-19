package client.viewmodel;

import client.model.Model;
import javafx.beans.property.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import server.model.domain.appointment.TimeInterval;
import server.model.domain.user.Administrator;
import server.model.domain.user.Nurse;
import server.model.domain.user.Schedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class StaffDetailsViewModel implements StaffDetailsViewModelInterface
{
  private Model model;
  private ViewState viewState;
  private StringProperty nameProperty;
  private StringProperty cprProperty;
  private StringProperty idProperty;
  private StringProperty phoneProperty;
  private StringProperty emailProperty;
  private ObjectProperty<LocalDate> dateProperty;
  private ObjectProperty<Boolean> shift0;
  private ObjectProperty<Boolean> shift1;
  private ObjectProperty<Boolean> shift2;
  private ObjectProperty<Boolean> display;
  private StringProperty errorProperty;

  public StaffDetailsViewModel(Model model, ViewState viewState)
  {
    this.model = model;
    this.viewState = viewState;
    nameProperty = new SimpleStringProperty();
    cprProperty = new SimpleStringProperty();
    idProperty = new SimpleStringProperty();
    phoneProperty = new SimpleStringProperty();
    emailProperty = new SimpleStringProperty();
    dateProperty = new SimpleObjectProperty<>();
    shift0 = new SimpleObjectProperty<>();
    shift1 = new SimpleObjectProperty<>();
    shift2 = new SimpleObjectProperty<>();
    errorProperty = new SimpleStringProperty();
    display = new SimpleObjectProperty<>();
  }

  @Override public void reset()
  {
    nameProperty.set(viewState.getSelectedUser().getFullName());
    cprProperty.set("CPR: " + viewState.getSelectedUser().getCpr());
    phoneProperty.set("Phone: " + viewState.getSelectedUser().getPhone());
    emailProperty.set("Email: " + viewState.getSelectedUser().getEmail());
    dateProperty.set(null);
    shift0.set(false);
    shift1.set(false);
    shift2.set(false);
    errorProperty.set("");
    if (viewState.getSelectedUser() instanceof Administrator)
    {
      idProperty.set("ID: " + ((Administrator) viewState.getSelectedUser()).getEmployeeId());
      display.set(false);
    }
    else
    {
      idProperty.set("ID: " + ((Nurse) viewState.getSelectedUser()).getEmployeeId());
      display.set(true);
    }
  }

  @Override public void confirm()
  {
    if (viewState.getSelectedUser() instanceof Nurse)
    {
      if (confirmEditing())
      {
        Nurse nurse = (Nurse) model.getNurses().getUserByCpr(viewState.getSelectedUser().getCpr());
        TimeInterval shift0Time = new TimeInterval(model.getTimeIntervalList().get(LocalTime.of(8, 0), LocalTime.of(9, 0)).getId(),LocalTime.of(8, 0), LocalTime.of(9, 0));
        TimeInterval shift1Time = new TimeInterval(model.getTimeIntervalList().get(LocalTime.of(8, 0), LocalTime.of(14, 0)).getId(),LocalTime.of(8, 0), LocalTime.of(14, 0));
        TimeInterval shift2Time = new TimeInterval(model.getTimeIntervalList().get(LocalTime.of(14, 0), LocalTime.of(20, 0)).getId(),LocalTime.of(14, 0), LocalTime.of(20, 0));
        //if (shift0.get())
          //model.removeSchedule(nurse, new Schedule(dateProperty.get(), shift0Time));
        //else if (shift1.get())
          //model.addSchedule(nurse, new Schedule(dateProperty.get(), shift1Time));
        //else if (shift2.get())
          //model.addSchedule(nurse, new Schedule(dateProperty.get(), shift2Time));
        errorProperty.set("Schedule successfully changed");
        if (!shift0.get() && !shift1.get() && !shift2.get())
          errorProperty.set("Option not selected");
        if (dateProperty.get() == null)
          errorProperty.set("Select the week first");
      }
    }
  }

  @Override public void back()
  {
    viewState.removeSelectedUser();
  }

  @Override public void loadShift()
  {
//    Nurse nurse = (Nurse) model.getNurses().getUserByCpr(viewState.getSelectedUser().getCpr());
//    if (nurse!=null) {
//      Schedule schedule = nurse.getSchedule(dateProperty.get());
//      if (schedule == null)
//        shift0.set(true);
//      else if (schedule.getTimeInterval().getFrom().equals(LocalTime.of(8, 0)))
//        shift1.set(true);
//      else
//        shift2.set(true);
   // }
  }

  @Override public void disableDays(DatePicker week)
  {
    Callback<DatePicker, DateCell> callB = new Callback<>()
    {
      @Override public DateCell call(final DatePicker param)
      {
        return new DateCell()
        {
          @Override public void updateItem(LocalDate item, boolean empty)
          {
            super.updateItem(item, empty);
            LocalDate today = LocalDate.now();
            if (item.compareTo(today) < 0 || item.getDayOfWeek() != DayOfWeek.MONDAY)
              setDisable(true);
          }
        };
      }
    };
    week.setDayCellFactory(callB);
  }

  @Override public boolean removeRole() {
    if (confirmRoleRemoving()) {
      if (viewState.getSelectedUser().getCpr().equals(viewState.getUser().getCpr())) {
        errorProperty.set("You can not remove your role");
        return false;
      }
      else {
        model.removeRole(viewState.getSelectedUser());
        return true;
      }
    }
    else return false;
  }

  private boolean confirmRoleRemoving() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirm editing");
    alert.setHeaderText("Are you sure you want to edit the user's role? \n\n" + "Role: " + viewState.getSelectedUser().getClass().getSimpleName());
    Optional<ButtonType> result = alert.showAndWait();
    return result.isPresent() && result.get() == ButtonType.OK;
  }

  private boolean confirmEditing() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirm editing");
    String shift = "";
    if (shift0.get())
      shift = "Remove the schedule";
    else if (shift1.get())
      shift = "Time: 08:00 - 14:00";
    else if (shift0.get())
      shift = "Time: 14:00 - 20:00";
    alert.setHeaderText("Are you sure you want to edit the nurse's schedule? \n\n" + "Date: " + dateProperty.get() + "\n" + shift);
    Optional<ButtonType> result = alert.showAndWait();
    return result.isPresent() && result.get() == ButtonType.OK;
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

  @Override public ObjectProperty<Boolean> getShift0()
  {
    return shift0;
  }

  @Override public ObjectProperty<Boolean> getShift1()
  {
    return shift1;
  }

  @Override public ObjectProperty<Boolean> getShift2()
  {
    return shift2;
  }

  @Override public StringProperty getErrorProperty()
  {
    return errorProperty;
  }

  @Override public ObjectProperty<Boolean> getDisplayProperty()
  {
    return display;
  }

}
