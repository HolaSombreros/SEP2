package server.model;

public class Nurse extends Patient
{
  private String employeeId;

  public Nurse(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email, String employeeId)
  {
    super(cpr, password, firstName, middleName, lastName, address, phone, email);
    setEmployeeId(employeeId);
  }

  public Nurse(String cpr, String password, String firstName, String lastName, Address address, String phone, String email, String employeeId)
  {
    super(cpr, password, firstName, lastName, address, phone, email);
    setEmployeeId(employeeId);
  }

  public String getEmployeeId()
  {
    return employeeId;
  }

  public void setEmployeeId(String employeeId)
  {
    this.employeeId = employeeId;
  }

  public String toString()
  {
    return employeeId + " " + super.toString();
  }

  public boolean equals(Object obj)
  {
    if (!(obj instanceof Nurse))
      return false;

    Nurse other = (Nurse) obj;
    return super.equals(other) && employeeId.equals(other.employeeId);
  }
}
