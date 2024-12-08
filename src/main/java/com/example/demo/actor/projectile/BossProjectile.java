package com.example.demo.actor.projectile;

/**
 * BossProjectile class representing a projectile fired by the boss.
 */
public class BossProjectile extends Projectile {

    /**
     * Constructor for BossProjectile.
     *
     * @param imageName          The image file name for the boss projectile.
     * @param imageHeight        The height of the boss projectile image.
     * @param initialXPos        The initial X position of the projectile.
     * @param initialYPos        The initial Y position of the projectile.
     * @param horizontalVelocity The horizontal velocity of the projectile.
     */
    public BossProjectile(String imageName, int imageHeight, double initialXPos, double initialYPos, double horizontalVelocity) {
        super(imageName, imageHeight, initialXPos, initialYPos, horizontalVelocity);
    }

    // Add any BossProjectile-specific behavior here
}
