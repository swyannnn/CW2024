package com.example.demo.manager;
import com.example.demo.state.GameState;

import javafx.scene.input.KeyEvent;

public class InputManager {
    private GameState currentState;

    public void setCurrentState(GameState state) {
        this.currentState = state;
    }

    public void handleInput(KeyEvent event) {
        if (currentState != null) {
            currentState.handleInput(event);
        }
    }
}
