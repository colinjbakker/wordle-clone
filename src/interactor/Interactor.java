package interactor;

import model.Model;

public class Interactor {
    private Model model;

    public Interactor(Model model) {
        this.model = model;
    }

    public void submitGuess(){
        int gc = model.getGuessCount();
        String guess = model.getListString(gc);
        System.out.println(guess + " " + gc);
        
        if(guess.length() != 5){
            System.out.println("INCORRECT LENGTH");
        }
        else if(checkWin() || (gc == model.getGuessList().size()-1)){
            System.out.println("GAME END");
            model.swapGameDisable();
            model.getListGuess(gc).swapEnable();
        }else{
            System.out.println("GAME CONTINUE");
            increment();
        } 
    }

    private boolean checkWin(){
        return model.getListString(model.getGuessCount()).equals(model.getSolution().toUpperCase());
    }

    private void increment(){
        model.getListGuess(model.getGuessCount()).swapEnable();
        model.getListGuess(model.getGuessCount()+1).swapEnable();
        model.incrementGuessCount();
    }
}
