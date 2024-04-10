package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Guess {
    private final SimpleStringProperty text = new SimpleStringProperty("");
    private final SimpleBooleanProperty enable = new SimpleBooleanProperty(true);

    public Guess(String string, Boolean bool){
        text.set(string);
        enable.set(bool);
    }
    public String getText(){
        return text.get();
    }

    public SimpleStringProperty getTextProperty(){
        return text;
    }

    public void setText(String text){
        this.text.set(text);
    }

    public Boolean getEnable(){
        return enable.get();
    }

    public SimpleBooleanProperty getEnableProperty(){
        return enable;
    }

    public void swapEnable(){
        enable.set(!enable.get());
    }
}
