package com.example.demo.strategy.movement;

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
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/strategy/movement/MultiPhaseBossMovementStrategy.java">Github Source Code</a>
 * @see MovementStrategy
 * @see FighterPlane
 */
public class MultiPhaseBossMovementStrategy implements MovementStrategy {
    private double horizontalVelocity;
    private double verticalVelocity;
    private int currentPhase;
    private long phaseStartTime;
    private double sineWaveBaseX;

    private int movementFrameCount;
    private MovementType movementState;

    private int previousPhase = 1; // To track phase changes
    private boolean switchedDueToBounds = false; // Flag to prevent immediate toggling

    private final int maxFramesWithSameMove = GameConstant.MultiPhaseBossPlane.MAX_FRAMES_WITH_SAME_MOVE;
    private final int XUpperBound = GameConstant.MultiPhaseBossPlane.X_UPPER_BOUND;
    private final int XLowerBound = GameConstant.MultiPhaseBossPlane.X_LOWER_BOUND;
    private final int YUpperBound = GameConstant.MultiPhaseBossPlane.Y_UPPER_BOUND;
    private final int YLowerBound = GameConstant.MultiPhaseBossPlane.Y_LOWER_BOUND;

    /**
     * Constructs a new MultiPhaseBossMovementStrategy with initial settings.
     * 
     * Initializes the movement strategy for a multi-phase boss character in the game.
     * Sets the initial phase to 1, records the spawn time, and sets the initial movement state to horizontal.
     * Also initializes the movement frame count and sets the horizontal and vertical velocities for phase 1.
     * The sine wave base X position is also initialized.
     */
    public MultiPhaseBossMovementStrategy(int speed) {
        this.currentPhase = 1;
        this.phaseStartTime = System.nanoTime();
        this.movementState = MovementType.HORIZONTAL;
        this.movementFrameCount = 0;
        this.horizontalVelocity = GameConstant.MultiPhaseBossPlane.HORIZONTAL_VELOCITY;
        this.verticalVelocity = GameConstant.MultiPhaseBossPlane.VERTICAL_VELOCITY;
        this.sineWaveBaseX = 0; // Will be set during phase transition
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
     * - Phase 3: Alternates between horizontal and sine wave movement.
     *
     * Additionally, the method ensures the plane remains within the game boundaries.
     */
    @Override
    public void move(FighterPlane plane, long now) {
        // Check for phase transition
        if (currentPhase != previousPhase) {
            handlePhaseTransition(plane);
            previousPhase = currentPhase;
        }

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
     * Handles phase transitions by adjusting the plane's position if necessary.
     *
     * @param plane the FighterPlane instance to adjust
     */
    private void handlePhaseTransition(FighterPlane plane) {
        if (currentPhase == 2) {
            // Before transitioning to Phase 2, ensure the plane is at a safe X position
            adjustPlanePositionForSineWave(plane);
            // Initialize sineWaveBaseX based on current position
            sineWaveBaseX = plane.getTranslateX();
            // Reset phase start time for accurate sine wave calculations
            phaseStartTime = System.nanoTime();
        } else if (currentPhase == 3) {
            // Reset movement state and frame count when entering Phase 3
            movementState = MovementType.HORIZONTAL;
            movementFrameCount = 0;
            switchedDueToBounds = false; // Ensure flag is reset
            // Optionally, adjust velocities or other parameters for Phase 3
        }
    }

    /**
     * Moves the given fighter plane horizontally by updating its X coordinate.
     *
     * @param plane the fighter plane to be moved
     */
    private void moveHorizontally(FighterPlane plane) {
        double oldX = plane.getTranslateX();
        plane.setTranslateX(oldX + horizontalVelocity);
    }

    /**
     * Moves the given FighterPlane in a sine wave pattern horizontally and updates its vertical position based on a constant velocity.
     *
     * @param plane the FighterPlane to be moved
     * @param now the current time in nanoseconds
     */
    private void moveInSineWavePattern(FighterPlane plane, long now) {
        double timeInSeconds = (now - phaseStartTime) / 1_000_000_000.0;

        double amplitude = 100; // Horizontal oscillation amplitude in pixels
        double frequency = 0.5; // Oscillation frequency in Hz

        // Calculate new X position based on sine wave
        double sineValue = Math.sin(2 * Math.PI * frequency * timeInSeconds);
        double newX = sineWaveBaseX + amplitude * sineValue;

        double deltaTime = 1.0 / 60.0; // Assuming ~60 FPS
        double newY = plane.getTranslateY() + verticalVelocity * deltaTime;
        plane.setTranslateX(newX);
        plane.setTranslateY(newY);
    }

    /**
     * Handles the movement of the fighter plane during phase 3.
     * Depending on the current movement state, the plane will either move horizontally
     * or in a sine wave pattern. The movement state is toggled after a certain
     * number of frames.
     *
     * Additionally, if the plane is in SINE state and goes out of horizontal bounds,
     * it immediately switches back to HORIZONTAL state and resets the movement frame count.
     *
     * @param plane The fighter plane whose movement is being controlled.
     * @param now The current time in nanoseconds.
     */
    private void handlePhase3Movement(FighterPlane plane, long now) {
        // Move according to current movement state
        if (movementState == MovementType.HORIZONTAL) {
            moveHorizontally(plane);
        } else if (movementState == MovementType.SINE_WAVE) {
            moveInSineWavePattern(plane, now);
    
            // Check horizontal bounds specifically in SINE state
            if (isOutOfHorizontalBounds(plane)) {
                // Switch back to HORIZONTAL state
                movementState = MovementType.HORIZONTAL;
                movementFrameCount = 0;
                switchedDueToBounds = true;
            }
        }
    
        // Always increment movementFrameCount
        movementFrameCount++;
    
        // Check if it's time to switch movement state
        if (movementFrameCount >= maxFramesWithSameMove) {
            if (!switchedDueToBounds) {
                toggleMovementState();
            } else {
                // Do not toggle movement state due to bounds violation
                // Just reset movementFrameCount and clear the flag
                switchedDueToBounds = false;
            }
            movementFrameCount = 0;
        }
    }
    

    /**
     * Toggles the movement state of the boss between horizontal and sine wave patterns.
     * If the current movement state is horizontal, it switches to sine wave.
     * If the current movement state is sine wave, it switches to horizontal.
     */
    private void toggleMovementState() {
        movementState = (movementState == MovementType.HORIZONTAL) ? MovementType.SINE_WAVE : MovementType.HORIZONTAL;
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

        boolean outOfHorizontal = currentX < XUpperBound
                || currentX > XLowerBound;
        boolean outOfVertical = currentY < YUpperBound
                || currentY > YLowerBound;
        return outOfHorizontal || outOfVertical;
    }

    /**
     * Checks if the plane is out of horizontal bounds.
     *
     * @param plane the FighterPlane to check
     * @return true if the plane is out of horizontal bounds, false otherwise
     */
    private boolean isOutOfHorizontalBounds(FighterPlane plane) {
        double currentX = plane.getLayoutX() + plane.getTranslateX();

        return currentX < XUpperBound
                || currentX > XLowerBound;
    }

/**
     * Constrains the given FighterPlane within the specified bounds.
     * If the plane's current position exceeds the defined upper or lower bounds
     * for either the X or Y coordinates, the corresponding velocity is reversed,
     * and the plane's position is adjusted to lie within the bounds.
     *
     * @param plane the FighterPlane to be constrained within bounds
     */
    private void constrainWithinBounds(FighterPlane plane) {
        double currentX = plane.getLayoutX() + plane.getTranslateX();
        double currentY = plane.getLayoutY() + plane.getTranslateY();

        // Handle horizontal bounds
        if (currentX < XUpperBound) {
            horizontalVelocity = Math.abs(horizontalVelocity); // Ensure velocity is positive
            plane.setTranslateX(XUpperBound - plane.getLayoutX());
            sineWaveBaseX = plane.getTranslateX(); // Re-align sine wave base
        } else if (currentX > XLowerBound) {
            horizontalVelocity = -Math.abs(horizontalVelocity); // Ensure velocity is negative
            plane.setTranslateX(XLowerBound - plane.getLayoutX());
            sineWaveBaseX = plane.getTranslateX(); // Re-align sine wave base
        }

        // Handle vertical bounds (if needed)
        if (currentY < YUpperBound) {
            verticalVelocity = Math.abs(verticalVelocity); // Ensure velocity is positive
            plane.setTranslateY(YUpperBound - plane.getLayoutY());
        } else if (currentY > YLowerBound) {
            verticalVelocity = -Math.abs(verticalVelocity); // Ensure velocity is negative
            plane.setTranslateY(YLowerBound - plane.getLayoutY());
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
        this.horizontalVelocity *= 1.2;
        this.verticalVelocity *= 2;
    }

    /**
     * Adjusts the plane's X position to a safe range to perform sine wave movement without going out of bounds.
     *
     * @param plane the FighterPlane to adjust
     */
    private void adjustPlanePositionForSineWave(FighterPlane plane) {
        double amplitude = 100; // Must match the amplitude used in sine wave movement
        double minX = XUpperBound + amplitude;
        double maxX = XLowerBound - amplitude;
    
        double currentX = plane.getLayoutX() + plane.getTranslateX();
    
        if (currentX < minX) {
            plane.setTranslateX(minX - plane.getLayoutX());
        } else if (currentX > maxX) {
            plane.setTranslateX(maxX - plane.getLayoutX());
        }
    }    
}
