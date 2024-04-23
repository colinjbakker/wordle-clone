package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

//Data representation of the GUI, no logic

public class Model {
    //Variables are all final, but because they are properties, can be changed with the set function
    //It's better that they are final so that I can make sure they are not accidentially changed, causing something to be unlinked
    private final SimpleStringProperty solution = new SimpleStringProperty();
    private final SimpleStringProperty warning = new SimpleStringProperty();
    private final SimpleIntegerProperty guessCount = new SimpleIntegerProperty();
    private final SimpleIntegerProperty inputCount = new SimpleIntegerProperty();
    private final SimpleBooleanProperty gameDisable = new SimpleBooleanProperty();
    private final SimpleBooleanProperty flipFlop = new SimpleBooleanProperty();
    private final SimpleBooleanProperty statsVisibility = new SimpleBooleanProperty();
    private final SimpleBooleanProperty hardMode = new SimpleBooleanProperty();
    private final Shadow guessList = new Shadow();
    private final Stats stats = new Stats();

    public Model(){
        solution.set("");
        warning.set("");
        guessCount.set(0);
        inputCount.set(0);
        gameDisable.set(true);
        flipFlop.set(false);
        statsVisibility.set(true);
        hardMode.set(false);
    }

    public void reset(){
        //warning and flipflip not reset, causes empty warning to appear
        solution.set("");
        guessCount.set(0);
        gameDisable.set(false);
        inputCount.set(0);
        guessList.reset();
        hardMode.set(false);
    }

    //Data Getters
    public String getSolution()         { return solution.get(); }
    public String getWarning()          { return warning.get(); }
    public int getGuessCount()          { return guessCount.get(); }
    public int getInputCount()          { return inputCount.get(); }
    public Boolean getGameDisable()     { return gameDisable.get(); }
    public Boolean getFlipFlop()        { return flipFlop.get(); }
    public Boolean getStatsVisibility() { return statsVisibility.get(); }
    public Boolean getHardMode()        { return hardMode.get(); }
    public Shadow getShadow()           { return guessList; }
    public Stats getStats()             { return stats; }

    //Property Getters
    public SimpleStringProperty getSolutionProperty()         { return solution; }
    public SimpleStringProperty getWarningProperty()          { return warning; }
    public SimpleIntegerProperty getGuessCountProperty()      { return guessCount; }
    public SimpleIntegerProperty getInputCountProperty()      { return inputCount; }
    public SimpleBooleanProperty getGameDisableProperty()     { return gameDisable; }
    public SimpleBooleanProperty getFlipFlopProperty()        { return flipFlop; }
    public SimpleBooleanProperty getStatsVisibilityProperty() { return statsVisibility; }
    public SimpleBooleanProperty getHardModeProperty()        { return hardMode; }

    //Setters
    public void setSolution(String solution)                { this.solution.set(solution); }
    public void setWarning(String warning)                  { this.warning.set(warning); }
    public void setGuessCount(int guessCount)               { this.guessCount.set(guessCount); }
    public void setInputCount(int inputCount)               { this.inputCount.set(inputCount); }
    public void setGameDisable(Boolean gameDisable)         { this.gameDisable.set(gameDisable); }
    public void setFlipFlip(Boolean flipFlop)               { this.flipFlop.set(flipFlop); }
    public void setStatsVisibility(Boolean statsVisibility) { this.statsVisibility.set(statsVisibility); }
    public void setHardMode(Boolean hardMode)               { this.hardMode.set(hardMode); }

    public void flipFlipFlop()    { setFlipFlip(!getFlipFlop()); }
    public void flipGameDisable() { setGameDisable(!getGameDisable()); }
    public void flipHardMode()    { setHardMode(!getHardMode()); }
}
