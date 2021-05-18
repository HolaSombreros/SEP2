package client.viewmodel;

import client.model.Model;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.model.domain.faq.Category;
import server.model.domain.user.Administrator;

import java.util.Collections;

public class AddFAQViewModel implements AddFAQViewModelInterface
{
  private Model model;
  private ViewState viewState;
  private ObservableList<Category> categories;
  private ObjectProperty<Category> categoryProperty;
  private StringProperty questionProperty;
  private StringProperty answerProperty;
  private StringProperty errorProperty;

  public AddFAQViewModel(Model model, ViewState viewState)
  {
    this.model = model;
    this.viewState = viewState;
    categories = FXCollections.observableArrayList();
    categoryProperty = new SimpleObjectProperty<>();
    questionProperty = new SimpleStringProperty();
    answerProperty = new SimpleStringProperty();
    errorProperty = new SimpleStringProperty();
  }

  @Override public void reset()
  {
    categories.clear();
    Collections.addAll(categories, Category.values());
    categoryProperty.set(categories.get(0));
    questionProperty.set("");
    answerProperty.set("");
    errorProperty.set("");
  }

  @Override public boolean addFAQ() {
    try {
      model.addFAQ(questionProperty.get(), answerProperty.get(), categoryProperty.get(), (Administrator) viewState.getUser());
      return true;
    }
    catch (Exception e) {
      errorProperty.set(e.getMessage());
      return false;
    }
  }

  @Override public ObservableList<Category> getAllCategories()
  {
    return categories;
  }

  @Override public ObjectProperty<Category> getCategoryProperty()
  {
    return categoryProperty;
  }

  @Override public StringProperty getQuestionProperty()
  {
    return questionProperty;
  }

  @Override public StringProperty getAnswerProperty()
  {
    return answerProperty;
  }

  @Override public StringProperty getErrorProperty()
  {
    return errorProperty;
  }
}
