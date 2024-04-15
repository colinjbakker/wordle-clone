package app;

import controller.Controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class App extends Application {
	@Override
    public void start(Stage primaryStage) {
		Controller controller = new Controller();
		controller.prepWordFile("resources/valid-wordle-words.txt");
		Scene scene = new Scene(controller.getView(), 500, 816);
		primaryStage.setScene(scene);
		primaryStage.setMinHeight(816);
		primaryStage.setMinWidth(500);
		primaryStage.show();
	}
}

