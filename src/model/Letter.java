package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Letter {
    private final SimpleStringProperty letterString = new SimpleStringProperty(); //bind to input textproperty
    private final SimpleBooleanProperty letterDisable = new SimpleBooleanProperty(); //bind to input disableproperty
    private final SimpleBooleanProperty green = new SimpleBooleanProperty(); //bind to bg color      if(lettercorrect) : green, elif(letterinsolution) : yellow, else : color gray
    private final SimpleBooleanProperty yellow = new SimpleBooleanProperty(); //bind to bg color
    private final SimpleBooleanProperty gray = new SimpleBooleanProperty();

    public Letter(String letterString){
        this.letterString.set(letterString);
        letterDisable.set(true);
        green.set(false);
        yellow.set(false);
        gray.set(false);
    }

    public void reset(){
        letterString.set("");
        letterDisable.set(true);
        resetColors();
    }

    public void resetColors(){
        green.set(false);
        yellow.set(false);
        gray.set(false);
    }

    public SimpleStringProperty getLetterStringProperty(){
        //Get the actual letterString property
        return letterString;
    }

    public String getLetterString(){
        //Get the string in the letterString property
        return letterString.get();
    }

    public void setLetterString(String letter){
        //set the strin in the letterString property
        letterString.set(letter);
    }

    public SimpleBooleanProperty getLetterDisable(){
        //get the actual boolean for the letterDisable property
        return letterDisable;
    }

    public Boolean getLetterDisableBoolean(){
        //get the boolean represented be the letterDisable property
        return letterDisable.get();
    }

    public void flipLetterDisable(){
        letterDisable.set(!letterDisable.get());
    }

    public SimpleBooleanProperty getGreen(){
        return green;
    }

    public Boolean getGreenBoolean(){
        return green.get();
    }

    public void makeGreen(){
        green.set(true);
    }

    public SimpleBooleanProperty getYellow(){
        return yellow;
    }

    public Boolean getYelowBoolean(){
        return yellow.get();
    }

    public void makeYellow(){
        yellow.set(true);
    }

    public SimpleBooleanProperty getGray(){
        return gray;
    }

    public Boolean getGrayBoolean(){
        return gray.get();
    }

    public void makeGray(){
        gray.set(true);
    }
}

