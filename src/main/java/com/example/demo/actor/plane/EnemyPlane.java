package com.example.demo.actor.plane;

import com.example.demo.util.PlaneConfig;

/**
 * EnemyPlane class representing the enemy aircraft in the game.
 */
public class EnemyPlane extends FighterPlane {
    /**
     * Constructs an EnemyPlane at the specified initial position.
     *
     * @param initialXPos The initial X position of the enemy plane (right-most part of the screen).
     * @param initialYPos The initial Y position of the enemy plane.
     */
    public EnemyPlane(PlaneConfig config) {
        super(config);
    }

    @Override
    protected void performAdditionalUpdates(long now) {
        // Implement any additional updates specific to EnemyPlane if necessary
    }
}