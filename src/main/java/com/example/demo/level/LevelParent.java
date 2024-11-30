package com.example.demo.level;

import java.util.List;

import com.example.demo.actor.plane.UserPlane;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ActorManager;
import com.example.demo.manager.AudioManager;
import com.example.demo.manager.GameStateManager;
import com.example.demo.manager.ImageManager;
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
    
        for (UserPlane player : actorManager.getPlayers()) {
            levelView.showHeartDisplay(player);
        }
    }    

    /**
     * Initializes the background images for scrolling effect.
     *
     * @param backgroundImageName The path to the background image.
     */
    private void initializeBackground(String backgroundImageName) {
        ImageView img1 = new ImageView(ImageManager.getInstance().getImage(backgroundImageName));
        ImageView img2 = new ImageView(ImageManager.getInstance().getImage(backgroundImageName));

        img1.setFitHeight(GameConstant.GameSettings.SCREEN_HEIGHT);
        img1.setFitWidth(GameConstant.GameSettings.SCREEN_WIDTH);
        img1.setOpacity(0.7);
        img2.setFitHeight(GameConstant.GameSettings.SCREEN_HEIGHT);
        img2.setFitWidth(GameConstant.GameSettings.SCREEN_WIDTH);
        img2.setOpacity(0.7);
        // Position the second image right after the first
        img1.setTranslateX(0);
        img2.setTranslateX(GameConstant.GameSettings.SCREEN_WIDTH);

        backgrounds = new ImageView[] { img1, img2 };

        // Add both images to the root
        root.getChildren().addAll(backgrounds);
    }

    /**
     * Updates the background positions to create a scrolling effect.
     */
    public void updateBackground() {
        for (ImageView img : backgrounds) {
            img.setTranslateX(img.getTranslateX() - scrollSpeed);

            // If the image has moved completely out of view, reset its position
            if (img.getTranslateX() + img.getFitWidth() <= 0) {
                img.setTranslateX(GameConstant.GameSettings.SCREEN_WIDTH - scrollSpeed);
            }
        }
    }

    private void initializeBackgroundMusic(String backgroundMusicName) {
        audioManager.playMusic(backgroundMusicName);
    }

    public LevelView instantiateLevelView() {
        return new LevelView(controller.getGameStateManager().getActorManager(), playerInitialHealth);
    }

    protected void initializeFriendlyUnits() {
        UserPlane player = new UserPlane(playerInitialHealth, controller);
        actorManager.addActor(player);
        System.out.println("Player position: X=" + player.getTranslateX() + ", Y=" + player.getTranslateY());

        levelView = instantiateLevelView(); // Instantiate LevelView before adding listener
        player.addHealthChangeListener(levelView); // Register LevelView as listener for health changes
    }

    public void updateLevelView() {
        List<UserPlane> players = actorManager.getPlayers();

        if (players.isEmpty()) {
            return; 
        }

        for (UserPlane player : players) {
            levelView.showHeartDisplay(player);
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

    public abstract boolean userHasReachedKillTarget();

    // protected abstract void initializeFriendlyUnits();

    public abstract void spawnEnemyUnits();
}
