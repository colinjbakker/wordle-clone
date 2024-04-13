package app;

import controller.Controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

//Calls all of the JavaFX functions to start the GUI
//Includes only the controller
 
public class App extends Application {
	@Override
    public void start(Stage primaryStage) {
		

		Controller controller = new Controller();
		controller.prepWordFile("resources/sgb-words.txt");
		Scene scene = new Scene(controller.getView());
		primaryStage.setScene(scene);
		primaryStage.setMinHeight(680);
		primaryStage.setMinWidth(500);
		primaryStage.show();
	}
}

