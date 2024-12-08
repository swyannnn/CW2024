package com.example.demo.strategy.movement;

import com.example.demo.actor.plane.FighterPlane;

/**
 * The MovementStrategy interface defines a strategy for moving a FighterPlane.
 * Implementations of this interface will provide specific movement behaviors.
 */
public interface MovementStrategy {
    /**
     * Defines the movement behavior for a FighterPlane.
     *
     * @param plane the FighterPlane that will be moved
     * @param now the current time in milliseconds
     */
    void move(FighterPlane plane, long now);
}
