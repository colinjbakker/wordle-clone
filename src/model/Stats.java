package model;

import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Stats {
    private final SimpleIntegerProperty gameCount = new SimpleIntegerProperty();
    private final SimpleIntegerProperty winCount = new SimpleIntegerProperty();
    private final SimpleStringProperty winPercent = new SimpleStringProperty();
    private final SimpleIntegerProperty currentStreak = new SimpleIntegerProperty();
    private final SimpleIntegerProperty maxStreak = new SimpleIntegerProperty();
    private ArrayList<Integer> winCountArray = new ArrayList<Integer>(6);
    private ArrayList<SimpleIntegerProperty> winArray = new ArrayList<SimpleIntegerProperty>(6);

    public Stats(){
        this.gameCount.set(0);
        this.winCount.set(0);
        for(int i = 0; i < 6; i++){
            winCountArray.add(0);
            winArray.add(new SimpleIntegerProperty(0));
        }
    }

    public SimpleIntegerProperty getGameCountProperty(){
        return gameCount;
    }

    public int getGameCount(){
        return gameCount.get();
    }

    public void setGameCount(int gameCount){
        this.gameCount.set(gameCount);
    }

    public SimpleIntegerProperty getWinCountProperty(){
        return winCount;
    }

    public int getWinCount(){
        return winCount.get();
    }

    public void setWinCount(int winCount){
        this.winCount.set(winCount);
    }

    public SimpleStringProperty getWinRateProperty(){
        return winPercent;
    }

    public String getWinRate(){
        return winPercent.get();
    }

    public void setWinRate(String winPercent){
        this.winPercent.set(winPercent);
    }

    public SimpleIntegerProperty getCurrentStreakProperty(){
        return currentStreak;
    }

    public int getCurrentStreak(){
        return currentStreak.get();
    }

    public void setCurrentStreak(int currentStreak){
        this.currentStreak.set(currentStreak);
    }

    public SimpleIntegerProperty getMaxStreakProperty(){
        return maxStreak;
    }

    public int getMaxStreak(){
        return maxStreak.get();
    }

    public void setMaxStreak(int maxStreak){
        this.maxStreak.set(maxStreak);
    }

    public ArrayList<Integer> getWinCountArray(){
        return winCountArray;
    }

    public int winCountArrayAt(int index){
        return winCountArray.get(index);
    }

    public void setWinCountArray(int index, int value){
        winCountArray.set(index, value);
    }

    public ArrayList<SimpleIntegerProperty> getWinArray(){
        return winArray;
    }

    public int winArrayAt(int index){
        return winArray.get(index).get();
    }

    public SimpleIntegerProperty winArrayPropertyAt(int index){
        return winArray.get(index);
    }

    public void updateWinArray(){
        for(int i = 0; i < 6; i++){
            winArray.get(i).set(winCountArrayAt(i) * 200 / winCount.get());
        }
    }

    public String toString(){
        return "Games: " + gameCount.get() + " Wins: " + winCount.get() + " WinArray: " + winCountArray + " Win Percent: " + winPercent.get() + " current streak: " + currentStreak.get() + " max streak: " + maxStreak.get();
    }

}
