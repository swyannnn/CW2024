package com.example.demo.manager;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.plane.UserPlane;
import com.example.demo.controller.Controller;
import com.example.demo.listener.CollisionListener;
import com.example.demo.state.GameState;
import com.example.demo.state.GameStateFactory;
import com.example.demo.state.LevelState;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * GameStateManager handles the transitions and management of different game states.
 * It uses the Singleton pattern to ensure only one instance is used throughout the application.
 */
public class GameStateManager implements PropertyChangeListener {
    private static GameStateManager instance;
    private GameState currentState;
    private final GameStateFactory stateFactory;
    private AnimationTimer gameLoop;

    // Managers
    private final AudioManager audioManager;
    private final ImageManager imageManager;
    private final ActorManager actorManager;
    private final CollisionManager collisionManager;

    // Pause control
    private final BooleanProperty isPaused = new SimpleBooleanProperty(false);

    /**
     * Private constructor to enforce the Singleton pattern.
     *
     * @param stage      The main Stage object used for rendering scenes.
     * @param controller The Controller handling game logic.
     */
    private GameStateManager(Stage stage, Controller controller) {
        // Initialize managers
        this.audioManager = AudioManager.getInstance();
        this.imageManager = ImageManager.getInstance();
        this.collisionManager = CollisionManager.getInstance();

        // Initialize ActorManager using the Controller's root group
        Group root = controller.getRootGroup();
        this.actorManager = ActorManager.getInstance(root);

        // Initialize GameStateFactory
        this.stateFactory = new GameStateFactory(stage, controller, this);

        // Setup game loop
        setupGameLoop();

        System.out.println("GameStateManager initialized.");
    }

    /**
     * Retrieves the singleton instance of GameStateManager, creating it if necessary.
     *
     * @param stage      The main Stage object used for rendering scenes.
     * @param controller The Controller handling game logic.
     * @return The singleton instance of GameStateManager.
     */
    public static synchronized GameStateManager getInstance(Stage stage, Controller controller) {
        if (instance == null) {
            instance = new GameStateManager(stage, controller);
        }
        return instance;
    }

    /**
     * Sets up the game loop to continuously update and render the current game state.
     * The loop runs via an AnimationTimer and is controlled by the isPaused flag.
     */
    private void setupGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!isPaused.get() && currentState != null) {
                    update(now);
                    render();
                }
            }
        };
        gameLoop.start();
        System.out.println("Game loop started.");
    }

    /**
     * Transitions to the main menu state.
     */
    public void goToMainMenu() {
        actorManager.cleanup();
        setState(stateFactory.createMainMenuState());
    }

    /**
     * Starts the game by transitioning to the first level.
     */
    public void startGame() {
        System.out.println("GameStateManager: Starting game.");
        goToLevel(1);
    }

    /**
     * Transitions to a specific level based on the level number.
     *
     * @param levelNumber The number of the level to transition to.
     */
    public void goToLevel(int levelNumber) {
        setState(stateFactory.createLevelState(levelNumber));
    }

    /**
     * Transitions to a win state.
     */
    public void goToWinState() {
        setState(stateFactory.createWinState());
    }

    /**
     * Transitions to a lose state.
     */
    public void goToLoseState() {
        setState(stateFactory.createLoseState());
    }

    public void setState(GameState newState) {
        if (currentState != null) {
            currentState.cleanup();
            System.out.println("GameStateManager: Cleaned up previous state: " + currentState.getClass().getSimpleName());
            
            // Unregister as listener if the previous state was LevelState
            if (currentState instanceof LevelState) {
                ((LevelState) currentState).removePropertyChangeListener(this);
            }
        }
        currentState = newState;
        if (currentState != null) {
            // Register as listener if the new state is LevelState
            if (newState instanceof LevelState) {
                ((LevelState) newState).addPropertyChangeListener(this);
            }
            currentState.initialize();
            setupInputHandlers();
            System.out.println("GameStateManager: Initialized new state: " + currentState.getClass().getSimpleName());
        }

        // Ensure the game is not paused when setting a new state
        if (isPaused.get()) {
            isPaused.set(false);
        }
    }


    /**
     * Updates the current game state with the current timestamp.
     *
     * @param now The current timestamp in nanoseconds.
     */
    public void update(long now) {
        if (currentState != null) {
            currentState.update(now);
        }
    }

    /**
     * Handles rendering of the current game state.
     */
    public void render() {
        if (currentState != null) {
            currentState.render();
        }
    }

    private void setupInputHandlers() {
        Scene scene = currentState.getScene();
        if (scene != null) {
            scene.setOnKeyPressed(this::handleInput);
            scene.setOnKeyReleased(this::handleInput);
        }
    }
    

    /**
     * Handles input events and delegates them to the current game state.
     *
     * @param event The KeyEvent to be processed.
     */
    public void handleInput(KeyEvent event) {
        if (currentState != null) {
            currentState.handleInput(event);
        }
    }

    /**
     * Cleans up resources when the game is stopped.
     */
    public void cleanup() {
        audioManager.stopMusic();
        if (currentState != null) {
            currentState.cleanup();
        }
        gameLoop.stop();
        System.out.println("GameStateManager: Cleanup completed.");
    }

    // Getters for other managers
    public AudioManager getAudioManager() {
        return audioManager;
    }

    public ImageManager getImageManager() {
        return imageManager;
    }

    public ActorManager getActorManager() {
        return actorManager;
    }

    public CollisionManager getCollisionManager() {
        return collisionManager;
    }

    /**
     * Handles property change events from LevelParent.
     *
     * @param evt The PropertyChangeEvent.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "win":
                goToWinState();
                break;
            case "lose":
                goToLoseState();
                break;
            case "level":
                int nextLevelNumber = (int) evt.getNewValue();
                goToLevel(nextLevelNumber);
                break;
            default:
                break;
        }
    }

    /**
     * Pauses the game by setting the isPaused flag to true.
     */
    public void pauseGame() {
        if (!isPaused.get()) {
            isPaused.set(true);
            if (currentState != null) {
                currentState.handlePause();
            }
            audioManager.pauseMusic(); // Assuming AudioManager has pauseMusic()
            System.out.println("GameStateManager: Game paused.");
        }
    }

    /**
     * Resumes the game by setting the isPaused flag to false.
     */
    public void resumeGame() {
        if (isPaused.get()) {
            isPaused.set(false);
            if (currentState != null) {
                currentState.handleResume();
            }
            audioManager.resumeMusic(); // Assuming AudioManager has resumeMusic()
            System.out.println("GameStateManager: Game resumed.");
        }
    }

    public Boolean isPaused() {
        return isPaused.get();
    }
}
