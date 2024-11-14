package com.example.demo.level;
import com.example.demo.ActiveActorDestructible;
import com.example.demo.FighterPlane;
import com.example.demo.GameControl;
import com.example.demo.UserPlane;
import com.example.demo.util.ScreenConstants;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.stream.Collectors;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;


/**
 * Abstract base class for game levels.
 */
public abstract class LevelParent {
    protected GameControl gameControl;
    private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
    private final double screenHeight;
    private final double screenWidth;
    private final double enemyMaximumYPosition;

    private final Group root;
    private final UserPlane user;
    private final Scene scene;
    private final ImageView background;

    private final List<ActiveActorDestructible> friendlyUnits;
    private final List<ActiveActorDestructible> enemyUnits;
    private final List<ActiveActorDestructible> userProjectiles;
    private final List<ActiveActorDestructible> enemyProjectiles;

    private int currentNumberOfEnemies;
    private LevelView levelView;


    // PropertyChangeSupport to handle listeners
    private final PropertyChangeSupport support;

    /**
     * Constructs a new LevelParent instance.
     *
     * @param backgroundImageName The path to the background image.
     * @param screenHeight        The height of the screen.
     * @param screenWidth         The width of the screen.
     * @param playerInitialHealth The initial health of the player.
     */
    public LevelParent(GameControl gameControl, String backgroundImageName, int playerInitialHealth) {
        this.root = new Group();
        this.gameControl = gameControl;
        this.screenHeight = ScreenConstants.SCREEN_HEIGHT;
        this.screenWidth = ScreenConstants.SCREEN_WIDTH;
        this.scene = new Scene(root, screenWidth, screenHeight);
        this.user = new UserPlane(screenHeight, screenWidth, playerInitialHealth);
        this.friendlyUnits = new ArrayList<>();
        this.enemyUnits = new ArrayList<>();
        this.userProjectiles = new ArrayList<>();
        this.enemyProjectiles = new ArrayList<>();

        this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
        this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
        this.levelView = instantiateLevelView();

        if (this.levelView == null) {
            System.err.println("Error: LevelView is null in LevelParent constructor.");
        }else {
            System.out.println("LevelView is not null in LevelParent constructor.");
        }

        this.currentNumberOfEnemies = 0;
        this.support = new PropertyChangeSupport(this); // Initialize PropertyChangeSupport

        initializeBackground();
        initializeFriendlyUnits();
        levelView.showHeartDisplay();

        friendlyUnits.add(user);
    }

