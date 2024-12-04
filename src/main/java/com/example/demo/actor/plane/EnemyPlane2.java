package com.example.demo.actor.plane;

import com.example.demo.actor.projectile.EnemyProjectile;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ActorManager;
import com.example.demo.util.GameConstant;

/**
 * EnemyPlane class representing the enemy aircraft in the game.
 */
public class EnemyPlane2 extends FighterPlane {
    private static final String imageName = GameConstant.EnemyPlane2.IMAGE_NAME;
    private static final int imageHeight = GameConstant.EnemyPlane2.IMAGE_HEIGHT;
    private static final int horizontalVelocity = GameConstant.EnemyPlane2.HORIZONTAL_VELOCITY; 
	private static final double projectileXPositionOffset = GameConstant.EnemyProjectile.PROJECTILE_X_POSITION_OFFSET;
	private static final double projectileYPositionOffset = GameConstant.EnemyProjectile.PROJECTILE_Y_POSITION_OFFSET;
    private static final int initialHealth = GameConstant.EnemyPlane2.INITIAL_HEALTH;
    private static final long fireIntervalNanoseconds = GameConstant.EnemyProjectile.FIRE_INTERVAL_NANOSECONDS; 
    private static final double fireRate = GameConstant.EnemyProjectile.FIRE_RATE;
    private static final double yUpperBound = GameConstant.EnemyPlane2.Y_UPPER_BOUND;
    private static final double yLowerBound = GameConstant.EnemyPlane2.Y_LOWER_BOUND;
    private static final double xUpperBound = GameConstant.EnemyPlane2.X_UPPER_BOUND;
    private static final double xLowerBound = GameConstant.EnemyPlane2.X_LOWER_BOUND;
    private ActorManager actorManager;

    /**
     * Constructs an EnemyPlane at the specified initial position.
     *
     * @param initialXPos The initial X position of the enemy plane (right-most part of the screen).
     * @param initialYPos The initial Y position of the enemy plane.
     * @param controller  The game controller.
     */
    public EnemyPlane2(Controller controller) {
        super(controller, imageName, imageHeight, xLowerBound, calculateInitialYPos(), initialHealth, fireIntervalNanoseconds);
        this.actorManager = controller.getGameStateManager().getActorManager();
        setHorizontalBounds(xUpperBound, xLowerBound);
        setVerticalBounds(yUpperBound, yLowerBound);
    }

    /**
     * Calculates the initial Y position within the defined vertical bounds.
     *
     * @return The initial Y position.
     */
    private static double calculateInitialYPos() {
        return Math.random() * (yLowerBound - yUpperBound) + yUpperBound;
    }
    
    /**
     * Fires a projectile from the enemy plane's current position.
     */
    @Override
    public void fireProjectile() {
        if (Math.random() < fireRate) { // Use the specified firing probability
            double projectileX = getProjectileXPosition(projectileXPositionOffset);
            double projectileY = getProjectileYPosition(projectileYPositionOffset);

            EnemyProjectile projectile = new EnemyProjectile(projectileX, projectileY);
            actorManager.addActor(projectile);
        }
    }

    @Override
    protected void performMovement(long now) {
        moveHorizontally(horizontalVelocity);
        if (isOutOfBounds()) {
            actorManager.removeActor(this); // Remove self from ActorManager
            System.out.println("EnemyPlane removed for moving off-screen.");
        }
    }

    @Override
    protected void performAdditionalUpdates(long now) {
        // Implement any additional updates specific to EnemyPlane if necessary
    }
}