// File: UserMovementStrategy.java
package com.example.demo.strategy;

import java.util.Set;
import com.example.demo.actor.plane.FighterPlane;
import com.example.demo.util.PlayerKeyBindings;
import com.example.demo.util.GameConstant;
import javafx.scene.input.KeyCode;

public class UserMovementStrategy implements MovementStrategy {
    private final Set<KeyCode> activeKeys;
    private final PlayerKeyBindings bindings;
    private final double speed;

    public UserMovementStrategy(Set<KeyCode> activeKeys, PlayerKeyBindings bindings, double speed) {
        this.activeKeys = activeKeys;
        this.bindings = bindings;
        this.speed = speed;
    }

    @Override
    public void move(FighterPlane plane, long now) {
        double deltaX = 0;
        double deltaY = 0;

        if (bindings.isMovingUp(activeKeys)) {
            deltaY -= speed;
        }
        if (bindings.isMovingDown(activeKeys)) {
            deltaY += speed;
        }
        if (bindings.isMovingLeft(activeKeys)) {
            deltaX -= speed;
        }
        if (bindings.isMovingRight(activeKeys)) {
            deltaX += speed;
        }

        plane.move(deltaX, deltaY);

        // Bounds checking
        double currentX = plane.getLayoutX() + plane.getTranslateX();
        double currentY = plane.getLayoutY() + plane.getTranslateY();

        if (currentX < 0) {
            plane.setTranslateX(-plane.getLayoutX());
        } else if (currentX + plane.getBoundsInParent().getWidth() > GameConstant.GameSettings.SCREEN_WIDTH) {
            plane.setTranslateX(GameConstant.GameSettings.SCREEN_WIDTH - plane.getLayoutX() - plane.getBoundsInParent().getWidth());
        }

        if (currentY < 0) {
            plane.setTranslateY(-plane.getLayoutY());
        } else if (currentY + plane.getBoundsInParent().getHeight() > GameConstant.GameSettings.SCREEN_HEIGHT) {
            plane.setTranslateY(GameConstant.GameSettings.SCREEN_HEIGHT - plane.getLayoutY() - plane.getBoundsInParent().getHeight());
        }
    }
}
