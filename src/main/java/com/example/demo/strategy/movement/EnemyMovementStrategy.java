package com.example.demo.strategy.movement;

import com.example.demo.actor.plane.FighterPlane;
import com.example.demo.util.GameConstant;


/**
 * The EnemyMovementStrategy class implements the MovementStrategy interface
 * and defines the movement behavior for enemy planes in the game.
 * 
 * <p>This strategy moves the enemy plane horizontally to the left at a constant speed.
 * If the plane moves out of the screen bounds, it is destroyed.</p>
 * 
 * <p>The speed of the enemy plane is determined by the constant 
 * {@code GameConstant.EnemyPlane.HORIZONTAL_VELOCITY}.</p>
 * 
 * @see MovementStrategy
 */
public class EnemyMovementStrategy implements MovementStrategy {
    private final int horizontalVelocity;


    /**
     * Constructs an EnemyMovementStrategy with the default speed
     * set to the horizontal velocity of the enemy plane as defined
     * in the GameConstant class.
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
        double newX = plane.getLayoutX() - horizontalVelocity;

        // Destroy the plane if it goes out of bounds
        if (newX + plane.getBoundsInParent().getWidth() < 0) {
            plane.destroy();
            return;
        }
        plane.setLayoutX(newX);
    }
}

