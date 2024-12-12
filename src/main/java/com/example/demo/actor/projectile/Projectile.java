package com.example.demo.actor.projectile;

import com.example.demo.actor.ActiveActor;
import com.example.demo.util.GameConstant;

/**
 * Abstract Projectile class representing a generic projectile in the game.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/actor/projectile/Projectile.java">Github Source Code</a>
 * @see ActiveActor
 */
public abstract class Projectile extends ActiveActor {
    protected double horizontalVelocity;
    protected double xUpperBound = GameConstant.Projectile.X_UPPER_BOUND;
    protected double xLowerBound = GameConstant.Projectile.X_LOWER_BOUND;

    /**
     * Constructor for Projectile.
     *
     * @param config The ProjectileConfig containing all necessary configuration.
     */
    public Projectile(ProjectileConfig config) {
        super(config.getType().getImageName(), config.getType().getImageHeight(),
              config.getInitialXPos(), config.getInitialYPos());
        this.horizontalVelocity = config.getType().getHorizontalVelocity();
    }

    /**
     * Inflicts damage to the projectile, causing it to be destroyed.
     *
     * @return true indicating the projectile has taken damage and is destroyed.
     */
    @Override
    public boolean takeDamage() {
        this.destroy();
        return true;
    }

    /**
     * Updates the projectile's position and checks if it is out of bounds.
     * 
     * @param now The current timestamp in nanoseconds.
     * 
     * This method moves the projectile horizontally based on its velocity.
     * It then checks the current horizontal position of the projectile.
     * If the projectile's position is outside the defined bounds (xUpperBound or xLowerBound),
     * the projectile is destroyed to save memory.
     */
    public void update(long now) {
        moveHorizontally(horizontalVelocity);
        double currentX = getLayoutX() + getTranslateX();
        if (currentX < xUpperBound || currentX > xLowerBound ) {
            this.destroy();
        }
    }
}
