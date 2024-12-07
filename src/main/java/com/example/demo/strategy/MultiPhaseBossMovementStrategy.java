package com.example.demo.strategy;

import com.example.demo.actor.plane.FighterPlane;
import com.example.demo.util.GameConstant;


/**
 * The MultiPhaseBossMovementStrategy class implements the MovementStrategy interface
 * and defines the movement behavior for a multi-phase boss in a game. The boss has
 * different movement patterns based on its current phase.
 * 
 * <p>Phases:
 * <ul>
 *   <li>Phase 1: Horizontal movement</li>
 *   <li>Phase 2: Sine wave movement</li>
 *   <li>Phase 3: Alternates between horizontal and sine wave movement</li>
 * </ul>
 * 
 * <p>The movement strategy also includes boundary checking to ensure the boss stays
 * within defined bounds and reverses direction if necessary.
 * 
 * @see MovementStrategy
 */
public class MultiPhaseBossMovementStrategy implements MovementStrategy {
    private double horizontalVelocity;
    private double verticalVelocity;
    private int currentPhase;
    private long spawnTime;
    private double sineWaveBaseX;

    private int movementFrameCount;
    private MovementState movementState;

    /**
     * Enum representing the different movement states of a multi-phase boss.
     */
    private enum MovementState {
        HORIZONTAL,
        SINE
    }

    /**
     * Constructs a new MultiPhaseBossMovementStrategy with initial settings.
     * 
     * Initializes the movement strategy for a multi-phase boss character in the game.
     * Sets the initial phase to 1, records the spawn time, and sets the initial movement state to horizontal.
     * Also initializes the movement frame count and sets the horizontal and vertical velocities for phase 1.
     * The sine wave base X position is also initialized.
     */
    public MultiPhaseBossMovementStrategy() {
        this.currentPhase = 1;
        this.spawnTime = System.nanoTime();
        this.movementState = MovementState.HORIZONTAL;
        this.movementFrameCount = 0;
        this.horizontalVelocity = GameConstant.MultiPhaseBossPlane.HORIZONTAL_VELOCITY_PHASE1;
        this.verticalVelocity = GameConstant.MultiPhaseBossPlane.VERTICAL_VELOCITY_PHASE1;
        this.sineWaveBaseX = 0;
    }

    /**
     * Moves the fighter plane based on the current phase of the boss.
     *
     * @param plane the fighter plane to be moved
     * @param now the current time in nanoseconds
     *
     * The movement strategy changes based on the current phase:
     * - Phase 1: Moves the plane horizontally.
     * - Phase 2: Moves the plane in a sine wave pattern.
     * - Phase 3: Handles movement specific to phase 3.
     *
     * Additionally, the method ensures the plane remains within the game boundaries.
     */
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
     * Moves the given fighter plane horizontally by updating its X coordinate.
     *
     * @param plane the fighter plane to be moved
     */
    private void moveHorizontally(FighterPlane plane) {
        plane.setTranslateX(plane.getTranslateX() + horizontalVelocity);
    }

    /**
     * Moves the given FighterPlane in a sine wave pattern horizontally and updates its vertical position based on a constant velocity.
     *
     * @param plane the FighterPlane to be moved
     * @param now the current time in nanoseconds
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
     * Handles the movement of the fighter plane during phase 3.
     * Depending on the current movement state, the plane will either move horizontally
     * or in a sine wave pattern. The movement state is toggled after a certain number
     * of frames.
     *
     * @param plane The fighter plane whose movement is being controlled.
     * @param now The current time in nanoseconds.
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
     * Toggles the movement state of the boss between horizontal and sine wave patterns.
     * If the current movement state is horizontal, it switches to sine wave.
     * If the current movement state is sine wave, it switches to horizontal.
     */
    private void toggleMovementState() {
        movementState = (movementState == MovementState.HORIZONTAL) ? MovementState.SINE : MovementState.HORIZONTAL;
    }

    /**
     * Checks if the given FighterPlane is out of the defined bounds.
     *
     * @param plane the FighterPlane to check
     * @return true if the plane is out of bounds, false otherwise
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
     * Constrains the given FighterPlane within the specified bounds.
     * If the plane's current position exceeds the defined upper or lower bounds
     * for either the X or Y coordinates, the corresponding velocity is reversed.
     *
     * @param plane the FighterPlane to be constrained within bounds
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
     * Updates the current phase of the boss and adjusts its movement parameters accordingly.
     *
     * @param newPhase the new phase to set for the boss. Valid values are:
     *                 1 - Default phase with horizontal movement only.
     *                 2 - Phase with specific horizontal and vertical velocities, and sine wave movement.
     *                 3 - Phase with specific horizontal velocity and additional adjustments if necessary.
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
