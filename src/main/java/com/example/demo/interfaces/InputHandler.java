package com.example.demo.interfaces;

import javafx.scene.input.KeyEvent;

/**
 * The InputHandler interface defines a contract for handling input events.
 * Implementations of this interface should provide the logic for processing
 * input events, such as key presses.
 */
public interface InputHandler {
    /**
     * Handles the input event triggered by a key press.
     *
     * @param event the KeyEvent object containing details about the key press event
     */
    void handleInput(KeyEvent event);
}
