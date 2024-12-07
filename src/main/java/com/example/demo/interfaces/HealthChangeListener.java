package com.example.demo.interfaces;

import com.example.demo.actor.plane.UserPlane;

/**
 * Interface for listening to changes in the health of a user plane.
 */
public interface HealthChangeListener {
    /**
     * Called when the health of the user plane changes.
     *
     * @param player The user plane whose health has changed.
     * @param newHealth The new health of the user plane.
     */
    void onHealthChange(UserPlane player, int newHealth);
}
