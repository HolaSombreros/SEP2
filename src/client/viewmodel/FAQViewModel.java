package client.viewmodel;

import client.model.Model;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import server.model.domain.faq.Category;
import server.model.domain.faq.FAQ;
import server.model.domain.faq.FAQList;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;
import utility.observer.subject.LocalSubject;

public class FAQViewModel implements FAQViewModelInterface, LocalListener<FAQ, FAQ>
{
    private Model model;
    private ViewState viewState;
    private ObservableList<VBox> content;
    private BooleanProperty adminProperty;
    private BooleanProperty removeButton;
    private StringProperty errorLabel;
    private VBox vBox;
    private TitledPane selectedBox;

    public FAQViewModel(Model model, ViewState viewState) {
        this.model = model;
        this.viewState = viewState;
        this.vBox = null;
        this.selectedBox = null;
        this.removeButton = new SimpleBooleanProperty();
        this.errorLabel = new SimpleStringProperty();
        content = FXCollections.observableArrayList();
        adminProperty = new SimpleBooleanProperty();
//        loadFromModel();
        model.addListener(this,"FAQ", "FAQRemove");
    }
    
    private void loadFromModel() {
        content.clear();
        FAQList faqList = model.getFAQList();
        generateContent(faqList);
        vBox.getChildren().setAll(content);
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
                    titledPane.expandedProperty().addListener((obs, oldVal,newVal) -> {
                        if(newVal)
                            setSelectedBox(titledPane);
                    });
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
    public void addBox(VBox box) {
        vBox = box;
    }

    @Override
    public void remove()
    {
        if(selectedBox != null) {
            model.removeFAQ(selectedBox.getText(), ((Label) ((VBox) selectedBox.getContent()).getChildren().get(0)).getText());
            selectedBox = null;
        }
        else
            errorLabel.set("Please select a FAQ first");

    }

    @Override
    public void setSelectedBox(TitledPane box) {
       selectedBox = box;
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

    @Override
    public BooleanProperty removeButtonProperty()
    {
        return removeButton;
    }

    @Override
    public StringProperty errorLabelProperty()
    {
        return errorLabel;
    }

    @Override
    public void propertyChange(ObserverEvent<FAQ, FAQ> observerEvent)
    {
        Platform.runLater(() -> {
            loadFromModel();
            System.out.println(observerEvent.getPropertyName());
        });
    }
}
