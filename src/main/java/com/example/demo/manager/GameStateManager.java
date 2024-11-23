package com.example.demo.manager;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.planes.UserPlane;
import com.example.demo.controller.Controller;
import com.example.demo.level.LevelParent;
import com.example.demo.listener.CollisionListener;
import com.example.demo.memento.Caretaker;
import com.example.demo.memento.GameSettingsMemento;
import com.example.demo.memento.LevelStateMemento;
import com.example.demo.memento.PlayerStateMemento;
import com.example.demo.state.GameState;
import com.example.demo.state.GameStateFactory;
import com.example.demo.state.LevelState;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * GameStateManager handles the transitions and management of different game states.
 * It uses the Singleton pattern to ensure only one instance is used throughout the application.
 */
public class GameStateManager implements PropertyChangeListener, CollisionListener {
    private static GameStateManager instance;
    private GameState currentState;
    private final GameStateFactory stateFactory;
    private final Caretaker caretaker;
    private AnimationTimer gameLoop;

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
    private GameStateManager(Stage stage, Controller controller) {
        this.stateFactory = new GameStateFactory(stage, controller, this); // Controller will be set later
        this.caretaker = new Caretaker();
    
        // Initialize other managers
        this.audioManager = AudioManager.getInstance();
        this.imageManager = ImageManager.getInstance();
        this.collisionManager = CollisionManager.getInstance();
    
        // Initialize ActorManager using the Controller's root group
        Group root = controller.getRootGroup();
        this.actorManager = ActorManager.getInstance(root);
        this.stateFactory.setActorManager(actorManager);
    
        setupGameLoop();
    
        // Set the collision listener after initialization
        collisionManager.setCollisionListener(this);
    }
    

    /**
     * Retrieves the singleton instance of GameStateManager, creating it if necessary.
     *
     * @param stage The main Stage object used for rendering scenes.
     * @return The singleton instance of GameStateManager.
     */
    public static synchronized GameStateManager getInstance(Stage stage, Controller controller) {
        if (instance == null) {
            instance = new GameStateManager(stage, controller);
            System.out.println("GameStateManager initialized.");
        }
        return instance;
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
        System.out.println("Transitioned to new state: " + newState.getClass().getSimpleName());
    }

    /**
     * Transitions to the main menu state.
     */
    public void goToMainMenu() {
        setState(stateFactory.createMainMenuState());
    }

    /**
     * Sets up the game loop to continuously update and render the current game state.
     */
    private void setupGameLoop() {
        gameLoop = new AnimationTimer() {
            private long lastUpdate = System.nanoTime();
            private final double nsPerUpdate = 1e9 / 60.0; // 60 updates per second

            @Override
            public void handle(long now) {
                while (now - lastUpdate >= nsPerUpdate) {
                    update();
                    lastUpdate += nsPerUpdate;
                }
                render();
            }
        };
    }

    public void startGame() {
        goToLevel(1);
    }

    /**
     * Transitions to a specific level based on the level number.
     *
     * @param levelNumber The number of the level to transition to.
     */
    public void goToLevel(int levelNumber) {
        gameLoop.start();
        setState(stateFactory.createLevelState(levelNumber));
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
                // Save player states as a list of mementos
                List<PlayerStateMemento> playerMementos = currentLevel.createPlayerMementos();
                caretaker.saveMemento("PlayerState", playerMementos);

                // Save level state
                LevelStateMemento levelMemento = currentLevel.createLevelMemento();
                caretaker.saveMemento("LevelState", levelMemento);

                // Save game settings
                GameSettingsMemento settingsMemento = GameSettingsMemento.createFromSettings(audioManager, imageManager);
                caretaker.saveMemento("GameSettings", settingsMemento);

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
                List<PlayerStateMemento> playerMementos = (List<PlayerStateMemento>) caretaker.getMemento("PlayerState");
                LevelStateMemento levelMemento = (LevelStateMemento) caretaker.getMemento("LevelState");
                GameSettingsMemento settingsMemento = (GameSettingsMemento) caretaker.getMemento("GameSettings");

                if (playerMementos != null) {
                    currentLevel.restorePlayerStates(playerMementos);
                } else {
                    System.err.println("Player mementos not found. Cannot restore player states.");
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
        collisionManager.handleUserProjectileEnemyCollisions(actorManager.getUserProjectiles(), actorManager.getEnemyUnits());
        collisionManager.handlePlayerEnemyProjectileCollisions(actorManager.getEnemyProjectiles(), actorManager.getPlayers());
        collisionManager.handleUnitCollisions(actorManager.getFriendlyUnits(), actorManager.getEnemyUnits());
        collisionManager.handleEnemyPlayerCollisions(actorManager.getEnemyUnits(), actorManager.getPlayers());
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
    @Override
    public void onProjectileHitEnemy(UserPlane userPlane, ActiveActorDestructible enemy) {
        userPlane.incrementKillCount();
        System.out.println("Kill count for user updated: " + userPlane.getNumberOfKills());
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
