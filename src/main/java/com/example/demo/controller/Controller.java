package com.example.demo.controller;

import com.example.demo.manager.*;
import com.example.demo.util.GameConstant;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller class manages the primary setup of the game, including initializing
 * various managers, setting up the main scene, and orchestrating interactions
 * between different components.
 */
public class Controller {
    private final Stage stage;
    private final Group rootGroup;
    private final Scene scene;

    // Managers
    private final AudioManager audioManager;
    private final ActorManager actorManager;
    private final CollisionManager collisionManager;
    private final GameLoopManager gameLoopManager;
    private final StateManager stateManager;
    private final InputManager inputManager;

    /**
     * Constructor initializes the Controller with the main stage and sets up the game scene.
     *
     * @param stage The main Stage object used for rendering scenes.
     */
    public Controller(Stage stage) {
        this.stage = stage;
        this.rootGroup = new Group();
        this.scene = new Scene(rootGroup, GameConstant.GameSettings.SCREEN_WIDTH, GameConstant.GameSettings.SCREEN_HEIGHT);
        stage.setScene(scene);
        stage.setTitle(GameConstant.GameSettings.TITLE);
        stage.show();

        // Initialize Managers
        this.audioManager = AudioManager.getInstance();
        this.actorManager = ActorManager.getInstance(rootGroup);
        this.collisionManager = CollisionManager.getInstance();

        // Initialize GameLoopManager first
        this.gameLoopManager = new GameLoopManager();

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
     */
    public void initializeGame() {
        stateManager.goToMainMenu(); // Transition to the main menu
    }

    /**
     * Returns the root Group of the scene, used for adding visual elements.
     *
     * @return The root Group object.
     */
    public Group getRootGroup() {
        return this.rootGroup;
    }

    /**
     * Cleanup method to stop the game loop and cleanup all managers.
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

        // Additional cleanups if necessary
        System.out.println("Controller: Cleanup completed.");
    }
}
