package server.model.domain.faq;

import java.io.Serializable;

public enum Category implements Serializable {
    GENERAL("General questions"),
    PASSPORT("How can I obtain my passport for the COVID-vaccination?");
    
    private String title;
    
    private Category(String title) {
        this.title = title;
    }
    
    public static Category fromString(String value) {
        for (Category option : Category.values()) {
            if (option.title.equalsIgnoreCase(value)) {
                return option;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return title;
    }
}
