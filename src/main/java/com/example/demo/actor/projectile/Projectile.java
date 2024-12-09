package com.example.demo.actor.projectile;

import com.example.demo.actor.ActiveActor;
import com.example.demo.util.GameConstant;

/**
 * Abstract Projectile class representing a generic projectile in the game.
 */
public abstract class Projectile extends ActiveActor {
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
