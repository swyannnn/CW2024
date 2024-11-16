package com.example.demo.controller;

import javafx.application.Application;
import com.example.demo.manager.GameStateManager;
import com.example.demo.util.GameConstant;
import javafx.stage.Stage;

/**
 * Main class serves as the entry point for the JavaFX application.
 * It initializes and displays the game.
 */
public class Main extends Application {
    private static final String TITLE = "Sky Battle";

    @Override
    public void start(Stage stage) {
        stage.setTitle(TITLE);
        stage.setResizable(false);
        stage.setWidth(GameConstant.SCREEN_WIDTH);
        stage.setHeight(GameConstant.SCREEN_HEIGHT);

        // Step 1: Create an instance of GameStateManager with only the Stage
        GameStateManager gameStateManager = GameStateManager.getInstance(stage);

        // Step 2: Create the Controller and set it in GameStateManager
        Controller controller = new Controller(stage, gameStateManager);
        gameStateManager.setController(controller); // This method sets the Controller

        // Step 3: Start the game from the Controller
        controller.startGame();
    }

    @Override
    public void stop() {
        GameStateManager.getInstance(null).cleanup(); 
    }

    public static void main(String[] args) {
        launch();
    }
}
