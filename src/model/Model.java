package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
//Data representation of the GUI, no logic

public class Model {
    private final SimpleStringProperty guess = new SimpleStringProperty("");
    private final SimpleStringProperty solution = new SimpleStringProperty("hello");
    private final SimpleIntegerProperty guessCount = new SimpleIntegerProperty(0);
    private final SimpleBooleanProperty win = new SimpleBooleanProperty(false);

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

    public Boolean getWin(){
        return win.getValue();
    }

    public SimpleBooleanProperty winProperty(){
        return win;
    }

    public void playerWin(){
        win.setValue(true);
    }
}
