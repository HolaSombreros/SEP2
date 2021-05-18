package client.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import server.model.domain.appointment.Type;
import server.model.domain.faq.Category;

public interface AddFAQViewModelInterface
{
  void reset();
  boolean addFAQ();
  ObservableList<Category> getAllCategories();
  ObjectProperty<Category> getCategoryProperty();
  StringProperty getQuestionProperty();
  StringProperty getAnswerProperty();
  StringProperty getErrorProperty();
}
