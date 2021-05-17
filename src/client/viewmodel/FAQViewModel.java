package client.viewmodel;

import client.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import server.model.domain.faq.FAQ;
import server.model.domain.faq.FAQContent;
import server.model.domain.faq.FAQList;
import server.model.domain.faq.FAQType;

import java.util.Map;

public class FAQViewModel implements FAQViewModelInterface {
    private Model model;
    private ViewState viewState;
    private ObservableList<Node> content;
    
    public FAQViewModel(Model model, ViewState viewState) {
        this.model = model;
        this.viewState = viewState;
        content = FXCollections.observableArrayList();
        
        loadFromModel();
    }
    
    private void loadFromModel() {
        content.clear();
        FAQContent faqContent = model.getFaqContent();
        if (faqContent.size() > 0) {
            for (Map.Entry<FAQType, FAQList> entry : faqContent.getContent().entrySet()) {
                System.out.println(entry.getKey().toString());
                VBox vBox = generateContent(entry);
                content.add(vBox);
            }
        }
    }
    
    private VBox generateContent(Map.Entry<FAQType, FAQList> faqList) {
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        Label header = new Label(faqList.getKey().toString());
        header.getStyleClass().add("sub-header");
        vBox.getChildren().add(header);
        Accordion accordion = new Accordion();
        for (FAQ faq : faqList.getValue().getQuestions()) {
            TitledPane titledPane = new TitledPane();
            titledPane.setText(faq.getQuestion());
            Label answer = new Label(faq.getAnswer());
            answer.setWrapText(true);
            titledPane.setContent(new VBox(answer));
            accordion.getPanes().add(titledPane);
        }
        vBox.getChildren().add(accordion);
        return vBox;
    }
    
    @Override
    public void reset() {
        loadFromModel();
    }
    
    @Override
    public ObservableList<Node> getFAQContent() {
        return content;
    }
}
