package interactor;

import model.Model;

//Will connect to any external database probably not necessary might remove

public class Interactor {
    private Model model;

    public Interactor(Model model) {
        this.model = model;
    }

    public void submitGuess(){
        System.out.println(model.getGuess() + " " + model.getGuessCount());
        if(model.getGuess().equals(model.getSolution())){
            model.playerWin();
            System.out.println("CORRECT");
        }else{
            System.out.println("INCORRECT");
        }
        model.incrementGuessCount();
    }
}
