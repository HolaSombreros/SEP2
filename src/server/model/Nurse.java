package server.model;

public class Nurse extends Staff {



    public Nurse(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email, String employeeId) {
        super(cpr, password, firstName, middleName, lastName, address, phone, email, employeeId);
    }

    public Nurse(String cpr, String password, String firstName, String lastName, Address address, String phone, String email, String employeeId){
        this(cpr, password,firstName, null, lastName, address,phone, email, employeeId);
    }
    
    @Override
    public String getType() {
        return "Nurse";
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
