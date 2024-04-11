package controller;

import interactor.Interactor;
import model.*;
import view.ViewBuilder;

import javafx.util.Builder;
import javafx.scene.layout.Region;

//Instantiates the Model, Interactor and the View
//Connection between the main and the other classes

public class Controller{
	private Builder<Region> viewBuilder;
	private Interactor interactor;

	public Controller() {
		Model model = new Model();
		FileIO fileio = new FileIO();
		interactor = new Interactor(model, fileio);
		viewBuilder = new ViewBuilder(model, interactor::submitGuess, interactor::changeHandler);
	}

	public void prepWordFile(String fileName){
		interactor.prepWordFile(fileName);
	}

	public Region getView() {
		return viewBuilder.build();
	}
}
