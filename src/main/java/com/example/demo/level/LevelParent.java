package com.example.demo.level;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.FighterPlane;
import com.example.demo.UserPlane;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ActorManager;
import com.example.demo.manager.CollisionManager;
import com.example.demo.manager.GameStateManager;
import com.example.demo.manager.ImageManager;
import com.example.demo.memento.LevelStateMemento;
import com.example.demo.memento.PlayerStateMemento;
import com.example.demo.util.GameConstant;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

/**
 * Abstract base class for game levels.
 * Now streamlined to focus on level-specific logic.
 */
public abstract class LevelParent {
    protected final double screenHeight;
    protected final double screenWidth;
    protected final double enemyMaximumYPosition;

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
     * @param backgroundImageName The path to the background image.
     * @param playerInitialHealth The initial health of the player.
     * @param gameStateManager    The GameStateManager instance managing game states.
     */
    public LevelParent(Controller controller, String backgroundImageName, int playerInitialHealth) {
        this.screenHeight = GameConstant.SCREEN_HEIGHT;
        this.screenWidth = GameConstant.SCREEN_WIDTH;
        this.enemyMaximumYPosition = screenHeight - GameConstant.SCREEN_HEIGHT_ADJUSTMENT;

        this.root = new Group();
        this.scene = new Scene(root, screenWidth, screenHeight);

        // Initialize background
        this.background = new ImageView(ImageManager.getInstance().getImage(backgroundImageName));
        initializeBackground();

        // Initialize User Plane
        this.user = new UserPlane(screenHeight, screenWidth, playerInitialHealth);
        initializeUser();

        // Initialize Managers
        this.gameStateManager = controller.getGameStateManager();
        this.actorManager = gameStateManager.getActorManager();
        this.collisionManager = gameStateManager.getCollisionManager();

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
        background.setFitHeight(screenHeight);
        background.setFitWidth(screenWidth);
        root.getChildren().add(background);
    }

    /**
     * Initializes the user plane in the scene.
     */
    private void initializeUser() {
        if (!root.getChildren().contains(user)) {
            root.getChildren().add(user);
        }
    }

    // Getter methods for subclasses or controller
    public UserPlane getUser() {
        return user;
    }

    public ActorManager getActorManager() {
        return actorManager;
    }

    /**
     * Creates a PlayerStateMemento to save the current state of the player.
     *
     * @return A PlayerStateMemento containing the current player state.
     */
    public PlayerStateMemento createPlayerMemento() {
        return new PlayerStateMemento(user.getHealth(), user.getScore(), user.getPositionX(), user.getPositionY());
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
        user.setHealth(memento.getHealth());
        user.setScore(memento.getScore());
        user.setPosition(memento.getPositionX(), memento.getPositionY());
    }

    /**
     * Restores the level's state from a LevelStateMemento.
     *
     * @param memento The LevelStateMemento to restore from.
     */
    public void restoreLevelState(LevelStateMemento memento) {
        setCurrentLevelNumber(memento.getLevelNumber());
        // Implement logic to recreate enemy units based on the saved state if necessary
    }

    // Abstract methods to be implemented by subclasses
    public abstract int getCurrentLevelNumber();

    protected abstract void setCurrentLevelNumber(int levelNumber);

    public abstract boolean userHasReachedKillTarget();

    protected abstract void initializeFriendlyUnits();

    protected abstract void checkIfGameOver();

    protected abstract void spawnEnemyUnits();

    protected abstract LevelView instantiateLevelView();

    public LevelView getLevelView() {
        return this.levelView;
    }

    /**
     * Checks if the user's plane has been destroyed.
     *
     * @return true if the user's plane is destroyed; false otherwise.
     */
    public boolean userIsDestroyed() {
        return user.getHealth() <= 0;
    }

    /**
     * Fires a projectile from the user's plane.
     */
    public void fireProjectile() {
        ActiveActorDestructible projectile = user.fireProjectile();
        if (projectile != null) {
            actorManager.addUserProjectile(projectile);
        }
    }

    /**
     * Generates projectiles from enemy units.
     */
    private void generateEnemyFire() {
        for (ActiveActorDestructible enemy : actorManager.getEnemyUnits()) {
            if (enemy instanceof FighterPlane) {
                ActiveActorDestructible projectile = ((FighterPlane) enemy).fireProjectile();
                if (projectile != null) {
                    actorManager.addEnemyProjectile(projectile);
                }
            }
        }
    }

    /**
     * Updates all actors in the level.
     */
    private void updateActors() {
        actorManager.updateAllActors();
    }

    protected double getEnemyMaximumYPosition() {
        return enemyMaximumYPosition;
    }

    /**
     * Removes all destroyed actors from their respective lists and the scene graph.
     */
    private void removeAllDestroyedActors() {
        actorManager.removeDestroyedActors();
    }

    /**
     * Handles when enemies penetrate defenses and reach the user.
     */
    private void handleEnemyPenetration() {
        for (ActiveActorDestructible enemy : actorManager.getEnemyUnits()) {
            if (enemyHasPenetratedDefenses(enemy)) {
                user.takeDamage();
                enemy.destroy();
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
        return Math.abs(enemy.getTranslateX()) > screenWidth;
    }

    /**
     * Updates the level view based on the user's health.
     */
    private void updateLevelView() {
        levelView.removeHearts(user.getHealth());
    }

    /**
     * Updates the kill count based on the current number of enemies.
     */
    private void updateKillCount() {
        int kills = currentNumberOfEnemies - actorManager.getEnemyUnits().size();
        for (int i = 0; i < kills; i++) {
            user.incrementKillCount();
        }
    }

    /**
     * Updates the number of current enemies.
     */
    private void updateNumberOfEnemies() {
        currentNumberOfEnemies = actorManager.getEnemyUnits().size();
    }

    /**
     * Updates the game state. This method is called by the game loop.
     */
    public void update() {
        updateScene();
    }

    /**
     * Updates the game scene by handling spawning, actor updates, collisions, and game state.
     * This method is called by the game loop.
     */
    private void updateScene() {
        spawnEnemyUnits();
        updateActors();
        generateEnemyFire();
        updateNumberOfEnemies();
        handleEnemyPenetration();
        collisionManager.handleProjectileCollisions(actorManager.getUserProjectiles(), actorManager.getEnemyUnits());
        collisionManager.handleEnemyProjectileCollisions(actorManager.getEnemyProjectiles(), actorManager.getFriendlyUnits());
        collisionManager.handleUnitCollisions(actorManager.getFriendlyUnits(), actorManager.getEnemyUnits());
        removeAllDestroyedActors();
        updateKillCount();
        updateLevelView();
        checkIfGameOver();
    }

    /**
     * Renders the game view. This method is called by the game loop.
     * In JavaFX, rendering is often handled by the scene graph, so this method
     * may handle updating UI elements or LevelView.
     */
    public void render() {
        levelView.updateView();
    }

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
        // Any initialization before the game loop starts can be done here.
        background.requestFocus();
    }

    /**
     * Handles the game over state.
     */
    public void loseGame() {
        gameStateManager.goToLoseState();
    }

    /**
     * Handles the win game state.
     */
    public void winGame() {
        gameStateManager.goToWinState();
    }

    /**
     * Transitions to the next level by firing a property change event.
     *
     * @param nextLevelNumber The number of the next level.
     */
    public void goToNextLevel(Integer nextLevelNumber) {
        gameStateManager.goToLevel(nextLevelNumber);
    }
}
