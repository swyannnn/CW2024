package com.example.demo.actor.projectile;

import com.example.demo.actor.plane.UserPlane;

/**
 * Factory class for creating Projectile instances.
 */
public class ProjectileFactory {

    /**
     * Creates a Projectile instance based on the provided ProjectileType.
     * Applicable for ENEMY and BOSS projectile types.
     *
     * @param type        The type of the projectile (ENEMY or BOSS).
     * @param initialXPos The initial X position of the projectile.
     * @param initialYPos The initial Y position of the projectile.
     * @return A new Projectile instance.
     */
    public Projectile createProjectile(ProjectileType type, double initialXPos, double initialYPos) {
        if (type == null) {
            throw new IllegalArgumentException("ProjectileType cannot be null.");
        }

        switch (type) {
            case ENEMY:
                return new EnemyProjectile(
                    type.getImageName(),
                    type.getImageHeight(),
                    initialXPos,
                    initialYPos,
                    type.getHorizontalVelocity()
                );
            case BOSS:
                return new BossProjectile(
                    type.getImageName(),
                    type.getImageHeight(),
                    initialXPos,
                    initialYPos,
                    type.getHorizontalVelocity()
                );
            default:
                throw new UnsupportedOperationException("Unsupported ProjectileType for this method: " + type);
        }
    }

    /**
     * Creates a Projectile instance based on the provided ProjectileType and UserPlane.
     * Specifically for USER projectile type.
     *
     * @param type        The type of the projectile (USER).
     * @param initialXPos The initial X position of the projectile.
     * @param initialYPos The initial Y position of the projectile.
     * @param userPlane   The UserPlane that owns this projectile.
     * @return A new Projectile instance.
     */
    public Projectile createProjectile(ProjectileType type, double initialXPos, double initialYPos, UserPlane userPlane) {
        if (type == null) {
            throw new IllegalArgumentException("ProjectileType cannot be null.");
        }

        if (type != ProjectileType.USER) {
            throw new UnsupportedOperationException("This method is only applicable for USER ProjectileType.");
        }

        if (userPlane == null) {
            throw new IllegalArgumentException("UserPlane cannot be null for USER ProjectileType.");
        }

        return new UserProjectile(
            type.getImageName(),
            type.getImageHeight(),
            initialXPos,
            initialYPos,
            type.getHorizontalVelocity(),
            userPlane
        );
    }

    /**
     * Convenience method to create ENEMY and BOSS projectiles when initial positions are known.
     * Assumes default initial positions if not provided.
     *
     * @param type The type of the projectile (ENEMY or BOSS).
     * @return A new Projectile instance.
     */
    public Projectile createProjectile(ProjectileType type) {
        // Define default initial positions or retrieve them from context/game state
        double defaultX = 0.0;
        double defaultY = 0.0;
        return createProjectile(type, defaultX, defaultY);
    }

    /**
     * Convenience method to create USER projectile when initial positions and UserPlane are known.
     *
     * @param type      The type of the projectile (USER).
     * @param userPlane The UserPlane that owns this projectile.
     * @return A new Projectile instance.
     */
    public Projectile createProjectile(ProjectileType type, UserPlane userPlane) {
        // Define default initial positions or retrieve them from context/game state
        double defaultX = 0.0;
        double defaultY = 0.0;
        return createProjectile(type, defaultX, defaultY, userPlane);
    }
}
