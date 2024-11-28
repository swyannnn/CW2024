package com.example.demo.level;

import java.util.List;

import com.example.demo.actor.plane.UserPlane;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ActorManager;
import com.example.demo.manager.GameStateManager;
import com.example.demo.manager.ImageManager;
import com.example.demo.ui.LevelView001;
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
    protected final ImageView background;

    protected UserPlane user;
    protected LevelView001 levelView;
    protected Controller controller;

    // Managers are injected to ensure they're properly initialized
    protected final ActorManager actorManager;
    protected final GameStateManager gameStateManager;
    /**
     * Constructs a new LevelParent instance.
     *
     * @param controller The Controller instance.
     * @param backgroundImageName The path to the background image.
     * @param playerInitialHealth The initial health of the player.
     */
    public LevelParent(Controller controller, String backgroundImageName, int playerInitialHealth) {

        if (controller == null) {
            System.err.println("Controller is null in LevelParent");
        }
        else {
            System.out.println("Controller is not null in LevelParent");
        }
        this.controller = controller;
    
        this.root = new Group();
        this.scene = new Scene(root, GameConstant.GameSettings.SCREEN_WIDTH, GameConstant.GameSettings.SCREEN_HEIGHT);
    
        // Initialize background
        this.background = new ImageView(ImageManager.getInstance().getImage(backgroundImageName));
        initializeBackground();

        // Initialize Managers
        this.gameStateManager = controller.getGameStateManager();
        this.actorManager = gameStateManager.getActorManager();
    
        // Pass the root to ActorManager
        this.actorManager.updateRoot(this.root);

        // Initialize LevelView
        this.levelView = instantiateLevelView();
    
        for (UserPlane player : actorManager.getPlayers()) {
            levelView.showHeartDisplay(player);
        }
    }    
    

    /**
     * Initializes the background image.
     */
    private void initializeBackground() {
        background.setFitHeight(GameConstant.GameSettings.SCREEN_HEIGHT);
        background.setFitWidth(GameConstant.GameSettings.SCREEN_WIDTH);
        // background.setOpacity(0.3);
        root.getChildren().add(background);
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

    // Abstract methods to be implemented by subclasses
    public abstract int getCurrentLevelNumber();

    protected abstract void setCurrentLevelNumber(int levelNumber);

    public abstract boolean userHasReachedKillTarget();

    protected abstract void initializeFriendlyUnits();

    public abstract void spawnEnemyUnits();

    public abstract LevelView001 instantiateLevelView();

    public LevelView001 getLevelView() {
        return this.levelView;
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
}
