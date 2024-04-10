package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Model {
    private final StringProperty guess = new SimpleStringProperty("");
    private final StringProperty solution = new SimpleStringProperty("hello");

    public String getGuess(){
        return guess.get();
    }

    public StringProperty guessProperty() {
        return guess;
    }  

    public void setGuess(String guess){
        this.guess.set(guess);
    }

    public String getSolution() {
        return solution.get();
    }

    public StringProperty solutionProperty() {
        return solution;
    }

    public void setSolution(String solution){
        this.solution.set(solution);
    }
}
