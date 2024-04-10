package controller;

import interactor.Interactor;
import model.Model;
import view.ViewBuilder;

import javafx.util.Builder;
import javafx.scene.layout.Region;

public class Controller{
	private Builder<Region> viewBuilder;
	private Interactor interactor;

	public Controller() {
		Model model = new Model();
		interactor = new Interactor(model);
		viewBuilder = new ViewBuilder(model, interactor::submitGuess);
	}

	public Region getView() {
		return viewBuilder.build();
	}
}
