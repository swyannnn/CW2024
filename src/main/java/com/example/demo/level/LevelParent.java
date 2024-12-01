package com.example.demo.level;

import java.util.List;

import com.example.demo.actor.plane.UserPlane;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ActorManager;
import com.example.demo.manager.AudioManager;
import com.example.demo.manager.GameStateManager;
import com.example.demo.ui.LevelView;
import com.example.demo.util.GameConstant;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

/**
 * Abstract base class for game levels.
 * Now streamlined to focus on level-specific logic.
 */
public abstract class LevelParent {
    protected final Group root;
    protected final Scene scene;
    protected ImageView[] backgrounds;
    protected final int playerInitialHealth;

    protected UserPlane user;
    protected LevelView levelView;
    protected Controller controller;

    // Managers are injected to ensure they're properly initialized
    protected final ActorManager actorManager;
    protected final AudioManager audioManager;
    protected final GameStateManager gameStateManager;
    private final double scrollSpeed = 1.0; // Adjust as needed
    /**
     * Constructs a new LevelParent instance.
     *
     * @param controller The Controller instance.
     * @param backgroundImageName The path to the background image.
     * @param playerInitialHealth The initial health of the player.
     */
    public LevelParent(Controller controller, String backgroundImageName, String backgroundMusicName, int playerInitialHealth) {
        this.controller = controller;
        this.playerInitialHealth = playerInitialHealth;
        this.root = new Group();
        this.scene = new Scene(root, GameConstant.GameSettings.SCREEN_WIDTH, GameConstant.GameSettings.SCREEN_HEIGHT);

        // Retrieve singleton Managers
        this.gameStateManager = controller.getGameStateManager();
        this.actorManager = gameStateManager.getActorManager();
        this.audioManager = gameStateManager.getAudioManager();
        // Pass the root to ActorManager
        this.actorManager.updateRoot(this.root);
        // Initialize LevelView
        this.levelView = instantiateLevelView();

        initializeBackground(backgroundImageName);
        initializeBackgroundMusic(backgroundMusicName);
    
        List<UserPlane> players = actorManager.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            levelView.showHeartDisplay(players.get(i), i);
        }
    }    

    /**
     * Initializes the background images for scrolling effect.
     *
     * @param backgroundImageName The path to the background image.
     */
    private void initializeBackground(String backgroundImageName) {
        levelView.initializeBackground(backgroundImageName);
    }

    /**
     * Updates the background positions to create a scrolling effect.
     */
    public void updateBackground() {
        levelView.updateBackground(scrollSpeed);
    }

    private void initializeBackgroundMusic(String backgroundMusicName) {
        audioManager.playMusic(backgroundMusicName);
    }

    public LevelView instantiateLevelView() {
        return new LevelView(this.root);
    }
    
    protected void initializeFriendlyUnits() {
        int numberOfPlayers = gameStateManager.getNumberOfPlayers();
    
        // Initialize player 1
        UserPlane player1 = new UserPlane(playerInitialHealth, controller, 1);
        actorManager.addActor(player1);
        System.out.println("Player 1 position: X=" + player1.getTranslateX() + ", Y=" + player1.getTranslateY());
        player1.addHealthChangeListener(this.levelView); // Register LevelView as listener for health changes
    
        // If two-player mode, initialize player 2
        if (numberOfPlayers == 2) {
            UserPlane player2 = new UserPlane(playerInitialHealth, controller, 2);
            actorManager.addActor(player2);
            System.out.println("Player 2 position: X=" + player2.getTranslateX() + ", Y=" + player2.getTranslateY());
            player2.addHealthChangeListener(this.levelView); // Register LevelView as listener for health changes
        }
    }

    public void updateLevelView() {
        List<UserPlane> players = actorManager.getPlayers();

        if (players.isEmpty()) {
            return; 
        }

        for (int i = 0; i < players.size(); i++) {
            levelView.showHeartDisplay(players.get(i), i);
        }
    }

    public Group getRoot() {
        return this.root;
    } 

    /**
     * Initializes and returns the scene for this level.
     *
     * @return The initialized scene.
     */
    public Scene getScene() {
        return scene;
    }

    // Abstract methods to be implemented by subclasses
    public abstract int getCurrentLevelNumber();

    protected abstract void setCurrentLevelNumber(int levelNumber);

    public abstract boolean userHasReachedTarget();

    public abstract void spawnEnemyUnits();
}
