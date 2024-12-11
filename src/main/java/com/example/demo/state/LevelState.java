package com.example.demo.state;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.demo.actor.ActiveActor;
import com.example.demo.actor.plane.UserPlane;
import com.example.demo.handler.CollisionHandler;
import com.example.demo.level.LevelParent;
import com.example.demo.manager.ActorManager;
import com.example.demo.manager.ButtonManager;
import com.example.demo.manager.CollisionManager;
import com.example.demo.manager.GameLoopManager;
import com.example.demo.screen.PauseScreen;
import com.example.demo.strategy.movement.UserMovementStrategy;
import com.example.demo.util.GameConstant;
import com.example.demo.util.PlayerKeyBindings;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;



/**
 * The LevelState class represents the state of a game level and manages various aspects of the game,
 * including input handling, collision detection, actor management, and state transitions.
 * It implements the GameState and CollisionHandler interfaces.
 * 
 * This class is responsible for initializing the level, updating the game state, handling input events,
 * managing the pause overlay, and cleaning up resources when the level is completed or reset.
 * 
 * Key features of this class include:
 * - Initializing and displaying the game level.
 * - Updating the game state and handling collisions.
 * - Managing input events for player controls and game state transitions.
 * - Displaying and hiding the pause overlay.
 * - Cleaning up resources and listeners when the level is completed or reset.
 * - Assigning key bindings to players and setting up their movement and firing strategies.
 * 
 * The class also provides methods for adding and removing PropertyChangeListeners, handling projectile
 * collisions with enemies, and checking if the level is completed.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/state/LevelState.java">Github Source Code</a>
 * @see GameState
 * @see CollisionHandler
 * @see LevelParent
 * @see ActorManager
 * @see CollisionManager
 * @see GameLoopManager
 * @see StateTransitioner
 */
public class LevelState implements GameState, CollisionHandler {
    private final String buttonImageName = GameConstant.PauseButton.IMAGE_NAME;
    private final int buttonImageWidth = GameConstant.PauseButton.IMAGE_WIDTH;
    private final int buttonImageHeight = GameConstant.PauseButton.IMAGE_HEIGHT;
    private final int buttonXPosition = GameConstant.PauseButton.X_POSITION;
    private final int buttonYPosition = GameConstant.PauseButton.Y_POSITION;
    
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private final LevelParent level;
    private final Stage stage;
    private final ActorManager actorManager;
    private final CollisionManager collisionManager;
    private final GameLoopManager gameLoopManager;
    private final StateTransitioner stateTransitioner;
    private boolean levelCompleted;
    private PauseScreen pauseOverlay;
    private Scene scene;
    private boolean isExplosionActive = false;
    // Map to hold each player's key bindings
    private final Map<UserPlane, PlayerKeyBindings> playerKeyBindingsMap = new HashMap<>();
    // Set to keep track of currently pressed keys
    private final Set<KeyCode> activeKeys = new java.util.HashSet<>();

    /**
     * Represents the state of a level in the game.
     * 
     * @param stage The stage where the level is rendered.
     * @param level The parent level object.
     * @param actorManager Manages the actors within the level.
     * @param collisionManager Manages collision detection and handling.
     * @param gameLoopManager Manages the game loop.
     * @param stateTransitioner Handles transitions between different states.
     */
    public LevelState(Stage stage, LevelParent level, ActorManager actorManager, CollisionManager collisionManager, GameLoopManager gameLoopManager, StateTransitioner stateTransitioner) {
        this.level = level;
        this.stage = stage;
        this.actorManager = actorManager;
        this.collisionManager = collisionManager;
        collisionManager.setCollisionHandler(this);
        this.gameLoopManager = gameLoopManager;
        this.stateTransitioner = stateTransitioner;
        this.levelCompleted = false;
        // Assign key bindings to each player
        assignPlayerKeyBindings();
        this.gameLoopManager.addPropertyChangeListener(this::onGameLoopChange);
    }

    /**
     * Initializes the level state by setting up the scene and displaying the stage.
     * If the scene is null, an error message is printed and the method returns early.
     * Otherwise, the scene is set up, the stage is shown, and a message indicating
     * successful initialization is printed. Additionally, a pause overlay is created.
     */
    @Override
    public void initialize() {
        this.scene = level.getScene();
        if (this.scene == null) {
            System.err.println("LevelState: Failed to initialize scene for level " + level.getCurrentLevelNumber());
            return;
        }
        setupScene(this.scene);
        stage.show();
        createPauseOverlay();
    }

