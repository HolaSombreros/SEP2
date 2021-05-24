package server.model.domain.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScheduleList implements Serializable {
    private List<Schedule> schedules;

    public ScheduleList() {
        schedules = new ArrayList<>();
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void add(Schedule schedule){
        schedules.add(schedule);
    }

    public boolean contains(Schedule schedule){
        return schedules.contains(schedule);
    }

    public void remove(Schedule schedule){
        schedules.remove(schedule);
    }

    public Schedule getById(int id){
        for(Schedule schedule: schedules)
            if(schedule.getId() == id)
                return schedule;
        return null;
    }
    
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof ScheduleList))
            return false;
        ScheduleList other = (ScheduleList) obj;
        if(other.schedules.size() != schedules.size())
            return false;
        for(int i =0; i < schedules.size();i++){
            if(!schedules.get(i).equals(other.schedules.get(i)))
                return false;
        }
        return true;
    }

    @Override
    public String toString(){
        String all = "";
        for(Schedule schedule : schedules){
            all += schedule.toString() + " \n";
        }
        return all;
    }
}
