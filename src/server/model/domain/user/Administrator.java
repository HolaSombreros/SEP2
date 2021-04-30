package server.model.domain.user;

public class Administrator extends Staff {
    public Administrator(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email, String employeeId) {
        super(cpr, password, firstName, middleName, lastName, address, phone, email, employeeId);
    }
    
    public Administrator(String cpr, String password, String firstName, String lastName, Address address, String phone, String email, String employeeId) {
        super(cpr, password, firstName, null, lastName, address, phone, email, employeeId);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Administrator)) {
            return false;
        }
        return super.equals(obj);
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
}
