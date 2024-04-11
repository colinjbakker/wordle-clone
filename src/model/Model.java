package model;

import java.util.ArrayList;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

//Data representation of the GUI, no logic

public class Model {
    private final SimpleStringProperty guess = new SimpleStringProperty("");
    private final SimpleStringProperty solution = new SimpleStringProperty("hello");
    private final SimpleIntegerProperty guessCount = new SimpleIntegerProperty(0);
    private final SimpleBooleanProperty universalDisable = new SimpleBooleanProperty(false);
    private final SimpleIntegerProperty inputCount = new SimpleIntegerProperty(0);

    private final SimpleBooleanProperty test = new SimpleBooleanProperty(true);

    private final Shadow guessList = new Shadow();

    public int getInputCount(){
        return inputCount.get();
    }

    public void setInputCount(int inputCount){
        this.inputCount.set(inputCount);
    }
    
    public SimpleBooleanProperty getTest(){
        return test;
    }
    public String getGuess(){
        return guess.get();
    }

    public SimpleStringProperty guessProperty() {
        return guess;
    }  

    public void setGuess(String guess){
        this.guess.set(guess);
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

    public Guess guessAt(int index){
        return guessList.guessAt(index);
    }

}
