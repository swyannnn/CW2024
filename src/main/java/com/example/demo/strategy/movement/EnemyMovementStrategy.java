package com.example.demo.strategy.movement;

import com.example.demo.actor.plane.FighterPlane;

/**
 * The EnemyMovementStrategy class implements the MovementStrategy interface
 * and defines the movement behavior for enemy planes in the game.
 * The enemy planes move horizontally to the left at a specified velocity.
 * If an enemy plane moves out of the left boundary of the screen, it is destroyed.
 *
 * <p>This class provides the following functionalities:
 * <ul>
 *   <li>Constructs an EnemyMovementStrategy with a specified horizontal velocity.</li>
 *   <li>Moves the fighter plane to the left by the specified speed and destroys it if it goes out of bounds.</li>
 * </ul>
 *
 * @see MovementStrategy
 * @see FighterPlane
 */
public class EnemyMovementStrategy implements MovementStrategy {
    private final int horizontalVelocity;


    /**
     * Constructs an EnemyMovementStrategy with the specified horizontal velocity.
     *
     * @param horizontalVelocity the horizontal velocity of the enemy movement
     */
    public EnemyMovementStrategy(int horizontalVelocity) {
        this.horizontalVelocity = horizontalVelocity;
    }

    /**
     * Moves the fighter plane to the left by a specified speed.
     * If the plane moves out of the left boundary of the screen, it is destroyed.
     *
     * @param plane the fighter plane to be moved
     * @param now the current timestamp in nanoseconds
     */
    @Override
    public void move(FighterPlane plane, long now) {
        double newX = plane.getLayoutX() + horizontalVelocity;

        // Destroy the plane if it goes out of bounds
        if (newX + plane.getBoundsInParent().getWidth() < 0) {
            plane.destroy();
            return;
        }
        plane.setLayoutX(newX);
    }
}

