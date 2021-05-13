package client.viewmodel;

import client.model.Model;
import javafx.beans.property.*;
import server.model.domain.appointment.TimeInterval;
import server.model.domain.user.Nurse;
import server.model.domain.user.Schedule;

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
  private IntegerProperty fromHourProperty;
  private IntegerProperty fromMinuteProperty;
  private IntegerProperty toHourProperty;
  private IntegerProperty toMinuteProperty;
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
    fromHourProperty = new SimpleIntegerProperty();
    fromMinuteProperty = new SimpleIntegerProperty();
    toHourProperty = new SimpleIntegerProperty();
    toMinuteProperty = new SimpleIntegerProperty();
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
    fromHourProperty.set(0);
    fromMinuteProperty.set(0);
    toHourProperty.set(0);
    toMinuteProperty.set(0);
    errorProperty.set("");
  }

  @Override public void confirm()
  {
    try
    {
      Schedule schedule = new Schedule(dateProperty.get(),
          new TimeInterval(LocalTime.of(fromHourProperty.get(), fromMinuteProperty.get()), LocalTime.of(toHourProperty.get(), toMinuteProperty.get())));
      Nurse nurse = (Nurse) model.getNurses().getUserByCpr(viewState.getSelectedUser().getCpr());
      if (dateProperty.get() == null)
        throw new IllegalStateException("Select a date");
      if (fromHourProperty.get() == 0 || fromMinuteProperty.get() == 0 || toHourProperty.get() == 0 || toMinuteProperty.get() == 0)
      {
        if (fromHourProperty.get() == 0 && fromMinuteProperty.get() == 0 && toHourProperty.get() == 0 && toMinuteProperty.get() == 0)
        {
          model.removeSchedule(nurse, schedule);
        }
        else
          throw new IllegalStateException("Invalid time");
      }
      else
      {
        model.addSchedule(nurse, schedule);
      }
      errorProperty.set("Schedule successfully changed");
    }
    catch (Exception e)
    {
      errorProperty.set("Invalid time");
    }
  }

  @Override public void back()
  {
    viewState.removeSelectedUser();
  }

  @Override public void loadTimeInterval()
  {
    Nurse nurse = (Nurse) model.getNurses().getUserByCpr(viewState.getSelectedUser().getCpr());
    Schedule schedule = nurse.getSchedule(dateProperty.get());
    if (schedule != null)
    {
      fromHourProperty.set(schedule.getTimeInterval().getFrom().getHour());
      fromMinuteProperty.set(schedule.getTimeInterval().getFrom().getMinute());
      toHourProperty.set(schedule.getTimeInterval().getTo().getHour());
      toMinuteProperty.set(schedule.getTimeInterval().getTo().getMinute());
    }
    else
    {
      fromHourProperty.set(0);
      fromMinuteProperty.set(0);
      toHourProperty.set(0);
      toMinuteProperty.set(0);
    }
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

  @Override public IntegerProperty getFromHourProperty()
  {
    return fromHourProperty;
  }

  @Override public IntegerProperty getFromMinuteProperty()
  {
    return fromMinuteProperty;
  }

  @Override public IntegerProperty getToHourProperty()
  {
    return toHourProperty;
  }

  @Override public IntegerProperty getToMinuteProperty()
  {
    return toMinuteProperty;
  }

  @Override public StringProperty getErrorProperty()
  {
    return errorProperty;
  }
}
