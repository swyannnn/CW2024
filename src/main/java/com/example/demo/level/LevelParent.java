package com.example.demo.level;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.FighterPlane;
import com.example.demo.UserPlane;
import com.example.demo.manager.ActorManager;
import com.example.demo.manager.CollisionManager;
import com.example.demo.memento.LevelStateMemento;
import com.example.demo.memento.PlayerStateMemento;
import com.example.demo.util.GameConstant;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Abstract base class for game levels.
 */
public abstract class LevelParent {
    private final double screenHeight;
    private final double screenWidth;
    private final double enemyMaximumYPosition;

    private final Group root;
    private final UserPlane user;
    private final Scene scene;
    private final ImageView background;

    private LevelView levelView;

    private int currentNumberOfEnemies;

    // PropertyChangeSupport to handle listeners
    private final PropertyChangeSupport support;

    // Managers
    protected final ActorManager actorManager;
    protected final CollisionManager collisionManager;

    /**
     * Constructs a new LevelParent instance.
     *
     * @param gameControl          The GameControl instance managing game actions. Remove if not using.
     * @param backgroundImageName  The path to the background image.
     * @param playerInitialHealth  The initial health of the player.
     */
    public LevelParent(String backgroundImageName, int playerInitialHealth) {
        this.screenHeight = GameConstant.SCREEN_HEIGHT;
        this.screenWidth = GameConstant.SCREEN_WIDTH;
        this.root = new Group();
        this.scene = new Scene(root, screenWidth, screenHeight);
        this.user = new UserPlane(screenHeight, screenWidth, playerInitialHealth);
        this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
        this.enemyMaximumYPosition = screenHeight - GameConstant.SCREEN_HEIGHT_ADJUSTMENT;

        // Initialize PropertyChangeSupport
        this.support = new PropertyChangeSupport(this);

        // Initialize Managers
        this.actorManager = new ActorManager(root);
        this.collisionManager = CollisionManager.getInstance();

        // Initialize LevelView
        this.levelView = instantiateLevelView();

        // Initialize other components
        this.currentNumberOfEnemies = 0;

        // Add the background and user to the root
        initializeBackground();
        initializeUser();

        // Initialize friendly units and view
        initializeFriendlyUnits();
        levelView.showHeartDisplay();
    }

    /**
     * Initializes the background image.
     */
    private void initializeBackground() {
        background.setFitHeight(screenHeight);
        background.setFitWidth(screenWidth);
        root.getChildren().add(background);
        System.out.println("Background added to root.");
    }

    /**
     * Initializes the user plane in the scene.
     */
    private void initializeUser() {
        if (!root.getChildren().contains(user)) {
            root.getChildren().add(user);
        }
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
        background.requestFocus();
        // Any other initialization before the game loop starts can be done here.
    }

    /**
     * Handles the game over state.
     */
    public void loseGame() {
        Platform.runLater(() -> support.firePropertyChange("lose", false, true));
        // Additional lose logic here
    }

    /**
     * Handles the win game state.
     */
    public void winGame() {
        Platform.runLater(() -> support.firePropertyChange("win", false, true));
        // Additional win logic here
    }

    /**
     * Transitions to the next level by firing a property change event.
     *
     * @param nextLevelNumber The number of the next level.
     */
    public void goToNextLevel(Integer nextLevelNumber) {
        support.firePropertyChange("level", null, nextLevelNumber); // Notify listeners about the level change
    }

    // Getter methods for subclasses or controller
    public UserPlane getUser() {
        return user;
    }

    public Group getRoot() {
        return this.root;
    }

    public ActorManager getActorManager() {
        return actorManager;
    }

    public CollisionManager getCollisionManager() {
        return collisionManager;
    }

    // Methods to add and remove listeners
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
