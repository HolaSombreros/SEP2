package server.model.validator;

import server.model.domain.user.Patient;

public class MessageValidator
{

    public static void validateMessage(String text, Patient patient){
        if(text == null || text.trim().isEmpty())
            throw new IllegalArgumentException("Please enter a message");
        
        if(text.length() > 400)
            throw new IllegalArgumentException("Your message is " + (text.length() -400) + " characters too long");
        
        if(patient == null)
            throw new IllegalArgumentException("Patient cannot be null");
    }
}
