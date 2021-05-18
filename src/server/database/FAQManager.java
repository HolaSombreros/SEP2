package server.database;

import server.model.domain.faq.Category;
import server.model.domain.faq.FAQ;
import server.model.domain.faq.FAQList;
import server.model.domain.user.Administrator;

import java.sql.*;

public class FAQManager {
    public FAQManager() {
    
    }
    
    public FAQ addFAQ(String question, String answer, Category category, Administrator creator) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO faq (question, answer, category_id, created_by) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, question);
            statement.setString(2, answer);
            statement.setInt(3, Category.getId(category));
            statement.setString(4, creator.getCpr());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                return new FAQ(keys.getInt("id"), question, answer, category);
            }
            else {
                throw new SQLException("No keys were generated");
            }
        }
    }
    
    public FAQList getAllFAQs() throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM faq ORDER BY category_id;");
            ResultSet rs = statement.executeQuery();
            FAQList faqList = new FAQList();
            while (rs.next()) {
                int id = rs.getInt("id");
                String question = rs.getString("question");
                String answer = rs.getString("answer");
                Category category = Category.values()[rs.getInt("category_id") - 1];
                faqList.add(new FAQ(id, question, answer, category));
            }
            return faqList;
        }
    }
}