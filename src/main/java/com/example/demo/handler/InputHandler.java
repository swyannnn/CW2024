package com.example.demo.handler;

import javafx.scene.input.KeyEvent;

/**
 * The InputHandler interface defines a contract for handling input events.
 * Implementations of this interface should provide the logic for processing
 * input events, such as key presses.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/handler/InputHandler.java">Github Source Code</a>
 */
public interface InputHandler {
    /**
     * Handles the input event triggered by a key press.
     *
     * @param event the KeyEvent object containing details about the key press event
     */
    void handleInput(KeyEvent event);
}
