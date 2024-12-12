package com.example.demo.util;

import java.util.Collections;
import java.util.Set;
import javafx.scene.input.KeyCode;


/**
 * The PlayerKeyBindings class manages key bindings for player movement in four directions: up, down, left, and right.
 * It provides methods to check if any of the active keys correspond to the movement directions.
 *
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/util/PlayerKeyBindings.java">Github Source Code</a>
 */
public class PlayerKeyBindings {
    private final Set<KeyCode> upKeys;
    private final Set<KeyCode> downKeys;
    private final Set<KeyCode> leftKeys;
    private final Set<KeyCode> rightKeys;

    /**
     * Constructs a PlayerKeyBindings object with the specified key sets for movement directions.
     *
     * @param upKeys    the set of key codes that represent the "up" movement
     * @param downKeys  the set of key codes that represent the "down" movement
     * @param leftKeys  the set of key codes that represent the "left" movement
     * @param rightKeys the set of key codes that represent the "right" movement
     */
    public PlayerKeyBindings(Set<KeyCode> upKeys, Set<KeyCode> downKeys, Set<KeyCode> leftKeys, Set<KeyCode> rightKeys) {
        this.upKeys = upKeys;
        this.downKeys = downKeys;
        this.leftKeys = leftKeys;
        this.rightKeys = rightKeys;
    }

    /**
     * Checks if any of the keys in the provided set of active keys are mapped to the "move up" action.
     *
     * @param activeKeys the set of currently active keys
     * @return true if any of the active keys are mapped to the "move up" action, false otherwise
     */
    public boolean isMovingUp(Set<KeyCode> activeKeys) {
        return !Collections.disjoint(upKeys, activeKeys);
    }

    /**
     * Checks if any of the keys in the specified set of active keys are associated with moving down.
     *
     * @param activeKeys the set of currently active keys
     * @return true if any of the active keys are associated with moving down, {@code false} otherwise
     */
    public boolean isMovingDown(Set<KeyCode> activeKeys) {
        return !Collections.disjoint(downKeys, activeKeys);
    }

    /**
     * Checks if the player is moving left based on the active keys.
     *
     * @param activeKeys a set of currently active key codes
     * @return true if any of the keys in the leftKeys set are active, false otherwise
     */
    public boolean isMovingLeft(Set<KeyCode> activeKeys) {
        return !Collections.disjoint(leftKeys, activeKeys);
    }

    /**
     * Checks if the player is moving to the right based on the active keys.
     *
     * @param activeKeys a set of currently active key codes
     * @return true if any of the keys in the rightKeys set are present in the activeKeys set, false otherwise
     */
    public boolean isMovingRight(Set<KeyCode> activeKeys) {
        return !Collections.disjoint(rightKeys, activeKeys);
    }
}
