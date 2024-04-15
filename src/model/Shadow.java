package model;

import java.util.ArrayList;

public class Shadow{
    private final ArrayList<Guess> shadowList = new ArrayList<Guess>(6);
    private final ArrayList<Letter> keyboard = new ArrayList<Letter>(26);

    public Shadow() {
        //adds 6 guesses to the array
        for(int i = 0; i < 6; i++){
            shadowList.add(new Guess());
        }
        //enables the first letter
        shadowList.get(0).flipDisable();

        String[] qwerty = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"};
        for(String j : qwerty){
            keyboard.add(new Letter(j));
        }
    }

    public Letter keyAt(int index){
        return keyboard.get(index);
    }

    public Guess guessAt(int index){
        return shadowList.get(index);
    }

    public void reset(){
        for(Guess i : shadowList){
            i.reset();
        }
        for(Letter j : keyboard){
            j.resetColors();
        }
        shadowList.get(0).flipDisable();
    }
}