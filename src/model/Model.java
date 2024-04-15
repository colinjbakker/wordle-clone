package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

//Data representation of the GUI, no logic

public class Model {
    private final SimpleStringProperty solution = new SimpleStringProperty("");
    private final SimpleIntegerProperty guessCount = new SimpleIntegerProperty(0);
    private final SimpleBooleanProperty universalDisable = new SimpleBooleanProperty(false);
    private final SimpleIntegerProperty inputCount = new SimpleIntegerProperty(0);
    private final SimpleStringProperty warning = new SimpleStringProperty("");
    private final SimpleBooleanProperty flipFlop = new SimpleBooleanProperty(false);


    private final SimpleBooleanProperty startVisibility = new SimpleBooleanProperty(true);
    private final SimpleBooleanProperty gameVisibility = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty statsVisibility = new SimpleBooleanProperty(false);

    private final Shadow guessList = new Shadow();

    public void reset(){
        solution.set("");
        guessCount.set(0);
        universalDisable.set(false);
        inputCount.set(0);
        warning.set("");
        flipFlop.set(false);
        guessList.reset();
    }

    public SimpleBooleanProperty getStartVisibilityProperty(){
        return startVisibility;
    }

    public Boolean getStartVisibility(){
        return startVisibility.get();
    }

    public SimpleBooleanProperty getGameVisibilityProperty(){
        return gameVisibility;
    }

    public Boolean getGameVisibility(){
        return gameVisibility.get();
    }

    public SimpleBooleanProperty getStatsVisibilityProperty(){
        return statsVisibility;
    }

    public Boolean getStatsVisibility(){
        return statsVisibility.get();
    }
    
    public void setVisibility(int index){
        if(index == 0){
            startVisibility.set(true);
            gameVisibility.set(false);
            statsVisibility.set(false);
        } else if(index == 1){
            startVisibility.set(false);
            gameVisibility.set(true);
            statsVisibility.set(false);
        } else {
            startVisibility.set(false);
            gameVisibility.set(false);
            statsVisibility.set(true);
        }
    }

    public SimpleBooleanProperty getFlipFlop(){
        return flipFlop;
    }

    public Boolean getFlipFlopBoolean(){
        return flipFlop.get();
    }

    public void flipFlipFlop(){
        flipFlop.set(!flipFlop.get());
    }

    public int getInputCount(){
        return inputCount.get();
    }

    public void setInputCount(int inputCount){
        this.inputCount.set(inputCount);
    }

    public String getSolution() {
        return solution.get();
    }

    public SimpleStringProperty solutionProperty() {
        return solution;
    }

    public void setSolution(String solution){
        this.solution.set(solution);
    }

    public int getGuessCount() {
        return guessCount.get();
    }

    public SimpleIntegerProperty guessCountProperty() {
        return guessCount;
    }

    public void setGuessCount(int guessCount) {
        this.guessCount.set(guessCount);
    }

    public void incrementGuessCount() {
        guessCount.set(getGuessCount() + 1);
    }

    public Boolean getGameDisable(){
        return universalDisable.getValue();
    }

    public SimpleBooleanProperty gameDisableProperty(){
        return universalDisable;
    }

    public void swapGameDisable(){
        universalDisable.setValue(!universalDisable.getValue());
    }

    public Shadow getShadow(){
        return guessList;
    }

    public Guess guessAt(int index){
        return guessList.guessAt(index);
    }

    public String getWarningString(){
        return warning.get();
    }
    
    public SimpleStringProperty getWarningProperty(){
        return warning;
    }

    public void setWarning(int type){
        if(type == 0){
            warning.set("Not in word list");
        } else if(type == 1){
            warning.set("Not enough letters");
        } else{
            warning.set("");
        }
    }

}
