package com.example.demo.actor.projectile;

/**
 * The BossProjectile class represents a projectile specifically used by a boss character in the game.
 * It extends the Projectile class and inherits its properties and methods.
 * 
 * <p>This class can be used to create and manage boss projectiles with specific attributes such as
 * image name, image height, initial position, and horizontal velocity.</p>
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/actor/projectile/BossProjectile.java">Github Source Code</a>
 * @see Projectile
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
