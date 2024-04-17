package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Letter {
    private final SimpleStringProperty letterString = new SimpleStringProperty(); //bind to input textproperty
    private final SimpleStringProperty color = new SimpleStringProperty();
    private final SimpleBooleanProperty letterDisable = new SimpleBooleanProperty(); //bind to input disableproperty
    

    public Letter(String letterString){
        this.letterString.set(letterString);
        letterDisable.set(true);
        color.set("rgb(0, 4, 23)");
    }

    public void reset(){
        letterString.set("");
        letterDisable.set(true);
        resetColors();
    }

    public void resetColors(){
        color.set("rgb(0, 4, 23)");
    }

    public String getLetterString()   { return letterString.get(); }
    public String getColor()          { return color.get(); }
    public Boolean getLetterDisable() { return letterDisable.get(); }

    public SimpleStringProperty getLetterStringProperty()   { return letterString; }
    public SimpleStringProperty getColorProperty()          { return color; }
    public SimpleBooleanProperty getLetterDisableProperty() { return letterDisable; }

    public void setLetterString(String letter)          { letterString.set(letter); }
    public void setColor(String color)                  { this.color.set(color); }
    public void setLetterDisable(Boolean letterDisable) { this.letterDisable.set(letterDisable); }

    public void flipLetterDisable(){
        setLetterDisable(!getLetterDisable());
    }
}

