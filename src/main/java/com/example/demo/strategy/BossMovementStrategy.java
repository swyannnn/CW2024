package com.example.demo.strategy;

import com.example.demo.actor.plane.FighterPlane;
import com.example.demo.util.GameConstant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Strategy for moving the BossPlane both horizontally and vertically based on a move pattern.
 */
public class BossMovementStrategy implements MovementStrategy {
    private final double yUpperBound;
    private final double yLowerBound;

    // Movement pattern for vertical movement
    private final List<Integer> movePattern;
    private int consecutiveMovesInSameDirection;
    private int currentMoveIndex;
    private final int maxFramesWithSameMove;

    public BossMovementStrategy() {
        this.yUpperBound = GameConstant.BossPlane.Y_POSITION_UPPER_BOUND;
        this.yLowerBound = GameConstant.BossPlane.Y_POSITION_LOWER_BOUND;
        this.maxFramesWithSameMove = GameConstant.BossPlane.MAX_FRAMES_WITH_SAME_MOVE;

        this.movePattern = new ArrayList<>();
        this.consecutiveMovesInSameDirection = 0;
        this.currentMoveIndex = 0;
        initializeMovePattern();
    }

    /**
     * Initializes the movement pattern for vertical movement.
     */
    private void initializeMovePattern() {
        for (int i = 0; i < GameConstant.BossPlane.MOVE_FREQUENCY_PER_CYCLE; i++) {
            movePattern.add(GameConstant.BossPlane.VERTICAL_VELOCITY);
            movePattern.add(-GameConstant.BossPlane.VERTICAL_VELOCITY);
            movePattern.add(GameConstant.BossPlane.ZERO);
        }
        Collections.shuffle(movePattern);
    }

    /**
     * Determines the next vertical movement delta.
     *
     * @return The vertical movement delta.
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
