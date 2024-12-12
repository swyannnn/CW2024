package com.example.demo.actor.projectile;

import com.example.demo.util.GameConstant;


/**
 * Enum representing different types of projectiles in the game.
 * Each projectile type has an associated image name, image height, and horizontal velocity.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/actor/projectile/ProjectileType.java">Github Source Code</a>
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

    /**
     * Constructs a new ProjectileType with the specified image name, image height, and horizontal velocity.
     *
     * @param imageName the name of the image representing the projectile
     * @param imageHeight the height of the image representing the projectile
     * @param horizontalVelocity the horizontal velocity of the projectile
     */
    ProjectileType(String imageName, int imageHeight, double horizontalVelocity) {
        this.imageName = imageName;
        this.imageHeight = imageHeight;
        this.horizontalVelocity = horizontalVelocity;
    }

    /**
     * Retrieves the name of the image associated with this projectile type.
     *
     * @return the image name as a String.
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Returns the height of the image associated with this projectile type.
     *
     * @return the height of the image in pixels
     */
    public int getImageHeight() {
        return imageHeight;
    }

    /**
     * Returns the horizontal velocity of the projectile.
     *
     * @return the horizontal velocity as a double.
     */
    public double getHorizontalVelocity() {
        return horizontalVelocity;
    }
}
