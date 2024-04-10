package app;

import controller.Controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class App extends Application { // could be different
    //public static void main(String[] args) { launch(args); }
    @Override
    public void start(Stage primaryStage) {
		primaryStage.setScene(new Scene(new Controller().getView()));
		primaryStage.show();
	}
}

