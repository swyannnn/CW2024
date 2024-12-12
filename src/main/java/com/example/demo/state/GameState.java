package com.example.demo.state;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

/**
 * Represents a state in the game. Implementations of this interface define
 * specific behaviors and logic for different game states.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/state/GameState.java">Github Source Code</a>
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
     * Retrieves the current Scene of the state.
     *
     * @return The Scene object representing the current state.
     */
    Scene getScene();
}
