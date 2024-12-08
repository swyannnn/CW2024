package com.example.demo.strategy.firing;

import com.example.demo.actor.plane.FighterPlane;

/**
 * The FiringStrategy interface defines the contract for different firing strategies
 * that can be implemented for a FighterPlane. Implementations of this interface
 * will provide specific behavior for firing weapons.
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
