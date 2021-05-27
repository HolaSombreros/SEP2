package server.model.domain.user;

import server.model.validator.UserValidator;

import java.io.Serializable;

public abstract class User implements Serializable {
    private String cpr;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private Address address;
    private String phone;
    private String email;
    
    public User(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email) {
        setCpr(cpr);
        setPassword(password);
        setFirstName(firstName);
        setMiddleName(middleName);
        setLastName(lastName);
        setAddress(address);
        setPhone(phone);
        setEmail(email);
    }
    
    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        UserValidator.setCpr(cpr);
        if (cpr.contains("-")) {
            cpr = cpr.replace("-", "");
        }
        this.cpr = cpr;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        UserValidator.setPassword(password);
        this.password = password;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
       UserValidator.setFirstName(firstName);
        this.firstName = firstName;
    }
    
    public String getMiddleName() {
        return middleName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        UserValidator.setLastName(lastName);
        this.lastName = lastName;
    }
    
    public String getFullName() {
        return middleName == null ? firstName + " " + lastName : firstName + " " + middleName + " " + lastName;
    }
    
    public Address getAddress() {
        return address.copy();
    }
    
    public void setAddress(Address address) {
        this.address = address.copy();
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        UserValidator.setPhone(phone);
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        UserValidator.setEmail(email);
        this.email = email;
    }
    public void editUserInformation(String password, String firstName, String middleName, String lastName, Address address, String phone, String email){
        setPassword(password);
        setFirstName(firstName);
        setMiddleName(middleName);
        setLastName(lastName);
        setAddress(address);
        setPhone(phone);
        setEmail(email);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }
        User user = (User) obj;
        // Different middle names:
        if ((middleName == null && user.middleName != null) || (middleName != null && user.middleName == null)) {
            return false;
        }

        // Same middle name or both are null:
        if (middleName != null && !middleName.equals(user.middleName)) {
            return false;
        }

        return cpr.equals(user.cpr) &&
            password.equals(user.password) &&
            firstName.equals(user.firstName) &&
            lastName.equals(user.lastName) &&
            address.equals(user.address) &&
            phone.equals(user.phone) &&
            email.equals(user.email);
    }
    
    @Override
    public String toString() {
        if (middleName == null)
            return cpr + " " + password + " " + firstName + " " + lastName + " " + address.toString() + " " + phone + " " + email;
        else
            return cpr + " " + password + " " + firstName + " " + middleName + " " + lastName + " " + address.toString() + " " + phone + " " + email;
    }
}
