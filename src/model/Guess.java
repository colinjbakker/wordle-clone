package model;


import java.util.ArrayList;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Guess {
    private final ArrayList<Letter> letterArray = new ArrayList<Letter>();
    private final SimpleStringProperty guessString = new SimpleStringProperty("");
    private final SimpleBooleanProperty disableProperty = new SimpleBooleanProperty(true);

    public Guess(){
        //creates five letters in the guess array, default empty string, disabled, incorrect, not in solution
        for(int i = 0; i < 5; i++){
            letterArray.add(new Letter("", true, false, false));
        }
    }

    public SimpleBooleanProperty getDisableProperty(){
        return disableProperty;
    }

    public Boolean getDisable(){
        return disableProperty.get();
    }

    public void flipDisable(){
        disableProperty.set(!disableProperty.get());
    }

    public ArrayList<Letter> getLetterArray(){
        //returns enture array
        return letterArray;
    }

    public Letter letterAt(int index){
        //returns the letter at index
        return letterArray.get(index);
    }

    public void createLetterArray(){
        //returns a string of all the letters concatenated
        for(int i = 0; i < guessString.get().length(); i++){
            letterArray.get(i).setLetterString(guessString.get().substring(i, i+1));
        }
        for(int j = guessString.get().length(); j < 5; j++){
            letterArray.get(j).setLetterString(" ");
        }
    }

    public SimpleStringProperty getGuessStringProperty(){
        return guessString;
    }

    public String getGuessString(){
        return guessString.get();
    }

    public void setGuessString(String guessString){
        this.guessString.set(guessString);
    }
}
