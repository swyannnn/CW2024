package com.example.demo.state;

import com.example.demo.level.LevelParent;
import com.example.demo.manager.ActorManager;
import com.example.demo.manager.AudioManager;
import com.example.demo.manager.GameStateManager;
import com.example.demo.manager.ImageManager;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * LevelState manages the game logic and rendering for a specific level.
 */
public class LevelState implements GameState {
    private final LevelParent level;
    private final Stage stage;
    private final GameStateManager gameStateManager;
    private final AudioManager audioManager;
    private final ImageManager imageManager;
    private final ActorManager actorManager;
    private boolean levelCompleted;

    /**
     * Constructor for LevelState.
     *
     * @param level The LevelParent object representing the game level.
     * @param actorManager The ActorManager handling game actors.
     * @param stage The main Stage object used for rendering scenes.
     * @param gameStateManager The GameStateManager handling game state transitions.
     */
    public LevelState(Stage stage, LevelParent level, ActorManager actorManager, GameStateManager gameStateManager, AudioManager audioManager, ImageManager imageManager) {
        this.level = level;
        this.stage = stage;
        this.actorManager = actorManager;
        this.gameStateManager = gameStateManager;
        this.audioManager = audioManager;
        this.imageManager = imageManager;
        this.levelCompleted = false;
    }

    @Override
    public void initialize() {
        Scene scene = level.initializeScene();
        if (scene == null) {
            System.err.println("LevelState: Failed to initialize scene for level " + level.getCurrentLevelNumber());
            return;
        }

        setupScene(scene);
        level.startGame();
        stage.show();
        System.out.println("LevelState: Level " + level.getCurrentLevelNumber() + " initialized and displayed.");

        // Play background music for this level
        audioManager.playMusic("background_music.mp3");
    }

    @Override
    public void update() {
        if (!levelCompleted) {
            level.update();
            checkLevelCompletion();
        }
    }

    @Override
    public void render() {
        if (!levelCompleted) {
            level.render();
        }
    }

    @Override
    public void handleInput(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            handleKeyPressed(event);
        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            handleKeyReleased(event);
        }
    }

    @Override
    public void cleanup() {
        removeEventHandlers();
        if (actorManager != null) {
            actorManager.removeDestroyedActors();
        }
        // audioManager.stopMusic(); // Stop music when level is cleaned up
    }

    /**
     * Sets up the scene and registers input event handlers.
     *
     * @param scene The scene to set on the stage.
     */
    private void setupScene(Scene scene) {
        stage.setScene(scene);
        registerEventHandlers();
    }

    /**
     * Registers event handlers for key input.
     */
    private void registerEventHandlers() {
        Scene scene = stage.getScene();
        if (scene != null) {
            scene.setOnKeyPressed(this::handleKeyPressed);
            scene.setOnKeyReleased(this::handleKeyReleased);
        }
    }

    /**
     * Removes input event handlers.
     */
    private void removeEventHandlers() {
        Scene scene = stage.getScene();
        if (scene != null) {
            scene.setOnKeyPressed(null);
            scene.setOnKeyReleased(null);
        }
    }

    /**
     * Checks if the level has been completed and triggers the appropriate actions.
     */
    private void checkLevelCompletion() {
        if (level.userIsDestroyed()) {
            level.loseGame();
        } else if (level.userHasReachedKillTarget()) {
            onLevelComplete();
        }
    }

    /**
     * Handles KeyPressed events and delegates actions to the level's user.
     *
     * @param event The KeyEvent to process.
     */
    private void handleKeyPressed(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        switch (keyCode) {
            case UP -> level.getUser().moveUp();
            case DOWN -> level.getUser().moveDown();
            case LEFT -> level.getUser().moveLeft();
            case RIGHT -> level.getUser().moveRight();
            case SPACE -> level.fireProjectile();
            default -> {}
        }
    }

    /**
     * Handles KeyReleased events and stops the user's movement.
     *
     * @param event The KeyEvent to process.
     */
    private void handleKeyReleased(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        switch (keyCode) {
            case UP, DOWN -> level.getUser().stopVertical();
            case LEFT, RIGHT -> level.getUser().stopHorizontal();
            default -> {}
        }
    }

    /**
     * Called when the level is complete, transitioning to the next level.
     */
    public void onLevelComplete() {
        if (!levelCompleted) {
            levelCompleted = true;
            int nextLevelNumber = level.getCurrentLevelNumber() + 1;
            System.out.println("LevelState: Transitioning to Level " + nextLevelNumber);
            Platform.runLater(() -> gameStateManager.goToLevel(nextLevelNumber)); 
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
