package com.example.demo.actor.plane;

import com.example.demo.actor.ActiveActor;
import com.example.demo.strategy.FiringStrategy;
import com.example.demo.strategy.MovementStrategy;
import com.example.demo.util.PlaneConfig;

/**
 * FighterPlane class representing a generic fighter plane in the game.
 * Both EnemyPlane and UserPlane extend this class.
 */
public abstract class FighterPlane extends ActiveActor {
    protected int health;
    private long lastFireTime = 0;
    private final long fireIntervalNanoseconds;
    protected final double fireRate;
    private FiringStrategy firingStrategy;
    protected MovementStrategy movementStrategy;

    /**
     * Constructs a FighterPlane with specified parameters.
     *
     * @param imageName             The image file name for the plane.
     * @param imageHeight           The height of the plane's image.
     * @param initialXPos           The initial X position.
     * @param initialYPos           The initial Y position.
     * @param health                The initial health.
     * @param fireIntervalNanoseconds The interval between fires in nanoseconds.
     */
    public FighterPlane(PlaneConfig config) {
        super(config.imageName, config.imageHeight, config.initialXPos, config.initialYPos);
        this.health = config.health;
        this.fireIntervalNanoseconds = config.fireIntervalNanoseconds;
        this.fireRate = config.fireRate;
        this.firingStrategy = config.firingStrategy;
        this.movementStrategy = config.movementStrategy;
    }

    @Override
    public boolean takeDamage() {
        health--;
        System.out.println(getClass().getSimpleName() + " took damage. Health: " + health);
        System.out.println(getClass().getSimpleName() + " took damage. Health: " + getHealth());
        if (healthAtZero()) {
            this.destroy();
            System.out.println(getClass().getSimpleName() + " destroyed.");
            return true; // Indicates that destruction occurred
        }
        return false; // Damage was applied, but not necessarily destruction
    }

    public double getProjectileXPosition(double xPositionOffset) {
        return getLayoutX() + getTranslateX() + xPositionOffset;
    }

    public double getProjectileYPosition(double yPositionOffset) {
        return getLayoutY() + getTranslateY() + yPositionOffset;
    }

    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }
    
    public void setFiringStrategy(FiringStrategy firingStrategy) {
        this.firingStrategy = firingStrategy;
    }

    public boolean healthAtZero() {
        return health <= 0;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean CanFire(FighterPlane plane) {
        return (plane instanceof UserPlane) || (plane instanceof EnemyPlane)|| (plane instanceof BossPlane);
    }

    /**
     * Template method to update the FighterPlane's state.
     * Handles firing logic and delegates movement to subclasses.
     *
     * @param now The current timestamp in nanoseconds.
     */
    public void update(long now) {
        if (firingStrategy != null && now - lastFireTime >= fireIntervalNanoseconds) {
            firingStrategy.fire(this, now);
            lastFireTime = now;
        }
        if (movementStrategy != null) {
            movementStrategy.move(this, now);
        }
        performAdditionalUpdates(now); 
    }

    /**
     * Hook method for subclasses to perform additional updates.
     * Subclasses can override this method to add behavior without altering the template.
     *
     * @param now The current timestamp in nanoseconds.
     */
    protected void performAdditionalUpdates(long now) {
        // Default implementation does nothing.
    }
}
