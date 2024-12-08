package com.example.demo.actor.projectile;

import com.example.demo.util.GameConstant;

/**
 * Enumeration of different projectile types with their configurations.
 */
public enum ProjectileType {
    ENEMY(
        GameConstant.EnemyProjectile.IMAGE_NAME,
        GameConstant.EnemyProjectile.IMAGE_HEIGHT,
        GameConstant.EnemyProjectile.HORIZONTAL_VELOCITY
    ),
    BOSS(
        GameConstant.BossProjectile.IMAGE_NAME,
        GameConstant.BossProjectile.IMAGE_HEIGHT,
        GameConstant.BossProjectile.HORIZONTAL_VELOCITY
    ),
    USER(
        GameConstant.UserProjectile.IMAGE_NAME,
        GameConstant.UserProjectile.IMAGE_HEIGHT,
        GameConstant.UserProjectile.HORIZONTAL_VELOCITY
    );

    private final String imageName;
    private final int imageHeight;
    private final double horizontalVelocity;

    ProjectileType(String imageName, int imageHeight, double horizontalVelocity) {
        this.imageName = imageName;
        this.imageHeight = imageHeight;
        this.horizontalVelocity = horizontalVelocity;
    }

    public String getImageName() {
        return imageName;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public double getHorizontalVelocity() {
        return horizontalVelocity;
    }
}
