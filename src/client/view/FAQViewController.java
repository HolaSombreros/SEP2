package client.view;

import client.viewmodel.FAQViewModelInterface;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import server.model.domain.faq.Category;
import server.model.domain.faq.FAQ;

public class FAQViewController extends ViewController {
    private FAQViewModelInterface viewModel;
    
    @FXML private VBox dynamicVBox;
    @FXML private Button addEditFAQ;
    @FXML private Button removeButton;
    @FXML private Label errorLabel;

    public FAQViewController() { }

    @Override
    protected void init() {
        viewModel = getViewModelFactory().getFaqViewModel();
        addEditFAQ.visibleProperty().bind(viewModel.isAdminProperty());
        removeButton.visibleProperty().bind(viewModel.isAdminProperty());
        errorLabel.textProperty().bind(viewModel.errorLabelProperty());
        updateContent(viewModel.getFAQContent());
        viewModel.getUpdatedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                updateContent(viewModel.getFAQContent());
            }
        });
    
        reset();
    }
    
    private void updateContent(ObservableList<VBox> content) {
        System.out.println("Updated");
        dynamicVBox.getChildren().setAll(content);
    }
    
    @Override
    public void reset() {
        viewModel.reset();
    }
    
    @FXML
    private void chat() {
    
    }
    @FXML private void removeButton(){
        viewModel.remove();
    }

    @FXML
    private void addEditFAQ() {
        viewModel.addEditFAQ();
        getViewHandler().openView(View.ADDEDITFAQ);
    }
    
    @FXML
    private void back() {
        if (viewModel.isAdminProperty().get())
            getViewHandler().openView(View.USERLIST);
        else getViewHandler().openView(View.DASHBOARD);
    }
}
