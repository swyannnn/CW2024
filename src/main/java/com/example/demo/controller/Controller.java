package com.example.demo.controller;

import com.example.demo.manager.GameStateManager;
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
    private final GameStateManager gameStateManager;
    private final Group rootGroup;
    private AnimationTimer gameLoop;

    /**
     * Constructor initializes the Controller with the main stage and sets up the game.
     *
     * @param stage The main Stage object used for rendering scenes.
     * @param gameStateManager The GameStateManager instance for managing game states.
     */
    public Controller(Stage stage, GameStateManager gameStateManager) {
        this.stage = stage;
        this.gameStateManager = gameStateManager;
        this.rootGroup = new Group(); // Initialize the root Group for the scene

        // Set up the scene with the root group and attach it to the stage
        Scene scene = new Scene(rootGroup);
        stage.setScene(scene);

        initialize();
        setupGameLoop();
    }

    /**
     * Initializes the game by setting up input handling and transitioning to the main menu.
     */
    private void initialize() {
        // Set up key event handlers for input
        stage.getScene().setOnKeyPressed(event -> gameStateManager.handleInput(event));
        stage.getScene().setOnKeyReleased(event -> gameStateManager.handleInput(event));

        // Initialize GameStateManager to show the main menu
        gameStateManager.goToMainMenu();
    }

    /**
     * Sets up the game loop to continuously update and render the current game state.
     */
    private void setupGameLoop() {
        gameLoop = new AnimationTimer() {
            private long lastUpdate = System.nanoTime();
            private final double nsPerUpdate = 1e9 / 60.0; // 60 updates per second

            @Override
            public void handle(long now) {
                while (now - lastUpdate >= nsPerUpdate) {
                    gameStateManager.update();
                    lastUpdate += nsPerUpdate;
                }
                gameStateManager.render();
            }
        };
    }

    /**
     * Starts the game loop and transitions to the first level.
     */
    public void startGame() {
        if (gameLoop != null) {
            gameLoop.start();
            gameStateManager.goToLevel(1); // Transition to level 1
        }
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
        return rootGroup;
    }

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }
}
