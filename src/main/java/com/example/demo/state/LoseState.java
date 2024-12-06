package com.example.demo.state;

import com.example.demo.manager.GameStateManager;
import com.example.demo.ui.LoseScreen;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * LoseState class manages the lose screen state of the game.
 */
public class LoseState implements IGameState {
    private final Stage stage;
    private final GameStateManager gameStateManager;
    private LoseScreen loseScreen;
    private Scene scene;

    /**
     * Constructor for LoseState.
     *
     * @param stage The primary stage of the application.
     * @param gameStateManager The GameStateManager instance.
     */
    public LoseState(Stage stage, GameStateManager gameStateManager) {
        this.stage = stage;
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void initialize() {
        // Initialize the LoseScreen and set the scene
        System.out.println("Initializing Lose State");
        loseScreen = new LoseScreen(stage, gameStateManager);
        this.scene = loseScreen.getLoseScreenScene();
        stage.setScene(this.scene);
        stage.show();
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public void update(long now) {
        // No update logic needed for static lose screen
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
        // Cleanup resources if necessary
        if (gameStateManager.getAudioManager() != null) {
            gameStateManager.getAudioManager().stopMusic();
        }
    }
}
