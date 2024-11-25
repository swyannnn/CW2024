package com.example.demo.actor.plane;

import com.example.demo.memento.PlayerStateMemento;
import com.example.demo.util.GameConstant;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.actor.projectile.UserProjectile;
import com.example.demo.controller.Controller;
import com.example.demo.listener.HealthChangeListener;
import com.example.demo.manager.ActorManager;

public class UserPlane extends FighterPlane {
    private static final String IMAGE_NAME = "userplane.png";
    private List<HealthChangeListener> healthChangeListeners = new ArrayList<>();
    private static final int IMAGE_HEIGHT = 150;
    private static final int VERTICAL_VELOCITY = 8;
    private static final int HORIZONTAL_VELOCITY = 8;
    private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
    private static final int PROJECTILE_X_POSITION_OFFSET = 90;
    private static final int PROJECTILE_Y_POSITION_OFFSET = 20;

    private final double yUpperBound;
    private final double yLowerBound;
    private final double xUpperBound;
    private final double xLowerBound;
    private final double initialXPosition;
    private final double initialYPosition;

    private int verticalVelocityMultiplier = 0;
    private int horizontalVelocityMultiplier = 0;
    private int numberOfKills = 0;
    private int score;
    private double positionX;
    private double positionY;
    private ActorManager actorManager;

    /**
     * Constructs a UserPlane object with specified stage dimensions and initial health.
     * @param stageHeight The height of the stage.
     * @param stageWidth The width of the stage.
     * @param initialHealth The initial health of the user plane.
     */
    public UserPlane(int initialHealth, Controller controller) {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth, GameConstant.USERPLANE_FIRE_INTERVAL_NANOSECONDS);
        // Set dynamic bounds based on stage dimensions
        this.actorManager = controller.getGameStateManager().getActorManager();
        this.yUpperBound = -40;
        this.yLowerBound = 600.0; 
        this.xUpperBound = 10;
        this.xLowerBound = GameConstant.SCREEN_WIDTH - 150; 
        this.initialXPosition = INITIAL_X_POSITION;
        this.initialYPosition = INITIAL_Y_POSITION;
        this.health = initialHealth;
        this.score = 0;
    }

    /**
     * Fires a projectile from the user plane's current position.
     */
    public void fireProjectile() {
        double currentX =  getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
        double currentY = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);

        UserProjectile projectile = new UserProjectile(currentX, currentY, this);
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
     * Creates a memento of the current player state.
     *
     * @return A PlayerStateMemento containing the current state.
     */
    public PlayerStateMemento createMemento() {
        return new PlayerStateMemento(health, score, positionX, positionY);
    }

    /**
     * Restores the player state from the given memento.
     *
     * @param memento The PlayerStateMemento to restore from.
     */
    public void restoreMemento(PlayerStateMemento memento) {
        this.health = memento.getHealth();
        this.score = memento.getScore();
        this.positionX = memento.getPositionX();
        this.positionY = memento.getPositionY();
    }

    // public int getHealth() {
    //     return health;
    // }

    /**
     * Updates the position of the user plane based on velocity multipliers.
     */
    @Override
    public void updatePosition() {
        // Handle vertical movement
        if (verticalVelocityMultiplier != 0) {
            double initialTranslateY = getTranslateY();
            moveVertically(VERTICAL_VELOCITY * verticalVelocityMultiplier);
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
            moveHorizontally(HORIZONTAL_VELOCITY * horizontalVelocityMultiplier);
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
        return numberOfKills;
    }

    public void incrementKillCount() {
        numberOfKills++;
    }
}
