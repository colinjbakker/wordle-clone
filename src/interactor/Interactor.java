package interactor;

import model.Model;

public class Interactor {
    private Model model;

    public Interactor(Model model) {
        this.model = model;
    }

    public void submitGuess(){
        int gc = model.getGuessCount();
        String guess = model.guessAt(gc).getGuessString();
        System.out.println(guess + " " + gc);
        
        if(guess.length() != 5){
            System.out.println("INCORRECT LENGTH");
        }
        else if(checkWin(guess) || (gc == 5)){
            System.out.println("GAME END");
            setFlags();
            model.swapGameDisable();
            model.guessAt(gc).flipDisable();
        }else{
            System.out.println("GAME CONTINUE");
            setFlags();
            increment();
        }
        
    }
    private void setFlags(){
        String guess = model.guessAt(model.getGuessCount()).getGuessString();
        String solution = model.getSolution().toUpperCase();
        System.out.println(guess + " " + solution);
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if(guess.substring(i, i+1).equals(solution.substring(j, j+1))){
                    model.guessAt(model.getGuessCount()).letterAt(i).flipLetterInSolution();
                    if(i == j){
                        model.guessAt(model.getGuessCount()).letterAt(i).flipLetterCorrect();
                    }
                }
            }
        }
    }
    private void increment(){
        model.guessAt(model.getGuessCount()).flipDisable();
        model.guessAt(model.getGuessCount()+1).flipDisable();
        model.incrementGuessCount();
        System.out.println(model.getGuessCount());
    }

    private boolean checkWin(String guess){
        return guess.equals(model.getSolution().toUpperCase());
    }

    public void changeHandler(){
        model.guessAt(model.getGuessCount()).createLetterArray();
        //on textfield change
        //field now has zero letters
        //  field at beginning of guess
        //  field not at beginning
        //field now has one letter
        //  
        //field now has two letters
        //  field at end of guess
        //  field not at end of guess
        // int digit = model.getInputCount();
        // int gc = model.getGuessCount();
        // System.out.println(digit + " " + gc);

        // if(model.guessAt(gc).letterAt(digit).getLetterString().length() == 0){
        //     if(digit > 0){
        //         model.guessAt(gc).letterAt(digit).flipLetterDisable();
        //         model.guessAt(gc).letterAt(digit - 1).flipLetterDisable();
        //         model.guessAt(gc).letterAt(digit - 1).setLetterString("");
        //         model.setInputCount(model.getInputCount() - 1);
        //     }
        // } else if (model.guessAt(gc).letterAt(digit).getLetterString().length() == 1){
        //     if(digit < 4){
        //         model.guessAt(gc).letterAt(digit + 1).flipLetterDisable();
        //         model.guessAt(gc).letterAt(digit).flipLetterDisable();
                
        //         //model.guessAt(gc).letterAt(digit + 1).setLetterString(model.guessAt(gc).letterAt(digit).getLetterString().substring(1,2));
        //         model.setInputCount(model.getInputCount() + 1);
        //     }
        //     //model.guessAt(gc).letterAt(digit).setLetterString(model.guessAt(gc).letterAt(digit).getLetterString().substring(0,1));
        // }
        // System.out.println(model.guessAt(gc).letterAt(digit).getLetterString());

    }
}
