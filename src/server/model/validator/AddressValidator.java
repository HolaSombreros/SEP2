package server.model.validator;

public class AddressValidator {

    /**
     * Throws exception if the street is null or empty
     *
     * @param street
     */
    public static void streetValidator(String street){
        if (street == null || street.equals(""))
            throw new IllegalArgumentException("Please enter the street name");
    }
    /**
     * Throws exception if the number is null or empty
     *
     * @param number
     */
    public static void setNumber(String number){
        if (number == null || number.equals(""))
            throw new IllegalArgumentException("Please enter the number of your address");
    }
    /**
     * Throws exception if the zipcode is invalid
     *
     * @param zipcode between 1000 and 9999
     */
    public static void setZipcode(int zipcode){
        if (zipcode < 1000 || zipcode > 9999)
            throw new IllegalArgumentException("Invalid zipcode");
    }
    /**
     * Throws exception if the city is null or empty
     *
     * @param city
     */
    public static void setCity(String city) {
        if (city == null || city.equals(""))
            throw new IllegalArgumentException("Please enter the city");
    }
}
