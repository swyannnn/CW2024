package com.example.demo.actor.projectile;

import com.example.demo.actor.plane.UserPlane;


/**
 * Factory class for creating different types of Projectile instances.
 * This class provides methods to create projectiles for ENEMY, BOSS, and USER types.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/actor/projectile/ProjectileFactory.java">Github Source Code</a>
 * @see ProjectileType
 */
public class ProjectileFactory {

    /**
     * Creates a Projectile instance based on the provided ProjectileConfig.
     * Applicable for ENEMY and BOSS projectile types.
     *
     * @param config The ProjectileConfig containing all necessary configuration.
     * @return A new Projectile instance.
     */
    public Projectile createProjectile(ProjectileConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("ProjectileConfig cannot be null.");
        }

        ProjectileType type = config.getType();
        if (type == null) {
            throw new IllegalArgumentException("ProjectileType cannot be null.");
        }

        switch (type) {
            case ENEMY:
                return new EnemyProjectile(config);
            case BOSS:
                return new BossProjectile(config);
            case USER:
                if (config.getUserPlane() == null) {
                    throw new IllegalArgumentException("UserPlane cannot be null for USER ProjectileType.");
                }
                return new UserProjectile(config);
            default:
                throw new UnsupportedOperationException("Unsupported ProjectileType: " + type);
        }
    }

    /**
     * Convenience method to create ENEMY and BOSS projectiles with specified initial positions.
     *
     * @param type The type of the projectile (ENEMY or BOSS).
     * @param initialXPos The initial X position of the projectile.
     * @param initialYPos The initial Y position of the projectile.
     * @return A new Projectile instance.
     */
    public Projectile createProjectile(ProjectileType type, double initialXPos, double initialYPos) {
        ProjectileConfig config = new ProjectileConfig(type, initialXPos, initialYPos);
        return createProjectile(config);
    }

    /**
     * Convenience method to create USER projectile with specified initial positions and a UserPlane.
     *
     * @param type        The type of the projectile (USER).
     * @param initialXPos The initial X position of the projectile.
     * @param initialYPos The initial Y position of the projectile.
     * @param userPlane   The UserPlane that owns this projectile.
     * @return A new Projectile instance.
     */
    public Projectile createProjectile(ProjectileType type, double initialXPos, double initialYPos, UserPlane userPlane) {
        ProjectileConfig config = new ProjectileConfig(initialXPos, initialYPos, userPlane);
        return createProjectile(config);
    }
}
