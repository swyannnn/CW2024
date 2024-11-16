package com.example.demo.controller;

import javafx.application.Application;
import com.example.demo.manager.GameStateManager;
import com.example.demo.util.ScreenConstant;
import javafx.stage.Stage;

/**
 * Main class serves as the entry point for the JavaFX application.
 * It initializes and displays the HomeMenu.
 */
public class Main extends Application {
    private static final String TITLE = "Sky Battle";

    @Override
    public void start(Stage stage) {
        // Set stage properties
        stage.setTitle(TITLE);
        stage.setResizable(false);
        stage.setWidth(ScreenConstant.SCREEN_WIDTH);
        stage.setHeight(ScreenConstant.SCREEN_HEIGHT);

        // Initialize GameStateManager and Controller
        GameStateManager gameStateManager = GameStateManager.getInstance(stage, null); // Temporarily pass null for the controller
        Controller controller = new Controller(stage, gameStateManager);

        // Now that the controller is created, set it in the GameStateManager
        gameStateManager.setController(controller);

        // Start the game by going to the main menu or another initial state
        gameStateManager.goToMainMenu();
    }

    @Override
    public void stop() {
        // Perform any necessary cleanup here
    }

    public static void main(String[] args) {
        launch();
    }
}
