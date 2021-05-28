package client.viewmodel.faq;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import server.model.domain.appointment.Type;
import server.model.domain.faq.Category;

public interface AddFAQViewModelInterface {
    void reset();

    boolean addEditFAQ();

    void back();

    ObservableList<Category> getAllCategories();

    ObjectProperty<Category> getCategoryProperty();

    StringProperty getQuestionProperty();

    StringProperty getAnswerProperty();

    StringProperty getErrorProperty();
}