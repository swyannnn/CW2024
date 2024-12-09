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
     * @param imageName          The image file name for the user projectile.
     * @param imageHeight        The height of the user projectile image.
     * @param initialXPos        The initial X position of the projectile.
     * @param initialYPos        The initial Y position of the projectile.
     * @param horizontalVelocity The horizontal velocity of the projectile.
     * @param owner              The UserPlane that fired this projectile.
     */
    public UserProjectile(String imageName, int imageHeight, double initialXPos, double initialYPos, double horizontalVelocity, UserPlane owner) {
        super(imageName, imageHeight, initialXPos, initialYPos, horizontalVelocity);
        this.owner = owner;
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
