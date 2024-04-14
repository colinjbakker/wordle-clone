package view;

import model.Model;

import javafx.scene.paint.Color;

import javafx.util.Duration;
import java.util.function.UnaryOperator;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
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

public class ViewBuilder{
    private final Model model;
    private final Runnable submitHandler;
    private final Runnable changeHandler;

    public ViewBuilder(Model model, Runnable submitHandler, Runnable changeHandler){
        this.model = model;
        this.submitHandler = submitHandler;
        this.changeHandler = changeHandler;
    }

    public Region build(){
        BorderPane page = new BorderPane();
        page.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        page.setTop(headingLabel("WORDLE"));
        page.setCenter(createCenter());
        page.setBottom(createBottom());
        return page;
    }

    //----------------TOP------------------
    private Node headingLabel(String content){
        HBox hbox = new HBox();
        Label label = new Label(content);
        label.setStyle("-fx-text-fill: white;"+"-fx-font-family: serif;"+"-fx-font-size: 50.0;" + "-fx-font-weight: bold;");
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
        textField.textProperty().addListener((obs, oldVal, newVal) ->  {
            textField.setText(textField.getText().toUpperCase());
            changeHandler.run();
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
                hboxArray[i].getChildren().add(createLabel(model.guessAt(i).letterAt(j).getLetterStringProperty(), model.guessAt(i).getDisableProperty(), model.guessAt(i).letterAt(j).getGreen(), model.guessAt(i).letterAt(j).getYellow(), model.guessAt(i).letterAt(j).getGray()));
            }
        }
        vbox.getChildren().addAll(hboxArray);
        return vbox;
    }

    private Node createLabel(StringProperty stringProperty, BooleanProperty disableProperty, BooleanProperty green, BooleanProperty yellow, BooleanProperty gray){
        Label label = new Label();
        label.textProperty().bind(stringProperty);
        label.setPrefSize(80, 80);
        label.setAlignment(Pos.CENTER);
        String style = "-fx-text-fill: white;" + "-fx-font-family: serif;"+"-fx-font-size: 35.0;" + "-fx-font-weight: bold;" + "-fx-border-style: solid;" + "-fx-border-width: 3;";
        label.styleProperty().bind(Bindings.when(disableProperty).then(style + "-fx-border-color: rgb(57, 57, 57);").otherwise(style + "-fx-border-color: white;"));
        label.backgroundProperty().bind(
            Bindings.when(green).then(new Background(new BackgroundFill(Color.GREEN, null, null))).otherwise(
                Bindings.when(yellow).then(new Background(new BackgroundFill(Color.rgb(219, 161, 0), null, null))).otherwise(
                    Bindings.when(gray).then(new Background(new BackgroundFill(Color.rgb(57, 57, 57), null, null))).otherwise(
                        new Background(new BackgroundFill(Color.BLACK, null, null))
                    )
                )
            )
        );

        return label;
    }

    private Node createWarnings(){
        Label warning = new Label();
        warning.setOpacity(0);
        warning.setTranslateY(-15);
        warning.textProperty().bindBidirectional(model.getWarningProperty());

        Timeline tl = new Timeline();
        tl.getKeyFrames().add(new KeyFrame(Duration.ZERO, (e) -> {warning.setOpacity(0.95);}));
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(1500)));

        tl.onFinishedProperty().set((ActionEvent e) -> {
            warning.setOpacity(0);
            warning.setText("");
        });
        model.getFlipFlop().addListener((obs, oldVal, newVal) -> {
            tl.stop();
            tl.play();
        });
        warning.setStyle("-fx-font-family: serif;" + "-fx-font-size: 20.0;" + "-fx-font-weight: bold;" + "-fx-background-radius: 2;" + "-fx-text-fill: black;" + "-fx-background-color: white;");
        warning.setPadding(new Insets(7, 10, 7, 10));
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
        submit.setOnAction(e -> submitHandler.run());
        return submit;
    }    
}