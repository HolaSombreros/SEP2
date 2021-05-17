package server.model.domain.faq;

import java.io.Serializable;

public enum FAQType implements Serializable {
    GENERAL("General questions"),
    PASSPORT("How can I obtain my passport for the COVID-vaccination?");
    
    private String text;
    
    private FAQType(String text) {
        this.text = text;
    }
    
    public static FAQType fromString(String value) {
        for (FAQType option : FAQType.values()) {
            if (option.text.equalsIgnoreCase(value)) {
                return option;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return text;
    }
}
