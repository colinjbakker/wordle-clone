package app;

import controller.Controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class App extends Application {
	@Override
    public void start(Stage primaryStage) {
		Controller controller = new Controller();
		controller.prepWordFile("resources/wordlists/userList.txt", "resources/wordlists/completeList.txt");
		Scene scene = new Scene(controller.getView());
		primaryStage.setScene(scene);
		primaryStage.setMinHeight(826);
		primaryStage.setMinWidth(500);
		primaryStage.show();
	}
}

