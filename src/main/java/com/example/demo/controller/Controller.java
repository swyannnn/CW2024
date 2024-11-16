package com.example.demo.controller;

import com.example.demo.GameControl;
import com.example.demo.manager.AudioManager;
import com.example.demo.manager.GameStateManager;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

/**
 * Controller class manages the main game flow, including state transitions,
 * game loop, and interactions with the GameStateManager.
 */
public class Controller implements GameControl {
    private final Stage stage;
    private final AudioManager audioManager;
    private final GameStateManager gameStateManager;
    private AnimationTimer gameLoop;

    /**
     * Constructor initializes the Controller with the main stage and sets up the game.
     *
     * @param stage The main Stage object used for rendering scenes.
     */
    public Controller(Stage stage, GameStateManager gameStateManager) {
        this.stage = stage;
        this.audioManager = new AudioManager();
        this.gameStateManager = gameStateManager;
        initialize();
        startGameLoop();
    }

    /**
     * Initializes the game by transitioning to the main menu.
     */
    private void initialize() {
        // Example setup: Transition to the main menu
        gameStateManager.goToMainMenu();

        stage.getScene().setOnKeyPressed(event -> gameStateManager.handleInput(event));
        stage.getScene().setOnKeyReleased(event -> gameStateManager.handleInput(event));

        // Play background music
        audioManager.playMusic("background_music.mp3");
    }

    /**
     * Starts the game loop that continuously updates and renders the current game state.
     */
    private void startGameLoop() {
        final long[] previous = {System.nanoTime()};
        final double nsPerUpdate = 1e9 / 60.0; // 60 updates per second
    
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                while (now - previous[0] >= nsPerUpdate) {
                    gameStateManager.update();
                    previous[0] += nsPerUpdate;
                }
                gameStateManager.render();
            }
        };
        gameLoop.start();
    }    

    /**
     * Handles the transition from the main menu to the first level.
     */
    public void startGame() {
        audioManager.stopMusic();
        gameStateManager.goToLevel(1); // Use GameStateManager to transition to level 1
    }

    /**
     * Stops the game loop and handles losing the current level.
     */
    public void stopGameLoopAndLose() {
        stopGameLoop();
        gameStateManager.goToMainMenu(); // Transition to the main menu when the player loses
    }

    /**
     * Stops the game loop and handles winning the current level.
     */
    public void stopGameLoopAndWin() {
        stopGameLoop();
        gameStateManager.goToMainMenu(); // Transition to the main menu when the player wins
    }

    /**
     * Stops the game loop if it is running.
     */
    private void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }

    /**
     * Gets the AudioManager instance for managing game audio.
     *
     * @return The AudioManager object.
     */
    public AudioManager getAudioManager() {
        return audioManager;
    }
}
