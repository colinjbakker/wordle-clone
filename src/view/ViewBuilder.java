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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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

    // private Node createStartPane(){
    //     BorderPane borderPane = new BorderPane();
    //     //borderPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    //     borderPane.setTop(headingLabel("WORDLE"));
    //     borderPane.setCenter(createResetButton("START"));
    //     borderPane.visibleProperty().bind(model.getStartVisibilityProperty());
    //     return borderPane;
    // }

    private Node createResetButton(String content){
        Button reset = new Button(content);
        reset.setOnAction(e -> newGame.run());
        reset.setStyle("-fx-font-family: serif;" +"-fx-font-size: 16.0;" + "-fx-font-weight: bold;" + "-fx-border-style: solid;" + "-fx-border-width: 3;"+"-fx-border-color: rgb(65, 65, 65);"+"-fx-text-fill: white;" + "-fx-background-color: "+backgroundColor+";");
        return reset;
    }

    private Node createGamePane(){
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: " + backgroundColor + ";");
        HBox wordleHeader = (HBox)headingLabel("WORDLE");
        wordleHeader.setOpacity(0.65);
        model.getStatsVisibilityProperty().addListener((obs, oldVal, newVal) -> {
            if(model.getStatsVisibility()){
                wordleHeader.setOpacity(0.65);
            } else{
                wordleHeader.setOpacity(1);
            }
        });
        borderPane.setTop(wordleHeader);
        borderPane.setCenter(createCenter());
        borderPane.setBottom(createBottom());
        //borderPane.visibleProperty().bind(model.getGameVisibilityProperty());
        return borderPane;
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
            vbox.getChildren().add(createTextField(model.getShadow().guessAt(i).getGuessStringProperty(), model.getShadow().guessAt(i).getDisableProperty()));
        }
        return vbox;
    }

    private Node createTextField(StringProperty stringProperty, BooleanProperty disableProperty){
        TextField textField = new TextField();
        stringProperty.addListener((obs, newVal, oldVal) -> {
            textField.end();
        });
        textField.textProperty().bindBidirectional(stringProperty);
        textField.disableProperty().bind(disableProperty);
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
                if(!disableProperty.get()){
                    textField.requestFocus();
                    
                }
            }
        });
        // disableProperty.addListener((obs, oldVal, newVal) -> {
        //     if(!disableProperty.get()){
        //         System.out.println("request focus 2");
        //         textField.requestFocus();
        //     }
        // });
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
        VBox vbox = new VBox(8);
        vbox.setStyle("-fx-background-color: " + backgroundColor + ";");
        HBox[] hboxArray = new HBox[6];
        for(int i = 0; i < 6; i++){
            hboxArray[i] = new HBox(8);
            hboxArray[i].setAlignment(Pos.CENTER);
            for(int j = 0; j < 5; j++){
                Label label = (Label)createLabel(model.getShadow().guessAt(i).letterAt(j).getLetterStringProperty(), model.getShadow().guessAt(i).getDisableProperty(), model.getShadow().guessAt(i).letterAt(j).getColorProperty());
                if(i == 0){
                    label.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
                }
                hboxArray[i].getChildren().add(label);
            }
        }
        vbox.getChildren().addAll(hboxArray);            
        return vbox;
    }

    private Node createLabel(StringProperty stringProperty, BooleanProperty disableProperty, StringProperty colorProperty){
        Label label = new Label();
        label.textProperty().bind(stringProperty);
        label.setPrefSize(80, 80);
        label.setAlignment(Pos.CENTER);
        label.setOpacity(0.65);
        String style = "-fx-text-fill: white;" + "-fx-font-family: serif;"+"-fx-font-size: 35.0;" + "-fx-font-weight: bold;";
        label.setStyle(style);
        label.setBorder(new Border(new BorderStroke(Color.rgb(65, 65, 65), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4))));

        //label.styleProperty().bind(Bindings.when(disableProperty).then(style + "-fx-border-color: rgb(65, 65, 65);").otherwise(style + "-fx-border-color: white;"));

        disableProperty.addListener((obs, oldVal, newVal) -> {
            if(disableProperty.get()){
                label.setBorder(new Border(new BorderStroke(Color.rgb(65, 65, 65), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4))));
            } else{
                label.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4))));
            }
        });

        model.getStatsVisibilityProperty().addListener((obs, oldVal, newVal) -> {
            if(model.getStatsVisibility()){
                label.setOpacity(0.65);
            } else{
                label.setOpacity(1);
            }
        });

        Timeline tl = new Timeline();
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(200), new KeyValue(label.scaleYProperty(), 0)));
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(200), (e) -> label.setStyle(style + "-fx-background-color: " + colorProperty.get() + ";")));
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(400), new KeyValue(label.scaleYProperty(), 1)));

        colorProperty.addListener((obs, oldVal, newVal) -> {
            tl.play();
        });
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
        warning.textProperty().addListener((obs, oldVal, newVal) ->{
            if(warning.getText() == ""){
                warning.setOpacity(0);
            }
        });
        model.getFlipFlopProperty().addListener((obs, oldVal, newVal) -> {
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
            colorProp.addListener((obs, newVal, oldVal) -> {
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

        model.getStatsVisibilityProperty().addListener((obs, oldVal, newVal) -> {
            statsPane.setVisible(model.getStatsVisibility());
            if(model.getStatsVisibility()){
                System.out.println("playing animation");
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
