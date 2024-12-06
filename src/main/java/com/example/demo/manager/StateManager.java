package com.example.demo.manager;

import com.example.demo.listeners.StateTransitioner;
import com.example.demo.state.GameStateFactory;
import com.example.demo.state.IGameState;
import com.example.demo.state.LevelState;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * StateManager is responsible for managing game state transitions and lifecycle.
 * It implements StateTransitioner and listens for property changes from game states.
 */
public class StateManager implements StateTransitioner, PropertyChangeListener {
    private IGameState currentState;
    private final GameStateFactory stateFactory;
    private int numberOfPlayers;

    /**
     * Constructor for StateManager.
     *
     * @param stage             The primary stage of the application.
     * @param actorManager      The ActorManager instance.
     * @param collisionManager  The CollisionManager instance.
     * @param gameLoopManager   The GameLoopManager instance for handling the game loop and pausing.
     * @param audioManager      The AudioManager instance.
     * @param numberOfPlayers   The initial number of players.
     */
    public StateManager(
        Stage stage,
        ActorManager actorManager,
        CollisionManager collisionManager,
        GameLoopManager gameLoopManager,
        AudioManager audioManager,
        int numberOfPlayers
    ) {
        this.numberOfPlayers = numberOfPlayers;

        // Initialize GameStateFactory
        this.stateFactory = new GameStateFactory(
            stage,
            actorManager,
            collisionManager,
            gameLoopManager, // PauseManager
            this,            // StateTransitioner
            audioManager
        );
    }

    /**
     * Transitions to the main menu state.
     */
    @Override
    public void goToMainMenu() {
        cleanupCurrentState();
        setState(stateFactory.createMainMenuState());
    }

    /**
     * Transitions to a specific level based on the level number.
     *
     * @param levelNumber The number of the level to transition to.
     */
    @Override
    public void goToLevel(int levelNumber) {
        cleanupCurrentState();
        setState(stateFactory.createLevelState(levelNumber));
    }

    /**
     * Transitions to a win state.
     */
    @Override
    public void goToWinState() {
        cleanupCurrentState();
        setState(stateFactory.createWinState());
    }

    /**
     * Transitions to a lose state.
     */
    @Override
    public void goToLoseState() {
        cleanupCurrentState();
        setState(stateFactory.createLoseState());
    }

    /**
     * Sets the current game state to a new state, performing cleanup on the old state and initialization on the new one.
     *
     * @param newState The new IGameState to transition to.
     */
    private void setState(IGameState newState) {
        currentState = newState;
        if (currentState != null) {
            if (newState instanceof LevelState) {
                ((LevelState) newState).addPropertyChangeListener(this);
            }
            currentState.initialize();
            setupInputHandlers();
            System.out.println("StateManager: Initialized new state: " + currentState.getClass().getSimpleName());
        }
    }

    /**
     * Cleans up the current state before transitioning to a new state.
     */
    private void cleanupCurrentState() {
        if (currentState != null) {
            currentState.cleanup();
            System.out.println("StateManager: Cleaned up previous state: " + currentState.getClass().getSimpleName());

            if (currentState instanceof LevelState) {
                ((LevelState) currentState).removePropertyChangeListener(this);
            }
            currentState = null;
        }
    }

    /**
     * Responds to property changes from LevelStates, such as "win", "lose", or "level" transitions.
     *
     * @param evt The PropertyChangeEvent triggered by a LevelState.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "win":
                goToWinState();
                break;
            case "lose":
                goToLoseState();
                break;
            case "level":
                int nextLevelNumber = (int) evt.getNewValue();
                goToLevel(nextLevelNumber);
                break;
            default:
                break;
        }
    }

    /**
     * Retrieves the current game state.
     *
     * @return The current IGameState.
     */
    public IGameState getCurrentState() {
        return currentState;
    }

    /**
     * Sets the number of players for the game.
     *
     * @param numberOfPlayers The number of players (1 or 2).
     */
    @Override
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Retrieves the current number of players in the game.
     *
     * @return The current number of players.
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Cleanup method to clean up the current state.
     */
    public void cleanup() {
        cleanupCurrentState();
        System.out.println("StateManager: Cleanup completed.");
    }

    /**
     * Sets up input handlers for the current state's scene.
     */
    private void setupInputHandlers() {
        Scene scene = currentState.getScene();
        if (scene != null) {
            scene.setOnKeyPressed(event -> currentState.handleInput(event));
            scene.setOnKeyReleased(event -> currentState.handleInput(event));
        }
    }
}
