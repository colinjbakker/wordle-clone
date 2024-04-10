package interactor;

import model.Model;

public class Interactor {
    private Model model;

    public Interactor(Model model) {
        this.model = model;
    }

    public void submitGuess(){
        System.out.println(model.getGuess());
        if(model.getGuess().equals(model.getSolution())){
            System.out.println("CORRECT");
        }else{
            System.out.println("INCORRECT");
        }
    }
}
