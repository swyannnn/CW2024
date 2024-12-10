package com.example.demo.actor.projectile;

import com.example.demo.actor.plane.UserPlane;

/**
 * UserProjectile class representing a projectile fired by the user.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/actor/projectile/UserProjectile.java">Github Source Code</a>
 * @see Projectile
 */
public class UserProjectile extends Projectile {
    private final UserPlane owner;

    /**
     * Constructor for UserProjectile.
     *
     * @param config The ProjectileConfig containing all necessary configuration.
     */
    public UserProjectile(ProjectileConfig config) {
        super(config);
        if (config.getType() != ProjectileType.USER) {
            throw new IllegalArgumentException("Invalid ProjectileType for UserProjectile.");
        }
        this.owner = config.getUserPlane();
        // Additional initialization specific to UserProjectile if needed
    }

    /**
     * Retrieves the owner of this projectile.
     *
     * @return the UserPlane object representing the owner of this projectile.
     */
    public UserPlane getOwner() {
        return owner;
    }

    // Add any UserProjectile-specific behavior here
}
