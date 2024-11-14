package com.example.demo.controller;

import com.example.demo.GameControl;
import com.example.demo.level.LevelFactory;
import com.example.demo.level.LevelParent;
import com.example.demo.manager.AudioManager;
import com.example.demo.manager.GameStateManager;
import com.example.demo.state.LevelState;

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
    public Controller(Stage stage) {
        this.stage = stage;
        this.audioManager = new AudioManager();
        this.gameStateManager = GameStateManager.getInstance(stage, this);
        initialize();
    }

    /**
     * Initializes the game by transitioning to the main menu.
     */
    private void initialize() {
        gameStateManager.goToMainMenu();
    }

    /**
     * Handles the transition from the main menu to the first level.
     */
    public void startGame() {
        audioManager.stopMusic();
        LevelParent level = LevelFactory.createLevel(1, this); // Create the initial level
        LevelState levelState = new LevelState(level, stage); // Create a new LevelState for the level
        gameStateManager.setState(levelState); // Set the new LevelState in GameStateManager
        startGameLoop(levelState); // Start the game loop with the new LevelState
    }

    /**
     * Starts or resets the game loop for a specific LevelState.
     *
     * @param levelState The LevelState object to be updated and rendered in the game loop.
     */
    public void startGameLoop(LevelState levelState) {
        if (gameLoop != null) {
            gameLoop.stop();
        }

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                levelState.update(); // Update game logic
                levelState.render(); // Render game elements
            }
        };
        gameLoop.start();
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
