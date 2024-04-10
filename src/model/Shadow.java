package model;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

public class Shadow{
    //boolean property bound to disable and visible
    //array of character lists
    //set of 

    //integer property(?) for each character bound to colors for "not in word", "in word, incorrect position", "correct"
    //
    private final ArrayList<Guess> ShadowList = new ArrayList<Guess>(6);


    public Shadow() {
        ShadowList.add(new Guess("", false));
        for(int i = 1; i < 6; i++){
            ShadowList.add(new Guess("", true));
        }
    }

    public String getString(int index){
        return ShadowList.get(index).getText();
    }

    public Guess getGuess(int index){
        return ShadowList.get(index);
    }

    public Boolean getBoolean(int index){
        return ShadowList.get(index).getEnable();
    }

    public ArrayList<Guess> getList(){
        return ShadowList;
    }
}