package server.model.domain.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Nurse extends Staff {

    private ScheduleList schedules;

    public Nurse(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email, String employeeId) {
        super(cpr, password, firstName, middleName, lastName, address, phone, email, employeeId);
        schedules = new ScheduleList();
    }

    public Nurse(String cpr, String password, String firstName, String lastName, Address address, String phone, String email, String employeeId) {
        this(cpr, password, firstName, null, lastName, address, phone, email, employeeId);
    }
    
    public ScheduleList getSchedules() {
        return schedules;
    }

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    public void editSchedule(Schedule schedule) {
        for (Schedule schedule1 : schedules.getSchedules())
            if (schedule1.equals(schedule))
                schedule1.setShift(schedule.getShift());
    }

    public void removeSchedule(Schedule schedule)
    {
        boolean remove = false;
        for (Schedule schedule1 : schedules.getSchedules())
            if (schedule1.equals(schedule))
            {
                schedule = schedule1;
                remove = true;
            }
        if (remove)
        schedules.remove(schedule);
    }

    public Schedule getSchedule(LocalDate date)
    {
        for (Schedule schedule : schedules.getSchedules())
            return schedule;
        return null;
    }

    public boolean worksThatDay(Schedule schedule)
    {
        for (Schedule schedule1 : schedules.getSchedules())
                return true;
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Nurse)) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString() + getEmployeeId() + schedules.toString();
    }
}
