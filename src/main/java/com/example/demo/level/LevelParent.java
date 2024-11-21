package com.example.demo.level;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.UserPlane;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ActorManager;
import com.example.demo.manager.CollisionManager;
import com.example.demo.manager.GameStateManager;
import com.example.demo.manager.ImageManager;
import com.example.demo.memento.LevelStateMemento;
import com.example.demo.memento.PlayerStateMemento;
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
    protected LevelView levelView;
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
        this.scene = new Scene(root, GameConstant.SCREEN_WIDTH, GameConstant.SCREEN_HEIGHT);
    
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
    
        // Add LevelView related UI elements
        levelView.showHeartDisplay();
    }    
    

    /**
     * Initializes the background image.
     */
    private void initializeBackground() {
        background.setFitHeight(GameConstant.SCREEN_HEIGHT);
        background.setFitWidth(GameConstant.SCREEN_WIDTH);
        background.setOpacity(0.3);
        root.getChildren().add(background);
    }

    /**
     * Creates a PlayerStateMemento to save the current state of the player.
     *
     * @return A PlayerStateMemento containing the current player state.
     */
    public PlayerStateMemento createPlayerMemento() {
        return new PlayerStateMemento(actorManager.getPlayer().getHealth(), actorManager.getPlayer().getScore(), actorManager.getPlayer().getPositionX(), actorManager.getPlayer().getPositionY());
    }

    /**
     * Creates a LevelStateMemento to save the current state of the level.
     *
     * @return A LevelStateMemento containing the current level state.
     */
    public LevelStateMemento createLevelMemento() {
        return new LevelStateMemento(getCurrentLevelNumber(), actorManager.getEnemyUnits().size());
    }

    /**
     * Restores the player's state from a PlayerStateMemento.
     *
     * @param memento The PlayerStateMemento to restore from.
     */
    public void restorePlayerState(PlayerStateMemento memento) {
        actorManager.getPlayer().setHealth(memento.getHealth());
        actorManager.getPlayer().setScore(memento.getScore());
        actorManager.getPlayer().setPosition(memento.getPositionX(), memento.getPositionY());
        System.out.println("Player state restored.");
    }

    /**
     * Restores the level's state from a LevelStateMemento.
     *
     * @param memento The LevelStateMemento to restore from.
     */
    public void restoreLevelState(LevelStateMemento memento) {
        setCurrentLevelNumber(memento.getLevelNumber());
        // Implement logic to recreate enemy units based on the saved state if necessary
        System.out.println("Level state restored.");
    }

    // Abstract methods to be implemented by subclasses
    public abstract int getCurrentLevelNumber();

    protected abstract void setCurrentLevelNumber(int levelNumber);

    public abstract boolean userHasReachedKillTarget();

    protected abstract void initializeFriendlyUnits();

    public abstract void checkIfGameOver();

    public abstract void spawnEnemyUnits();

    public abstract LevelView instantiateLevelView();

    public LevelView getLevelView() {
        return this.levelView;
    }

    public Group getRoot() {
        return this.root;
    }

    /**
     * Checks if the user's plane has been destroyed.
     *
     * @return true if the user's plane is destroyed; false otherwise.
     */
    public boolean userIsDestroyed() {
        return actorManager.getPlayer().getHealth() <= 0;
    }

    protected double getEnemyMaximumYPosition() {
        return GameConstant.ENEMY_MAXIMUM_Y_POSITION;
    }

    protected double getEnemyMinimumYPosition() {
        return GameConstant.ENEMY_MINIMUM_Y_POSITION;
    }

    /**
     * Handles when enemies penetrate defenses and reach the user
     */
    public void handleEnemyPenetration() {
        for (ActiveActorDestructible enemy : actorManager.getEnemyUnits()) {
            if (enemyHasPenetratedDefenses(enemy)) {
                actorManager.getPlayer().takeDamage();
                enemy.destroy();
                System.out.println("Enemy penetrated defenses: " + enemy + ". User took damage.");
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
        return Math.abs(enemy.getTranslateX()) > GameConstant.SCREEN_WIDTH;
    }

    /**
     * Updates the level view based on the user's health.
     */
    public void updateLevelView() {
        if (user == null) {
            // System.err.println("UserPlane is null in updateLevelView");
            return; // Early return to avoid NullPointerException
        }

        // Now it's safe to call methods on user
        int health = actorManager.getPlayer().getHealth();
        System.out.println("UserPlane health: " + health);

        // Other update logic...
    }

    /**
     * Updates the kill count based on the current number of enemies.
     */
    public void updateKillCount() {
        int kills = currentNumberOfEnemies - actorManager.getEnemyUnits().size(); // logic error
        for (int i = 0; i < kills; i++) {
            actorManager.getPlayer().incrementKillCount();
            System.out.println("Kill count incremented. Total kills: " + actorManager.getPlayer().getNumberOfKills());
        }
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

    // Additional level-specific methods can be added here
}
