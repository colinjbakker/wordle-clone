package view;

import model.Model;

import javafx.util.Builder;

import javafx.scene.paint.Color;

import java.util.function.UnaryOperator;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
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
        page.setTop(headingLabel("WORDLE"));
        page.setCenter(createCenter());
        page.setBottom(createBottom());
        return page;
    }

    //----------------TOP-----------------
    private Node headingLabel(String content){
        HBox hbox = new HBox();
        Label label = new Label(content);
        label.setTextFill(Color.WHITE);
        label.setStyle("-fx-font-family: serif;"+"-fx-font-size: 50.0;" + "-fx-font-weight: bold;");
        hbox.getChildren().add(label);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(15));
        return hbox;
    }

    //--------------CENTER-----------------
    private Node createCenter(){
        Node input = createInput();
        Node overlay = createOverlay();
        Node warnings = createWarnings();
        StackPane stackPane = new StackPane();
        StackPane.setAlignment(input, Pos.CENTER);
        StackPane.setAlignment(overlay, Pos.CENTER);
        StackPane.setAlignment(warnings, Pos.TOP_CENTER);
        stackPane.getChildren().addAll(input, overlay, warnings);
        return stackPane;
    }

    private Node createInput(){
        VBox vbox = new VBox(6);
        for(int i = 0; i < 6; i++){
            vbox.getChildren().add(createTextField(model.guessAt(i).getGuessStringProperty(), model.guessAt(i).getDisableProperty()));
        }
        return vbox;
    }

    private Node createTextField(StringProperty stringProperty, BooleanProperty disableProperty){
        TextField textField = new TextField();
        textField.textProperty().bindBidirectional(stringProperty);
        textField.disableProperty().bind(disableProperty);
        
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getControlNewText();
            //[a-zA-Z]* 0 or more letters between a and z
            if(text.length() <= 5 && text.matches("[a-zA-Z]*")){
                return change;
            }
            return null;
        };
        TextFormatter<String> tf = new TextFormatter<>(filter);
        textField.setTextFormatter(tf);

        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
                textField.setText(textField.getText().toUpperCase());
                changeHandler.run();
            }
        });

        return textField;
    }

    private Node createOverlay(){
        VBox vbox = new VBox(6);
        vbox.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        HBox[] hboxArray = new HBox[6];
        for(int i = 0; i < 6; i++){
            hboxArray[i] = new HBox(6);
            hboxArray[i].setAlignment(Pos.CENTER);
            for(int j = 0; j < 5; j++){
                hboxArray[i].getChildren().add(createLabel(model.guessAt(i).letterAt(j).getLetterStringProperty(), model.guessAt(i).getDisableProperty(), model.guessAt(i).letterAt(j).getLetterCorrect(), model.guessAt(i).letterAt(j).getLetterInSolution(), model.guessAt(i).letterAt(j).getLetterUnsubmitted()));
            }
        }
        vbox.getChildren().addAll(hboxArray);
        return vbox;
    }

    private Node createLabel(StringProperty stringProperty, BooleanProperty disableProperty, BooleanProperty correctProperty, BooleanProperty inSolutionProperty, BooleanProperty letterUnsubmitted){
        Label label = new Label();
        label.textProperty().bind(stringProperty);
        label.setPrefSize(80, 80);
        label.setAlignment(Pos.CENTER);
        label.setTextFill(Color.WHITE);
        String style = "-fx-font-family: serif;"+"-fx-font-size: 35.0;" + "-fx-font-weight: bold;" + "-fx-border-style: solid;" + "-fx-border-width: 3;";
        label.styleProperty().bind(Bindings.when(disableProperty).then(style + "-fx-border-color: rgb(57, 57, 57);").otherwise(style + "-fx-border-color: white;"));
        label.backgroundProperty().bind(Bindings.when(letterUnsubmitted).then(new Background(new BackgroundFill(Color.BLACK, null, null))).otherwise(Bindings.when(correctProperty)
                                            .then(new Background(new BackgroundFill(Color.GREEN, null, null))).otherwise(Bindings.when(inSolutionProperty)
                                            .then(new Background(new BackgroundFill(Color.rgb(219, 161, 0), null, null))).otherwise(new Background(new BackgroundFill(Color.rgb(57, 57, 57), null, null))))));
        return label;
    }

    private Node createWarnings(){
        Label warning = new Label();
        warning.textProperty().bind(model.getWarningProperty());
        warning.setTextFill(Color.BLACK);
        warning.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        warning.setStyle("-fx-font-family: serif;"+"-fx-font-size: 15.0;" + "-fx-font-weight: bold;");
        return warning;
    }

    //-------------BOTTOM---------------
    private Node createBottom(){
        VBox bottom = new VBox(10);
        bottom.getChildren().add(createSubmit());
        bottom.setAlignment(Pos.CENTER);
        return bottom;
    }

    private Node createSubmit(){
        Button submit = new Button("Enter");
        submit.setDefaultButton(true);
        submit.disableProperty().bind(model.gameDisableProperty());
        submit.setOnAction(evt -> submitHandler.run());
        return submit;
    }    
}