package com.example.demo.level;

import java.util.List;

import com.example.demo.actor.ActiveActor;
import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.PlaneFactory;
import com.example.demo.actor.PlaneFactory.PlaneType;
import com.example.demo.actor.plane.UserPlane;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ActorManager;
import com.example.demo.manager.AudioManager;
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
    protected final ActorSpawner actorSpawn;
    protected final AudioManager audioManager;
    protected final PlaneFactory planeFactory;
    private final double scrollSpeed = 1.0; 
    private int currentLevelNumber;
    private int numberOfPlayers;
    /**
     * Constructs a new LevelParent instance.
     *
     * @param controller The Controller instance.
     * @param backgroundImageName The path to the background image.
     * @param playerInitialHealth The initial health of the player.
     */
    public LevelParent(int currentLevelNumber, int numberOfPlayers, String backgroundImageName, String backgroundMusicName, int playerInitialHealth, ActorSpawner actorSpawner, AudioManager audioManager) {
        setCurrentLevelNumber(currentLevelNumber);
        this.playerInitialHealth = playerInitialHealth;
        this.numberOfPlayers = numberOfPlayers;
        this.root = new Group();
        this.scene = new Scene(root, GameConstant.GameSettings.SCREEN_WIDTH, GameConstant.GameSettings.SCREEN_HEIGHT);
        this.planeFactory = new PlaneFactory(actorSpawner);
        this.actorSpawn = actorSpawner;
        this.audioManager = audioManager;

        // Pass the root to ActorManager
        actorSpawner.updateRoot(this.root);
        // Initialize LevelView
        this.levelView = instantiateLevelView();
        levelView.showInstructions(currentLevelNumber);

        initializeBackground(backgroundImageName);
        initializeBackgroundMusic(backgroundMusicName);
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
        // Initialize player 1
        int playerId1 = 1;
        ActiveActor player1 = planeFactory.createPlane(PlaneType.USER_PLANE, playerId1);
        actorSpawn.spawnActor(player1);
        System.out.println("Player 1 position: X=" + player1.getTranslateX() + ", Y=" + player1.getTranslateY());
        ((UserPlane) player1).addHealthChangeListener(this.levelView);
    
        // If two-player mode, initialize player 2
        if (numberOfPlayers == 2) {
            int playerId2 = 2;
            ActiveActor player2 = planeFactory.createPlane(PlaneType.USER_PLANE, playerId2);
            actorSpawn.spawnActor(player2);
            System.out.println("Player 2 position: X=" + player2.getTranslateX() + ", Y=" + player2.getTranslateY());
            ((UserPlane) player2).addHealthChangeListener(this.levelView);
        }
    }

    public void updateLevelView() {
        List<UserPlane> players = actorSpawn.getPlayers();

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

    public int getCurrentLevelNumber() {
        return currentLevelNumber;
    }

    public void setCurrentLevelNumber(int levelNumber) {
        this.currentLevelNumber = levelNumber;
    }

    // Abstract methods to be implemented by subclasses
    public abstract boolean userHasReachedTarget();

    public abstract void spawnEnemyUnits();
}