    /**
     * Initializes the background image and input handlers.
     */
    private void initializeBackground() {
        // background.setFocusTraversable(true);
        background.setFitHeight(screenHeight);
        background.setFitWidth(screenWidth);
        background.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                KeyCode kc = e.getCode();
                switch (kc) {
                    case UP:
                        user.moveUp();
                        break;
                    case DOWN:
                        user.moveDown();
                        break;
                    case LEFT:
                        user.moveLeft();
                        break;
                    case RIGHT:
                        user.moveRight();
                        break;
                    case SPACE:
                        fireProjectile();
                        break;
                    default:
                        break;
                }
            }
        });
        background.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                KeyCode kc = e.getCode();
                switch (kc) {
                    case UP:
                    case DOWN:
                        user.stopVertical();
                        break;
                    case LEFT:
                    case RIGHT:
                        user.stopHorizontal();
                        break;
                    default:
                        break;
                }
            }
        });
        root.getChildren().add(background);
    }

    
    public LevelView getLevelView() {
        if (levelView == null) {
            System.err.println("Error: levelView is null in getLevelView.");
        }
        else {
            System.out.println("Success: levelView is not null in getLevelView.");
        }
        return this.levelView;
    }
     
    /**
     * Fires a projectile from the user's plane.
     */
    private void fireProjectile() {
        ActiveActorDestructible projectile = user.fireProjectile();
        if (projectile != null) {
            root.getChildren().add(projectile);
            userProjectiles.add(projectile);
        }
    }

    /**
     * Generates projectiles from enemy units.
     */
    private void generateEnemyFire() {
        for (ActiveActorDestructible enemy : enemyUnits) {
            ActiveActorDestructible projectile = ((FighterPlane) enemy).fireProjectile();
            if (projectile != null) {
                root.getChildren().add(projectile);
                enemyProjectiles.add(projectile);
            }
        }
    }

    /**
     * Updates all actors in the level.
     */
    private void updateActors() {
        for (ActiveActorDestructible plane : friendlyUnits) {
            plane.updateActor();
        }
        for (ActiveActorDestructible enemy : enemyUnits) {
            enemy.updateActor();
        }
        for (ActiveActorDestructible projectile : userProjectiles) {
            projectile.updateActor();
        }
        for (ActiveActorDestructible projectile : enemyProjectiles) {
            projectile.updateActor();
        }
    }

    /**
     * Removes all destroyed actors from their respective lists and the scene graph.
     */
    private void removeAllDestroyedActors() {
        removeDestroyedActors(friendlyUnits);
        removeDestroyedActors(enemyUnits);
        removeDestroyedActors(userProjectiles);
        removeDestroyedActors(enemyProjectiles);
    }

    /**
     * Removes destroyed actors from a given list.
     *
     * @param actors The list of actors to remove destroyed actors from.
     */
    private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream()
                .filter(ActiveActorDestructible::isDestroyed)
                .collect(Collectors.toList());
        root.getChildren().removeAll(destroyedActors);
        actors.removeAll(destroyedActors);
    }

    /**
     * Handles collisions between two lists of actors.
     *
     * @param actors1 The first list of actors.
     * @param actors2 The second list of actors.
     */
    private void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor : actors2) {
            for (ActiveActorDestructible otherActor : actors1) {
                if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
                    actor.takeDamage();
                    otherActor.takeDamage();
                }
            }
        }
    }

    /**
     * Handles the game over state.
     */
    // Method to handle losing the game
    public void loseGame() {
        if (gameControl != null) {
            gameControl.stopGameLoopAndLose(); // Call the interface method
        }
        // Additional lose logic here
        
    }

    /**
     * Handles the win game state.
     */
    public void winGame() {
        if (gameControl != null) {
            gameControl.stopGameLoopAndWin(); // Call the interface method
        }
        // Additional win logic here
    }

    /**
     * Handles collisions between planes.
     */
    private void handlePlaneCollisions() {
        handleCollisions(friendlyUnits, enemyUnits);
    }

    /**
     * Handles collisions between user projectiles and enemies.
     */
    private void handleUserProjectileCollisions() {
        handleCollisions(userProjectiles, enemyUnits);
    }

    /**
     * Handles collisions between enemy projectiles and friendly units.
     */
    private void handleEnemyProjectileCollisions() {
        handleCollisions(enemyProjectiles, friendlyUnits);
    }

    /**
     * Handles when enemies penetrate defenses and reach the user.
     */
    private void handleEnemyPenetration() {
        for (ActiveActorDestructible enemy : enemyUnits) {
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
        int kills = currentNumberOfEnemies - enemyUnits.size();
        for (int i = 0; i < kills; i++) {
            user.incrementKillCount();
        }
    }

    /**
     * Updates the number of current enemies.
     */
    private void updateNumberOfEnemies() {
        currentNumberOfEnemies = enemyUnits.size();
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
        handleUserProjectileCollisions();
        handleEnemyProjectileCollisions();
        handlePlaneCollisions();
        removeAllDestroyedActors();
        updateKillCount();
        updateLevelView();
        checkIfGameOver();
    }

    /**
     * Updates the game state. This method is called by the game loop.
     */
    public void update() {
        updateScene();
    }

    /**
     * Renders the game view. This method is called by the game loop.
     * In JavaFX, rendering is often handled by the scene graph, so this method
     * may handle updating UI elements or LevelView.
     */
    public void render() {
        // In JavaFX, rendering is handled by the scene graph.
        // If you have any custom rendering logic, implement it here.
        // For example:
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
     * Transitions to the next level by firing a property change event.
     *
     * @param levelName The fully qualified class name of the next level.
     */
    public void goToNextLevel(Integer nextLevelNumber) {
        support.firePropertyChange("level", null, nextLevelNumber); // Notify listeners about the level change
    }

    // Methods to add and remove listeners
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Getter for the GameControl instance.
     * @return The GameControl instance associated with this level.
     */
    public GameControl getGameControl() {
        return gameControl;
    }

    // Abstract methods that must be implemented by subclasses
    protected abstract void initializeFriendlyUnits();

    protected abstract void checkIfGameOver();

    protected abstract void spawnEnemyUnits();

    protected abstract LevelView instantiateLevelView();

    // Getter methods for subclasses or controller
    protected UserPlane getUser() {
        return user;
    }

    protected Group getRoot() {
        return root;
    }

    protected int getCurrentNumberOfEnemies() {
        return enemyUnits.size();
    }

    protected void addEnemyUnit(ActiveActorDestructible enemy) {
        enemyUnits.add(enemy);
        root.getChildren().add(enemy);
    }

    protected double getEnemyMaximumYPosition() {
        return enemyMaximumYPosition;
    }

    protected double getScreenWidth() {
        return screenWidth;
    }

    protected boolean userIsDestroyed() {
        return user.isDestroyed();
    }

    // Additional helper methods can be added here
}
