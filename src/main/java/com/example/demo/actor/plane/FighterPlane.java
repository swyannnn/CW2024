package com.example.demo.actor.plane;

import com.example.demo.actor.ActiveActor;
import com.example.demo.strategy.FiringStrategy;
import com.example.demo.strategy.MovementStrategy;
import com.example.demo.util.PlaneConfig;


/**
 * The FighterPlane class represents an abstract type of plane that can engage in combat.
 * It extends the ActiveActor class and includes properties and behaviors specific to a fighter plane.
 * 
 * <p>This class includes health management, firing and movement strategies, and provides a template method
 * for updating the plane's state. Subclasses should implement specific behaviors by overriding the 
 * performAdditionalUpdates method.</p>
 * 
 * <p>Key properties include:</p>
 * <ul>
 *   <li>health: The current health of the plane.</li>
 *   <li>fireIntervalNanoseconds: The interval between firing actions in nanoseconds.</li>
 *   <li>fireRate: The rate at which the plane can fire.</li>
 *   <li>firingStrategy: The strategy used for firing projectiles.</li>
 *   <li>movementStrategy: The strategy used for moving the plane.</li>
 * </ul>
 * 
 * <p>Key methods include:</p>
 * <ul>
 *   <li>{@link #takeDamage()}: Reduces the plane's health and checks for destruction.</li>
 *   <li>{@link #getProjectileXPosition(double)}: Calculates the X position for firing a projectile.</li>
 *   <li>{@link #getProjectileYPosition(double)}: Calculates the Y position for firing a projectile.</li>
 *   <li>{@link #setMovementStrategy(MovementStrategy)}: Sets the movement strategy.</li>
 *   <li>{@link #setFiringStrategy(FiringStrategy)}: Sets the firing strategy.</li>
 *   <li>{@link #healthAtZero()}: Checks if the plane's health is zero.</li>
 *   <li>{@link #getHealth()}: Returns the current health of the plane.</li>
 *   <li>{@link #setHealth(int)}: Sets the health of the plane.</li>
 *   <li>{@link #update(long)}: Updates the plane's state, handling firing and movement logic.</li>
 *   <li>{@link #performAdditionalUpdates(long)}: Hook method for subclasses to add additional update behavior.</li>
 * </ul>
 * 
 * <p>Constructor:</p>
 * <ul>
 *   <li>{@link #FighterPlane(PlaneConfig)}: Constructs a FighterPlane with specified parameters from a PlaneConfig object.</li>
 * </ul>
 * 
 * @see ActiveActor
 * @see FiringStrategy
 * @see MovementStrategy
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

    /**
     * Applies damage to the fighter plane by decrementing its health.
     * If the health reaches zero, the plane is destroyed.
     *
     * @return true if the plane is destroyed as a result of the damage, false otherwise.
     */
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

    /**
     * Calculates the X position of the projectile based on the current layout X position,
     * translation X position, and an additional offset.
     *
     * @param xPositionOffset the offset to be added to the current X position
     * @return the calculated X position of the projectile
     */
    public double getProjectileXPosition(double xPositionOffset) {
        return getLayoutX() + getTranslateX() + xPositionOffset;
    }

    /**
     * Calculates the Y position of the projectile based on the current layout Y position,
     * translation Y position, and a given offset.
     *
     * @param yPositionOffset the offset to be added to the current Y position
     * @return the calculated Y position of the projectile
     */
    public double getProjectileYPosition(double yPositionOffset) {
        return getLayoutY() + getTranslateY() + yPositionOffset;
    }

    /**
     * Sets the movement strategy for the fighter plane.
     * currently solely used by user planes
     * 
     * @param movementStrategy the movement strategy to be used by the fighter plane
     */
    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    /**
     * Checks if the health of the fighter plane is at zero or below.
     *
     * @return true if the health is less than or equal to zero, false otherwise.
     */
    public boolean healthAtZero() {
        return health <= 0;
    }

    /**
     * Retrieves the current health of the fighter plane.
     *
     * @return the health value of the fighter plane.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health of the fighter plane.
     *
     * @param health the new health value to be set
     */
    public void setHealth(int health) {
        this.health = health;
    }


    /**
     * Updates the state of the FighterPlane.
     *
     * @param now The current time in nanoseconds.
     * 
     * This method performs the following actions:
     * 1. If a firing strategy is set and the time since the last fire is greater than or equal to the fire interval, 
     *    it triggers the firing strategy and updates the last fire time.
     * 2. If a movement strategy is set, it triggers the movement strategy.
     * 3. Calls the performAdditionalUpdates method to handle any additional updates.
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
     * Performs additional updates specific to the fighter plane.
     * This method is intended to be overridden by subclasses to provide
     * custom update logic. The default implementation does nothing.
     *
     * @param now the current time in milliseconds
     */
    protected void performAdditionalUpdates(long now) {
        // Default implementation does nothing.
    }
}
