package com.example.demo;

import com.example.demo.controller.Controller;

import javafx.application.Application;
import javafx.stage.Stage;


/**
 * The Main class extends the JavaFX Application class and serves as the entry point for the application.
 * It initializes the controller, sets up the game, and handles cleanup operations when the application is stopped.
 * 
 * <p>This class contains the following methods:</p>
 * <ul>
 *   <li>{@link #start(Stage)} - Starts the JavaFX application by initializing the controller and setting up the game.</li>
 *   <li>{@link #stop()} - Stops the application and performs cleanup operations.</li>
 *   <li>{@link #main(String[])} - The main entry point for the application.</li>
 * </ul>
 */
public class Main extends Application {
    private Controller controller; 

    /**
     * Starts the JavaFX application by initializing the controller and setting up the game.
     *
     * @param stage the primary stage for this application, onto which the application scene can be set
     */
    @Override
    public void start(Stage stage) {
        controller = new Controller(stage);
        controller.initializeGame();
    }

    /**
     * Stops the application and performs cleanup operations.
     * This method is called when the application is stopped.
     * If the controller is not null, it will invoke the cleanup method on the controller.
     */
    @Override
    public void stop() {
        if (controller != null) {
            controller.cleanup(); 
        }
    }

    
    /**
     * The main entry point for the application.
     * This method is called when the application is started.
     *
     * @param args The command line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch();
    }
}
