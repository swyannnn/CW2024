package com.example.demo.actor.projectile;

/**
 * EnemyProjectile class representing a projectile fired by enemies.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/actor/projectile/EnemyProjectile.java">Github Source Code</a>
 * @see Projectile
 */
public class EnemyProjectile extends Projectile {

    /**
     * Constructor for EnemyProjectile.
     *
     * @param imageName          The image file name for the enemy projectile.
     * @param imageHeight        The height of the enemy projectile image.
     * @param initialXPos        The initial X position of the projectile.
     * @param initialYPos        The initial Y position of the projectile.
     * @param horizontalVelocity The horizontal velocity of the projectile.
     */
    public EnemyProjectile(String imageName, int imageHeight, double initialXPos, double initialYPos, double horizontalVelocity) {
        super(imageName, imageHeight, initialXPos, initialYPos, horizontalVelocity);
    }

    // Add any EnemyProjectile-specific behavior here
}
