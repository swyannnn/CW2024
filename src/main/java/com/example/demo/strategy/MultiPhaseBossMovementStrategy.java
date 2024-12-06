package com.example.demo.strategy;

import com.example.demo.actor.plane.FighterPlane;
import com.example.demo.util.GameConstant;

/**
 * Strategy for moving the MultiPhaseBossPlane with phase-specific behaviors.
 */
public class MultiPhaseBossMovementStrategy implements MovementStrategy {
    private double horizontalVelocity;
    private double verticalVelocity;
    private int currentPhase;
    private long spawnTime;
    private double sineWaveBaseX;

    private int movementFrameCount;
    private MovementState movementState;

    // Enum to represent movement states
    private enum MovementState {
        HORIZONTAL,
        SINE
    }

    public MultiPhaseBossMovementStrategy() {
        this.currentPhase = 1;
        this.spawnTime = System.nanoTime();
        this.movementState = MovementState.HORIZONTAL;
        this.movementFrameCount = 0;
        this.horizontalVelocity = GameConstant.MultiPhaseBossPlane.HORIZONTAL_VELOCITY_PHASE1;
        this.verticalVelocity = GameConstant.MultiPhaseBossPlane.VERTICAL_VELOCITY_PHASE1;
        this.sineWaveBaseX = 0;
    }

    @Override
    public void move(FighterPlane plane, long now) {
        // Update current phase based on plane's health if needed
        // For simplicity, assume the phase is managed externally

        switch (currentPhase) {
            case 1:
                moveHorizontally(plane);
                break;
            case 2:
                moveInSineWavePattern(plane, now);
                break;
            case 3:
                handlePhase3Movement(plane, now);
                break;
            default:
                break;
        }

        // Boundary checking
        if (isOutOfBounds(plane)) {
            constrainWithinBounds(plane);
        }
    }

    /**
     * Handles horizontal movement for Phase 1.
     */
    private void moveHorizontally(FighterPlane plane) {
        plane.setTranslateX(plane.getTranslateX() + horizontalVelocity);
    }

    /**
     * Handles sine wave movement for Phase 2.
     */
    private void moveInSineWavePattern(FighterPlane plane, long now) {
        // Calculate time elapsed since sine movement started
        double timeInSeconds = (now - spawnTime) / 1_000_000_000.0;
    
        double amplitude = 100; // Horizontal oscillation amplitude in pixels
        double frequency = 0.5; // Oscillation frequency in Hz
    
        // Calculate new X position based on sine wave
        double sineValue = Math.sin(2 * Math.PI * frequency * timeInSeconds);
        double newX = sineWaveBaseX + amplitude * sineValue;
    
        // Calculate new Y position based on vertical velocity
        double deltaTime = 0.016; // Approximate time between frames (assuming ~60 FPS)
        double newY = plane.getTranslateY() + verticalVelocity * deltaTime;
        plane.setTranslateX(newX);
        plane.setTranslateY(newY);
    }
    

    /**
     * Handles movement logic for Phase 3, toggling between horizontal and sine wave patterns.
     */
    private void handlePhase3Movement(FighterPlane plane, long now) {
        if (movementState == MovementState.HORIZONTAL) {
            moveHorizontally(plane);
        } else if (movementState == MovementState.SINE) {
            moveInSineWavePattern(plane, now);
        }

        // Increment frame counter
        movementFrameCount++;

        // Check if it's time to switch movement state
        if (movementFrameCount >= GameConstant.MultiPhaseBossPlane.MAX_FRAMES_WITH_SAME_MOVE) {
            toggleMovementState();
            movementFrameCount = 0;
        }
    }

    /**
     * Toggles the movement state between HORIZONTAL and SINE.
     */
    private void toggleMovementState() {
        movementState = (movementState == MovementState.HORIZONTAL) ? MovementState.SINE : MovementState.HORIZONTAL;
    }

    /**
     * Checks if the plane is out of defined bounds.
     */
    private boolean isOutOfBounds(FighterPlane plane) {
        double currentX = plane.getLayoutX() + plane.getTranslateX();
        double currentY = plane.getLayoutY() + plane.getTranslateY();

        boolean outOfHorizontal = currentX < GameConstant.MultiPhaseBossPlane.X_UPPER_BOUND
                || currentX > GameConstant.MultiPhaseBossPlane.X_LOWER_BOUND;
        boolean outOfVertical = currentY < GameConstant.MultiPhaseBossPlane.Y_UPPER_BOUND
                || currentY > GameConstant.MultiPhaseBossPlane.Y_LOWER_BOUND;
        return outOfHorizontal || outOfVertical;
    }

    /**
     * Constrains the plane within bounds and reverses direction if necessary.
     */
    private void constrainWithinBounds(FighterPlane plane) {
        double currentX = plane.getLayoutX() + plane.getTranslateX();
        double currentY = plane.getLayoutY() + plane.getTranslateY();

        if (currentX < GameConstant.MultiPhaseBossPlane.X_UPPER_BOUND
                || currentX > GameConstant.MultiPhaseBossPlane.X_LOWER_BOUND) {
            horizontalVelocity = -horizontalVelocity;
        }
        if (currentY < GameConstant.MultiPhaseBossPlane.Y_UPPER_BOUND
                || currentY > GameConstant.MultiPhaseBossPlane.Y_LOWER_BOUND) {
            verticalVelocity = -verticalVelocity;
        }
    }

    /**
     * Updates the current phase and adjusts velocities accordingly.
     *
     * @param newPhase The new phase to transition into.
     */
    public void updatePhase(int newPhase) {
        this.currentPhase = newPhase;
        switch (newPhase) {
            case 2:
                this.horizontalVelocity = GameConstant.MultiPhaseBossPlane.HORIZONTAL_VELOCITY_PHASE2;
                this.verticalVelocity = GameConstant.MultiPhaseBossPlane.VERTICAL_VELOCITY_PHASE2;
                this.sineWaveBaseX = 0; // Reset for sine movement
                this.spawnTime = System.nanoTime(); // Reset spawn time for sine calculation
                break;
            case 3:
                this.horizontalVelocity = GameConstant.MultiPhaseBossPlane.HORIZONTAL_VELOCITY_PHASE3;
                // Additional adjustments for phase 3 if necessary
                break;
            default:
                this.horizontalVelocity = GameConstant.MultiPhaseBossPlane.HORIZONTAL_VELOCITY_PHASE1;
                this.verticalVelocity = 0;
                break;
        }
    }
}
