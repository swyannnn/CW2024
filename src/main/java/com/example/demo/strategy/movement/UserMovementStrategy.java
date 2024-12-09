// File: UserMovementStrategy.java
package com.example.demo.strategy.movement;

import java.util.Set;
import com.example.demo.actor.plane.FighterPlane;
import com.example.demo.util.PlayerKeyBindings;
import com.example.demo.util.GameConstant;
import javafx.scene.input.KeyCode;

/**
 * The UserMovementStrategy class implements the MovementStrategy interface
 * and defines the movement behavior for the player's plane in the game.
 * 
 * <p>This strategy moves the player's plane based on the active key inputs.
 * The plane can move up, down, left, or right depending on the keys pressed.</p>
 * 
 * <p>The speed of the player's plane is determined by the {@code speed} parameter
 * passed to the constructor.</p>
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/strategy/movement/UserMovementStrategy.java">Github Source Code</a>
 * @see MovementStrategy
 * @see FighterPlane
 */
public class UserMovementStrategy implements MovementStrategy {
    private final Set<KeyCode> activeKeys;
    private final PlayerKeyBindings bindings;
    private final double speed;

    /**
     * Constructs a UserMovementStrategy with the specified active keys, player key bindings, and movement speed.
     *
     * @param activeKeys a set of KeyCode representing the currently active keys
     * @param bindings the PlayerKeyBindings object that defines the key bindings for the player
     * @param speed the movement speed of the player
     */
    public UserMovementStrategy(Set<KeyCode> activeKeys, PlayerKeyBindings bindings, int speed) {
        this.activeKeys = activeKeys;
        this.bindings = bindings;
        this.speed = speed;
    }

    /**
     * Moves the fighter plane based on the current active keys and speed.
     * The movement is bounded within the screen dimensions defined in GameConstant.GameSettings.
     *
     * @param plane the fighter plane to be moved
     * @param now the current timestamp
     */
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
