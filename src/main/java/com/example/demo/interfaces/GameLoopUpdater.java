package com.example.demo.interfaces;

/**
 * Functional interface for updating game logic.
 */
@FunctionalInterface
public interface GameLoopUpdater {
    /**
     * Updates the game logic based on the current timestamp.
     *
     * @param now The current timestamp in nanoseconds.
     */
    void update(long now);
}

