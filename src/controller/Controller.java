package controller;

import view.View;
import model.*;

import java.util.ArrayList;

import javafx.scene.layout.Region;


public class Controller{
	private View view;
	private Model model;
    private FileIO fileIO;

	public Controller() {
		this.model = new Model();
        this.fileIO = new FileIO();
		view = new View(model, this::submitGuess, this::changeHandler, this::newGame, this::loadGame, this::showHideStats);
	}

    public void prepWordFile(String userList, String completeList){ fileIO.scanFile(userList, completeList); }
	public Region getView() { return view.build(); }

	public void newGame(){
        model.reset();
        model.setSolution(fileIO.getRandomWord());
        System.out.println(model.getSolution()); //hehe
        model.getShadow().guessAt(0).flipDisable();
        model.setStatsVisibility(false);
        fileIO.writeWordToFile(model.getSolution(), "resources/userdata/gameLog.txt", false);
    }

    public void loadGame(){
        fileIO.loadSave("resources/userdata/gameLog.txt");
        ArrayList<String> log = fileIO.getGameLogList();
        if(log.isEmpty() || log.get(0) == ""){
            model.setWarning("Nothing to load");
            model.flipFlipFlop();
        } else{
            model.reset();
            model.getShadow().guessAt(0).flipDisable();
            model.setSolution(log.get(0)); //solution is first word in log
            for(int i = 0; i < log.size()-1; i++){
                model.getShadow().guessAt(i).setGuessString(log.get(i+1)); //subsequent words are guesses
                model.getShadow().guessAt(i).createLetterArray();
                setBoardFlags();
                model.getShadow().guessAt(i).flipDisable();
                model.getShadow().guessAt(i+1).flipDisable();
                model.setGuessCount(i+1);
            }
            
            System.out.println(model.getSolution()); //hehe
            model.setStatsVisibility(false);
        }
    }

    public void loadStats(){
        fileIO.loadStats("resources/userdata/playerStats.txt");
        ArrayList<Integer> stats = fileIO.getStatsList();
        model.getStats().setWinCount(stats.get(0));
        model.getStats().setGameCount(stats.get(1));
        model.getStats().setMaxStreak(stats.get(2));
        model.getStats().setCurrentStreak(stats.get(3));
        for(int i = 0; i < 6; i++){
            model.getStats().setWinCountArray(i, stats.get(i+4));
        }
        model.getStats().setWinPercent(model.getStats().getGameCount() == 0 ? "0%" : (100 * model.getStats().getWinCount()) / (model.getStats().getGameCount()) + "%"); 
        model.getStats().updateWinArray();
    }

    public void showHideStats(){
        model.setStatsVisibility(!model.getStatsVisibility());
        if(!checkWin(model.getShadow().guessAt(model.getGuessCount()).getGuessString())){
            model.flipGameDisable();
        }
    }

    private boolean checkWin(String guess){ return guess.equals(model.getSolution().toUpperCase()); }

    public void updateStats(boolean win){
        Stats currStats = model.getStats();

        currStats.setGameCount(currStats.getGameCount() + 1); //increase game count

        if(win){ 
            currStats.setWinCount(currStats.getWinCount() + 1); //increase win count
            currStats.setWinCountArray(model.getGuessCount(), currStats.winCountArrayAt(model.getGuessCount()) + 1); //increment win array
            currStats.setCurrentStreak(currStats.getCurrentStreak() + 1); //increment current streak

            if(currStats.getCurrentStreak() > currStats.getMaxStreak()){
                currStats.setMaxStreak(currStats.getCurrentStreak()); // update max streak
            }
        } else{
            currStats.setCurrentStreak(0);
        }
        
        currStats.setWinPercent(currStats.getGameCount() == 0 ? "0%" : (100 * currStats.getWinCount()) / (currStats.getGameCount()) + "%"); //update win percent

        currStats.updateWinArray();
        model.setStatsVisibility(true);
    }

    public void submitGuess(){
        int gc = model.getGuessCount();
        String guess = model.getShadow().guessAt(gc).getGuessString();
        model.getShadow().guessAt(model.getGuessCount()).createLetterArray();
        if(checkValid(guess)){
            if(checkWin(guess) || (gc == 5)){
                setBoardFlags();
                model.flipGameDisable();
                model.getShadow().guessAt(gc).flipDisable();
                updateStats(checkWin(guess));
                fileIO.updateList("resources/wordlists/userList.txt");
                fileIO.writeWordToFile("","resources/userdata/gameLog.txt", false);
                fileIO.writeStats("resources/userdata/playerStats.txt", model.getStats());
            }
            else{
                setBoardFlags();
                model.getShadow().guessAt(model.getGuessCount()).flipDisable();
                model.getShadow().guessAt(model.getGuessCount()+1).flipDisable();
                model.setGuessCount(gc + 1);
                fileIO.writeWordToFile(guess, "resources/userdata/gameLog.txt", true);
            }
        }
    }
    
    public void changeHandler() { model.getShadow().guessAt(model.getGuessCount()).createLetterArray(); }

    private Boolean checkValid(String guess){
        if(guess.length() != 5){
            model.setWarning("Not enough letters");
            model.flipFlipFlop();
            return false;
        }
        else if(!fileIO.inList(guess)){
            model.setWarning("Not in word list");
            model.flipFlipFlop();
            return false;
        }

        model.setWarning("");
        return true;
    }

    private int getKeyIndex(String letter){
        String[] qwerty = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"};
        for(int i = 0; i < qwerty.length; i++) if(qwerty[i].equals(letter)) return i;
        return -1;
    }

    private void setBoardFlags(){
        String guess = model.getShadow().guessAt(model.getGuessCount()).getGuessString();
        String solution = model.getSolution().toUpperCase();
        int guessLetterCount;
        int solutionLetterCount;
        Letter currLetter;
        Letter currKey;
        String green = "rgb(1, 112, 66)";
        String yellow = "rgb(181, 135, 9)";
        String gray = "rgb(65, 65, 65)";
        int keyIndex = 0;

        for(int i = 0; i < 5; i++){
            currLetter = model.getShadow().guessAt(model.getGuessCount()).letterAt(i);
            keyIndex = getKeyIndex(currLetter.getLetterString());
            currKey = model.getShadow().keyAt(keyIndex);

            if(keyIndex < 0) break; //should never happen but just in case

            if(solution.contains(currLetter.getLetterString())){
                if(currLetter.getLetterString().equals(solution.substring(i, i+1))){
                    //correct guess
                    currLetter.setColor(green);
                    currKey.setColor(green);
                } else {
                    //give info about number of letter appearances in word
                    guessLetterCount = 0;
                    solutionLetterCount = 0; 

                    if(currKey.getColor() != green) currKey.setColor(yellow);

                    for(int j = 0; j < 5; j++){
                        if((j < i) && guess.substring(j, j+1).equals(currLetter.getLetterString())) { guessLetterCount++; }
                        if(solution.substring(j, j+1).equals(currLetter.getLetterString())) { solutionLetterCount++; }
                        if(solution.substring(j, j+1).equals(guess.substring(j, j+1)) && guess.substring(j, j+1).equals(currLetter.getLetterString()) && solutionLetterCount == 1) { guessLetterCount = 100; } //special case correct letter appears once in solution but is correctly located later in guess
                    }

                    currLetter.setColor(guessLetterCount < solutionLetterCount ? yellow : gray);
                }
            } else {
                //incorrect guess
                currLetter.setColor(gray);
                currKey.setColor(gray);
            }
        }
    }
}
