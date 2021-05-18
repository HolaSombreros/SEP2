package server.model.domain.faq;

import java.io.Serializable;

public enum Category implements Serializable {
    GENERAL("General questions"),
    PASSPORT("How can I obtain my passport for the COVID-vaccination?");
    
    private String title;
    
    private Category(String title) {
        this.title = title;
    }
    
    public static int getId(Category category) {
        for (int i = 0; i < Category.values().length; i++) {
            if (category.toString().equalsIgnoreCase(Category.values()[i].toString())) {
                return i + 1;
            }
        }
        throw new IllegalStateException("Something went very wrong...");
    }
    
    @Override
    public String toString() {
        return title;
    }
}
