package com.example.demo.actor.plane;

import com.example.demo.util.GameConstant;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.actor.projectile.UserProjectile;
import com.example.demo.controller.Controller;
import com.example.demo.listener.HealthChangeListener;
import com.example.demo.manager.ActorManager;
import com.example.demo.manager.AudioManager;

public class UserPlane extends FighterPlane {
    private List<HealthChangeListener> healthChangeListeners = new ArrayList<>();
    private ActorManager actorManager;
    private AudioManager audioManager;
    private Controller controller;

    private static final String imageName = GameConstant.UserPlane.IMAGE_NAME;
    private static final int imageHeight = GameConstant.UserPlane.IMAGE_HEIGHT; 
    private final double initialXPosition = GameConstant.UserPlane.INITIAL_X_POSITION;
    private final double initialYPosition = GameConstant.UserPlane.INITIAL_Y_POSITION;

    private static final double verticalVelocity = GameConstant.UserPlane.VERTICAL_VELOCITY;
    private static final double horizontalVelocity = GameConstant.UserPlane.HORIZONTAL_VELOCITY;
    private static final int projectileXPositionOffset = GameConstant.UserProjectile.PROJECTILE_X_POSITION_OFFSET;
    private static final int projectileYPositionOffset = GameConstant.UserProjectile.PROJECTILE_Y_POSITION_OFFSET;

    private final double yUpperBound = GameConstant.UserPlane.Y_UPPER_BOUND;
    private final double yLowerBound = GameConstant.UserPlane.Y_LOWER_BOUND;
    private final double xUpperBound = GameConstant.UserPlane.X_UPPER_BOUND;
    private final double xLowerBound = GameConstant.UserPlane.X_LOWER_BOUND;

    private int verticalVelocityMultiplier = GameConstant.UserPlane.VERTICAL_VELOCITY_MULTIPLIER;
    private int horizontalVelocityMultiplier = GameConstant.UserPlane.HORIZONTAL_VELOCITY_MULTIPLIER;
    private int numberOfKills = GameConstant.UserPlane.NUMBER_OF_KILLS;
    private int score;

    /**
     * Constructs a UserPlane object with specified stage dimensions and initial health.
     * @param stageHeight The height of the stage.
     * @param stageWidth The width of the stage.
     * @param initialHealth The initial health of the user plane.
     */
    public UserPlane(int initialHealth, Controller controller) {
        super(controller, imageName, imageHeight, GameConstant.UserPlane.INITIAL_X_POSITION, GameConstant.UserPlane.INITIAL_Y_POSITION, initialHealth, GameConstant.UserProjectile.FIRE_INTERVAL_NANOSECONDS);
        this.controller = controller;
        this.actorManager = controller.getGameStateManager().getActorManager();
        this.audioManager = controller.getGameStateManager().getAudioManager();
        this.health = initialHealth;
        setHorizontalBounds(xUpperBound, xLowerBound);
        setVerticalBounds(yUpperBound, yLowerBound);
        System.out.println("Initial layout position: (" + getLayoutX() + ", " + getLayoutY() + ")");
        System.out.println("Initial translate position: (" + getTranslateX() + ", " + getTranslateY() + ")");
    }

    /**
     * Fires a projectile from the user plane's current position.
     */
    public void fireProjectile() {
        double currentX =  getProjectileXPosition(projectileXPositionOffset);
        double currentY = getProjectileYPosition(projectileYPositionOffset);

        UserProjectile projectile = new UserProjectile(currentX, currentY, this, controller);
        actorManager.addActor(projectile);
        audioManager.playSoundEffect(3);
    }

    @Override
    protected void performMovement(long now) {
        boolean moved = false; // Flag to track if movement occurred
        double initialTranslateX = getTranslateX();
        double initialTranslateY = getTranslateY();
    
        // Handle vertical movement
        if (verticalVelocityMultiplier != 0) {
            moveVertically(verticalVelocity * verticalVelocityMultiplier);
            moved = true;
        }
    
        // Handle horizontal movement
        if (horizontalVelocityMultiplier != 0) {
            moveHorizontally(horizontalVelocity * horizontalVelocityMultiplier);
            moved = true;
        }
    
        // If any movement occurred, check for out-of-bounds
        if (moved && isOutOfBounds()) {
            setTranslateX(initialTranslateX);
            setTranslateY(initialTranslateY);
        }
    }
    

    @Override
    protected void performAdditionalUpdates(long now) {
        // Implement any additional updates specific to UserPlane if necessary
    }

    public void addHealthChangeListener(HealthChangeListener listener) {
        healthChangeListeners.add(listener);
    }

    public void removeHealthChangeListener(HealthChangeListener listener) {
        healthChangeListeners.remove(listener);
    }

    private void notifyHealthChange() {
        for (HealthChangeListener listener : healthChangeListeners) {
            listener.onHealthChange(this, this.health);
        }
    }

    @Override
    public void takeDamage() {
        health--;
        if (health < 0) {
            health = 0;
        }

        notifyHealthChange(); // Notify listeners of health change
        System.out.println("notifyHealthChange() called in UserPlane.takeDamage().");
        if (super.healthAtZero()) {
            System.out.println("UserPlane destroyed because health zero.");
            destroy();
        }
    }

    public void setHealth(int health) {
        // notifyHealthChange();
        this.health = health;
    }

    // Score management methods
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // Method to check if the user is destroyed
    public boolean isDestroyed() {
        return health <= 0;
    }

    /**
     * Resets the user plane's position to the initial coordinates.
     */
    public void resetPosition() {
        setLayoutX(initialXPosition);
        setLayoutY(initialYPosition);
    }

    // Movement methods
    public void moveUp() {
        verticalVelocityMultiplier = -1;
    }

    public void moveDown() {
        verticalVelocityMultiplier = 1;
    }

    public void moveRight() {
        horizontalVelocityMultiplier = 1;
    }

    public void moveLeft() {
        horizontalVelocityMultiplier = -1;
    }

    public void stopVertical() {
        verticalVelocityMultiplier = 0;
    }

    public void stopHorizontal() {
        horizontalVelocityMultiplier = 0;
    }

    // Kill count methods
    public int getNumberOfKills() {
        // System.out.println("UserPlane.getNumberOfKills():" + numberOfKills);
        return numberOfKills;
    }

    public void incrementKillCount() {
        System.out.println("UserPlane.incrementKillCount(): " + numberOfKills);
        numberOfKills++;
    }
}
