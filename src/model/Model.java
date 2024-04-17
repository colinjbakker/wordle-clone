package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

//Data representation of the GUI, no logic

public class Model {
    private final SimpleStringProperty solution = new SimpleStringProperty("");
    private final SimpleStringProperty warning = new SimpleStringProperty("");

    private final SimpleIntegerProperty guessCount = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty inputCount = new SimpleIntegerProperty(0);

    private final SimpleBooleanProperty gameDisable = new SimpleBooleanProperty(true);
    private final SimpleBooleanProperty flipFlop = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty statsVisibility = new SimpleBooleanProperty(true);

    private final Shadow guessList = new Shadow();
    private final Stats stats = new Stats();

    public void reset(){
        solution.set("");
        guessCount.set(0);
        gameDisable.set(false);
        inputCount.set(0);
        flipFlop.set(false);
        guessList.reset();
    }

    //Data Getters
    public String getSolution()         { return solution.get(); }
    public String getWarning()          { return warning.get(); }
    public int getGuessCount()          { return guessCount.get(); }
    public int getInputCount()          { return inputCount.get(); }
    public Boolean getGameDisable()     { return gameDisable.get(); }
    public Boolean getFlipFlop()        { return flipFlop.get(); }
    public Boolean getStatsVisibility() { return statsVisibility.get(); }
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

    //Setters
    public void setSolution(String solution)                { this.solution.set(solution); }
    public void setWarning(String warning)                  { this.warning.set(warning); }
    public void setGuessCount(int guessCount)               { this.guessCount.set(guessCount); }
    public void setInputCount(int inputCount)               { this.inputCount.set(inputCount); }
    public void setGameDisable(Boolean gameDisable)         { this.gameDisable.set(gameDisable); }
    public void setFlipFlip(Boolean flipFlop)               { this.flipFlop.set(flipFlop); }
    public void setStatsVisibility(Boolean statsVisibility) { this.statsVisibility.set(statsVisibility); }

    public void flipFlipFlop(){
        setFlipFlip(!getFlipFlop());
    }

    public void swapGameDisable(){
        setGameDisable(!getGameDisable());
    }

    public void incrementGuessCount() {
        guessCount.set(getGuessCount() + 1);
    }    
}
