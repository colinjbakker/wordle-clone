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
        model.setVisibility(1);
    }

    public void prepWordFile(String fileName){
        fileIO.scanFile(fileName);
    }

    public void submitGuess(){
        int gc = model.getGuessCount();
        String guess = model.guessAt(gc).getGuessString();
        model.guessAt(model.getGuessCount()).createLetterArray();
        if(checkValid(guess)){
            if(checkWin(guess) || (gc == 5)){
                setBoardFlags();
                model.swapGameDisable();
                model.guessAt(gc).flipDisable();
            } else{
                setBoardFlags();
                increment();
            }
        }
    }
    
    public void changeHandler(){
        model.guessAt(model.getGuessCount()).createLetterArray();
    }

    private Boolean checkValid(String guess){
        if(guess.length() != 5){
            model.setWarning(1);
            model.flipFlipFlop();;
            return false;
        }
        else if(!fileIO.inList(guess)){
            model.setWarning(0);
            model.flipFlipFlop();
            return false;
        }
        model.setWarning(2);
        
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
        String guess = model.guessAt(model.getGuessCount()).getGuessString();
        String solution = model.getSolution().toUpperCase();
        int guessLetterCount;
        int solutionLetterCount;
        boolean foundFlag;
        int keyIndex = 0;
        for(int i = 0; i < 5; i++){
            keyIndex = getKeyIndex(guess.substring(i, i+1));
            if(solution.contains(guess.substring(i, i+1))){
                if(guess.substring(i, i+1).equals(solution.substring(i, i+1))){
                    //correct guess
                    model.guessAt(model.getGuessCount()).letterAt(i).makeGreen();
                    if(keyIndex >= 0){
                        model.getShadow().keyAt(keyIndex).makeGreen(); 
                    }
                } else {
                    //give info about number of letter appearances in word
                    guessLetterCount = 0;
                    solutionLetterCount = 0; 
                    foundFlag = false;

                    if(!model.getShadow().keyAt(keyIndex).getGreen().get()){
                        if(keyIndex >= 0){
                            model.getShadow().keyAt(keyIndex).makeYellow(); 
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
                        model.guessAt(model.getGuessCount()).letterAt(i).makeYellow();
                    } else {
                        model.guessAt(model.getGuessCount()).letterAt(i).makeGray();
                    }
                }
            } else {
                //incorrect guess
                model.guessAt(model.getGuessCount()).letterAt(i).makeGray();
                if(keyIndex >= 0){
                    model.getShadow().keyAt(keyIndex).makeGray(); 
                }
            }
        }
    }
    private void increment(){
        model.guessAt(model.getGuessCount()).flipDisable();
        model.guessAt(model.getGuessCount()+1).flipDisable();
        model.incrementGuessCount();
    }

    private boolean checkWin(String guess){
        return guess.equals(model.getSolution().toUpperCase());
    }

    
}
