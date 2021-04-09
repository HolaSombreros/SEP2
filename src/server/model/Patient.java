package server.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Patient
{
  private String cpr;
  private String password;
  private String firstName;
  private String middleName;
  private String lastName;
  private Address address;
  private String phone;
  private String email;

  public Patient(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email)
  {
    setCpr(cpr);
    setPassword(password);
    setFirstName(firstName);
    setMiddleName(middleName);
    setLastName(lastName);
    setAddress(address);
    setPhone(phone);
    setEmail(email);
  }

  public String getCpr()
  {
    return cpr;
  }

  public void setCpr(String cpr)
  {
    if (cpr == null || cpr.equals(""))
      throw new IllegalArgumentException("Please enter your CPR");
    if (cpr.contains("-"))
      cpr = cpr.replace("-", "");
    if (cpr.length() != 10)
      throw new IllegalArgumentException("Invalid CPR");
    try
    {
      Long.parseLong(cpr);
    }
    catch (Exception e)
    {
      throw new IllegalArgumentException("Invalid CPR");
    }
    this.cpr = cpr;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    if (password == null || password.equals(""))
      throw new IllegalArgumentException("Please enter a password between 6 and 20 characters");
    if (password.length()<6)
      throw new IllegalArgumentException("The password must contain at least 6 characters");
    if (password.length()>20)
      throw new IllegalArgumentException("The password must not contain more than 20 characters");
    this.password = password;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    if (firstName == null || firstName.equals(""))
      throw new IllegalArgumentException("Please enter your first name");
    this.firstName = firstName;
  }

  public String getMiddleName()
  {
    return middleName;
  }

  public void setMiddleName(String middleName)
  {
    this.middleName = middleName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String lastName)
  {
    if (lastName == null || lastName.equals(""))
      throw new IllegalArgumentException("Please enter your last name");
    this.lastName = lastName;
  }
  
  public String getFullName() {
    return middleName == null ? firstName + " " + lastName : firstName + " " + middleName + " " + lastName;
  }

  public Address getAddress()
  {
    return address;
  }

  public void setAddress(Address address)
  {
    this.address = address;
  }

  public String getPhone()
  {
    return phone;
  }

  public void setPhone(String phone)
  {
    if (phone == null || phone.equals(""))
      throw new IllegalArgumentException("Please enter your phone number");
    try
    {
      long number = Long.parseLong(phone.replace("+", ""));
    }
    catch (Exception e)
    {
      throw new IllegalArgumentException("The phone number is not valid");
    }
    this.phone = phone;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    if (email == null || email.equals(""))
      throw new IllegalArgumentException("Please enter your email address");
    String emailValidation = "^[A-Za-z0-9+_.-]+@(?:[a-zA-Z0-9-]+.)+[a-zA-Z]{2,6}$";
    Pattern pattern = Pattern.compile(emailValidation);
    Matcher matcher = pattern.matcher(email);
    if (!matcher.matches())
      throw new IllegalArgumentException("The email is not valid");
     this.email = email;
  }

  public String toString()
  {
    if (middleName == null)
      return cpr + " " + password + ":" + firstName + " " + lastName + " " + address.toString() + " " + phone + " " + email;
    else
      return cpr + " " + password + ":" + firstName + " " + lastName + " " + lastName + " " + address.toString() + " " + phone + " " + email;
  }

  public boolean equals(Object obj)
  {
    if (!(obj instanceof Patient))
      return false;

    Patient other = (Patient) obj;
    if (middleName == null)
      return cpr.equals(other.cpr) && password.equals(other.password) && firstName.equals(other.firstName) && lastName.equals(other.lastName) && address.equals(other.address)
          && phone.equals(other.phone) && email.equals(other.email);
    else
      return cpr.equals(other.cpr) && password.equals(other.password) && firstName.equals(other.firstName) && middleName.equals(other.middleName) && lastName.equals(other.lastName)
          && address.equals(other.address) && phone.equals(other.phone) && email.equals(other.email);
  }
}
