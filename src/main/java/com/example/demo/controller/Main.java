package com.example.demo.controller;

import javafx.application.Application;
import com.example.demo.util.GameConstant;
import javafx.stage.Stage;

/**
 * Main class serves as the entry point for the JavaFX application.
 * It initializes and displays the game.
 */
public class Main extends Application {
    private static final String TITLE = "Sky Battle";
    private Controller controller; 

    @Override
    public void start(Stage stage) {
        stage.setTitle(TITLE);
        stage.setResizable(false);
        stage.setWidth(GameConstant.SCREEN_WIDTH);
        stage.setHeight(GameConstant.SCREEN_HEIGHT);

        // GameStateManager gameStateManager = GameStateManager.getInstance(stage);
        // Controller controller = new Controller(stage, gameStateManager);
        Controller controller = new Controller(stage);
        controller.initialize();
    }

    @Override
    public void stop() {
        if (controller != null) {
            controller.cleanup(); // Call cleanup method to avoid passing null
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
