package server.model.domain.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShiftList implements Serializable {
    private List<Shift> shifts;

    public ShiftList() {
        shifts = new ArrayList<>();
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void add(Shift shift){
        shifts.add(shift);
    }

    public boolean contains(Shift shift){
        return shifts.contains(shift);
    }

    public void remove(Shift shift){
        shifts.remove(shift);
    }

    public Shift getById(int id){
        for(Shift shift: shifts)
            if(shift.getId() == id)
                return shift;
        return null;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof ShiftList))
            return false;
        ShiftList other = (ShiftList) obj;
        if(other.shifts.size() != shifts.size())
            return false;
        for(int i =0; i < shifts.size();i++){
            if(!shifts.get(i).equals(other.shifts.get(i)))
                return false;
        }
        return true;
    }

    @Override
    public String toString(){
        String all = "";
        for(Shift shift : shifts){
            all += shift.toString() + " \n";
        }
        return all;
    }
}
