package com.example.demo.manager;

import com.example.demo.handler.InputHandler;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

/**
 * InputManager is responsible for handling and delegating input events.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/manager/InputManager.java">Github Source Code</a>
 */
public class InputManager {
    private final Scene scene;
    private final InputHandler inputHandler;

    /**
     * Constructor for InputManager.
     *
     * @param scene        The scene to attach input handlers to.
     * @param inputHandler The handler to process input events.
     */
    public InputManager(Scene scene, InputHandler inputHandler) {
        this.scene = scene;
        this.inputHandler = inputHandler;
        setupInputHandlers();
    }

    /**
     * Sets up key event handlers on the scene.
     */
    private void setupInputHandlers() {
        scene.setOnKeyPressed(this::handleInput);
        scene.setOnKeyReleased(this::handleInput);
    }

    /**
     * Handles a key event and delegates it to the input handler.
     *
     * @param event The key event to handle.
     */
    private void handleInput(KeyEvent event) {
        inputHandler.handleInput(event);
    }
}
