package com.example.demo.actor.projectile;

import com.example.demo.actor.plane.UserPlane;

/**
 * Configuration class for creating projectiles.
 */
public class ProjectileConfig {
    private ProjectileType type;
    private double initialXPos;
    private double initialYPos;
    private UserPlane userPlane; // Only required for UserProjectile

    // Constructor for Enemy and Boss Projectiles
    public ProjectileConfig(ProjectileType type, double initialXPos, double initialYPos) {
        if (type == ProjectileType.USER) {
            throw new IllegalArgumentException("Use the other constructor for USER projectile type.");
        }
        this.type = type;
        this.initialXPos = initialXPos;
        this.initialYPos = initialYPos;
    }

    // Constructor for User Projectile
    public ProjectileConfig(double initialXPos, double initialYPos, UserPlane userPlane) {
        this.type = ProjectileType.USER;
        this.initialXPos = initialXPos;
        this.initialYPos = initialYPos;
        this.userPlane = userPlane;
    }

    public ProjectileType getType() {
        return type;
    }

    public double getInitialXPos() {
        return initialXPos;
    }

    public double getInitialYPos() {
        return initialYPos;
    }

    public UserPlane getUserPlane() {
        return userPlane;
    }
}
