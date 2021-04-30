package server.model.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    /**
     * @param cpr ignores "-"
     *            sets the cpr to a given value
     *            if the given String is empty of invalid throws an exception
     */
    public static void setCpr(String cpr) {
        if (cpr == null || cpr.equals(""))
            throw new IllegalArgumentException("Please enter your CPR");
        
        if (cpr.contains("-")) {
            cpr = cpr.replace("-", "");
        }
        
        try {
            Long.parseLong(cpr);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid CPR");
        }
        
//        int cprDay = Integer.parseInt(cpr.substring(0, 2));
//        int cprMonth = Integer.parseInt(cpr.substring(2, 4));
        
        // validate day and month
//        new Date(cprDay, cprMonth, 2000);
        
        if (cpr.length() != 10) {
            throw new IllegalArgumentException("Invalid CPR");
        }
    }
    
    /**
     * @param password sets the password to a given value
     *                 if the given String is empty or invalid throws an exception
     */
    public static void setPassword(String password) {
        if (password == null || password.equals(""))
            throw new IllegalArgumentException("Please enter a password between 6 and 20 characters");
        if (password.length() < 6)
            throw new IllegalArgumentException("The password must contain at least 6 characters");
        if (password.length() > 20)
            throw new IllegalArgumentException("The password must not contain more than 20 characters");
    }
    
    /**
     * @param firstName sets the firstname to a given value
     *                  if the given String is empty or null throws an exception
     */
    public static void setFirstName(String firstName) {
        if (firstName == null || firstName.equals(""))
            throw new IllegalArgumentException("Please enter your first name");
    }
    
    /**
     * @param lastName sets the lastName to a given value
     *                 if the given String is empty or null throws an exception
     */
    public static void setLastName(String lastName) {
        if (lastName == null || lastName.equals(""))
            throw new IllegalArgumentException("Please enter your last name");
    }
    
    /**
     * @param phone sets the phone to a given value
     *              if the given String is empty or has empty spaces throws an exception
     */
    public static void setPhone(String phone) {
        if (phone == null || phone.equals("")) {
            throw new IllegalArgumentException("Please enter your phone number");
        }
        
        phone = phone.replace(" ", "");
        phone = phone.replace("+", "");
        try {
            Long.parseLong(phone);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("The phone number is not valid");
        }
    }
    
    /**
     * @param email sets the email to a given value
     *              if the given String is empty or has empty spaces throws an exception
     *              otherwise it will validate the email string
     */
    public static void setEmail(String email) {
        if (email == null || email.equals(""))
            throw new IllegalArgumentException("Please enter your email address");
        String emailValidation = "^[A-Za-z0-9+_.-]+@(?:[a-zA-Z0-9-]+.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailValidation);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches())
            throw new IllegalArgumentException("The email is not valid");
    }
}
