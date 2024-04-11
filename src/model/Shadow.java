package model;

import java.util.ArrayList;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Shadow{
    private final ArrayList<Guess> ShadowList = new ArrayList<Guess>(6);


    public Shadow() {
        //adds 6 guesses to the array
        for(int i = 0; i < 6; i++){
            ShadowList.add(new Guess());
        }
        //enables the first letter
        ShadowList.get(0).flipDisable();
    }

    public Guess guessAt(int index){
        return ShadowList.get(index);
    }
}