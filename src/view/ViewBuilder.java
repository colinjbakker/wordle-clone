package view;

import model.Model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.function.UnaryOperator;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ViewBuilder{
    private final Model model;
    private final Runnable submitHandler;
    private final Runnable changeHandler;
    private final Runnable newGame;
    private final String backgroundColor = "rgb(0, 4, 23)";

    public ViewBuilder(Model model, Runnable submitHandler, Runnable changeHandler, Runnable newGame){
        this.model = model;
        this.submitHandler = submitHandler;
        this.changeHandler = changeHandler;
        this.newGame = newGame;
    }

    public Region build(){
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(createGamePane(), createStatsPane());
        return stackPane;
    }
    private Node createGamePane(){
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: " + backgroundColor + ";");
        borderPane.setTop(createTop());
        borderPane.setCenter(createCenter());
        borderPane.setBottom(createBottom());
        return borderPane;
    }

    private Node createResetButton(String content){
        Button reset = new Button(content);
        reset.setOnAction(e -> newGame.run());
        reset.setStyle("-fx-font-family: serif;" +"-fx-font-size: 16.0;" + "-fx-font-weight: bold;" + "-fx-border-style: solid;" + "-fx-border-width: 3;"+"-fx-border-color: rgb(65, 65, 65);"+"-fx-text-fill: white;" + "-fx-background-color: "+backgroundColor+";");
        return reset;
    }

    private Label headingLabel(String content){
        Label label = new Label(content);
        label.setStyle( "-fx-padding: 15;"+ "-fx-text-fill: white;"+"-fx-font-family: serif;"+"-fx-font-size: 50.0;" + "-fx-font-weight: bold;");
        return label;
    }

    //---------------TOP-------------------
    private Node createTop(){
        Label wordleHeader = headingLabel("WORDLE");
        //make header less opaque when stats pane is visible
        wordleHeader.setOpacity(0.65);
        model.getStatsVisibilityProperty().addListener((var1, var2, var3) -> { wordleHeader.setOpacity(model.getStatsVisibility() ? 0.65 : 1); });
        VBox vbox = new VBox(wordleHeader);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    //--------------CENTER-----------------
    private Node createCenter() { return new StackPane(createInput(), createOverlay(), createWarnings()); }

    private Node createInput(){
        VBox vbox = new VBox(6);
        for(int i = 0; i < 6; i++){
            vbox.getChildren().add(createTextField(model.getShadow().guessAt(i).getGuessStringProperty(), model.getShadow().guessAt(i).getDisableProperty()));
        }
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    private Node createOverlay(){
        VBox vbox = new VBox(8);
        vbox.setStyle("-fx-background-color: " + backgroundColor + ";");
        HBox[] hboxArray = new HBox[6];
        for(int i = 0; i < 6; i++){
            hboxArray[i] = new HBox(8);
            hboxArray[i].setAlignment(Pos.CENTER);
            for(int j = 0; j < 5; j++){
                Label label = createLetterLabel(model.getShadow().guessAt(i).letterAt(j).getLetterStringProperty(), model.getShadow().guessAt(i).getDisableProperty(), model.getShadow().guessAt(i).letterAt(j).getColorProperty(), j);
                if(i == 0){
                    label.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
                }
                hboxArray[i].getChildren().add(label);
            }
        }
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().addAll(hboxArray);            
        return vbox;
    }

    private Node createTextField(StringProperty stringProperty, BooleanProperty disableProperty){
        TextField textField = new TextField();
        stringProperty.addListener((var1, var2, var3) -> {
            textField.end();
        });

        textField.textProperty().bindBidirectional(stringProperty);
        textField.disableProperty().bind(disableProperty);
        textField.focusedProperty().addListener((var1, var2, var3) -> {
            if(!disableProperty.get()){
                textField.requestFocus();
            }
        });

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
        textField.textProperty().addListener((var1, var2, var3) ->  {
            textField.setText(textField.getText().toUpperCase());
            changeHandler.run();
        });

        return textField;
    }

    

    private Label createLetterLabel(StringProperty stringProperty, BooleanProperty disableProperty, StringProperty colorProperty, int index){
        Label label = new Label();
        label.textProperty().bind(stringProperty);
        label.setPrefSize(80, 80);
        label.setAlignment(Pos.CENTER);
        
        String style ="-fx-text-fill: white;" + "-fx-font-family: serif;"+"-fx-font-size: 35.0;" + "-fx-font-weight: bold;";
        label.setStyle(style);
        
        //label.styleProperty().bind(Bindings.when(disableProperty).then(style + "-fx-border-color: rgb(65, 65, 65);").otherwise(style + "-fx-border-color: white;"));

        //highlight current input line
        label.setBorder(new Border(new BorderStroke(Color.rgb(65, 65, 65), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4))));
        disableProperty.addListener((var1, var2, var3) -> { label.setBorder(new Border(new BorderStroke(disableProperty.get() ? Color.rgb(65, 65, 65) : Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4)))); });

        //make less opaque when stats pane visible
        label.setOpacity(0.65);
        model.getStatsVisibilityProperty().addListener((var1, var2, var3) -> { label.setOpacity(model.getStatsVisibility() ? 0.65 : 1); });


        //flip and change color when color changes
        Timeline flipTL = new Timeline();
        flipTL.getKeyFrames().add(new KeyFrame(Duration.millis(120), new KeyValue(label.scaleYProperty(), 0)));
        flipTL.getKeyFrames().add(new KeyFrame(Duration.millis(120), (e) -> label.setStyle(style + "-fx-background-color: " + colorProperty.get() + ";")));
        flipTL.getKeyFrames().add(new KeyFrame(Duration.millis(240), new KeyValue(label.scaleYProperty(), 1)));
        flipTL.setDelay(Duration.millis(index * 120));
        colorProperty.addListener((var1, var2, var3) -> { 
            if(colorProperty.get().equals(backgroundColor)){
                label.setStyle(style + "-fx-background-color: " + colorProperty.get() + ";");
            } else {
                flipTL.play(); 
            }
        });

        //make letter label "bump" when you type a letter
        Timeline bumpTL = new Timeline();
        bumpTL.getKeyFrames().add(new KeyFrame(Duration.millis(50), new KeyValue(label.scaleYProperty(), 1.12)));
        bumpTL.getKeyFrames().add(new KeyFrame(Duration.millis(50), new KeyValue(label.scaleXProperty(), 1.12)));
        bumpTL.setAutoReverse(true);
        bumpTL.setCycleCount(2);
        label.textProperty().addListener((var1, var2, var3) -> {
            if(var3 != " " && var3 != "")
                bumpTL.play();
        });

        return label;
    }

    private Node createWarnings(){
        Label warning = new Label();
        warning.setTranslateY(-270);
        warning.textProperty().bindBidirectional(model.getWarningProperty());

        //fade in and out animation
        warning.setOpacity(0);
        Timeline tl = new Timeline();
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(100), new KeyValue(warning.opacityProperty(), 0.95)));
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(1000), new KeyValue(warning.opacityProperty(), 0.95)));
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(1500), new KeyValue(warning.opacityProperty(), 0)));

        //submit valid answer before warning disappeares
        warning.textProperty().addListener((var1, var2, var3) ->{
            if(warning.getText() == ""){
                tl.stop();
                warning.setOpacity(0);
            }
        });

        //sometimes same warning twice in a row, so flip flop triggers warning animation not text property
        model.getFlipFlopProperty().addListener((var1, var2, var3) -> {
            tl.stop();
            tl.play();
        });

        warning.setStyle("-fx-font-family: serif;" + "-fx-font-size: 20.0;" + "-fx-font-weight: bold;" +  "-fx-background-radius: 2;" + "-fx-text-fill: black;" + "-fx-background-color: white;");
        warning.setPadding(new Insets(7, 10, 7, 10));
        return warning;
    }

    //-------------BOTTOM---------------
    private Node createBottom(){
        VBox bottom = new VBox(10);
        bottom.getChildren().addAll(createKeyboard(),createSubmit());
        bottom.setAlignment(Pos.TOP_CENTER);
        return bottom;
    }

    private Node createKeyboard(){
        VBox vbox = new VBox(6);
        HBox hbox1 = new HBox(6);
        HBox hbox2 = new HBox(6);
        HBox hbox3 = new HBox(6);
        vbox.setAlignment(Pos.TOP_CENTER);
        hbox1.setAlignment(Pos.CENTER);
        hbox2.setAlignment(Pos.CENTER);
        hbox3.setAlignment(Pos.CENTER);
        for(int i = 0; i < 26; i++){
            Button button = new Button();
            button.textProperty().bind(model.getShadow().keyAt(i).getLetterStringProperty());
            button.disableProperty().bind(model.getGameDisableProperty());
            button.setPrefSize(40, 40);
            button.setAlignment(Pos.CENTER);
            button.setOnAction(e -> {
                String newString = model.getShadow().guessAt(model.getGuessCount()).getGuessString() + button.textProperty().get();
                model.getShadow().guessAt(model.getGuessCount()).setGuessString(newString);
            });
            String style = "-fx-text-fill: white;" + "-fx-font-family: serif;"+"-fx-background-insets: 0;"+"-fx-font-size: 14.0;" + "-fx-font-weight: bold;" + "-fx-border-style: solid;" + "-fx-border-width: 3;"+ "-fx-border-color: rgb(65, 65, 65);";
            button.setStyle(style + "-fx-background-color: rgb(0,4,23);");
            SimpleStringProperty colorProp = model.getShadow().keyAt(i).getColorProperty();
            colorProp.addListener((var1, var2, var3) -> {
                button.setStyle(style + "-fx-background-color: " + colorProp.get() + ";");
            });
            

            // button.backgroundProperty().bind(
            //     Bindings.when(model.getShadow().keyAt(i).getGreen()).then(new Background(new BackgroundFill(Color.GREEN, null, null))).otherwise(
            //        Bindings.when(model.getShadow().keyAt(i).getYellow()).then(new Background(new BackgroundFill(Color.rgb(219, 161, 0), null, null))).otherwise(
            //             Bindings.when(model.getShadow().keyAt(i).getGray()).then(new Background(new BackgroundFill(Color.rgb(65, 65, 65), null, null))).otherwise(
            //                 new Background(new BackgroundFill(Color.rgb(0, 4, 23), null, null))
            //             )
            //         )
            //     )
            // );
            if(i < 10){
                hbox1.getChildren().add(button);
            }else if(i < 19){
                hbox2.getChildren().add(button);
            } else{
                hbox3.getChildren().add(button);
            }
        }
        vbox.getChildren().addAll(hbox1, hbox2, hbox3);
        return vbox;
    }

    private Node createSubmit(){
        Button submit = new Button("ENTER");
        submit.setDefaultButton(true);
        submit.disableProperty().bind(model.getGameDisableProperty());
        submit.setOnAction(e -> submitHandler.run());
        submit.setStyle("-fx-font-family: serif;" +"-fx-font-size: 16.0;" + "-fx-font-weight: bold;" + "-fx-border-style: solid;" + "-fx-border-width: 3;"+"-fx-border-color: rgb(65, 65, 65);"+"-fx-text-fill: white;" + "-fx-background-color: "+backgroundColor+";");
        return submit;
    }  

    private Node createStatsPane(){
        VBox statsPane = new VBox();
        Button resetButton = (Button)createResetButton("NEW GAME");
        resetButton.disableProperty().bind(model.getStatsVisibilityProperty().not());

        statsPane.getChildren().addAll(headingLabel("STATS"), createStatsCenter(), resetButton);

        statsPane.setVisible(true);
        
        Timeline tl = new Timeline();
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(400), new KeyValue(statsPane.translateYProperty(), 0)));
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(400), new KeyValue(statsPane.opacityProperty(), 1)));

        tl.setOnFinished((e) -> {statsPane.setDisable(false);});

        model.getStatsVisibilityProperty().addListener((var1, var2, var3) -> {
            statsPane.setVisible(model.getStatsVisibility());
            if(model.getStatsVisibility()){
                statsPane.setDisable(true);
                tl.play();
            } else{
                statsPane.setTranslateY(300);
                statsPane.setOpacity(0);
            }
        });

        statsPane.setAlignment(Pos.CENTER);
        statsPane.setMaxHeight(490);
        statsPane.setMaxWidth(380);
        statsPane.setStyle("-fx-background-color: rgb(0, 4, 23, 0.75);");
        statsPane.setEffect(new DropShadow(10.0, 3, 3, Color.rgb(6, 41, 77,0.5)));
        return statsPane;
    }

    private Node createStatsCenter(){
        //played, win%, streak, max streak
        //guess distribution
        VBox vbox = new VBox();
        //Button resetButton = (Button)createResetButton("NEW GAME");
        vbox.getChildren().addAll(createStatsHeader("STATISTICS"),createStatsLabels(), createStatsHeader("GUESS DISTRIBUTION"), createGraph());
        vbox.setAlignment(Pos.CENTER_LEFT);
        vbox.setSpacing(10);
        return vbox;
    }

    private Node createGraph(){
        VBox vbox = new VBox();
        for(int i = 1; i <= 6; i++){
            Label label1 = new Label(i + "");
            label1.setStyle("-fx-font-family: serif;"+"-fx-font-size: 20;"+"-fx-text-fill: white;");

            Label label2 = new Label();
            label2.textProperty().bind(model.getStats().winCountArrayPropertyAt(i-1).asString());
            //label2.translateXProperty().bind(Bindings.when(new SimpleBooleanProperty(model.getStats().winCountArrayPropertyAt(i-1).get() != 0)).then(-17).otherwise(0));
            label2.setStyle("-fx-font-family: serif;"+"-fx-font-size: 15;"+"-fx-text-fill: white;");
            label2.setTranslateX(-3);
            Rectangle rectangle = new Rectangle(0, 15, Color.rgb(1, 112, 66));
            rectangle.widthProperty().bind(model.getStats().winArrayPropertyAt(i-1));
            HBox hbox = new HBox(label1, rectangle, label2);
            hbox.setSpacing(6);
            hbox.setAlignment(Pos.CENTER_LEFT);
            vbox.getChildren().add(hbox);
            
        }
        vbox.setPadding(new Insets(0,0,0,50));
        vbox.setMaxWidth(260);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(10);
        return vbox;
    }

    private Node createStatsHeader(String content){
        Label label = new Label(content);
        label.setAlignment(Pos.CENTER_LEFT);
        label.setPadding(new Insets(0,0,0,50));
        label.setStyle("-fx-font-family: serif;"+"-fx-text-alignment: left;"+"-fx-font-weight: bold;"+"-fx-font-size: 12;"+"-fx-text-fill: white;"+"-fx-text-alignment: center;");
        return label;
    }

    private Node createStatsLabels(){
        HBox hbox = new HBox(
            createStatVBox(statLabel(model.getStats().getGameCountProperty()), statLabel("Played")),
            createStatVBox(statLabel(model.getStats().getWinRateProperty()), statLabel("Win %")), 
            createStatVBox(statLabel(model.getStats().getCurrentStreakProperty()), statLabel("Current Streak")), 
            createStatVBox(statLabel(model.getStats().getMaxStreakProperty()), statLabel("Max Streak"))
        );
        hbox.setSpacing(15);
        hbox.setAlignment(Pos.TOP_CENTER);
        return hbox;
    }

    private Node createStatVBox(Node label1, Node label2){
        VBox vbox = new VBox(label1, label2);
        vbox.setSpacing(6);
        vbox.setAlignment(Pos.TOP_CENTER);
        return vbox;
    }

    private Node statLabel(String content){
        Label label = new Label();
        label.setText(content);
        label.setAlignment(Pos.CENTER);
        label.setMaxWidth(70);
        label.setStyle("-fx-font-family: serif;"+"-fx-font-size: 15;"+"-fx-text-fill: white;"+"-fx-text-alignment: center;");
        label.setWrapText(true);
        return label;
    }
    private Node statLabel(SimpleIntegerProperty boundProperty){
        Label label = new Label();
        label.textProperty().bind(boundProperty.asString());
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-family: serif;"+"-fx-font-size: 25;"+"-fx-text-fill: white;"+"-fx-text-alignment: center;");
        return label;
    }

    private Node statLabel(SimpleStringProperty boundProperty){
        Label label = new Label();
        label.textProperty().bind(boundProperty);
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-family: serif;"+"-fx-font-size: 25;"+"-fx-text-fill: white;"+"-fx-text-alignment: center;");
        return label;
    }
}
