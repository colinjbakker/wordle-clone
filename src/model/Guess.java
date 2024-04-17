package model;


import java.util.ArrayList;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Guess {
    private final ArrayList<Letter> letterArray = new ArrayList<Letter>(5);
    private final SimpleStringProperty guessString = new SimpleStringProperty();
    private final SimpleBooleanProperty disableProperty = new SimpleBooleanProperty();

    public Guess(){
        guessString.set("");
        disableProperty.set(true);
        for(int i = 0; i < 5; i++){
            letterArray.add(new Letter(" "));
        }
    }

    public void reset(){
        for(Letter i : letterArray){
            i.reset();
        }
        guessString.set("");
        disableProperty.set(true);
    }

    public ArrayList<Letter> getLetterArray() { return letterArray; }
    public String getGuessString()            { return guessString.get(); }
    public Boolean getDisable()               { return disableProperty.get(); }

    public SimpleStringProperty getGuessStringProperty() { return guessString; }
    public SimpleBooleanProperty getDisableProperty()    { return disableProperty; }

    public void setGuessString(String guessString)          { this.guessString.set(guessString); }
    public void setDisableProperty(Boolean disableProperty) { this.disableProperty.set(disableProperty); }

    public void flipDisable(){
        setDisableProperty(!getDisable());
    }

    public Letter letterAt(int index){
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
}
