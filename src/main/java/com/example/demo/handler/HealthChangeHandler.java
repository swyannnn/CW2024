package com.example.demo.handler;

import com.example.demo.actor.plane.UserPlane;

/**
 * Interface for listening to changes in the health of a user plane.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/handler/HealthChangeHandler.java">Github Source Code</a>
 */
public interface HealthChangeHandler {
    /**
     * Called when the health of the user plane changes.
     *
     * @param player The user plane whose health has changed.
     * @param newHealth The new health of the user plane.
     */
    void onHealthChange(UserPlane player, int newHealth);
}
