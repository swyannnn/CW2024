package com.example.demo.actor.projectile;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.util.GameConstant;

/**
 * Abstract Projectile class representing a generic projectile in the game.
 */
public abstract class Projectile extends ActiveActorDestructible {
    protected double horizontalVelocity;
    protected double xUpperBound = GameConstant.Projectile.X_UPPER_BOUND;
    protected double xLowerBound = GameConstant.Projectile.X_LOWER_BOUND;

    /**
     * Constructor for Projectile.
     *
     * @param imageName         The image file name for the projectile.
     * @param imageHeight       The height of the projectile image.
     * @param initialXPos       The initial X position of the projectile.
     * @param initialYPos       The initial Y position of the projectile.
     * @param horizontalVelocity The horizontal velocity of the projectile.
     */
    public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos, double horizontalVelocity) {
        super(imageName, imageHeight, initialXPos, initialYPos);
        this.horizontalVelocity = horizontalVelocity;
        setHorizontalBounds(xUpperBound, xLowerBound);
    }

    @Override
    public boolean takeDamage() {
        this.destroy();
        return true;
    }

    /**
     * Moves the projectile horizontally and checks for out-of-bounds conditions.
     */
    public void update(long now) {
        moveHorizontally(horizontalVelocity);
        if (isOutOfBounds()) {
            this.destroy();
        }
    }
}
