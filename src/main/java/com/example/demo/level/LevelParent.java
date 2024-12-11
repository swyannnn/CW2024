package com.example.demo.level;

import java.util.List;

import com.example.demo.Controller;
import com.example.demo.actor.ActiveActor;
import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.plane.PlaneFactory;
import com.example.demo.actor.plane.UserPlane;
import com.example.demo.actor.plane.PlaneType;
import com.example.demo.manager.AudioManager;
import com.example.demo.screen.LevelScreen;
import com.example.demo.util.GameConstant;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;


/**
 * The LevelParent class serves as an abstract base class for game levels.
 * It provides common functionality and properties for managing game levels,
 * including the root group, scene, player registration, background music
 * initialization, and level view updates.
 * 
 * Subclasses of LevelParent must implement the abstract methods
 * userHasReachedTarget() and spawnEnemyUnits() to define specific level
 * behavior.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/level/LevelParent.java">Github Source Code</a>
 * @see LevelScreen
 */
public abstract class LevelParent {
    protected final Group root;
    protected final Scene scene;
    protected ImageView[] backgrounds;

    protected UserPlane user;
    protected LevelScreen levelScreen;
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
        this.levelScreen = new LevelScreen(this.root, currentLevelNumber);
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
     * handlers are added to the player planes.
     * 
     * Preconditions:
     * - The planeFactory and actorSpawn objects must be properly initialized.
     * - The levelView must be set to listen for health changes.
     * 
     * Postconditions:
     * - Player 1 is always initialized and spawned.
     * - Player 2 is initialized and spawned if the numberOfPlayers is 2.
     * - Health change handlers are added to the player planes.
     */
    private void registerPlayer() {
        // Initialize player 1
        int playerId1 = 1;
        ActiveActor player1 = planeFactory.createPlane(PlaneType.USER_PLANE, playerId1);
        actorSpawn.addActor(player1);
        ((UserPlane) player1).addHealthChangeHandler(this.levelScreen);
    
        // If double-player mode, initialize player 2
        if (numberOfPlayers == 2) {
            int playerId2 = 2;
            ActiveActor player2 = planeFactory.createPlane(PlaneType.USER_PLANE, playerId2);
            actorSpawn.addActor(player2);
            ((UserPlane) player2).addHealthChangeHandler(this.levelScreen);
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
            levelScreen.showHeartDisplay(players.get(i), i);
        }
        levelScreen.updateBackground();
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
