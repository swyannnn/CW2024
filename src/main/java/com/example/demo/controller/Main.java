package com.example.demo.controller;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class serves as the entry point for the JavaFX application.
 * It initializes and displays the game.
 */
public class Main extends Application {
    private Controller controller; 

    @Override
    public void start(Stage stage) {
        Controller controller = new Controller(stage);
        controller.initializeGame();
    }

    @Override
    public void stop() {
        if (controller != null) {
            controller.cleanup(); 
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
