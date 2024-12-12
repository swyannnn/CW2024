package com.example.demo;

import com.example.demo.manager.*;
import com.example.demo.util.GameConstant;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller class responsible for initializing and managing the main components of the game.
 * It sets up the scene, initializes various managers, and starts the game loop.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/Controller.java">Github Source Code</a>
 */
public class Controller {
    private final Group rootGroup;
    private final Scene scene;

    private final AudioManager audioManager;
    private final ActorManager actorManager;
    private final CollisionManager collisionManager;
    private final GameLoopManager gameLoopManager;
    private final StateManager stateManager;
    private final InputManager inputManager;

    /**
     * Controller class responsible for initializing and managing the main components of the game.
     * It sets up the scene, initializes various managers, and starts the game loop.
     *
     * @param stage The primary stage for this application.
     */
    public Controller(Stage stage) {
        this.rootGroup = new Group();
        this.scene = new Scene(rootGroup, GameConstant.GameSettings.SCREEN_WIDTH, GameConstant.GameSettings.SCREEN_HEIGHT);
        stage.setScene(scene);
        stage.setTitle(GameConstant.GameSettings.TITLE);
        stage.show();

        // Initialize Managers
        this.audioManager = AudioManager.getInstance();
        this.actorManager = ActorManager.getInstance(rootGroup);
        this.collisionManager = CollisionManager.getInstance();
        this.gameLoopManager = GameLoopManager.getInstance();

        // Initialize StateManager
        this.stateManager = new StateManager(
            stage,
            actorManager,
            collisionManager,
            gameLoopManager,
            audioManager,
            1 // Initial number of players
        );

        // Set the GameLoopUpdater in GameLoopManager
        this.gameLoopManager.setUpdater(
            now -> {
                if (stateManager.getCurrentState() != null) {
                    stateManager.getCurrentState().update(now);
                }
            }
        );

        // Initialize InputManager
        this.inputManager = new InputManager(
            scene,
            event -> {
                // Delegate input to the current state via StateManager
                if (stateManager.getCurrentState() != null) {
                    stateManager.getCurrentState().handleInput(event);
                }
            }
        );

        // Start the game loop
        this.gameLoopManager.startLoop();
    }


    /**
     * Initializes the game by transitioning to the main menu.
     * This method sets the initial state of the game by invoking the state manager
     * to navigate to the main menu screen.
     */
    public void initializeGame() {
        stateManager.goToMainMenu(); // Transition to the main menu
    }


    /**
     * Performs cleanup operations for various managers and resources.
     * <p>
     * This method stops the game loop, cleans up the state manager, stops the audio manager's music,
     * cleans up the actor manager, and performs cleanup for the image manager.
     * </p>
     * <p>
     * The following resources are cleaned up:
     * <ul>
     *   <li>Game loop manager</li>
     *   <li>State manager</li>
     *   <li>Audio manager</li>
     *   <li>Actor manager</li>
     *   <li>Image manager</li>
     * </ul>
     */
    public void cleanup() {
        // Stop the game loop
        if (gameLoopManager != null) {
            gameLoopManager.stopLoop();
        }

        // Cleanup StateManager
        if (stateManager != null) {
            stateManager.cleanup();
        }

        // Cleanup AudioManager
        if (audioManager != null) {
            audioManager.stopMusic();
        }

        if (actorManager != null) {
            actorManager.cleanup();
        }

        ImageManager.cleanup();
    }
}
