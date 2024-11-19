package com.example.demo.manager;

import com.example.demo.controller.Controller;
import com.example.demo.level.LevelParent;
import com.example.demo.memento.Caretaker;
import com.example.demo.memento.GameSettingsMemento;
import com.example.demo.memento.LevelStateMemento;
import com.example.demo.memento.PlayerStateMemento;
import com.example.demo.state.GameState;
import com.example.demo.state.GameStateFactory;
import com.example.demo.state.LevelState;
import javafx.scene.Group;
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
    private final Caretaker caretaker;
    private Controller controller;

    // Managers
    private final AudioManager audioManager;
    private final ImageManager imageManager;
    private ActorManager actorManager; // Made non-final to allow initialization after Controller is set
    private final CollisionManager collisionManager;

    /**
     * Private constructor to enforce the Singleton pattern.
     *
     * @param stage The main Stage object used for rendering scenes.
     */
    private GameStateManager(Stage stage) {
        this.stateFactory = new GameStateFactory(stage, controller, this); // Controller will be set later
        this.caretaker = new Caretaker();

        // Initialize other managers
        this.audioManager = AudioManager.getInstance();
        this.imageManager = ImageManager.getInstance();
        this.collisionManager = CollisionManager.getInstance();

        // ActorManager is initialized after Controller is set
        this.actorManager = null; // Initialize later
    }

    /**
     * Retrieves the singleton instance of GameStateManager, creating it if necessary.
     *
     * @param stage The main Stage object used for rendering scenes.
     * @return The singleton instance of GameStateManager.
     */
    public static synchronized GameStateManager getInstance(Stage stage) {
        if (instance == null) {
            instance = new GameStateManager(stage);
            System.out.println("GameStateManager initialized.");
        }
        return instance;
    }

    /**
     * Sets the Controller instance. This should only be called once to avoid reassignment issues.
     *
     * @param controller The Controller instance managing game flow.
     */
    public void setController(Controller controller) {
        if (this.controller == null) {
            this.controller = controller;
            this.stateFactory.setController(controller); // Update the factory with the controller

            // Initialize ActorManager now that Controller is set
            Group root = controller.getRootGroup();
            this.actorManager = ActorManager.getInstance(root);
            this.stateFactory.setActorManager(actorManager); // Pass ActorManager to the factory if needed

            // Now you can initialize the game or transition to the main menu
        } else {
            throw new IllegalStateException("Controller has already been set and cannot be reassigned.");
        }
    }

    /**
     * Sets the current game state, performing any necessary cleanup of the previous state.
     *
     * @param newState The new GameState to transition to.
     */
    public void setState(GameState newState) {
        if (currentState != null) {
            currentState.cleanup();
        }
        currentState = newState;
        currentState.initialize();
    }

    /**
     * Transitions to the main menu state.
     */
    public void goToMainMenu() {
        setState(stateFactory.createMainMenuState());
    }

    /**
     * Transitions to a specific level based on the level number.
     *
     * @param levelNumber The number of the level to transition to.
     */
    public void goToLevel(int levelNumber) {
        GameState levelState = stateFactory.createLevelState(levelNumber);
        if (levelState != null) {
            setState(levelState);
        } else {
            System.err.println("Failed to transition to Level " + levelNumber);
        }
    }

    /**
     * Transitions to the win state.
     */
    public void goToWinState() {
        setState(stateFactory.createWinState());
    }

    /**
     * Transitions to the lose state.
     */
    public void goToLoseState() {
        setState(stateFactory.createLoseState());
    }

    /**
     * Updates the current game state and handles actor updates and collisions.
     */
    public void update() {
        if (currentState != null) {
            currentState.update();
        }

        // Update all actors and handle collisions
        if (actorManager != null) {

            actorManager.updateAllActors();
            handleCollisions();
            actorManager.removeDestroyedActors();
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
     * Saves the current game state using mementos.
     */
    public void saveGameState() {
        if (currentState instanceof LevelState) {
            LevelParent currentLevel = ((LevelState) currentState).getLevel();
            if (currentLevel != null) {
                caretaker.saveMemento("PlayerState", currentLevel.createPlayerMemento());
                caretaker.saveMemento("LevelState", currentLevel.createLevelMemento());
                caretaker.saveMemento("GameSettings", GameSettingsMemento.createFromSettings(audioManager, imageManager));
                System.out.println("Game state saved.");
            } else {
                System.out.println("No current level to save.");
            }
        } else {
            System.out.println("Current state is not a LevelState.");
        }
    }

    /**
     * Loads the previously saved game state using mementos.
     */
    public void loadGameState() {
        if (currentState instanceof LevelState) {
            LevelParent currentLevel = ((LevelState) currentState).getLevel();
            if (currentLevel != null) {
                PlayerStateMemento playerMemento = (PlayerStateMemento) caretaker.getMemento("PlayerState");
                LevelStateMemento levelMemento = (LevelStateMemento) caretaker.getMemento("LevelState");
                GameSettingsMemento settingsMemento = (GameSettingsMemento) caretaker.getMemento("GameSettings");

                if (playerMemento != null) {
                    currentLevel.restorePlayerState(playerMemento);
                }
                if (levelMemento != null) {
                    currentLevel.restoreLevelState(levelMemento);
                }
                if (settingsMemento != null) {
                    settingsMemento.applySettings(audioManager, imageManager);
                }
                System.out.println("Game state loaded.");
            } else {
                System.out.println("No current level to load.");
            }
        } else {
            System.out.println("No saved state to load or current state is not a LevelState.");
        }
    }

    /**
     * Handles all collision detections.
     */
    private void handleCollisions() {
        collisionManager.handleProjectileCollisions(actorManager.getUserProjectiles(), actorManager.getEnemyUnits());
        collisionManager.handleEnemyProjectileCollisions(actorManager.getEnemyProjectiles(), actorManager.getFriendlyUnits());
        collisionManager.handleUnitCollisions(actorManager.getFriendlyUnits(), actorManager.getEnemyUnits());
    }

    /**
     * Gets the current level.
     *
     * @return The current LevelParent object, or null if the current state is not a LevelState.
     */
    public LevelParent getCurrentLevel() {
        if (currentState instanceof LevelState) {
            return ((LevelState) currentState).getLevel();
        }
        return null;
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
     * Cleans up resources when the game is stopped.
     */
    public void cleanup() {
        audioManager.stopMusic();
        if (currentState != null) {
            currentState.cleanup();
        }
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
                handleWin();
                break;
            case "lose":
                handleLose();
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
     * Handles the win state transition.
     */
    private void handleWin() {
        System.out.println("Player has won the level!");
        // Transition to a WinState or next level
        setState(stateFactory.createWinState());
    }

    /**
     * Handles the lose state transition.
     */
    private void handleLose() {
        System.out.println("Player has lost the level!");
        // Transition to a LoseState or restart the level
        setState(stateFactory.createLoseState());
    }
}
