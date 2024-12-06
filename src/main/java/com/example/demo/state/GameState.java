package com.example.demo.state;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

/**
 * GameState interface defines the basic structure for all game states.
 */
public interface GameState {
    /**
     * Initializes the game state. This method is called when the state is first loaded.
     */
    void initialize();

    /**
     * Updates the game state. This method is called in the game loop to update the state logic.
     * @param now The current timestamp in nanoseconds.
     */
    void update(long now);

    /**
     * Handles input events specific to the game state.
     *
     * @param event The KeyEvent to be processed.
     */
    void handleInput(KeyEvent event);

    /**
     * Cleans up resources or resets settings before transitioning to another state.
     * This method is called when the game state is about to be changed.
     */
    void cleanup();

    /**
     * Handles actions to perform when the game is paused.
     * Default implementation does nothing.
     */
    default void handlePause() {
        // Default implementation: do nothing.
        // Implementing classes can override if needed.
    }
    
    /**
     * Handles actions to perform when the game is resumed.
     * Default implementation does nothing.
     */
    default void handleResume() {}

    /**
     * Retrieves the current Scene of the state.
     *
     * @return The Scene object representing the current state.
     */
    Scene getScene();
}
