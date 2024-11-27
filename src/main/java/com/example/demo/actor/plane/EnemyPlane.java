package com.example.demo.actor.plane;

import com.example.demo.actor.projectile.EnemyProjectile;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ActorManager;
import com.example.demo.util.GameConstant;

/**
 * EnemyPlane class representing the enemy aircraft in the game.
 */
public class EnemyPlane extends FighterPlane {
    private static final String imageName = GameConstant.EnemyPlane.IMAGE_NAME;
    private static final int imageHeight = GameConstant.EnemyPlane.IMAGE_HEIGHT;
    private static final int horizontalVelocity = GameConstant.EnemyPlane.HORIZONTAL_VELOCITY; // Moves to the left
	private static final double projectileXPositionOffset = GameConstant.EnemyProjectile.PROJECTILE_X_POSITION_OFFSET;
	private static final double projectileYPositionOffset = GameConstant.EnemyProjectile.PROJECTILE_Y_POSITION_OFFSET;
    private static final int initialHealth = GameConstant.EnemyPlane.INITIAL_HEALTH;
    private static final long fireIntervalNanoseconds = GameConstant.EnemyProjectile.FIRE_INTERVAL_NANOSECONDS; // Fire every 1 second
    private static final double fireRate = GameConstant.EnemyProjectile.FIRE_RATE;
    private ActorManager actorManager;
    private Controller controller;

    /**
     * Constructs an EnemyPlane at the specified initial position.
     *
     * @param initialXPos The initial X position of the enemy plane (right-most part of the screen).
     * @param initialYPos The initial Y position of the enemy plane.
     * @param controller  The game controller.
     */
    public EnemyPlane(double initialXPos, double initialYPos, Controller controller) {
        super(imageName, imageHeight, initialXPos, initialYPos, initialHealth, fireIntervalNanoseconds);
        this.actorManager = controller.getGameStateManager().getActorManager();
        this.controller = controller;
    }

    /**
     * Fires a projectile from the enemy plane's current position.
     */
    @Override
    public void fireProjectile() {
        if (Math.random() < fireRate) { // Use the specified firing probability
            double projectileX = getProjectileXPosition(projectileXPositionOffset);
            double projectileY = getProjectileYPosition(projectileYPositionOffset);

            EnemyProjectile projectile = new EnemyProjectile(projectileX, projectileY, controller);
            System.out.println("EnemyPlane firing projectile at X=" + projectileX + ", Y=" + projectileY);
            ActorManager.getInstance(actorManager.getRoot()).addEnemyProjectile(projectile);
        }
    }

    /**
     * Updates the position of the enemy plane, moving it horizontally to the left.
     * Stops firing and removes the enemy plane from the game if it moves off-screen.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(horizontalVelocity);
        // System.out.println("EnemyPlane position: X=" + getTranslateX() + ", Y=" + getTranslateY());

        if (isOutOfHorizontalBounds()) {
            stopFiring(); // Stop firing before removal
            actorManager.removeEnemyUnit(this);
            // System.out.println("EnemyPlane removed for moving off-screen.");
        }
    }

    /**
     * Checks if the enemy plane is out of horizontal bounds.
     *
     * @return True if out of bounds, otherwise false.
     */
    private boolean isOutOfHorizontalBounds() {
        double newPositionX = getLayoutX() + getTranslateX();
        if (newPositionX < -getWidth()) {
            // System.out.println("enemy plane position out of screen: " + newPositionX + "=" + getLayoutX() + "+" + getTranslateX());
        }
        return newPositionX < 0;
    }

    /**
     * Updates the actor's state, called in each frame of the game loop.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }
}