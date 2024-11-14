package com.example.demo.state;

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
     */
    void update();

    /**
     * Renders the game state. This method is called in the game loop to render graphics or UI elements.
     */
    void render();

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
}
