package view;

import model.Model;

import javafx.util.Builder;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ViewBuilder implements Builder<Region>{
    private final Model model;
    private final Runnable submitHandler;
    private final Runnable changeHandler;

    public ViewBuilder(Model model, Runnable submitHandler, Runnable changeHandler){
        this.model = model;
        this.submitHandler = submitHandler;
        this.changeHandler = changeHandler;
    }

    @Override
    public Region build(){
        BorderPane page = new BorderPane();
        page.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        page.setTop(headingLabel("Wordle"));
        page.setCenter(new Group(createInput(),createOverlay()));
        page.setBottom(createBottom());
        return page;
    }

    private Node headingLabel(String content){
        return new Label(content);
    }

    private Node createOverlay(){
        VBox vbox = new VBox(6);
        vbox.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        HBox[] hboxArray = new HBox[6];
        for(int i = 0; i < 6; i++){
            hboxArray[i] = new HBox(6);
            for(int j = 0; j < 5; j++){
                hboxArray[i].getChildren().add(createLabel(model.guessAt(i).letterAt(j).getLetterStringProperty(), model.guessAt(i).letterAt(j).getLetterCorrect(), model.guessAt(i).letterAt(j).getLetterInSolution()));
            }
        }
        vbox.getChildren().addAll(hboxArray);
        return vbox;
    }

    private Node createInput(){
        VBox vbox = new VBox(6);
        for(int i = 0; i < 6; i++){
            vbox.getChildren().add(createTextField(model.guessAt(i).getGuessStringProperty(), model.guessAt(i).getDisableProperty()));
        }
        return vbox;
    }

    private Node createLabel(StringProperty stringProperty, BooleanProperty correctProperty, BooleanProperty inSolutionProperty){
        Label label = new Label();
        label.textProperty().bind(stringProperty);
        label.setPrefSize(80, 80);
        label.setAlignment(Pos.CENTER);
        label.setFont(new Font(30.0));
        label.setTextFill(Color.WHITE);
        label.setStyle("-fx-border-style: solid;" 
        + "-fx-border-width: 5;" 
        + "-fx-border-color: gray;");
        label.backgroundProperty().bind(Bindings.when(correctProperty)
                                            .then(new Background(new BackgroundFill(Color.GREEN, null, null))).otherwise(Bindings.when(inSolutionProperty)
                                            .then(new Background(new BackgroundFill(Color.GOLD, null, null))).otherwise(new Background(new BackgroundFill(Color.BLACK, null, null)))));
        return label;
    }

    private Node createTextField(StringProperty stringProperty, BooleanProperty disableProperty){
        TextField textField = new TextField();
        textField.textProperty().bindBidirectional(stringProperty);
        textField.disableProperty().bind(disableProperty);
        textField.setOnKeyReleased(evt -> changeHandler.run());
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
                if(textField.getText().length() > 5){
                    textField.setText(textField.getText().substring(0, 5));
                }
                textField.setText(textField.getText().toUpperCase());
            }
        });

        return textField;
    }

    private Node createBottom(){
        Button submit = new Button("Enter");
        submit.setDefaultButton(true);
        submit.disableProperty().bind(model.gameDisableProperty());
        submit.setOnAction(evt -> submitHandler.run());
        HBox bottom = new HBox(10, submit);
        bottom.setAlignment(Pos.CENTER_RIGHT);
        return bottom;
    }

    
}