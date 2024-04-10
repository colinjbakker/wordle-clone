package interactor;

import model.Model;

//Will connect to any external database probably not necessary might remove

public class Interactor {
    private Model model;

    public Interactor(Model model) {
        this.model = model;
    }

    public void submitGuess(){
        System.out.println(model.getListString(model.getGuessCount())+ " " + model.getGuessCount());

        if(checkWin()){
            model.swapGameDisable();
            System.out.println("WIN");
            model.getListGuess(model.getGuessCount()).swapEnable();
        }else if(model.getGuessCount() != model.getGuessList().size()-1){
            System.out.println("INCORRECT");
            incrementEnable();
            model.incrementGuessCount();
        } else{
            System.out.println("LOSE");
            model.swapGameDisable();
            model.getListGuess(model.getGuessCount()).swapEnable();
        }
    }

    public boolean checkWin(){
        return model.getListString(model.getGuessCount()).equals(model.getSolution());
    }

    public void incrementEnable(){
        model.getListGuess(model.getGuessCount()).swapEnable();
        model.getListGuess(model.getGuessCount()+1).swapEnable();
    }
}
