package com.example.demo.actor.plane;

import java.util.*;

import com.example.demo.Shield;
import com.example.demo.actor.projectile.BossProjectile;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ActorManager;
import com.example.demo.util.GameConstant;

/**
 * BossPlane class representing the boss enemy in the game.
 */
public class BossPlane extends FighterPlane {
    private final ActorManager actorManager;
    private final Shield shield;
    private final Controller controller;

    // Constants
    private static final String imageName = GameConstant.BossPlane.IMAGE_NAME;
    private static final int imageHeight = GameConstant.BossPlane.IMAGE_HEIGHT;
    private static final double initialXPosition = GameConstant.BossPlane.INITIAL_X_POSITION;
    private static final double initialYPosition = GameConstant.BossPlane.INITIAL_Y_POSITION;
    private static final double projectileYPositionOffset = GameConstant.BossProjectile.PROJECTILE_Y_POSITION_OFFSET;
    private static final double fireRate = GameConstant.BossProjectile.FIRE_RATE;

    // Shield-related constants
    private static final double BossShieldProbability = GameConstant.BossShield.BOSS_SHIELD_PROBABILITY;
    private static final int shieldXPositionOffset = GameConstant.BossShield.X_POSITION_OFFSET;
    private static final int shieldYPositionOffset = GameConstant.BossShield.Y_POSITION_OFFSET;
    private static final int maxFramesWithShield = GameConstant.BossShield.MAX_FRAMES_WITH_SHIELD;
    private static final int maxFramesWithoutShield = GameConstant.BossShield.MAX_FRAMES_WITHOUT_SHIELD;

    // Movement-related constants
    private static final int verticalVelocity = GameConstant.BossPlane.VERTICAL_VELOCITY;
    private static final int moveFrequencyPerCycle = GameConstant.BossPlane.MOVE_FREQUENCY_PER_CYCLE;
    private static final int zero = GameConstant.BossPlane.ZERO;
    private static final int maxFramesWithSameMove = GameConstant.BossPlane.MAX_FRAMES_WITH_SAME_MOVE;
    private static final int yUpperBound = GameConstant.BossPlane.Y_POSITION_UPPER_BOUND;
    private static final int yLowerBound = GameConstant.BossPlane.Y_POSITION_LOWER_BOUND;
    private static final int initialHealth = GameConstant.BossPlane.INITIAL_HEALTH;
    private static final long fireIntervalNanoseconds = GameConstant.BossPlane.FIRE_INTERVAL_NANOSECONDS;

    // Dynamic movement state
    private final List<Integer> movePattern;
    private int consecutiveMovesInSameDirection;
    private int indexOfCurrentMove;

    /**
     * Constructs a BossPlane instance.
     *
     * @param controller The game controller managing the state.
     */
    public BossPlane(Controller controller) {
        super(controller, imageName, imageHeight, initialXPosition, initialYPosition, initialHealth, fireIntervalNanoseconds);
        this.actorManager = controller.getGameStateManager().getActorManager();
        this.controller = controller;
        this.movePattern = new ArrayList<>();
        this.consecutiveMovesInSameDirection = 0;
        this.indexOfCurrentMove = 0;
        initializeMovePattern();

        // Initialize the shield and add it as a child node
        shield = new Shield(shieldXPositionOffset, shieldYPositionOffset,
                            BossShieldProbability, maxFramesWithShield, maxFramesWithoutShield);
        actorManager.addUIElement(shield); 
        setVerticalBounds(yUpperBound, yLowerBound);
    }

    /**
     * Fires a projectile from the boss plane's current position.
     */
    @Override
    public void fireProjectile() {
        if (bossFiresInCurrentFrame()) { // Use the specified firing probability
            double projectileY = getProjectileYPosition(projectileYPositionOffset);
            BossProjectile projectile = new BossProjectile(projectileY, controller);
            actorManager.addActor(projectile);
            // Uncomment for debugging:
            // System.out.println("Projectile fired by BossPlane at: " + projectileY);
        }
    }

    /**
     * Performs movement for the boss plane.
     *
     * @param now The current time in nanoseconds.
     */
    @Override
    protected void performMovement(long now) {
        double initialTranslateY = getTranslateY();
        moveVertically(getNextMove());
        if (isOutOfBounds()) {
            setTranslateY(initialTranslateY); // Revert to initial position if out of bounds
        }
        // Uncomment for debugging:
        // System.out.println("BossPlane.performMovement() called, current position: " + (getLayoutX() + getTranslateX()) + ", " + (getLayoutY() + getTranslateY()));
    }

    /**
     * Performs additional updates, specifically updating the shield.
     *
     * @param now The current time in nanoseconds.
     */
    @Override
    protected void performAdditionalUpdates(long now) {
        shield.updateShieldState(getTranslateX(), getTranslateY());
    }

    /**
     * Checks if the BossPlane is currently shielded.
     *
     * @return True if the shield is active, false otherwise.
     */
    public boolean isShielded() {
        return shield.isShielded();
    }

    /**
     * Method to take damage when the boss plane is not shielded.
     */
    @Override
    public boolean takeDamage() {
        System.out.println("BossPlane.takeDamage() called.");
        if (shield.isShielded()) {
            System.out.println("BossPlane is shielded. No damage taken.");
            return false; // Damage not applied
        }
        return super.takeDamage(); // Delegate to base class
    }
    
    /**
     * Initializes the movement pattern for the boss plane.
     */
    private void initializeMovePattern() {
        for (int i = 0; i < moveFrequencyPerCycle; i++) {
            movePattern.add(verticalVelocity);
            movePattern.add(-verticalVelocity);
            movePattern.add(zero);
        }
        Collections.shuffle(movePattern);
    }

    /**
     * Determines the next vertical movement for the boss plane.
     *
     * @return The vertical movement delta.
     */
    private int getNextMove() {
        int currentMove = movePattern.get(indexOfCurrentMove);
        consecutiveMovesInSameDirection++;
        if (consecutiveMovesInSameDirection == maxFramesWithSameMove) {
            Collections.shuffle(movePattern);
            consecutiveMovesInSameDirection = 0;
            indexOfCurrentMove++;
        }
        if (indexOfCurrentMove == movePattern.size()) {
            indexOfCurrentMove = 0;
        }
        return currentMove;
    }

    /**
     * Determines if the boss should fire a projectile in the current frame.
     *
     * @return True if the boss fires, else false.
     */
    private boolean bossFiresInCurrentFrame() {
        return Math.random() < fireRate;
    }
}
