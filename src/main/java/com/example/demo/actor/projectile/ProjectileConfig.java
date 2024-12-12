package com.example.demo.actor.projectile;

import com.example.demo.actor.plane.UserPlane;


/**
 * Configuration class for projectiles in the game.
 * This class holds the initial position and type of the projectile.
 * It also holds a reference to the UserPlane if the projectile is of type USER.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/actor/projectile/Projectile.java">Github Source Code</a>
 * @see ProjectileType
 */
public class ProjectileConfig {
    private ProjectileType type;
    private double initialXPos;
    private double initialYPos;
    private UserPlane userPlane; // Only required for UserProjectile because we need to know it owner

    /**
     * Constructs a ProjectileConfig object with the specified type and initial positions.
     * 
     * @param type The type of the projectile. Must not be ProjectileType.USER.
     * @param initialXPos The initial X position of the projectile.
     * @param initialYPos The initial Y position of the projectile.
     * @throws IllegalArgumentException if the type is ProjectileType.USER.
     */
    public ProjectileConfig(ProjectileType type, double initialXPos, double initialYPos) {
        if (type == ProjectileType.USER) {
            throw new IllegalArgumentException("Use the other constructor for USER projectile type.");
        }
        this.type = type;
        this.initialXPos = initialXPos;
        this.initialYPos = initialYPos;
    }

    /**
     * Constructs a new ProjectileConfig with the specified initial position and user plane.
     *
     * @param initialXPos the initial X position of the projectile
     * @param initialYPos the initial Y position of the projectile
     * @param userPlane the user plane associated with the projectile
     */
    public ProjectileConfig(double initialXPos, double initialYPos, UserPlane userPlane) {
        this.type = ProjectileType.USER;
        this.initialXPos = initialXPos;
        this.initialYPos = initialYPos;
        this.userPlane = userPlane;
    }

    /**
     * Retrieves the type of the projectile.
     *
     * @return the type of the projectile as a {@link ProjectileType}.
     */
    public ProjectileType getType() {
        return type;
    }

    /**
     * Returns the initial X position of the projectile.
     *
     * @return the initial X position as a double.
     */
    public double getInitialXPos() {
        return initialXPos;
    }

    /**
     * Gets the initial Y position of the projectile.
     *
     * @return the initial Y position as a double.
     */
    public double getInitialYPos() {
        return initialYPos;
    }

    /**
     * Retrieves the UserPlane associated with this configuration.
     *
     * @return the UserPlane object.
     */
    public UserPlane getUserPlane() {
        return userPlane;
    }
}
