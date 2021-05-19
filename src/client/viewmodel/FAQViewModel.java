package client.viewmodel;

import client.model.Model;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import server.model.domain.faq.Category;
import server.model.domain.faq.FAQ;
import server.model.domain.faq.FAQList;

public class FAQViewModel implements FAQViewModelInterface
{
    private Model model;
    private ViewState viewState;
    private ObservableList<VBox> content;
    private BooleanProperty adminProperty;

    public FAQViewModel(Model model, ViewState viewState) {
        this.model = model;
        this.viewState = viewState;
        content = FXCollections.observableArrayList();
        adminProperty = new SimpleBooleanProperty();
        loadFromModel();
    }
    
    private void loadFromModel() {
        content.clear();
        FAQList faqList = model.getFAQList();
        generateContent(faqList);
    }
    
    private void generateContent(FAQList faqList) {
        for (Category category : Category.values()) {
            VBox vBox = new VBox();
            vBox.setSpacing(5);
            Label header = new Label(category.toString());
            header.getStyleClass().add("sub-header");
            vBox.getChildren().add(header);
            Accordion accordion = new Accordion();
            vBox.getChildren().add(accordion);

            for (FAQ faq : faqList.getQuestions()) {
                if (faq.getCategory().equals(category)) {
                    TitledPane titledPane = new TitledPane();
                    titledPane.setText(faq.getQuestion());
                    Label answer = new Label(faq.getAnswer());
                    answer.setWrapText(true);
                    titledPane.setContent(new VBox(answer));
                    accordion.getPanes().add(titledPane);
                }
            }
            content.add(vBox);
        }
    }

    private void addFAQ(FAQ faq) {
        for (VBox vBox : content)
        {
            if (((Label) vBox.getChildren().get(0)).textProperty().get().equals(faq.getCategory().toString()))
            {
                TitledPane titledPane = new TitledPane();
                titledPane.setText(faq.getQuestion());
                Label answer = new Label(faq.getAnswer());
                answer.setWrapText(true);
                titledPane.setContent(new VBox(answer));
                Accordion accordion = (Accordion) vBox.getChildren().get(1);
                accordion.getPanes().add(titledPane);
            }
            else
            {
                TitledPane titledPane = new TitledPane();
                titledPane.setText(faq.getQuestion());
                Label answer = new Label(faq.getAnswer());
                answer.setWrapText(true);
                titledPane.setContent(new VBox(answer));
                VBox vBox1 = new VBox();
                vBox1.setSpacing(5);
                Category category = faq.getCategory();
                Label header = new Label(category.toString());
                header.getStyleClass().add("sub-header");
                vBox1.getChildren().add(header);
                Accordion accordion = new Accordion();
                vBox1.getChildren().add(accordion);
                accordion.getPanes().add(titledPane);
                content.add(vBox1);
            }
        }
    }
    
    @Override
    public void reset() {
        adminProperty.set(viewState.getAdmin() != null);
        loadFromModel();
    }
    
    @Override
    public ObservableList<VBox> getFAQContent() {
        return content;
    }

    @Override public BooleanProperty isAdminProperty() {
        return adminProperty;
    }

}
