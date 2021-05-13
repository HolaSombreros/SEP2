package server.model.domain.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Nurse extends Staff {

    private List<Schedule> schedules;

    public Nurse(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email, String employeeId) {
        super(cpr, password, firstName, middleName, lastName, address, phone, email, employeeId);
        schedules = new ArrayList<>();
    }

    public Nurse(String cpr, String password, String firstName, String lastName, Address address, String phone, String email, String employeeId) {
        this(cpr, password, firstName, null, lastName, address, phone, email, employeeId);
    }

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
        System.out.println(Arrays.toString(schedules.toArray()));
    }

    public void editSchedule(Schedule schedule) {
        for (Schedule schedule1 : schedules)
            if (schedule1.getDay().equals(schedule.getDay()))
                schedule1.setTimeInterval(schedule.getTimeInterval());
        System.out.println(Arrays.toString(schedules.toArray()));
    }

    public void removeSchedule(Schedule schedule)
    {
        for (Schedule schedule1 : schedules)
            if (schedule1.getDay().equals(schedule.getDay()))
                schedules.remove(schedule1);
        System.out.println(Arrays.toString(schedules.toArray()));
    }

    public Schedule getSchedule(LocalDate date)
    {
        for (Schedule schedule : schedules)
            if (schedule.getDay().equals(date))
                return schedule;
        return null;
    }

    public boolean worksThatDay(Schedule schedule)
    {
        for (Schedule schedule1 : schedules)
            if (schedule1.getDay().equals(schedule.getDay()))
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
        return super.toString();
    }
}
