package com.example.demo.state;

import java.util.List;

import com.example.demo.actor.plane.UserPlane;
import com.example.demo.controller.Controller;
import com.example.demo.level.LevelParent;
import com.example.demo.manager.ActorManager;
import com.example.demo.manager.GameStateManager;

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
    private UserPlane userPlane;
    private final ActorManager actorManager;
    private boolean levelCompleted;

    /**
     * Constructor for LevelState.
     *
     * @param stage The main Stage object used for rendering scenes.
     * @param controller The Controller handling game logic.
     * @param level The LevelParent object representing the game level.
     * @param actorManager The ActorManager handling game actors.
     * @param gameStateManager The GameStateManager handling game state transitions.
     * @param audioManager The AudioManager handling game audio.
     * @param imageManager The ImageManager handling game images.
     */
    public LevelState(Stage stage, Controller controller, LevelParent level, ActorManager actorManager, GameStateManager gameStateManager) {
        this.level = level;
        this.stage = stage;
        this.actorManager = actorManager;
        this.gameStateManager = gameStateManager;
        this.levelCompleted = false;

        // Initialize userPlanes (handles multiple players)
        List<UserPlane> players = actorManager.getPlayers();
        if (players.isEmpty()) {
            System.err.println("LevelState: No UserPlane found in ActorManager.");
        } else {
            this.userPlane = players.get(0); // For single-player, just take the first player
            System.out.println("LevelState: UserPlane initialized with " + players.size() + " players.");
        }
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
        System.out.println("LevelState:" + gameStateManager);
        gameStateManager.getAudioManager().playMusic("menubgm.mp3");
    }

    @Override
    public void update() {
        if (!levelCompleted) {
            actorManager.updateAllActors();
            level.spawnEnemyUnits();
            level.handleEnemyPenetration();
            level.updateNumberOfEnemies();
            level.updateKillCount();
            level.updateLevelView();
            checkLevelCompletion();
        }
    }

    @Override
    public void render() {
        if (!levelCompleted) {
            level.getLevelView().updateView();
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
        scene.getRoot().requestFocus();
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
            actorManager.cleanup();
            level.loseGame();
        } else if (level.userHasReachedKillTarget()) {
            actorManager.cleanup();
            System.out.println("LevelState: Level " + level.getCurrentLevelNumber() + " completed.");
            onLevelComplete();
        }
    }

    /**
     * Handles KeyPressed events and delegates actions to the level's user.
     *
     * @param event The KeyEvent to process.
     */
    private void handleKeyPressed(KeyEvent event) {
        if (userPlane == null) return; // Ensure userPlane is not null
        KeyCode keyCode = event.getCode();
        switch (keyCode) {
            case UP -> userPlane.moveUp();
            case DOWN -> userPlane.moveDown();
            case LEFT -> userPlane.moveLeft();
            case RIGHT -> userPlane.moveRight();
            default -> {}
        }
    }

    /**
     * Handles KeyReleased events and stops the user's movement.
     *
     * @param event The KeyEvent to process.
     */
    private void handleKeyReleased(KeyEvent event) {
        if (userPlane == null) return; // Ensure userPlane is not null
        KeyCode keyCode = event.getCode();
        switch (keyCode) {
            case UP, DOWN -> userPlane.stopVertical();
            case LEFT, RIGHT -> userPlane.stopHorizontal();
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
