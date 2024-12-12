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
     * @param config The ProjectileConfig containing all necessary configuration.
     */
    public BossProjectile(ProjectileConfig config) {
        super(config);
    }

    // Add any BossProjectile-specific behavior here
}
