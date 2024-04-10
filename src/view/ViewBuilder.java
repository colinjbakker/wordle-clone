package view;

import model.Model;

import javafx.util.Builder;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ViewBuilder implements Builder<Region>{
    private final Model model;
    private final Runnable submitHandler;

    public ViewBuilder(Model model, Runnable submitHandler){
        this.model = model;
        this.submitHandler = submitHandler;
    }

    @Override
    public Region build(){
        BorderPane page = new BorderPane();
        page.setTop(headingLabel("Wordle"));
        page.setCenter(createCenter());
        page.setBottom(createBottom());
        return page;
    }

    private Node headingLabel(String content){
        return new Label(content);
    }

    private Node createCenter(){
        VBox vbox = new VBox(6);
        for(int i = 0; i < 6; i++){
            vbox.getChildren().add(boundTextField(model.getListGuess(i).getTextProperty(), model.getListGuess(i).getEnableProperty()));
        }
        vbox.setPadding(new Insets(20));
        return vbox;
    }

    private Node createBottom(){
        Button submit = new Button("Guess");
        submit.setDefaultButton(true);
        submit.disableProperty().bind(model.gameDisableProperty());
        submit.setOnAction(evt -> submitHandler.run());
        HBox bottom = new HBox(10, submit);
        bottom.setAlignment(Pos.CENTER_RIGHT);
        return bottom;
    }

    private Node boundTextField(StringProperty boundProperty, BooleanProperty booleanProperty){
        TextField textField = new TextField();
        textField.textProperty().bindBidirectional(boundProperty);
        textField.disableProperty().bind(booleanProperty);
        return textField;
    }
}