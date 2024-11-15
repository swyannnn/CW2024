package com.example.demo.state;

import com.example.demo.level.LevelParent;
import com.example.demo.manager.GameStateManager;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * LevelState class that manages the game logic and rendering for a specific level.
 */
public class LevelState implements GameState {
    private final LevelParent level;
    private final Stage stage;
    private boolean levelCompleted;

    /**
     * Constructor for LevelState.
     *
     * @param level The LevelParent object representing the game level.
     * @param stage The main Stage object used for rendering scenes.
     */
    public LevelState(LevelParent level, Stage stage) {
        this.level = level;
        this.stage = stage;
        this.levelCompleted = false;
    }

    @Override
    public void initialize() {
        Scene scene = level.initializeScene();
        if (scene == null) {
            System.err.println("LevelState: initializeScene returned null for level " + level.getCurrentLevelNumber());
            return;
        }
        // Optionally, clear existing children or reset the stage
        stage.setScene(scene);
        // // Initialize the level and set the scene
        // stage.setScene(level.initializeScene());
        level.startGame();
        stage.show();

        System.out.println("LevelState: Level " + level.getCurrentLevelNumber() + " initialized and shown.");
        // Add event handlers for key input
        stage.getScene().setOnKeyPressed(this::handleKeyPressed);
        stage.getScene().setOnKeyReleased(this::handleKeyReleased);
    }

    @Override
    public void update() {
        if (!levelCompleted) {
            level.update();
            checkLevelCompletion(); // Check if the level has been completed
        }
    }

    /**
     * Checks if the level has been completed and calls onLevelComplete if true.
     */
    private void checkLevelCompletion() {
        if (level.userHasReachedKillTarget()) { // Assuming this method exists
            onLevelComplete();
        }
    }

    @Override
    public void render() {
        if (!levelCompleted) {
            level.render();
        }
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
     * Called when the level is complete. Transitions to the next level.
     */
    public void onLevelComplete() {
        if (!levelCompleted) {
            levelCompleted = true; // Mark the level as completed
            int nextLevelNumber = level.getCurrentLevelNumber() + 1;
            System.out.println("Transitioning to Level " + nextLevelNumber + " using onLevelComplete");
            Platform.runLater(() -> GameStateManager.getInstance().goToLevel(nextLevelNumber));
        }
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

    /**
     * Gets the current LevelParent object.
     *
     * @return The LevelParent object representing the game level.
     */
    public LevelParent getLevel() {
        return level;
    }
}
