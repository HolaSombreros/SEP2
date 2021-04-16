package server.model;

public abstract class Staff extends User {
    private String employeeId;
    
    public Staff(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email, String employeeId) {
        super(cpr, password, firstName, middleName, lastName, address, phone, email);
        setEmployeeId(employeeId);
    }
    
    public String getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Staff)) {
            return false;
        }
        Staff staff = (Staff) obj;
        return super.equals(obj) && employeeId.equals(staff.employeeId);
    }
    
    @Override
    public String toString() {
        return super.toString() + " | Employee Id: " + employeeId;
    }
}
