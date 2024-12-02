package com.example.demo.state;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.plane.UserPlane;
import com.example.demo.controller.Controller;
import com.example.demo.level.LevelParent;
import com.example.demo.listener.CollisionListener;
import com.example.demo.manager.ActorManager;
import com.example.demo.manager.ButtonManager;
import com.example.demo.manager.CollisionManager;
import com.example.demo.manager.GameStateManager;
import com.example.demo.ui.PauseOverlay;
import com.example.demo.util.GameConstant;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


/**
 * LevelState manages the game logic and rendering for a specific level.
 */
public class LevelState implements GameState, CollisionListener {
    private final String BUTTON_IMAGE_NAME = GameConstant.PauseButton.IMAGE_NAME;
    private final int BUTTON_IMAGE_WIDTH = GameConstant.PauseButton.IMAGE_WIDTH;
    private final int BUTTON_IMAGE_HEIGHT = GameConstant.PauseButton.IMAGE_HEIGHT;
    private final int BUTTON_X_POSITION = GameConstant.PauseButton.X_POSITION;
    private final int BUTTON_Y_POSITION = GameConstant.PauseButton.Y_POSITION;
    
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private final LevelParent level;
    private final Stage stage;
    private final GameStateManager gameStateManager;
    private final ActorManager actorManager;
    private final CollisionManager collisionManager;
    private boolean levelCompleted;
    private PauseOverlay pauseOverlay;
    private Scene scene;
    private boolean isExplosionActive = false;
    // Map to hold each player's key bindings
    private final Map<UserPlane, PlayerKeyBindings> playerKeyBindingsMap = new HashMap<>();
    // Set to keep track of currently pressed keys
    private final Set<KeyCode> activeKeys = new java.util.HashSet<>();

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
    public LevelState(Stage stage, Controller controller, LevelParent level) {
        this.level = level;
        this.stage = stage;
        this.gameStateManager = controller.getGameStateManager();
        this.actorManager = gameStateManager.getActorManager();
        this.collisionManager = gameStateManager.getCollisionManager();
        collisionManager.setCollisionListener(this);
        this.levelCompleted = false;
        // Assign key bindings to each player
        assignPlayerKeyBindings();
    }

    @Override
    public void initialize() {
        this.scene = level.getScene();
        if (this.scene == null) {
            System.err.println("LevelState: Failed to initialize scene for level " + level.getCurrentLevelNumber());
            return;
        }

        setupScene(this.scene);
        stage.show();
        System.out.println("LevelState: Level " + level.getCurrentLevelNumber() + " initialized and displayed.");

        // Play background music for this level
        System.out.println("LevelState:" + gameStateManager);
        createPauseOverlay();
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    public void onExplosionStarted() {
        isExplosionActive = true;
    }

    public void onExplosionFinished() {
        isExplosionActive = false;
    }


    public void update(long now) {
        if (!levelCompleted) {
            level.spawnEnemyUnits();
            actorManager.updateAllActors(now);
            actorManager.removeDestroyedActors(); // Move this before collision detection
            collisionManager.handleAllCollisions(actorManager);
            level.updateLevelView();
            level.updateBackground();
            checkLevelCompletion();
        }
    }
    

    @Override
    public void render() {
        // No rendering needed for LevelState
    }

    @Override
    public void handleInput(KeyEvent event) {
        if (gameStateManager.isPaused()) {
            // When paused, only ESCAPE key resumes the game
            if (event.getEventType() == KeyEvent.KEY_PRESSED && event.getCode() == KeyCode.SPACE) {
                gameStateManager.resumeGame();
            }
            return; // Ignore other inputs when paused
        }

        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            if (event.getCode() == KeyCode.SPACE) {
                // SPACE key pauses the game
                gameStateManager.pauseGame();
            } else {
                // Add other keys to activeKeys for movement
                activeKeys.add(event.getCode());
                processActiveKeys();
            }
        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            // Remove keys from activeKeys when released
            activeKeys.remove(event.getCode());
            processActiveKeys();
        }
    }

    @Override
    public void cleanup() {
        // no cleanup needed for LevelState
    }

