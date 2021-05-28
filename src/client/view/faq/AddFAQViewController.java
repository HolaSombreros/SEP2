package client.view.faq;

import client.view.View;
import client.view.ViewController;
import client.viewmodel.faq.AddFAQViewModelInterface;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import server.model.domain.faq.Category;

public class AddFAQViewController extends ViewController
{
    private AddFAQViewModelInterface viewModel;
    @FXML
    private ChoiceBox<Category> categoryChoice;
    @FXML
    private TextArea questionInput;
    @FXML
    private TextArea answerInput;
    @FXML
    private Label errorLabel;

    public AddFAQViewController() {
    }

    @Override
    protected void init() {
        viewModel = getViewModelFactory().getAddFAQViewModel();
        categoryChoice.setItems(viewModel.getAllCategories());
        categoryChoice.valueProperty().bindBidirectional(viewModel.getCategoryProperty());
        questionInput.textProperty().bindBidirectional(viewModel.getQuestionProperty());
        answerInput.textProperty().bindBidirectional(viewModel.getAnswerProperty());
        errorLabel.textProperty().bind(viewModel.getErrorProperty());
        reset();
    }

    @Override
    public void reset() {
        viewModel.reset();
    }

    @FXML
    private void back() {
        viewModel.back();
        getViewHandler().openView(View.FAQ);
    }

    @FXML
    private void addEdit() {
        if (viewModel.addEditFAQ())
            getViewHandler().openView(View.FAQ);
    }
}
