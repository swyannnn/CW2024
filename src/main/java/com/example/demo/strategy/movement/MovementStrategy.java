package com.example.demo.strategy.movement;

import com.example.demo.actor.plane.FighterPlane;

/**
 * The MovementStrategy interface defines a strategy for moving a FighterPlane.
 * Implementations of this interface will provide specific movement behaviors.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/strategy/movement/MovementStrategy.java">Github Source Code</a>
 * @see FighterPlane
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
