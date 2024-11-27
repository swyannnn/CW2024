package com.example.demo.level;

import java.util.List;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.plane.UserPlane;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ActorManager;
import com.example.demo.manager.CollisionManager;
import com.example.demo.manager.GameStateManager;
import com.example.demo.manager.ImageManager;
import com.example.demo.ui.LevelView001;
import com.example.demo.util.GameConstant;

import javafx.application.Platform;
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

    protected int currentNumberOfEnemies;

    // Managers are injected to ensure they're properly initialized
    protected final ActorManager actorManager;
    protected final CollisionManager collisionManager;
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
        this.collisionManager = gameStateManager.getCollisionManager();
        this.actorManager = gameStateManager.getActorManager();
    
        // Pass the root to ActorManager
        this.actorManager.updateRoot(this.root);

        // Initialize LevelView
        this.levelView = instantiateLevelView();
    
        // Initialize other components
        this.currentNumberOfEnemies = 0;
    
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
     * Checks if any user's plane has been destroyed.
     *
     * @return true if any user's plane is destroyed; false otherwise.
     */
    public boolean userIsDestroyed() {
        for (UserPlane player : actorManager.getPlayers()) {
            // System.out.println("Checking player health: " + player.getHealth());
            if (player.getHealth() <= 0) {
                System.out.println("Player destroyed: " + player);
                return true; // At least one player is destroyed
            }
        }
        return false; 
    }

    public boolean allUsersAreDestroyed() {
        return actorManager.getPlayers().stream()
                .allMatch(player -> player.getHealth() <= 0);
    }    

    /**
     * Spawns a new enemy unit at a random position.
     */ 
    protected double getEnemyMaximumYPosition() {
        return GameConstant.EnemyPlane.MAXIMUM_Y_POSITION;
    }

    protected double getEnemyMinimumYPosition() {
        return GameConstant.EnemyPlane.MINIMUM_Y_POSITION;
    }

    /**
     * Handles when enemies penetrate defenses and reach the users.
     */
    public void handleEnemyPenetration() {
        for (ActiveActorDestructible enemy : actorManager.getEnemyUnits()) {
            if (enemyHasPenetratedDefenses(enemy)) {
                // Choose the first player to take damage for now
                List<UserPlane> players = actorManager.getPlayers();
                if (!players.isEmpty()) {
                    players.get(0).takeDamage(); // First player takes the damage
                    enemy.destroy();
                    System.out.println("Enemy penetrated defenses: " + enemy + ". First user took damage.");
                }
            }
        }
    }

    /**
     * Checks if an enemy has penetrated defenses based on its position.
     *
     * @param enemy The enemy actor to check.
     * @return True if the enemy has penetrated defenses; false otherwise.
     */
    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        return Math.abs(enemy.getTranslateX()) > GameConstant.GameSettings.SCREEN_WIDTH;
    }

    /**
     * Updates the number of current enemies.
     */
    public void updateNumberOfEnemies() {
        currentNumberOfEnemies = actorManager.getEnemyUnits().size();
    }

    // Removed update() and render() methods to be managed by GameStateManager

    /**
     * Initializes and returns the scene for this level.
     *
     * @return The initialized scene.
     */
    public Scene initializeScene() {
        return scene;
    }

    /**
     * Starts the game by setting up necessary elements.
     * This method can be called when the level is loaded.
     */
    public void startGame() {
        // It's better to request focus on the scene or root node instead of background
        Platform.runLater(() -> scene.getRoot().requestFocus());
        System.out.println("Game started. Scene focus requested.");
    }

    /**
     * Handles the game over state.
     */
    public void loseGame() {
        gameStateManager.goToLoseState();
        System.out.println("Game lost. Transitioning to LoseState.");
    }

    /**
     * Handles the win game state.
     */
    public void winGame() {
        gameStateManager.goToWinState();
        System.out.println("Game won. Transitioning to WinState.");
    }

    /**
     * Transitions to the next level by firing a property change event.
     *
     * @param nextLevelNumber The number of the next level.
     */
    public void goToNextLevel(Integer nextLevelNumber) {
        gameStateManager.goToLevel(nextLevelNumber);
        System.out.println("Transitioning to Level " + nextLevelNumber);
    }
}
