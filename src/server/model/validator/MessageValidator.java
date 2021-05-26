package server.model.validator;

import server.model.domain.user.User;

public class MessageValidator
{

    public static void validateMessage(String text, User sender){
        if(text == null || text.trim().isEmpty())
            throw new IllegalArgumentException("Text cannot be null");
        if(text.length() >  400)
            throw new IllegalArgumentException("Your message is " + (text.length() -400) + " characters too long");
        if(sender == null)
            throw new IllegalArgumentException("User cannot be null");
    }
}
