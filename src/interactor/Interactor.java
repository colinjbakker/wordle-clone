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

    public void prepWordFile(String fileName){
        fileIO.scanFile(fileName);
        model.setSolution(fileIO.getRandomWord());
    }

    public void submitGuess(){
        final long startTime = System.currentTimeMillis();
        int gc = model.getGuessCount();
        String guess = model.guessAt(gc).getGuessString();
        model.guessAt(model.getGuessCount()).createLetterArray();
        if(checkValid(guess)){
            if(checkWin(guess) || (gc == 5)){
                setFlags();
                model.swapGameDisable();
                model.guessAt(gc).flipDisable();
            } else{
                setFlags();
                increment();
            }
        }
        final long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime) / 1000 + "s");
    }
    
    public void changeHandler(){
        model.guessAt(model.getGuessCount()).createLetterArray();
    }

    

    private Boolean checkValid(String guess){
        if(guess.length() != 5){
            System.out.println("test1");
            model.setWarning(1);
            System.out.println(model.getWarningString());
            return false;
        }
        else if(!fileIO.inList(guess)){
            model.setWarning(0);
            return false;
        }
        model.setWarning(2);
        
        return true;
    }

    private void setFlags(){
        String guess = model.guessAt(model.getGuessCount()).getGuessString();
        String solution = model.getSolution().toUpperCase();

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if(guess.substring(i, i+1).equals(solution.substring(j, j+1))){
                    model.guessAt(model.getGuessCount()).letterAt(i).flipLetterInSolution();
                    if(i == j){
                        model.guessAt(model.getGuessCount()).letterAt(i).flipLetterCorrect();
                    }
                }
                model.guessAt(model.getGuessCount()).letterAt(i).flipLetterUnsubmitted();
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
