package com.example.demo.manager;

import com.example.demo.controller.Controller;
import com.example.demo.level.LevelParent;
import com.example.demo.state.GameState;
import com.example.demo.state.LevelState;
import com.example.demo.state.MainMenuState;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * GameStateManager handles the transitions and management of different game states.
 * It follows the Singleton pattern to ensure only one instance is used throughout the application.
 */
public class GameStateManager {
    private static GameStateManager instance;
    private GameState currentState;
    private final Controller controller;
    private final Stage stage;

    /**
     * Private constructor to enforce the Singleton pattern.
     *
     * @param stage The main Stage object used for rendering scenes.
     * @param controller The Controller instance managing game flow.
     */
    private GameStateManager(Stage stage, Controller controller) {
        this.stage = stage;
        this.controller = controller;
    }

    /**
     * Retrieves the single instance of GameStateManager, creating it if necessary.
     *
     * @param stage The main Stage object used for rendering scenes.
     * @param controller The Controller instance managing game flow.
     * @return The singleton instance of GameStateManager.
     */
    public static GameStateManager getInstance(Stage stage, Controller controller) {
        if (instance == null) {
            instance = new GameStateManager(stage, controller);
        }
        return instance;
    }

    /**
     * Sets the current game state, performing any necessary cleanup of the previous state.
     *
     * @param state The new GameState to transition to.
     */
    public void setState(GameState state) {
        if (currentState != null) {
            currentState.cleanup();
        }
        currentState = state;
        currentState.initialize();
    }

    /**
     * Transitions to the main menu state.
     */
    public void goToMainMenu() {
        setState(new MainMenuState(stage, controller));
    }

    /**
     * Transitions to a specific level by creating and setting a LevelState.
     *
     * @param level The LevelParent object representing the level to transition to.
     */
    public void goToLevel(LevelParent level) {
        setState(new LevelState(level, stage));
    }

    /**
     * Updates the current game state. This method is called in the game loop.
     */
    public void update() {
        if (currentState != null) {
            currentState.update();
        }
    }

    /**
     * Renders the current game state. This method is called in the game loop.
     */
    public void render() {
        if (currentState != null) {
            currentState.render();
        }
    }

    /**
     * Handles input events and delegates them to the current game state.
     *
     * @param event The KeyEvent to be processed.
     */
    public void handleInput(KeyEvent event) {
        if (currentState != null) {
            currentState.handleInput(event);
        }
    }

    /**
     * Gets the current level.
     *
     * @return The current LevelParent object.
     */
    public LevelParent getCurrentLevel() {
        if (currentState instanceof LevelState) {
            return ((LevelState) currentState).getLevel();
        }
        return null;
    }
}
