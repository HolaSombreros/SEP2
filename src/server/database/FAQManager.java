package server.database;

import server.model.domain.faq.FAQContent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FAQManager {
    public FAQManager() {
    
    }
    
    public FAQContent getAllFAQs() throws SQLException {
        FAQContent faqContent = new FAQContent();
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM faq");
        }
        return faqContent;
    }
}
