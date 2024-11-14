package com.example.demo.state;

import com.example.demo.level.LevelParent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * LevelState class that manages the game logic and rendering for a specific level.
 */
public class LevelState implements GameState {
    private final LevelParent level;
    private final Stage stage;

    /**
     * Constructor for LevelState.
     *
     * @param level The LevelParent object representing the game level.
     * @param stage The main Stage object used for rendering scenes.
     */
    public LevelState(LevelParent level, Stage stage) {
        this.level = level;
        this.stage = stage;
    }

    @Override
    public void initialize() {
        // Initialize the level and set the scene
        stage.setScene(level.initializeScene());
        level.startGame();
        
        // Add event handlers for key input
        stage.getScene().setOnKeyPressed(this::handleKeyPressed);
        stage.getScene().setOnKeyReleased(this::handleKeyReleased);
    }

    @Override
    public void update() {
        level.update();
    }

    @Override
    public void render() {
        level.render();
    }

    @Override
    public void cleanup() {
        // Remove event handlers when cleaning up the state
        stage.getScene().setOnKeyPressed(null);
        stage.getScene().setOnKeyReleased(null);
    }

    @Override
    public void handleInput(KeyEvent event) {
        // Delegate to the appropriate method based on the event type
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            handleKeyPressed(event);
        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            handleKeyReleased(event);
        }
    }

    /**
     * Gets the current LevelParent object.
     *
     * @return The LevelParent object representing the game level.
     */
    public LevelParent getLevel() {
        return level;
    }

    /**
     * Handles KeyPressed events.
     *
     * @param event The KeyEvent to be processed.
     */
    private void handleKeyPressed(KeyEvent event) {
        KeyCode kc = event.getCode();
        switch (kc) {
            case UP:
                level.getUser().moveUp();
                break;
            case DOWN:
                level.getUser().moveDown();
                break;
            case LEFT:
                level.getUser().moveLeft();
                break;
            case RIGHT:
                level.getUser().moveRight();
                break;
            case SPACE:
                level.fireProjectile();
                break;
            default:
                break;
        }
    }

    /**
     * Handles KeyReleased events.
     *
     * @param event The KeyEvent to be processed.
     */
    private void handleKeyReleased(KeyEvent event) {
        KeyCode kc = event.getCode();
        switch (kc) {
            case UP:
            case DOWN:
                level.getUser().stopVertical();
                break;
            case LEFT:
            case RIGHT:
                level.getUser().stopHorizontal();
                break;
            default:
                break;
        }
    }
}
