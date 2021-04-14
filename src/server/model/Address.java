package server.model;

import java.io.Serializable;

public class Address implements Serializable
{

  private String street;
  private String number;
  private int zipcode;
  private String city;

  public Address(String street, String number, int zipcode, String city)
  {
    setStreet(street);
    setNumber(number);
    setZipcode(zipcode);
    setCity(city);
  }

  public String getStreet()
  {
    return street;
  }

  /**
   * Throws exception if the street is null or empty
   *
   * @param street
   */
  public void setStreet(String street)
  {
    if (street == null || street.equals(""))
      throw new IllegalArgumentException("Please enter the street name");
    this.street = street;
  }

  public String getNumber()
  {
    return number;
  }

  /**
   * Throws exception if the number is null or empty
   *
   * @param number
   */
  public void setNumber(String number)
  {
    if (number == null || number.equals(""))
      throw new IllegalArgumentException("Please enter the number of your address");
    this.number = number;
  }

  public int getZipcode()
  {
    return zipcode;
  }

  /**
   * Throws exception if the zipcode is invalid
   *
   * @param zipcode between 1000 and 9999
   */
  public void setZipcode(int zipcode)
  {
    if (zipcode < 1000 || zipcode > 9999)
      throw new IllegalArgumentException("Invalid zipcode");
    this.zipcode = zipcode;
  }

  public String getCity()
  {
    return city;
  }

  /**
   * Throws exception if the city is null or empty
   *
   * @param city
   */
  public void setCity(String city)
  {
    if (city == null || city.equals(""))
      throw new IllegalArgumentException("Please enter the city");
    this.city = city;
  }

  public Address copy()
  {
    return new Address(street, number, zipcode, city);
  }

  public boolean equals(Object obj)
  {
    if (!(obj instanceof Address))
      return false;

    Address other = (Address) obj;
    return street.equals(other.street) && city.equals(other.city) && number.equals(other.number) && zipcode == other.zipcode;
  }

  public String toString()
  {
    return getZipcode() + " - " + getCity() + ": " + getStreet() + " " + getNumber();
  }
}
