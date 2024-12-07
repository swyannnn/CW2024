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
 * It manages the common functionalities and properties shared across various levels.
 * 
 * <p>Key responsibilities include:</p>
 * <ul>
 *   <li>Initializing the scene and root group for the level.</li>
 *   <li>Managing the player's initial health and the number of players.</li>
 *   <li>Spawning friendly units (players) and initializing their positions.</li>
 *   <li>Handling background music initialization for the level.</li>
 *   <li>Updating the level view, including player health display and background updates.</li>
 * </ul>
 * 
 * <p>Subclasses are required to implement the abstract methods to define specific behaviors
 * for reaching targets and spawning enemy units.</p>
 * 
 * @param currentLevelNumber The current level number.
 * @param numberOfPlayers The number of players in the level.
 * @param playerInitialHealth The initial health of the player.
 * @param actorSpawner The ActorSpawner instance for managing actor spawning.
 * @param audioManager The AudioManager instance for managing audio playback.
 */
public abstract class LevelParent {
    protected final Group root;
    protected final Scene scene;
    protected ImageView[] backgrounds;

    protected UserPlane user;
    protected LevelView levelView;
    protected Controller controller;

    protected final ActorSpawner actorSpawn;
    protected final AudioManager audioManager;
    protected final PlaneFactory planeFactory;
    private int currentLevelNumber;
    private int numberOfPlayers;

    /**
     * Constructs a new LevelParent instance.
     *
     * @param currentLevelNumber the current level number
     * @param numberOfPlayers the number of players
     * @param actorSpawner the actor spawner used to spawn actors in the game
     * @param audioManager the audio manager used to manage game audio
     */
    public LevelParent(int currentLevelNumber, int numberOfPlayers, ActorSpawner actorSpawner, AudioManager audioManager) {
        this.currentLevelNumber = currentLevelNumber;
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
        // Initialize Player
        registerPlayer();
        // Initialize background music
        initializeBackgroundMusic();
    }

    /**
     * Initializes and plays the background music for the current level.
     * The background music is determined based on the current level number.
     * It retrieves the background music name using the GameConstant.LevelBGM class
     * and plays it using the audioManager.
     */
    private void initializeBackgroundMusic() {
        String backgroundMusicName = GameConstant.LevelBGM.getBGMForLevel(currentLevelNumber);
        audioManager.playMusic(backgroundMusicName);
    }
    
    /**
     * Initializes player(s) in the game level.
     * 
     * This method creates and spawns the player-controlled planes. It initializes
     * player 1 and, if the game is in double-player mode, also initializes player 2.
     * The positions of the players are printed to the console, and health change
     * listeners are added to the player planes.
     * 
     * Preconditions:
     * - The planeFactory and actorSpawn objects must be properly initialized.
     * - The levelView must be set to listen for health changes.
     * 
     * Postconditions:
     * - Player 1 is always initialized and spawned.
     * - Player 2 is initialized and spawned if the numberOfPlayers is 2.
     * - Health change listeners are added to the player planes.
     */
    private void registerPlayer() {
        // Initialize player 1
        int playerId1 = 1;
        ActiveActor player1 = planeFactory.createPlane(PlaneType.USER_PLANE, playerId1);
        actorSpawn.spawnActor(player1);
        System.out.println("Player 1 position: X=" + player1.getTranslateX() + ", Y=" + player1.getTranslateY());
        ((UserPlane) player1).addHealthChangeListener(this.levelView);
    
        // If double-player mode, initialize player 2
        if (numberOfPlayers == 2) {
            int playerId2 = 2;
            ActiveActor player2 = planeFactory.createPlane(PlaneType.USER_PLANE, playerId2);
            actorSpawn.spawnActor(player2);
            System.out.println("Player 2 position: X=" + player2.getTranslateX() + ", Y=" + player2.getTranslateY());
            ((UserPlane) player2).addHealthChangeListener(this.levelView);
        }
    }

    /**
     * Updates the level view by displaying the heart display for each player
     * and updating the background.
     * <p>
     * This method retrieves the list of players from the actorSpawn object.
     * Then, it iterates through the list of players and displays the heart display
     * for each player using the levelView object. Finally, it updates the
     * background of the level view.
     */
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

    /**
     * Returns the root group of the level.
     *
     * @return the root group of the level
     */
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

    /**
     * Retrieves the current level number.
     *
     * @return the current level number.
     */
    public int getCurrentLevelNumber() {
        return currentLevelNumber;
    }

    /**
     * Determines if the user has reached the target to advance to the next level.
     * 
     * This method should be implemented by subclasses to define the specific logic
     * for reaching the target in the level.
     */
    public abstract boolean userHasReachedTarget();

    /**
     * Spawns enemy units in the game level.
     * This method should be implemented by subclasses to define the specific logic
     * for spawning enemy units.
     */
    public abstract void spawnEnemyUnits();
}
