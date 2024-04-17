package interactor;

import model.Model;

import model.FileIO;

public class Interactor {
    private Model model;
    private FileIO fileIO;

    public Interactor(Model model, FileIO fileIO) {
        this.model = model;
        this.fileIO = fileIO;
    }

    public void newGame(){
        model.reset();
        model.setSolution(fileIO.getRandomWord());
        System.out.println(model.getSolution()); //hehe
        model.getShadow().guessAt(0).flipDisable();
        model.setStatsVisibility(false);
    }

    public void prepWordFile(String userList, String completeList){
        fileIO.scanFile(userList, completeList);
    }

    public void updateStats(){
        model.setStatsVisibility(true);

        model.getStats().setGameCount(model.getStats().getGameCount() + 1); //increase game count

        if(checkWin(model.getShadow().guessAt(model.getGuessCount()).getGuessString())){ //if win
            model.getStats().setWinCount(model.getStats().getWinCount() + 1); //increase win count
            model.getStats().setWinCountArray(model.getGuessCount(), model.getStats().winCountArrayAt(model.getGuessCount()) + 1); //increment win array
            model.getStats().setCurrentStreak(model.getStats().getCurrentStreak() + 1); //increment current streak
            if(model.getStats().getCurrentStreak() > model.getStats().getMaxStreak()){
                model.getStats().setMaxStreak(model.getStats().getCurrentStreak()); // update max streak
            }
        } else{
            model.getStats().setCurrentStreak(0);
        }
        if(model.getStats().getGameCount() == 0){
            model.getStats().setWinPercent("0%");
        } else{
            model.getStats().setWinPercent((100 * model.getStats().getWinCount()) / (model.getStats().getGameCount()) + "%"); // update win rate
        }
        model.getStats().updateWinArray();
    }

    public void submitGuess(){
        int gc = model.getGuessCount();
        String guess = model.getShadow().guessAt(gc).getGuessString();
        model.getShadow().guessAt(model.getGuessCount()).createLetterArray();
        if(checkValid(guess)){
            if(checkWin(guess) || (gc == 5)){
                setBoardFlags();
                model.swapGameDisable();
                model.getShadow().guessAt(gc).flipDisable();
                updateStats();
                fileIO.updateList("resources/wordlists/userList.txt");
            }
            else{
                setBoardFlags();
                increment();
            }
        }
    }
    
    public void changeHandler(){
        model.getShadow().guessAt(model.getGuessCount()).createLetterArray();
    }

    private Boolean checkValid(String guess){
        if(guess.length() != 5){
            model.setWarning("Not enough letters");
            model.flipFlipFlop();;
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
        int index = -1;
        for(int i = 0; i < qwerty.length; i++){
            if(qwerty[i].equals(letter)){
                index = i;
            }
        }
        return index;
    }

    private void setBoardFlags(){
        String guess = model.getShadow().guessAt(model.getGuessCount()).getGuessString();
        String solution = model.getSolution().toUpperCase();
        int guessLetterCount;
        int solutionLetterCount;
        boolean foundFlag;
        String green = "rgb(1, 112, 66)";
        String yellow = "rgb(181, 135, 9)";
        String gray = "rgb(65, 65, 65)";
        int keyIndex = 0;
        for(int i = 0; i < 5; i++){
            keyIndex = getKeyIndex(guess.substring(i, i+1));
            if(solution.contains(guess.substring(i, i+1))){
                if(guess.substring(i, i+1).equals(solution.substring(i, i+1))){
                    //correct guess
                    model.getShadow().guessAt(model.getGuessCount()).letterAt(i).setColor(green);
                    if(keyIndex >= 0){
                        model.getShadow().keyAt(keyIndex).setColor(green);
                    }
                } else {
                    //give info about number of letter appearances in word
                    guessLetterCount = 0;
                    solutionLetterCount = 0; 
                    foundFlag = false;

                    if(model.getShadow().keyAt(keyIndex).getColor() != "rgb(1, 112, 66)"){
                        if(keyIndex >= 0){
                            model.getShadow().keyAt(keyIndex).setColor(yellow);
                        }
                    }

                    for(int j = 0; j < 5; j++){
                        if(j < i){
                            if(guess.substring(j, j+1).equals(guess.substring(i, i+1))){
                                guessLetterCount++;
                            }
                        }
                        if(solution.substring(j, j+1).equals(guess.substring(i, i+1))){
                            solutionLetterCount++;
                        }
                        if(solution.substring(j, j+1).equals(guess.substring(j, j+1)) && guess.substring(j, j+1).equals(guess.substring(i, i+1))){
                            foundFlag = true;
                        }
                    }
                    if(foundFlag && solutionLetterCount == 1){
                        guessLetterCount = 100; //special case correct letter appears once in solution but is correctly located later in guess
                    }

                    if(guessLetterCount < solutionLetterCount){
                        model.getShadow().guessAt(model.getGuessCount()).letterAt(i).setColor(yellow);
                    } else {
                        model.getShadow().guessAt(model.getGuessCount()).letterAt(i).setColor(gray);
                    }
                }
            } else {
                //incorrect guess
                model.getShadow().guessAt(model.getGuessCount()).letterAt(i).setColor(gray);
                if(keyIndex >= 0){
                    model.getShadow().keyAt(keyIndex).setColor(gray);
                }
            }

        }
    }
    private void increment(){
        model.getShadow().guessAt(model.getGuessCount()).flipDisable();
        model.getShadow().guessAt(model.getGuessCount()+1).flipDisable();
        model.incrementGuessCount();
    }

    private boolean checkWin(String guess){
        return guess.equals(model.getSolution().toUpperCase());
    }

    
}
