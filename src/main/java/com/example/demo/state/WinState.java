package com.example.demo.state;

import com.example.demo.manager.GameStateManager;
import com.example.demo.ui.WinScreen;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * WinState class manages the win screen state of the game.
 */
public class WinState implements IGameState {
    private final Stage stage;
    private final GameStateManager gameStateManager;
    private WinScreen winScreen;
    private Scene scene;

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
        this.scene = winScreen.getWinScreenScene();
        stage.setScene(this.scene);
        stage.show();
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public void update(long now) {
        // No update logic needed for static win screen
    }

    @Override
    public void render() {
        // Rendering is handled by JavaFX's scene graph
    }

    @Override
    public void handleInput(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            KeyCode keyCode = event.getCode();
            switch (keyCode) {
                case ENTER:
                    gameStateManager.goToMainMenu(); // Return to main menu
                    break;
                case ESCAPE:
                    stage.close(); // Exit the game
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void cleanup() {
        if (gameStateManager.getAudioManager() != null) {
            gameStateManager.getAudioManager().stopMusic();
        }
    }
}
