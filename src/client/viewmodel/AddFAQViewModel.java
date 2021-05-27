package client.viewmodel;

import client.model.FAQModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.model.domain.faq.Category;
import server.model.domain.user.Administrator;

import java.util.Collections;

public class AddFAQViewModel implements AddFAQViewModelInterface {
    private FAQModel faqModel;
    private ViewState viewState;
    private ObservableList<Category> categories;
    private ObjectProperty<Category> categoryProperty;
    private StringProperty questionProperty;
    private StringProperty answerProperty;
    private StringProperty errorProperty;

    public AddFAQViewModel(FAQModel faqModel, ViewState viewState) {
        this.faqModel = faqModel;
        this.viewState = viewState;
        categories = FXCollections.observableArrayList();
        categoryProperty = new SimpleObjectProperty<>();
        questionProperty = new SimpleStringProperty();
        answerProperty = new SimpleStringProperty();
        errorProperty = new SimpleStringProperty();
    }

    @Override
    public void reset() {
        categories.clear();
        Collections.addAll(categories, Category.values());
        if (viewState.getSelectedFAQ() == null) {
            categoryProperty.set(categories.get(0));
            questionProperty.set("");
            answerProperty.set("");
        } else {
            categoryProperty.set(viewState.getSelectedFAQ().getCategory());
            questionProperty.set(viewState.getSelectedFAQ().getQuestion());
            answerProperty.set(viewState.getSelectedFAQ().getAnswer());
        }
        errorProperty.set("");
    }

    @Override
    public boolean addEditFAQ() {
        try {
            if (viewState.getSelectedFAQ() == null)
                faqModel.addFAQ(questionProperty.get(), answerProperty.get(), categoryProperty.get(), (Administrator) viewState.getUser());
            else
                faqModel.editFAQ(viewState.getSelectedFAQ(), questionProperty.get(), answerProperty.get(), categoryProperty.get());
            viewState.removeSelectedFAQ();
            return true;
        } catch (Exception e) {
            errorProperty.set(e.getMessage());
            return false;
        }
    }

    @Override
    public void back() {
        viewState.removeSelectedFAQ();
    }

    @Override
    public ObservableList<Category> getAllCategories() {
        return categories;
    }

    @Override
    public ObjectProperty<Category> getCategoryProperty() {
        return categoryProperty;
    }

    @Override
    public StringProperty getQuestionProperty() {
        return questionProperty;
    }

    @Override
    public StringProperty getAnswerProperty() {
        return answerProperty;
    }

    @Override
    public StringProperty getErrorProperty() {
        return errorProperty;
    }
}
