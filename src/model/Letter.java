package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Letter {
    private final SimpleStringProperty letterString = new SimpleStringProperty(" "); //bind to input textproperty
    private final SimpleBooleanProperty letterDisable = new SimpleBooleanProperty(false); //bind to input disableproperty
    private final SimpleBooleanProperty letterCorrect = new SimpleBooleanProperty(false); //bind to bg color      if(lettercorrect) : green, elif(letterinsolution) : yellow, else : color gray
    private final SimpleBooleanProperty letterInSolution = new SimpleBooleanProperty(false); //bind to bg color

    public Letter(String letter, Boolean disable, Boolean correct, Boolean inSolution){
        letterString.set(letter);
        letterDisable.set(disable);
        letterCorrect.set(correct);
        letterInSolution.set(inSolution);
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

    public SimpleBooleanProperty getLetterCorrect(){
        return letterCorrect;
    }

    public Boolean getLetterCorrectBoolean(){
        return letterCorrect.get();
    }

    public void flipLetterCorrect(){
        letterCorrect.set(true);
    }

    public SimpleBooleanProperty getLetterInSolution(){
        return letterInSolution;
    }

    public Boolean getLetterInSolutionBoolean(){
        return letterInSolution.get();
    }

    public void flipLetterInSolution(){
        letterInSolution.set(true);
    }
}

