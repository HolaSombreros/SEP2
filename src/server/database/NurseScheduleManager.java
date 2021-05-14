package server.database;

import server.model.domain.user.Nurse;
import server.model.domain.user.Schedule;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class NurseScheduleManager
{
  public NurseScheduleManager()
  {
  }

  public void addNurseSchedule(Nurse nurse, Schedule schedule) throws SQLException
  {
    try (Connection connection = DatabaseManager.getInstance().getConnection())
    {
      PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO nurse_schedule (nurse_cpr, date, time_from, time_to) VALUES(?,?,?,?);");
      insertStatement.setString(1, nurse.getCpr());
      insertStatement.setDate(2, Date.valueOf(schedule.getDay()));
      insertStatement.setTime(3, Time.valueOf(schedule.getTimeInterval().getFrom()));
      insertStatement.setTime(4, Time.valueOf(schedule.getTimeInterval().getTo()));
      insertStatement.executeUpdate();
    }
  }

  public void editNurseSchedule(Nurse nurse, Schedule schedule) throws SQLException
  {
    try (Connection connection = DatabaseManager.getInstance().getConnection())
    {
      PreparedStatement insertStatement = connection.prepareStatement("UPDATE nurse_schedule SET time_from = ?, time_to=? WHERE nurse_cpr = ? AND date = ?;");
      insertStatement.setTime(1, Time.valueOf(schedule.getTimeInterval().getFrom()));
      insertStatement.setTime(2, Time.valueOf(schedule.getTimeInterval().getTo()));
      insertStatement.setString(3, nurse.getCpr());
      insertStatement.setDate(4, Date.valueOf(schedule.getDay()));
      insertStatement.executeUpdate();
    }
  }

  public void removeNurseSchedule(Nurse nurse, Schedule schedule) throws SQLException
  {
    try (Connection connection = DatabaseManager.getInstance().getConnection())
    {
      PreparedStatement insertStatement = connection.prepareStatement("DELETE FROM nurse_schedule WHERE nurse_cpr = ? AND date = ?;");
      insertStatement.setString(1, nurse.getCpr());
      insertStatement.setDate(2, Date.valueOf(schedule.getDay()));
      insertStatement.executeUpdate();
    }
  }

  public void getAllSchedules() throws SQLException
  {
    List<Schedule> schedules = new ArrayList<>();
    try(Connection connection = DatabaseManager.getInstance().getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM nurse_schedule;");
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next())
      {
        String cpr = resultSet.getString("nurse_cpr");
        LocalDate date = resultSet.getDate("date").toLocalDate();
        LocalTime from = resultSet.getTime("time_from").toLocalTime();
        LocalTime to = resultSet.getTime("time_to").toLocalTime();
      }
    }
  }
}
