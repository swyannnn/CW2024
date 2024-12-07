package com.example.demo.level;

import java.util.List;

import com.example.demo.actor.ActiveActor;
import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.plane.PlaneFactory;
import com.example.demo.actor.plane.UserPlane;
import com.example.demo.actor.plane.PlaneFactory.PlaneType;
import com.example.demo.controller.Controller;
import com.example.demo.manager.AudioManager;
import com.example.demo.ui.LevelView;
import com.example.demo.util.GameConstant;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;


/**
 * The LevelParent class serves as an abstract base class for different levels in the game.
 * It provides common functionality and properties that are shared across various levels.
 * 
 * <p>This class handles the initialization of the game scene, background, music, and player units.
 * It also provides methods to update the background for a scrolling effect and to update the level view.</p>
 * 
 * <p>Subclasses are required to implement the abstract methods {@link #userHasReachedTarget()} and {@link #spawnEnemyUnits()}.</p>
 * 
 * <p>Key properties include:</p>
 * <ul>
 *   <li>{@code root}: The root group for the scene graph.</li>
 *   <li>{@code scene}: The scene associated with this level.</li>
 *   <li>{@code backgrounds}: An array of ImageView objects for the background images.</li>
 *   <li>{@code playerInitialHealth}: The initial health of the player.</li>
 *   <li>{@code user}: The user-controlled plane.</li>
 *   <li>{@code levelView}: The view component for the level.</li>
 *   <li>{@code controller}: The controller instance managing the game logic.</li>
 *   <li>{@code actorSpawn}: The manager responsible for spawning actors in the game.</li>
 *   <li>{@code audioManager}: The manager responsible for handling audio playback.</li>
 *   <li>{@code planeFactory}: The factory for creating plane objects.</li>
 *   <li>{@code scrollSpeed}: The speed at which the background scrolls.</li>
 *   <li>{@code currentLevelNumber}: The current level number.</li>
 *   <li>{@code numberOfPlayers}: The number of players in the game.</li>
 * </ul>
 * 
 * <p>Key methods include:</p>
 * <ul>
 *   <li>{@link #LevelParent(int, int, String, String, int, ActorSpawner, AudioManager)}: Constructor to initialize the level.</li>
 *   <li>{@link #initializeBackground(String)}: Initializes the background images for scrolling effect.</li>
 *   <li>{@link #updateBackground()}: Updates the background positions to create a scrolling effect.</li>
 *   <li>{@link #initializeBackgroundMusic(String)}: Initializes and plays the background music.</li>
 *   <li>{@link #instantiateLevelView()}: Instantiates the LevelView object.</li>
 *   <li>{@link #initializeFriendlyUnits()}: Initializes the friendly units (players) in the game.</li>
 *   <li>{@link #updateLevelView()}: Updates the level view with the current state of the players.</li>
 *   <li>{@link #getRoot()}: Returns the root group for the scene graph.</li>
 *   <li>{@link #getScene()}: Returns the initialized scene for this level.</li>
 *   <li>{@link #getCurrentLevelNumber()}: Returns the current level number.</li>
 *   <li>{@link #setCurrentLevelNumber(int)}: Sets the current level number.</li>
 * </ul>
 * 
 * @param currentLevelNumber The current level number.
 * @param numberOfPlayers The number of players in the game.
 * @param backgroundImageName The path to the background image.
 * @param backgroundMusicName The path to the background music.
 * @param playerInitialHealth The initial health of the player.
 * @param actorSpawner The manager responsible for spawning actors.
 * @param audioManager The manager responsible for handling audio playback.
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
    // private final double scrollSpeed = 1.0; 
    private int currentLevelNumber;
    private int numberOfPlayers;
    /**
     * Constructs a new LevelParent instance.
     *
     * @param controller The Controller instance.
     * @param backgroundImageName The path to the background image.
     * @param playerInitialHealth The initial health of the player.
     */
    public LevelParent(int currentLevelNumber, int numberOfPlayers, int playerInitialHealth, ActorSpawner actorSpawner, AudioManager audioManager) {
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
        this.levelView = new LevelView(this.root, currentLevelNumber);
        // Initialize background music
        initializeBackgroundMusic();
    }

    private void initializeBackgroundMusic() {
        String backgroundMusicName = GameConstant.LevelBGM.getBGMForLevel(currentLevelNumber);
        audioManager.playMusic(backgroundMusicName);
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
        levelView.updateBackground();
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
