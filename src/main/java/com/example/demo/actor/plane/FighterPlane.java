package com.example.demo.actor.plane;

import com.example.demo.actor.ActiveActor;
import com.example.demo.strategy.firing.FiringStrategy;
import com.example.demo.strategy.movement.MovementStrategy;

/**
 * The FighterPlane class represents an abstract type of plane that can engage in combat.
 * It extends the ActiveActor class and includes properties and behaviors specific to a fighter plane.
 * 
 * <p>This class includes health management, firing and movement strategies, and provides a template method
 * for updating the plane's state. Subclasses should implement specific behaviors by overriding the 
 * performAdditionalUpdates method.</p>
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/actor/plane/FighterPlane.java">Github Source Code</a>
 * @see ActiveActor
 * @see FiringStrategy
 * @see MovementStrategy
 */
public abstract class FighterPlane extends ActiveActor {
    /**
     * The health of the fighter plane.
     * This value represents the current health points of the plane.
     * It is used to determine the plane's survivability in combat.
     */
    protected int health;

    /**
     * The rate at which the fighter plane can fire its weapons.
     * This value is a double representing the number of shots per unit time.
     */
    protected final double fireRate;
    
    /**
     * The strategy used to determine the movement behavior of the fighter plane.
     * This allows for different movement algorithms to be applied to the plane.
     */
    protected MovementStrategy movementStrategy;
    private long lastFireTime = 0;
    private final long fireIntervalNanoseconds;
    private FiringStrategy firingStrategy;

    /**
     * Constructs a new FighterPlane with the specified configuration.
     *
     * @param config the configuration for the FighterPlane, containing properties such as
     *               image name, image height, initial position, health, fire interval,
     *               fire rate, firing strategy, and movement strategy.
     * 
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
        if (healthAtZero()) {
            this.destroy();
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
        if (healthAtZero()){
            this.destroy();
        }
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
     * @param now the current time in nanoseconds
     */
    protected void performAdditionalUpdates(long now) {
        // Default implementation does nothing.
    }
}
