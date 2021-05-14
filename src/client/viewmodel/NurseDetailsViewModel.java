package client.viewmodel;

import client.model.Model;
import javafx.beans.property.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import server.model.domain.appointment.TimeInterval;
import server.model.domain.user.Nurse;
import server.model.domain.user.Schedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

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
  private ObjectProperty<Boolean> shift0;
  private ObjectProperty<Boolean> shift1;
  private ObjectProperty<Boolean> shift2;
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
    shift0 = new SimpleObjectProperty<>();
    shift1 = new SimpleObjectProperty<>();
    shift2 = new SimpleObjectProperty<>();
    errorProperty = new SimpleStringProperty();
  }

  @Override public void reset()
  {
    Nurse nurse = (Nurse) model.getNurses().getUserByCpr(viewState.getSelectedUser().getCpr());
    viewState.setSelectedUser(nurse);
    nameProperty.set(nurse.getFullName());
    cprProperty.set("CPR: " + nurse.getCpr());
    idProperty.set("ID: " + nurse.getEmployeeId());
    phoneProperty.set("Phone: " + nurse.getPhone());
    emailProperty.set("Email: " + nurse.getEmail());
    dateProperty.set(null);
    shift0.set(false);
    shift1.set(false);
    shift2.set(false);
    errorProperty.set("");
  }

  @Override public void confirm()
  {
    if (confirmEditing())
    {
      Nurse nurse = (Nurse) model.getNurses().getUserByCpr(viewState.getSelectedUser().getCpr());
      if (dateProperty.get() == null)
        errorProperty.set("Select the week first");
      else
      {
        if (shift0.get())
          model.removeSchedule(nurse, new Schedule(dateProperty.get(), new TimeInterval(LocalTime.of(8, 0), LocalTime.of(9, 0))));
        else if (shift1.get())
          model.addSchedule(nurse, new Schedule(dateProperty.get(), new TimeInterval(LocalTime.of(8, 0), LocalTime.of(14, 0))));
        else if (shift2.get())
          model.addSchedule(nurse, new Schedule(dateProperty.get(), new TimeInterval(LocalTime.of(14, 0), LocalTime.of(20, 0))));
        errorProperty.set("Schedule successfully changed");
        if (!shift0.get() && !shift1.get() && !shift2.get())
          errorProperty.set("Option not selected");
      }
    }
  }

  @Override public void back()
  {
    viewState.removeSelectedUser();
  }

  @Override public void loadShift()
  {
    Nurse nurse = (Nurse) model.getNurses().getUserByCpr(viewState.getSelectedUser().getCpr());
    Schedule schedule = nurse.getSchedule(dateProperty.get());
    if (schedule == null)
      shift0.set(true);
    else if (schedule.getTimeInterval().getFrom().equals(LocalTime.of(8, 0)))
      shift1.set(true);
    else
      shift2.set(true);
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

  private boolean confirmEditing()
  {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirm editing");
    String shift = "";
    if (shift0.get())
      shift = "Remove the schedule";
    else if (shift1.get())
      shift = "Time: 08:00 - 14:00";
    else if (shift0.get())
      shift = "Time: 14:00 - 20:00";
      alert.setHeaderText("Are you sure you want to edit the nurse's schedule? \n\n" +
          "Date: " + dateProperty.get() + "\n" +
          shift);
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

}

