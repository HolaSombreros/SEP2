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
    @FXML private Button removeButton;

    public FAQViewController() { }

    @Override
    protected void init() {
        viewModel = getViewModelFactory().getFaqViewModel();
        addFAQ.visibleProperty().bind(viewModel.isAdminProperty());
        removeButton.visibleProperty().bind(viewModel.isAdminProperty());
//        removeButton..bindBidirectional(viewModel.removeButtonProperty());
        viewModel.addBox(dynamicVBox);
        reset();
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
