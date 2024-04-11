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
		primaryStage.setScene(new Scene(new Controller().getView(), 500, 600));
		primaryStage.show();
	}
}

