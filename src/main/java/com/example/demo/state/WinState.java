package com.example.demo.state;

import com.example.demo.manager.GameStateManager;
import com.example.demo.ui.WinScreen;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * WinState class manages the win screen state of the game.
 */
public class WinState implements GameState {
    private final Stage stage;
    private final GameStateManager gameStateManager;
    private WinScreen winScreen;

    /**
     * Constructor for WinState.
     *
     * @param stage The primary stage of the application.
     * @param gameStateManager The GameStateManager instance.
     */
    public WinState(Stage stage, GameStateManager gameStateManager) {
        this.stage = stage;
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void initialize() {
        // Initialize the WinScreen and set the scene
        System.out.println("Initializing Win State");
        winScreen = new WinScreen(stage, gameStateManager);
        stage.setScene(winScreen.getWinScreenScene());
        stage.show();
    }

    @Override
    public void update() {
        // No update logic needed for static win screen
    }

    @Override
    public void render() {
        // Rendering is handled by JavaFX's scene graph
    }

    @Override
    public void handleInput(KeyEvent event) {
        // Handle input if necessary
    }

    @Override
    public void cleanup() {
        // Cleanup resources if necessary
        if (gameStateManager.getAudioManager() != null) {
            gameStateManager.getAudioManager().stopMusic();
        }
    }
}
