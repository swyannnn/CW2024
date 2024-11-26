package com.example.demo.actor.plane;

import com.example.demo.util.GameConstant;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.actor.projectile.UserProjectile;
import com.example.demo.controller.Controller;
import com.example.demo.listener.HealthChangeListener;
import com.example.demo.manager.ActorManager;

public class UserPlane extends FighterPlane {
    private List<HealthChangeListener> healthChangeListeners = new ArrayList<>();
    private ActorManager actorManager;
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
    private double positionX;
    private double positionY;

    /**
     * Constructs a UserPlane object with specified stage dimensions and initial health.
     * @param stageHeight The height of the stage.
     * @param stageWidth The width of the stage.
     * @param initialHealth The initial health of the user plane.
     */
    public UserPlane(int initialHealth, Controller controller) {
        super(imageName, imageHeight, GameConstant.UserPlane.INITIAL_X_POSITION, GameConstant.UserPlane.INITIAL_Y_POSITION, initialHealth, GameConstant.UserProjectile.FIRE_INTERVAL_NANOSECONDS);
        this.controller = controller;
        this.actorManager = controller.getGameStateManager().getActorManager();
        this.health = initialHealth;
        this.score = 0;
    }

    /**
     * Fires a projectile from the user plane's current position.
     */
    public void fireProjectile() {
        double currentX =  getProjectileXPosition(projectileXPositionOffset);
        double currentY = getProjectileYPosition(projectileYPositionOffset);

        UserProjectile projectile = new UserProjectile(currentX, currentY, this, controller);
        actorManager.addUserProjectile(projectile);

        // System.out.println("Projectile fired by " + this + " at: " + currentX + ", " + currentY);
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

    /**
     * Updates the position of the user plane based on velocity multipliers.
     */
    @Override
    public void updatePosition() {
        // Handle vertical movement
        if (verticalVelocityMultiplier != 0) {
            double initialTranslateY = getTranslateY();
            moveVertically(verticalVelocity * verticalVelocityMultiplier);
            if (isOutOfVerticalBounds()) {
                setTranslateY(initialTranslateY); // Revert to the previous position if out of bounds
                // System.out.println("UserPlane out of vertical bounds, reverting Y position.");
            } else {
                // Update positionY
                positionY = getLayoutY() + getTranslateY();
            }
        }

        // Handle horizontal movement
        if (horizontalVelocityMultiplier != 0) {
            double initialTranslateX = getTranslateX();
            moveHorizontally(horizontalVelocity * horizontalVelocityMultiplier);
            if (isOutOfHorizontalBounds()) {
                setTranslateX(initialTranslateX); // Revert to the previous position if out of bounds
                // System.out.println("UserPlane out of horizontal bounds, reverting X position.");
            } else {
                // Update positionX
                positionX = getLayoutX() + getTranslateX();
            }
        }

        // System.out.println("Current user plane position: " + getTranslateX() + ", " + getTranslateY());
    }

    @Override
    public void takeDamage() {
        health--;
        if (health < 0) {
            health = 0;
        }

        notifyHealthChange(); // Notify listeners of health change
        System.out.println("notifyHealthChange() called in UserPlane.takeDamage().");
        if (health == 0) {
            System.out.println("UserPlane destroyed because health zero.");
            destroy();
        }
    }

    public void setHealth(int health) {
        notifyHealthChange();
        this.health = health;
    }

    // Score management methods
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    // Method to check if the user is destroyed
    public boolean isDestroyed() {
        return health <= 0;
    }

    /**
     * Checks if the user plane is out of vertical bounds.
     * @return True if out of bounds, otherwise false.
     */
    private boolean isOutOfVerticalBounds() {
        double newPositionY = getLayoutY() + getTranslateY();
        return newPositionY < yUpperBound || newPositionY > yLowerBound;
    }

    /**
     * Checks if the user plane is out of horizontal bounds.
     * @return True if out of bounds, otherwise false.
     */
    private boolean isOutOfHorizontalBounds() {
        double newPositionX = getLayoutX() + getTranslateX();
        return newPositionX < xUpperBound || newPositionX > xLowerBound;
    }

    /**
     * Resets the user plane's position to the initial coordinates.
     */
    public void resetPosition() {
        setLayoutX(initialXPosition);
        setLayoutY(initialYPosition);
    }

    /**
     * Updates the actor's state, called in each frame of the game loop.
     */
    @Override
    public void updateActor() {
        updatePosition();
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
        numberOfKills++;
    }
}
