package com.example.demo.handler;

/**
 * Functional interface representing a handler for the game loop.
 * Implementations of this interface provide the logic to update the game state.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/handler/GameLoopHandler.java">Github Source Code</a>
 */
public interface GameLoopHandler {
    /**
     * Updates the game logic based on the current timestamp.
     *
     * @param now The current timestamp in nanoseconds.
     */
    void update(long now);
}

