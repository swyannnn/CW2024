package com.example.demo.controller;

import com.example.demo.manager.GameStateManager;
import com.example.demo.util.GameConstant;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller class manages the main game flow, including state transitions,
 * game loop, and interactions with the GameStateManager.
 */
public class Controller {
    private final Stage stage;
    private final Group rootGroup;
    private AnimationTimer gameLoop;
    private GameStateManager gameStateManager;

    /**
     * Constructor initializes the Controller with the main stage and sets up the game.
     *
     * @param stage The main Stage object used for rendering scenes.
     * @param gameStateManager The GameStateManager instance for managing game states.
     */
    public Controller(Stage stage) {
        this.stage = stage;
        this.rootGroup = new Group(); 
        Scene scene = new Scene(rootGroup, GameConstant.GameSettings.SCREEN_WIDTH, GameConstant.GameSettings.SCREEN_HEIGHT); 
        stage.setScene(scene);
        stage.setTitle(GameConstant.GameSettings.TITLE);
        stage.show();
    }

    /**
     * Initializes the game by setting up input handling and transitioning to the main menu.
     */
    public void initializeGame() {
        gameStateManager = GameStateManager.getInstance(stage, this);
        
        gameStateManager.goToMainMenu(); // Transition to the main menu

        // Set up key event handlers for input
        stage.getScene().setOnKeyPressed(event -> gameStateManager.handleInput(event));
        stage.getScene().setOnKeyReleased(event -> gameStateManager.handleInput(event));
        stage.getScene().getRoot().requestFocus();
    }

    /**
     * Stops the game loop if it is running.
     */
    public void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }

    /**
     * Returns the root Group of the scene, used for adding visual elements.
     *
     * @return The root Group object.
     */
    public Group getRootGroup() {
        return this.rootGroup;
    }

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    /**
     * Cleanup method to stop the game loop and cleanup the GameStateManager.
     */
    public void cleanup() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
        if (gameStateManager != null) {
            gameStateManager.cleanup();
        }
    }
}