    /**
     * Sets up the scene and registers input event handlers.
     *
     * @param scene The scene to set on the stage.
     */
    private void setupScene(Scene scene) {
        stage.setScene(scene);
        scene.getRoot().requestFocus();
        addPauseButton(scene);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    /**
     * Handles projectile collisions with enemies.
     *
     * @param userPlane The user plane involved in the collision.
     * @param enemy     The enemy involved in the collision.
     */
    @Override
    public void onProjectileHitEnemy(UserPlane userPlane, ActiveActorDestructible enemy) {
        userPlane.incrementKillCount();
        System.out.println("Kill count for user updated: " + userPlane.getNumberOfKills());
    }
    
    /**
     * Adds a pause button to the scene.
     *
     * @param scene The current scene where the button will be added.
     */
    private void addPauseButton(Scene scene) {
        // Create the pause button with an icon
        Button pauseButton = ButtonManager.createImageButton(BUTTON_IMAGE_NAME, BUTTON_IMAGE_WIDTH, BUTTON_IMAGE_HEIGHT);

        // Set the action for the pause button
        pauseButton.setOnAction(e -> gameStateManager.pauseGame());
        pauseButton.setLayoutX(BUTTON_X_POSITION); // 10 pixels from the left
        pauseButton.setLayoutY(BUTTON_Y_POSITION); // 10 pixels from the top

        // Add the button to the scene
        Platform.runLater(() ->  actorManager.addUIElement(pauseButton));
    }

    /**
     * Creates the pause overlay UI.
     */
    private void createPauseOverlay() {
        System.out.println("Creating PauseOverlay for Level " + level.getCurrentLevelNumber());
        pauseOverlay = new PauseOverlay(gameStateManager, level.getCurrentLevelNumber());
    }

    /**
     * Displays the pause overlay.
     */
    @Override
    public void handlePause() {
        Platform.runLater(() -> {
            if (!level.getRoot().getChildren().contains(pauseOverlay.getOverlay())) {
                level.getRoot().getChildren().add(pauseOverlay.getOverlay());
                pauseOverlay.getOverlay().requestFocus();
            }
        });
        System.out.println("Pause overlay displayed.");
    }

    /**
     * Hides the pause overlay.
     */
    @Override
    public void handleResume() {
        Platform.runLater(() -> {
            level.getRoot().getChildren().remove(pauseOverlay.getOverlay());
            level.getRoot().requestFocus();
        });
        System.out.println("Pause overlay hidden.");
    }

    private void checkLevelCompletion() {
        if (allUsersAreDestroyed()) {
            actorManager.cleanup();
            pcs.firePropertyChange("lose", false, true);
        } else if (level.userHasReachedTarget()) {
            actorManager.cleanup();
            onLevelComplete();
        }
    }

    public boolean allUsersAreDestroyed() {
        return actorManager.getPlayers().stream()
                .allMatch(player -> player.getHealth() <= 0);
    }   

    public void onLevelComplete() {
        if (!levelCompleted) {
            levelCompleted = true;
            int nextLevelNumber = level.getCurrentLevelNumber() + 1;
            System.out.println("LevelState: Transitioning to Level " + nextLevelNumber);
            pcs.firePropertyChange("level", level.getCurrentLevelNumber(), nextLevelNumber);
        }
    } 
    
        /**
     * Assigns key bindings to each player based on the number of players.
     */
    private void assignPlayerKeyBindings() {
        List<UserPlane> players = actorManager.getPlayers();
        int numPlayers = gameStateManager.getNumberOfPlayers();

        for (int i = 0; i < numPlayers; i++) {
            UserPlane player = players.get(i);
            PlayerKeyBindings bindings;

            if (i == 0) {
                // Player 1: W, A, S, D for movement
                bindings = new PlayerKeyBindings(
                    EnumSet.of(KeyCode.UP, KeyCode.LEFT, KeyCode.DOWN, KeyCode.RIGHT)
                );
            } else if (i == 1) {
                // Player 2: Arrow keys for movement
                bindings = new PlayerKeyBindings(
                    EnumSet.of(KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D)
                );
            } else {
                // Additional players can be added here with their own bindings
                bindings = null;
            }

            playerKeyBindingsMap.put(player, bindings);
            System.out.println("Assigned key bindings for Player " + (i + 1));
        }
    }

    /**
     * Processes all active keys and updates each player's state accordingly.
     */
    private void processActiveKeys() {
        for (Map.Entry<UserPlane, PlayerKeyBindings> entry : playerKeyBindingsMap.entrySet()) {
            UserPlane player = entry.getKey();
            PlayerKeyBindings bindings = entry.getValue();

            // Handle vertical movement
            boolean movingUp = bindings.getMovementKeys().contains(KeyCode.UP) && activeKeys.contains(KeyCode.UP) ||
                                bindings.getMovementKeys().contains(KeyCode.W) && activeKeys.contains(KeyCode.W);
            boolean movingDown = bindings.getMovementKeys().contains(KeyCode.DOWN) && activeKeys.contains(KeyCode.DOWN) ||
                                  bindings.getMovementKeys().contains(KeyCode.S) && activeKeys.contains(KeyCode.S);

            if (movingUp) {
                player.moveUp();
            } else if (movingDown) {
                player.moveDown();
            } else {
                player.stopVertical();
            }

            // Handle horizontal movement
            boolean movingLeft = bindings.getMovementKeys().contains(KeyCode.LEFT) && activeKeys.contains(KeyCode.LEFT) ||
                                 bindings.getMovementKeys().contains(KeyCode.A) && activeKeys.contains(KeyCode.A);
            boolean movingRight = bindings.getMovementKeys().contains(KeyCode.RIGHT) && activeKeys.contains(KeyCode.RIGHT) ||
                                  bindings.getMovementKeys().contains(KeyCode.D) && activeKeys.contains(KeyCode.D);

            if (movingLeft) {
                player.moveLeft();
            } else if (movingRight) {
                player.moveRight();
            } else {
                player.stopHorizontal();
            }
        }
    }

    /**
     * Inner class to define key bindings for a player.
     */
    private static class PlayerKeyBindings {
        private final Set<KeyCode> movementKeys;

        public PlayerKeyBindings(Set<KeyCode> movementKeys) {
            this.movementKeys = movementKeys;
        }

        public Set<KeyCode> getMovementKeys() {
            return movementKeys;
        }
    }
}
