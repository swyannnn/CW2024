package com.example.demo.util;

import java.util.Collections;
import java.util.Set;
import javafx.scene.input.KeyCode;

public class PlayerKeyBindings {
    private final Set<KeyCode> upKeys;
    private final Set<KeyCode> downKeys;
    private final Set<KeyCode> leftKeys;
    private final Set<KeyCode> rightKeys;
    private final KeyCode fireKey;

    public PlayerKeyBindings(Set<KeyCode> upKeys, Set<KeyCode> downKeys, Set<KeyCode> leftKeys, Set<KeyCode> rightKeys, KeyCode fireKey) {
        this.upKeys = upKeys;
        this.downKeys = downKeys;
        this.leftKeys = leftKeys;
        this.rightKeys = rightKeys;
        this.fireKey = fireKey;
    }

    public boolean isMovingUp(Set<KeyCode> activeKeys) {
        return !Collections.disjoint(upKeys, activeKeys);
    }

    public boolean isMovingDown(Set<KeyCode> activeKeys) {
        return !Collections.disjoint(downKeys, activeKeys);
    }

    public boolean isMovingLeft(Set<KeyCode> activeKeys) {
        return !Collections.disjoint(leftKeys, activeKeys);
    }

    public boolean isMovingRight(Set<KeyCode> activeKeys) {
        return !Collections.disjoint(rightKeys, activeKeys);
    }

    public boolean isFiring(Set<KeyCode> activeKeys) {
        return activeKeys.contains(fireKey);
    }
}
