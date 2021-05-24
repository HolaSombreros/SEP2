package server.model.domain.user;

import server.model.validator.AddressValidator;

import java.io.Serializable;

public class Address implements Serializable {
    private String street;
    private String number;
    private int zipcode;
    private String city;
    
    public Address(String street, String number, int zipcode, String city) {
        setStreet(street);
        setNumber(number);
        setZipcode(zipcode);
        setCity(city);
    }
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        AddressValidator.streetValidator(street);
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        AddressValidator.setNumber(number);
        this.number = number;
    }
    
    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        AddressValidator.setZipcode(zipcode);
        this.zipcode = zipcode;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
       AddressValidator.setCity(city);
        this.city = city;
    }
    
    public Address copy() {
        return new Address(street, number, zipcode, city);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Address))
            return false;
        
        Address other = (Address) obj;
        return street.equals(other.street) && city.equals(other.city) && number.equals(other.number) && zipcode == other.zipcode;
    }
    
    @Override
    public String toString() {
        return getZipcode() + " - " + getCity() + ": " + getStreet() + " " + getNumber();
    }
}
