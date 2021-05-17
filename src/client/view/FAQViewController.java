package client.view;

import client.viewmodel.FAQViewModelInterface;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class FAQViewController extends ViewController {
    private FAQViewModelInterface viewModel;
    
    @FXML VBox dynamicVBox;
    
    @Override
    protected void init() {
        viewModel = getViewModelFactory().getFaqViewModel();
        dynamicVBox.getChildren().setAll(viewModel.getFAQContent());
    }
    
    @Override
    public void reset() {
        viewModel.reset();
    }
    
    @FXML
    private void chat() {
    
    }
    
    @FXML
    private void back() {
        getViewHandler().openView(View.DASHBOARD);
    }
}
