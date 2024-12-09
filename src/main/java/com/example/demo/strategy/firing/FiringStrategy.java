package com.example.demo.strategy.firing;

import com.example.demo.actor.plane.FighterPlane;

/**
 * The FiringStrategy interface defines the contract for different firing strategies
 * that can be implemented for a FighterPlane. Implementations of this interface
 * will provide specific behavior for firing weapons.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/strategy/firing/FiringStrategy.java">Github Source Code</a>
 */
public interface FiringStrategy {
    /**
     * Fires the weapon of the given fighter plane.
     *
     * @param plane the fighter plane that will fire its weapon
     * @param now the current time in milliseconds
     */
    void fire(FighterPlane plane, long now);
}
