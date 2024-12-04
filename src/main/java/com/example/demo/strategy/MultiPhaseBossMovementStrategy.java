package com.example.demo.strategy;

import com.example.demo.actor.plane.FighterPlane;
import com.example.demo.actor.plane.MultiPhaseBossPlane;
import com.example.demo.util.GameConstant;

/**
 * Handles movement logic for MultiPhaseBossPlane across different phases.
 */
public class MultiPhaseBossMovementStrategy implements MovementStrategy {
    private int currentPhase;
    private long phaseStartTime;
    
    // Constants for movement
    private final double horizontalVelocityPhase1;
    private final double horizontalVelocityPhase2;
    private final double horizontalVelocityPhase3;
    private final double ySpeedPhase2;
    private final int maxFramesWithSameMove;
    
    // Movement State for Phase 3
    private MultiPhaseBossPlane.MovementState movementState;
    private int movementFrameCount;

    public MultiPhaseBossMovementStrategy(MultiPhaseBossPlane bossPlane) {
        this.currentPhase = 1;
        this.phaseStartTime = System.nanoTime();
        this.horizontalVelocityPhase1 = bossPlane.getHorizontalVelocityPhase1();
        this.horizontalVelocityPhase2 = bossPlane.getHorizontalVelocityPhase2();
        this.horizontalVelocityPhase3 = bossPlane.getHorizontalVelocityPhase3();
        this.ySpeedPhase2 = bossPlane.getYSpeedPhase2();
        this.maxFramesWithSameMove = bossPlane.getMaxFramesWithSameMove();
        this.movementState = MultiPhaseBossPlane.MovementState.HORIZONTAL;
        this.movementFrameCount = 0;
    }

    @Override
    public void move(FighterPlane plane, long now) {
        MultiPhaseBossPlane boss = (MultiPhaseBossPlane) plane;

        switch (currentPhase) {
            case 1:
                // Phase 1: Simple horizontal movement
                moveHorizontally(boss, boss.getHorizontalVelocityPhase1());
                break;

            case 2:
                // Phase 2: Sine wave movement
                moveInSineWave(boss, now);
                break;

            case 3:
                // Phase 3: Alternating between horizontal and sine wave movement
                alternateMovement(boss, now);
                break;

            default:
                break;
        }

        // Boundary checking
        boss.constrainWithinBounds();
    }

    /**
     * Handles horizontal movement.
     */
    private void moveHorizontally(MultiPhaseBossPlane boss, double velocity) {
        double newX = boss.getLayoutX() - velocity;
        boss.setLayoutX(newX);
    }

    /**
     * Handles sine wave movement for Phase 2.
     */
    private void moveInSineWave(MultiPhaseBossPlane boss, long now) {
        // Calculate time elapsed since Phase 2 started
        double timeInSeconds = (now - phaseStartTime) / 1_000_000_000.0;

        double amplitude = 100; // Horizontal oscillation amplitude in pixels
        double frequency = 0.5; // Oscillation frequency in Hz

        // Calculate new X position based on sine wave
        double sineValue = Math.sin(2 * Math.PI * frequency * timeInSeconds);
        double newX = boss.getSineWaveBaseX() + amplitude * sineValue;

        // Update Y position based on vertical velocity
        double deltaTime = 0.016; // Approximate time between frames (assuming ~60 FPS)
        double newY = boss.getLayoutY() + ySpeedPhase2 * deltaTime;

        boss.setLayoutX(newX);
        boss.setLayoutY(newY);
    }

    /**
     * Handles alternating movement for Phase 3.
     */
    private void alternateMovement(MultiPhaseBossPlane boss, long now) {
        // Perform movement based on current movement state
        if (movementState == MultiPhaseBossPlane.MovementState.HORIZONTAL) {
            moveHorizontally(boss, horizontalVelocityPhase3);
        } else if (movementState == MultiPhaseBossPlane.MovementState.SINE) {
            moveInSineWave(boss, now);
        }

        // Increment frame counter
        movementFrameCount++;

        // Check if it's time to switch movement state
        if (movementFrameCount >= maxFramesWithSameMove) {
            toggleMovementState();
            movementFrameCount = 0;
        }
    }

    /**
     * Toggles the movement state between HORIZONTAL and SINE.
     */
    private void toggleMovementState() {
        if (movementState == MultiPhaseBossPlane.MovementState.HORIZONTAL) {
            movementState = MultiPhaseBossPlane.MovementState.SINE;
        } else {
            movementState = MultiPhaseBossPlane.MovementState.HORIZONTAL;
        }
    }

    /**
     * Transitions to the next phase.
     */
    public void transitionToNextPhase() {
        currentPhase++;
        phaseStartTime = System.nanoTime();
    }

    public int getCurrentPhase() {
        return currentPhase;
    }
}
