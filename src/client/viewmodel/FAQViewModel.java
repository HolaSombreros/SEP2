package client.viewmodel;

import client.model.Model;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import server.model.domain.faq.Category;
import server.model.domain.faq.FAQ;
import server.model.domain.faq.FAQList;
import server.model.domain.user.Administrator;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

public class FAQViewModel implements FAQViewModelInterface, LocalListener<FAQ,FAQ>
{
    private Model model;
    private ViewState viewState;
    private ObservableList<Node> content;
    private BooleanProperty adminProperty;

    public FAQViewModel(Model model, ViewState viewState) {
        this.model = model;
        model.addListener(this, "FAQ");
        this.viewState = viewState;
        content = FXCollections.observableArrayList();
        adminProperty = new SimpleBooleanProperty();
        loadFromModel();
    }
    
    private void loadFromModel() {
        content.clear();
        FAQList faqList = model.getFAQList();
        if (faqList.size() > 0) {
            generateContent(faqList);
        }
    }
    
    private void generateContent(FAQList faqList) {
        Category category = null;
        Accordion accordion = null;
        
        for (FAQ faq : faqList.getQuestions()) {
            TitledPane titledPane = new TitledPane();
            titledPane.setText(faq.getQuestion());
            Label answer = new Label(faq.getAnswer());
            answer.setWrapText(true);
            titledPane.setContent(new VBox(answer));
    
            if (!faq.getCategory().equals(category)) {
                VBox vBox = new VBox();
                vBox.setSpacing(5);
                category = faq.getCategory();
                Label header = new Label(category.toString());
                header.getStyleClass().add("sub-header");
                vBox.getChildren().add(header);
                accordion = new Accordion();
                vBox.getChildren().add(accordion);
                
                // Add category section to content
                content.add(vBox);
            }
            accordion.getPanes().add(titledPane);
        }
    }

    private void addFAQ(FAQ faq) {
      //  for (int i=0; i < content.size(); i++)
          //  content.
    }
    
    @Override
    public void reset() {
        loadFromModel();
        isAdminProperty().set(viewState.getUser() instanceof Administrator);
    }
    
    @Override
    public ObservableList<Node> getFAQContent() {
        return content;
    }

    @Override public BooleanProperty isAdminProperty() {
        return adminProperty;
    }

    @Override public void propertyChange(ObserverEvent<FAQ, FAQ> event)
    {
        Platform.runLater(()->addFAQ(event.getValue2()));
    }
}
