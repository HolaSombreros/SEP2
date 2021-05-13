package util;

import javafx.util.StringConverter;

public class IntStringConverter extends StringConverter<Number> {
    @Override
    public String toString(Number number) {
        if (number == null || number.intValue() == 0) {
            return "";
        }
        else {
            return number.toString();
        }
    }
    
    @Override
    public Number fromString(String string) {
        if (string.isEmpty())
            return 0;
        try {
            return Integer.parseInt(string);
        }
        catch (Exception e) {
            return -1;
        }
    }
}