    /**
     * Updates the state of the level.
     *
     * @param now The current time in nanoseconds.
     * 
     * If the level is not completed, this method performs the following actions:
     * - Spawns enemy units in the level.
     * - Updates all actors managed by the actor manager.
     * - Removes destroyed actors from the actor manager.
     * - Handles all collisions between actors.
     * - Updates the level view.
     * - Checks if the level is completed.
     */
    public void update(long now) {
        if (!levelCompleted) {
            level.spawnEnemyUnits();
            actorManager.updateAllActors(now);
            actorManager.removeDestroyedActors(); // Move this before collision detection
            collisionManager.handleAllCollisions(actorManager);
            level.updateLevelView();
            checkLevelCompletion();
        }
    }

    /**
     * Handles keyboard input events for the game.
     *
     * @param event the KeyEvent to handle
     *
     * This method processes key press and release events to control the game's state and player movements.
     * - When the game is paused, only the SPACE key can resume the game.
     * - When the game is running:
     *   - Pressing the SPACE key pauses the game.
     *   - Pressing other keys adds them to the activeKeys set for movement.
     *   - Releasing keys removes them from the activeKeys set.
     */
    @Override
    public void handleInput(KeyEvent event) {
        if (gameLoopManager.isPaused()) {
            if (event.getEventType() == KeyEvent.KEY_PRESSED && event.getCode() == KeyCode.SPACE) {
                gameLoopManager.resumeGame();
            }
            return; // Ignore other inputs when paused
        }

        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            if (event.getCode() == KeyCode.SPACE) {
                gameLoopManager.pauseGame();
            } else {
                // Add other keys to activeKeys for movement
                activeKeys.add(event.getCode());
            }
        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            // Remove keys from activeKeys when released
            activeKeys.remove(event.getCode());
        }
    }

    /**
     * Cleans up the current level state by performing necessary cleanup operations.
     * This includes removing health handlers from all players and invoking the
     * cleanup method on the actor manager.
     * 
     * This method is called when the level state needs to be reset or disposed of.
     * It ensures that all resources and handlers associated with the current level
     * are properly released.
     */
    @Override
    public void cleanup() {
        // remove health handlers from all players
        actorManager.cleanup();
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

    /**
     * Retrieves the current scene associated with this level state.
     *
     * @return the current Scene object.
     */
    @Override
    public Scene getScene() {
        return scene;
    }

    /**
     * This method is called when an explosion starts.
     * It sets the state to indicate that an explosion is currently active.
     */
    public void onExplosionStarted() {
        isExplosionActive = true;
    }

    /**
     * This method is called when an explosion has finished.
     * It sets the isExplosionActive flag to false, indicating that the explosion is no longer active.
     */
    public void onExplosionFinished() {
        isExplosionActive = false;
    }

    /**
     * Handles changes in the game loop state.
     * This method is triggered when a property change event occurs, specifically when the "paused" property changes.
     *
     * @param evt the property change event containing the new state of the game loop.
     */
    private void onGameLoopChange(PropertyChangeEvent evt) {
        if ("paused".equals(evt.getPropertyName())) {
            boolean isPaused = (boolean) evt.getNewValue();
            if (isPaused) {
                handlePause();
            } else {
                handleResume();
            }
        }
    }

    /**
     * Adds a PropertyChangeListener to the listener list.
     * The listener is registered for all properties.
     *
     * @param listener the PropertyChangeListener to be added
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    
    /**
     * Removes a PropertyChangeListener from the listener list.
     * This method should be called to unregister a listener that was previously added.
     *
     * @param listener the PropertyChangeListener to be removed
     */
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
    public void onProjectileHitEnemy(UserPlane userPlane, ActiveActor enemy) {
        userPlane.incrementKillCount();
    }
    
    /**
     * Adds a pause button to the scene.
     *
     * @param scene The current scene where the button will be added.
     */
    private void addPauseButton(Scene scene) {
        // Create the pause button with an icon
        Button pauseButton = ButtonManager.createImageButton(buttonImageName, buttonImageWidth, buttonImageHeight);

        // Set the action for the pause button
        pauseButton.setOnAction(e -> gameLoopManager.pauseGame());
        pauseButton.setLayoutX(buttonXPosition); // 10 pixels from the left
        pauseButton.setLayoutY(buttonYPosition); // 10 pixels from the top

        // Add the button to the scene
        Platform.runLater(() ->  actorManager.addUIElement(pauseButton));
    }


    /**
     * Creates a pause overlay for the current level.
     * This method initializes the PauseOverlay object with the game loop manager
     * and state transitioner, and prints a message indicating the creation of the
     * pause overlay for the current level.
     */
    private void createPauseOverlay() {
        pauseOverlay = new PauseScreen(gameLoopManager, stateTransitioner);
    }


    /**
     * Handles the pause action by displaying the pause overlay on the screen.
     * This method ensures that the pause overlay is added to the level's root
     * children if it is not already present. It also requests focus for the
     * pause overlay to ensure it is interactive.
     * The method runs on the JavaFX Application Thread using Platform.runLater.
     */
    public void handlePause() {
        Platform.runLater(() -> {
            if (!level.getRoot().getChildren().contains(pauseOverlay.getOverlay())) {
                level.getRoot().getChildren().add(pauseOverlay.getOverlay());
                pauseOverlay.getOverlay().requestFocus();
            }
        });
    }


    /**
     * Handles the resumption of the game from a paused state.
     * This method removes the pause overlay from the game's root node
     * and requests focus back to the game level.
     */
    public void handleResume() {
        Platform.runLater(() -> {
            level.getRoot().getChildren().remove(pauseOverlay.getOverlay());
            level.getRoot().requestFocus();
        });
    }

    /**
     * Checks the completion status of the current level.
     * <p>
     * This method performs the following checks:
     * <ul>
     *   <li>If all users are destroyed, it cleans up the actor manager and fires a property change event indicating a loss.</li>
     *   <li>If the user has reached the target, it cleans up the actor manager, marks the level as completed, 
     *       calculates the next level number, and fires a property change event indicating the level change.</li>
     * </ul>
     */
    private void checkLevelCompletion() {
        if (allUsersAreDestroyed()) {
            actorManager.cleanup();
            pcs.firePropertyChange("lose", false, true);
        } else if (level.userHasReachedTarget()) {
            actorManager.cleanup();
            levelCompleted = true;
            int nextLevelNumber = level.getCurrentLevelNumber() + 1;
            pcs.firePropertyChange("level", level.getCurrentLevelNumber(), nextLevelNumber);
        }
    }

    /**
     * Checks if all users (players) are destroyed.
     * A user is considered destroyed if their health is less than or equal to 0.
     *
     * @return true if any of the users are destroyed, false otherwise.
     */
    public boolean allUsersAreDestroyed() {
        return actorManager.getPlayers().stream()
                .anyMatch(player -> player.getHealth() <= 0);
    }

    /**
     * Assigns key bindings to players based on their index.
     * 
     * This method retrieves the list of players and the number of players from the actor manager
     * and state transitioner respectively. It then assigns specific key bindings to each player:
     * - Player 1: W, A, S, D for movement
     * - Player 2: Arrow keys for movement
     * 
     * For each player, the method also assigns a movement strategy using the assigned key bindings
     * and a predefined plane speed.
     * 
     * The key bindings and movement strategy are stored in the playerKeyBindingsMap and assigned
     * to the UserPlane object.
     */
    private void assignPlayerKeyBindings() {
        List<UserPlane> players = actorManager.getPlayers();
        int numPlayers = stateTransitioner.getNumberOfPlayers();

        for (int i = 0; i < numPlayers; i++) {
            UserPlane player = players.get(i);
            PlayerKeyBindings bindings;

            if (i == 0) {
                // Player 1: W, A, S, D for movement
                bindings = new PlayerKeyBindings(
                    EnumSet.of(KeyCode.UP),
                    EnumSet.of(KeyCode.DOWN),
                    EnumSet.of(KeyCode.LEFT),
                    EnumSet.of(KeyCode.RIGHT)                
                );
            } else if (i == 1) {
                // Player 2: Arrow keys for movement
                bindings = new PlayerKeyBindings(
                    EnumSet.of(KeyCode.W),
                    EnumSet.of(KeyCode.S),
                    EnumSet.of(KeyCode.A),
                    EnumSet.of(KeyCode.D)                
                );
            } else {
                bindings = null;
            }

            playerKeyBindingsMap.put(player, bindings);

            // Assign MovementStrategy and FiringStrategy to the UserPlane
            int planeSpeed = GameConstant.UserPlane.VELOCITY; 
            player.setMovementStrategy(new UserMovementStrategy(activeKeys, bindings, planeSpeed));
        }
    }
}
