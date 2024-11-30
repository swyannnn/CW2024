package com.example.demo.state;

import java.util.List;

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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    private UserPlane userPlane;
    private final ActorManager actorManager;
    private final CollisionManager collisionManager;
    private boolean levelCompleted;
    private PauseOverlay pauseOverlay;
    private Scene scene;
    private boolean isExplosionActive = false;

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
            if (event.getEventType() == KeyEvent.KEY_PRESSED && event.getCode() == KeyCode.ESCAPE) {
                gameStateManager.resumeGame();
            }
        } else {
            // Process regular game input
            if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                handleKeyPressed(event);
            } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                handleKeyReleased(event);
            }
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
        pauseOverlay = new PauseOverlay(gameStateManager);
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
    

    /**
     * Checks if any user's plane has been destroyed.
     *
     * @return true if any user's plane is destroyed; false otherwise.
     */
    public boolean userIsDestroyed() {
        for (UserPlane player : actorManager.getPlayers()) {
            // System.out.println("Checking player health: " + player.getHealth());
            if (player.getHealth() <= 0) {
                // System.out.println("Player destroyed: " + player);
                return true; // At least one player is destroyed
            }
        }
        return false; 
    }

    public boolean allUsersAreDestroyed() {
        return actorManager.getPlayers().stream()
                .allMatch(player -> player.getHealth() <= 0);
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
            case SPACE -> gameStateManager.pauseGame();
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

    public void onLevelComplete() {
        if (!levelCompleted) {
            levelCompleted = true;
            int nextLevelNumber = level.getCurrentLevelNumber() + 1;
            System.out.println("LevelState: Transitioning to Level " + nextLevelNumber);
            pcs.firePropertyChange("level", level.getCurrentLevelNumber(), nextLevelNumber);
        }
    }    
}
