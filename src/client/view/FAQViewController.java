package client.view;

import client.viewmodel.FAQViewModelInterface;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class FAQViewController extends ViewController {
    private FAQViewModelInterface viewModel;
    
    @FXML VBox dynamicVBox;
    @FXML private Button addFAQ;
    
    @Override
    protected void init() {
        viewModel = getViewModelFactory().getFaqViewModel();
        addFAQ.visibleProperty().bind(viewModel.isAdminProperty());
        reset();
    }
    
    @Override
    public void reset() {
        viewModel.reset();
        dynamicVBox.getChildren().setAll(viewModel.getFAQContent());
    }
    
    @FXML
    private void chat() {
    
    }

    @FXML
    private void addFAQ() {
        getViewHandler().openView(View.ADDFAQ);
    }
    
    @FXML
    private void back() {
        if (viewModel.isAdminProperty().get())
            getViewHandler().openView(View.USERLIST);
        else getViewHandler().openView(View.DASHBOARD);
    }
}
