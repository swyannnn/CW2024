package com.example.demo.manager;

import com.example.demo.controller.Controller;
import com.example.demo.level.LevelParent;
import com.example.demo.memento.GameStateMemento;
import com.example.demo.state.GameState;
import com.example.demo.state.GameStateFactory;
import com.example.demo.state.LevelState;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * GameStateManager handles the transitions and management of different game states.
 * It uses the Singleton pattern to ensure only one instance is used throughout the application.
 */
public class GameStateManager {
    private static GameStateManager instance;
    private GameState currentState;
    private final GameStateFactory stateFactory;
    private GameStateMemento savedState;

    /**
     * Private constructor to enforce the Singleton pattern.
     *
     * @param stage The main Stage object used for rendering scenes.
     * @param controller The Controller instance managing game flow.
     */
    private GameStateManager(Stage stage, Controller controller) {
        this.stateFactory = new GameStateFactory(stage, controller, this);
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
     * Sets the controller instance for GameStateManager.
     *
     * @param controller The Controller instance managing game flow.
     */
    public void setController(Controller controller) {
        stateFactory.setController(controller);
    }

    /**
     * Sets the current game state, performing any necessary cleanup of the previous state.
     *
     * @param newState The new GameState to transition to.
     */
    public void setState(GameState newState) {
        try {
            if (currentState != null) {
                currentState.cleanup();
            }
            currentState = newState;
            currentState.initialize();
        } catch (Exception e) {
            System.err.println("Error during state transition: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Transitions to the main menu state.
     */
    public void goToMainMenu() {
        setState(stateFactory.createMainMenuState());
    }

    /**
     * Transitions to a specific level based on the level number.
     *
     * @param levelNumber The number of the level to transition to.
     */
    public void goToLevel(int levelNumber) {
        GameState levelState = stateFactory.createLevelState(levelNumber);
        if (levelState != null) {
            setState(levelState);
        } else {
            System.err.println("Failed to transition to Level " + levelNumber);
        }
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
     * Saves the current game state.
     */
    public void saveGameState() {
        if (currentState instanceof LevelState) {
            LevelParent currentLevel = ((LevelState) currentState).getLevel();
            if (currentLevel != null) {
                savedState = currentLevel.saveState();
                System.out.println("Game state saved.");
            } else {
                System.out.println("No current level to save.");
            }
        } else {
            System.out.println("Current state is not a LevelState.");
        }
    }

    /**
     * Loads the previously saved game state.
     */
    public void loadGameState() {
        if (savedState != null && currentState instanceof LevelState) {
            LevelParent currentLevel = ((LevelState) currentState).getLevel();
            if (currentLevel != null) {
                currentLevel.restoreState(savedState);
                System.out.println("Game state loaded.");
            } else {
                System.out.println("No current level to load.");
            }
        } else {
            System.out.println("No saved state to load or current state is not a LevelState.");
        }
    }

    /**
     * Gets the current level.
     *
     * @return The current LevelParent object, or null if the current state is not a LevelState.
     */
    public LevelParent getCurrentLevel() {
        if (currentState instanceof LevelState) {
            return ((LevelState) currentState).getLevel();
        }
        return null;
    }
}
