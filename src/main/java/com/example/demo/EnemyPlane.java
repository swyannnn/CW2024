package com.example.demo;

import com.example.demo.controller.Controller;
import com.example.demo.manager.ActorManager;

/**
 * EnemyPlane class representing the enemy aircraft in the game.
 */
public class EnemyPlane extends FighterPlane {
    // Specific properties for EnemyPlane
    private static final String IMAGE_NAME = "enemyplane.png";
    private static final int IMAGE_HEIGHT = 150;
    private static final int HORIZONTAL_VELOCITY = -3; // Moves to the left
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
    private static final int INITIAL_HEALTH = 1;
    private static final long FIRE_INTERVAL_NANOSECONDS = 1_000_000_000; // Fire every 1 second
    private static final double FIRE_RATE = 0.5; // Probability of firing per interval

    private final Controller controller;

    /**
     * Constructs an EnemyPlane at the specified initial position.
     *
     * @param initialXPos The initial X position of the enemy plane (right-most part of the screen).
     * @param initialYPos The initial Y position of the enemy plane.
     * @param controller  The game controller.
     */
    public EnemyPlane(double initialXPos, double initialYPos, Controller controller) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH, controller, FIRE_INTERVAL_NANOSECONDS);
        this.controller = controller;
    }

    /**
     * Fires a projectile from the enemy plane's current position.
     */
    @Override
    public void fireProjectile() {
        if (Math.random() < FIRE_RATE) { // Use the specified firing probability
            double projectileX = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileY = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);

            EnemyProjectile projectile = new EnemyProjectile(projectileX, projectileY);
            ActorManager.getInstance(controller.getGameStateManager().getActorManager().getRoot()).addEnemyProjectile(projectile);

            System.out.println("EnemyPlane fired a projectile at: " + projectileX + ", " + projectileY);
        }
    }

    /**
     * Updates the position of the enemy plane, moving it horizontally to the left.
     * Stops firing and removes the enemy plane from the game if it moves off-screen.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
        System.out.println("EnemyPlane position: X=" + getTranslateX() + ", Y=" + getTranslateY());

        if (isOutOfHorizontalBounds()) {
            stopFiring();
            ActorManager.getInstance(controller.getGameStateManager().getActorManager().getRoot()).removeEnemyUnit(this);
            System.out.println("EnemyPlane removed for moving off-screen.");
        }
    }

    /**
     * Checks if the enemy plane is out of horizontal bounds.
     *
     * @return True if out of bounds, otherwise false.
     */
    private boolean isOutOfHorizontalBounds() {
        double newPositionX = getLayoutX() + getTranslateX();
        return newPositionX < -getWidth();
    }

    /**
     * Updates the actor's state, called in each frame of the game loop.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }
}
