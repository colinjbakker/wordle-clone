package model;

import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Stats {
    private final SimpleIntegerProperty gameCount = new SimpleIntegerProperty();
    private final SimpleIntegerProperty winCount = new SimpleIntegerProperty();
    private final SimpleIntegerProperty currentStreak = new SimpleIntegerProperty();
    private final SimpleIntegerProperty maxStreak = new SimpleIntegerProperty();
    private final SimpleStringProperty winPercent = new SimpleStringProperty();
    private ArrayList<SimpleIntegerProperty> winCountArray = new ArrayList<SimpleIntegerProperty>(6);
    private ArrayList<SimpleIntegerProperty> winArray = new ArrayList<SimpleIntegerProperty>(6);

    public Stats(){
        this.gameCount.set(0);
        this.winCount.set(0);
        this.winPercent.set("0%");
        this.currentStreak.set(0);
        this.maxStreak.set(0);
        for(int i = 0; i < 6; i++){
            winCountArray.add(new SimpleIntegerProperty(0));
            winArray.add(new SimpleIntegerProperty(0));
        }
    }

    //Getters
    public int getGameCount()                                  { return gameCount.get(); }
    public int getWinCount()                                   { return winCount.get(); }
    public int getCurrentStreak()                              { return currentStreak.get(); }
    public int getMaxStreak()                                  { return maxStreak.get(); }
    public String getWinPercent()                              { return winPercent.get(); }
    public ArrayList<SimpleIntegerProperty> getWinCountArray() { return winCountArray; }
    public ArrayList<SimpleIntegerProperty> getWinArray()      { return winArray; }

    //Property Getters
    public SimpleIntegerProperty getGameCountProperty()     { return gameCount; }
    public SimpleIntegerProperty getWinCountProperty()      { return winCount; }
    public SimpleIntegerProperty getCurrentStreakProperty() { return currentStreak; }
    public SimpleIntegerProperty getMaxStreakProperty()     { return maxStreak; }
    public SimpleStringProperty getWinRateProperty()        { return winPercent; }

    //Setters
    public void setGameCount(int gameCount)         { this.gameCount.set(gameCount); }
    public void setWinCount(int winCount)           { this.winCount.set(winCount); }
    public void setCurrentStreak(int currentStreak) { this.currentStreak.set(currentStreak); }
    public void setMaxStreak(int maxStreak)         { this.maxStreak.set(maxStreak); }
    public void setWinPercent(String winPercent)    { this.winPercent.set(winPercent); }

    public int winCountArrayAt(int index) { return winCountArray.get(index).get(); }  
    public int winArrayAt(int index) { return winArray.get(index).get(); }

    public SimpleIntegerProperty winCountArrayPropertyAt(int index) { return winCountArray.get(index); }
    public SimpleIntegerProperty winArrayPropertyAt(int index) { return winArray.get(index); }

    public void setWinCountArray(int index, int value) { winCountArray.get(index).set(value); }
    public void setWinArray(int index, int value) { winArray.get(index).set(value); }

    public void updateWinArray() { for(int i = 0; i < 6; i++) setWinArray(i, winCount.get() == 0 ? 20 : winCountArrayAt(i) * 230 / winCount.get() + 20); }
}
