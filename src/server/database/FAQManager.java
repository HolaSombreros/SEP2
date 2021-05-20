package server.database;

import server.model.domain.faq.Category;
import server.model.domain.faq.FAQ;
import server.model.domain.faq.FAQList;
import server.model.domain.user.Administrator;

import java.sql.*;

public class FAQManager {
    public FAQManager() {}
    
    public FAQ addFAQ(String question, String answer, Category category, Administrator creator) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO faq (question, answer, category, created_by) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, question);
            statement.setString(2, answer);
            statement.setString(3, category.toString());
            statement.setString(4, creator.getCpr());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                return new FAQ(keys.getInt("faq_id"), question, answer, category);
            }
            else {
                throw new SQLException("No keys were generated");
            }
        }
    }
    
    public FAQList getAllFAQs() throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM faq ORDER BY category;");
            ResultSet rs = statement.executeQuery();
            FAQList faqList = new FAQList();
            while (rs.next()) {
                int id = rs.getInt("faq_id");
                String question = rs.getString("question");
                String answer = rs.getString("answer");
                Category category = Category.fromString(rs.getString("category"));
                faqList.add(new FAQ(id, question, answer, category));
            }
            return faqList;
        }
    }
}
