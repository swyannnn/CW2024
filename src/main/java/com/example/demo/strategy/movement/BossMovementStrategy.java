package com.example.demo.strategy.movement;

import com.example.demo.actor.plane.FighterPlane;
import com.example.demo.util.GameConstant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The BossMovementStrategy class implements the MovementStrategy interface
 * and defines the movement behavior for the boss plane in the game.
 * The boss plane moves vertically within specified boundaries, following a predefined pattern.
 * The movement pattern includes positive, negative, and zero velocities, introducing randomness.
 *
 * <p>This class provides the following functionalities:
 * <ul>
 *   <li>Constructs a BossMovementStrategy with specified vertical velocity and initializes movement boundaries.</li>
 *   <li>Initializes and shuffles the movement pattern for the boss plane.</li>
 *   <li>Determines the next vertical move based on the current move pattern and shuffles if necessary.</li>
 *   <li>Moves the boss plane vertically and reverses direction if boundaries are reached.</li>
 * </ul>
 *
 * @see MovementStrategy
 * @see FighterPlane
 */
public class BossMovementStrategy implements MovementStrategy {
    private final double yUpperBound;
    private final double yLowerBound;

    // Movement pattern for vertical movement
    private final List<Integer> movePattern;
    private int consecutiveMovesInSameDirection;
    private int currentMoveIndex;
    private final int maxFramesWithSameMove;
    private final int verticalVelocity;

    /**
     * Constructs a BossMovementStrategy object and initializes its movement boundaries,
     * maximum frames with the same move, and the movement pattern.
     * 
     * The movement boundaries are defined by the constants in GameConstant.BossPlane:
     * - yUpperBound: The upper boundary for the Y position of the boss plane.
     * - yLowerBound: The lower boundary for the Y position of the boss plane.
     * - maxFramesWithSameMove: The maximum number of frames the boss plane can move in the same direction.
     * 
     * The movement pattern is initialized by calling the initializeMovePattern method.
     * The consecutiveMovesInSameDirection and currentMoveIndex are also initialized to 0.
     */
    public BossMovementStrategy(int verticalVelocity) {
        this.verticalVelocity = verticalVelocity;
        this.yUpperBound = GameConstant.BossPlane.Y_POSITION_UPPER_BOUND;
        this.yLowerBound = GameConstant.BossPlane.Y_POSITION_LOWER_BOUND;
        this.maxFramesWithSameMove = GameConstant.BossPlane.MAX_FRAMES_WITH_SAME_MOVE;

        this.movePattern = new ArrayList<>();
        this.consecutiveMovesInSameDirection = 0;
        this.currentMoveIndex = 0;
        initializeMovePattern();
    }

    /**
     * Initializes the movement pattern for the boss plane.
     * The pattern consists of a sequence of vertical velocities
     * (both positive and negative) and zero velocities, repeated
     * for a number of cycles defined by the game constants.
     * The pattern is then shuffled to introduce randomness.
     */
    private void initializeMovePattern() {
        for (int i = 0; i < GameConstant.BossPlane.MOVE_FREQUENCY_PER_CYCLE; i++) {
            movePattern.add(verticalVelocity);
            movePattern.add(-verticalVelocity);
            movePattern.add(GameConstant.BossPlane.ZERO);
        }
        Collections.shuffle(movePattern);
    }


    /**
     * Determines the next vertical move for the boss plane based on the current move pattern.
     * If the move pattern is empty, it returns a constant value indicating no movement.
     * The method keeps track of consecutive moves in the same direction and shuffles the move pattern
     * if the maximum allowed frames with the same move are reached.
     * 
     * @return the next vertical move for the boss plane.
     */
    private int getNextVerticalMove() {
        if (movePattern.isEmpty()) {
            return GameConstant.BossPlane.ZERO;
        }

        int currentMove = movePattern.get(currentMoveIndex);
        consecutiveMovesInSameDirection++;

        if (consecutiveMovesInSameDirection >= maxFramesWithSameMove) {
            Collections.shuffle(movePattern);
            consecutiveMovesInSameDirection = 0;
            currentMoveIndex++;
            if (currentMoveIndex >= movePattern.size()) {
                currentMoveIndex = 0;
            }
        }

        return currentMove;
    }

    /**
     * Moves the fighter plane based on a predefined movement pattern.
     * The movement is primarily vertical and will reverse direction
     * if the plane reaches the specified vertical boundaries.
     *
     * @param plane the fighter plane to be moved
     * @param now the current timestamp in nanoseconds
     */
    @Override
    public void move(FighterPlane plane, long now) {
        // Handle vertical movement based on move pattern
        int verticalDelta = getNextVerticalMove();
        double currentY = plane.getLayoutY();
        double newY = currentY + verticalDelta;

        // Check vertical boundaries and reverse direction if needed
        if (newY < yUpperBound || newY > yLowerBound) {
            verticalDelta = -verticalDelta;
            newY = currentY + verticalDelta;
        }

        plane.setLayoutY(newY);
    }
}
