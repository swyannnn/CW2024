package com.example.demo.interfaces;

import com.example.demo.actor.plane.UserPlane;

public interface HealthChangeListener {
    /**
     * Called when the health of the user plane changes.
     *
     * @param player The user plane whose health has changed.
     * @param newHealth The new health of the user plane.
     */
    void onHealthChange(UserPlane player, int newHealth);
}
