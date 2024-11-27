package com.example.demo.actor.plane;

import java.util.*;

import com.example.demo.ShieldImage;
import com.example.demo.actor.projectile.BossProjectile;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ActorManager;
import com.example.demo.util.GameConstant;

public class BossPlane extends FighterPlane {
    private ActorManager actorManager;
    private final ShieldImage shield;
    private Controller controller;
    private static final String imageName = GameConstant.BossPlane.IMAGE_NAME;
    private static final int imageHeight = GameConstant.BossPlane.IMAGE_HEIGHT;
    private static final double initialXPosition = GameConstant.BossPlane.INITIAL_X_POSITION;
    private static final double initialYPosition = GameConstant.BossPlane.INITIAL_Y_POSITION;
    private static final double projectileYPositionOffset = GameConstant.BossProjectile.PROJECTILE_Y_POSITION_OFFSET;
    private static final double fireRate = GameConstant.BossProjectile.FIRE_RATE;
    private static final double BossShieldProbability = GameConstant.BossShield.BOSS_SHIELD_PROBABILITY;
    private static final int verticalVelocity = GameConstant.BossPlane.VERTICAL_VELOCITY;
    private static final int shieldXPositionOffset = GameConstant.BossShield.X_POSITION_OFFSET;
    private static final int shieldYPositionOffset = GameConstant.BossShield.Y_POSITION_OFFSET;
    private static final int moveFrequencyPerCycle = GameConstant.BossPlane.MOVE_FREQUENCY_PER_CYCLE;
    private static final int zero = GameConstant.BossPlane.ZERO;
    private static final int maxFramesWithSameMove = GameConstant.BossPlane.MAX_FRAMES_WITH_SAME_MOVE;
    private static final int yPositionUpperBound = GameConstant.BossPlane.Y_POSITION_UPPER_BOUND;
    private static final int yPositionLowerBound = GameConstant.BossPlane.Y_POSITION_LOWER_BOUND;
    private static final int maxFramesWithShield = GameConstant.BossShield.MAX_FRAMES_WITH_SHIELD;
    private static final int maxFramesWithoutShield = GameConstant.BossShield.MAX_FRAMES_WITHOUT_SHIELD; 
    private static final int initialHealth = GameConstant.BossPlane.INITIAL_HEALTH;
    private static final long fireIntervalNanoseconds = GameConstant.BossPlane.FIRE_INTERVAL_NANOSECONDS;


    // Dynamic bounds and position based on screen size
    private final List<Integer> movePattern;
    private boolean isShielded;
    private int consecutiveMovesInSameDirection;
    private int indexOfCurrentMove;
    private int framesWithShieldActivated;
    private int framesSinceLastShield;

    public BossPlane(Controller controller) {
        super(imageName, imageHeight, initialXPosition, initialYPosition, initialHealth, fireIntervalNanoseconds);
        this.actorManager = controller.getGameStateManager().getActorManager();
        this.controller = controller;
        movePattern = new ArrayList<>();
        consecutiveMovesInSameDirection = 0;
        indexOfCurrentMove = 0;
        framesWithShieldActivated = 0;
        framesSinceLastShield = maxFramesWithoutShield;
        isShielded = false;
        initializeMovePattern();

        // Initialize the shield and add it as a child node
        shield = new ShieldImage();
        actorManager.getRoot().getChildren().add(shield); 
    }

    /**
     * Fires a projectile from the enemy plane's current position.
     */
    @Override
    public void fireProjectile() {
        if (bossFiresInCurrentFrame()) { // Use the specified firing probability
            double projectileY = getProjectileYPosition(projectileYPositionOffset);
            BossProjectile projectile = new BossProjectile(projectileY, controller);
            actorManager.addBossProjectile(projectile);
            System.out.println("Projectile fired by " + this + " at: " + projectileY);
        }
    }

    @Override
    protected void performMovement(long now) {
        double initialTranslateY = getTranslateY();
        moveVertically(getNextMove());
        double currentPosition = getLayoutY() + getTranslateY();
        if (currentPosition < yPositionUpperBound || currentPosition > yPositionLowerBound) {
            setTranslateY(initialTranslateY); // Revert to initial position if out of bounds
        }
    }

    @Override
    protected void performAdditionalUpdates(long now) {
        updateShield();
    }

    @Override
    public void takeDamage() {
        if (!isShielded) {
            super.takeDamage();
        }
    }

    private void initializeMovePattern() {
        for (int i = 0; i < moveFrequencyPerCycle; i++) {
            movePattern.add(verticalVelocity);
            movePattern.add(-verticalVelocity);
            movePattern.add(zero);
        }
        Collections.shuffle(movePattern);
    }

    private void updateShield() {
        if (isShielded) {
            shield.setTranslateX(getTranslateX() + shieldXPositionOffset);
            shield.setTranslateY(getTranslateY() + shieldYPositionOffset);
            framesWithShieldActivated++;
            if (shieldExhausted()) {
                deactivateShield();
            }
        } else {
            if (framesSinceLastShield < maxFramesWithoutShield) {
                framesSinceLastShield++;
            }
            if (shieldShouldBeActivated() && framesSinceLastShield >= maxFramesWithoutShield) {
                activateShield();
            }
        }
    }    

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

    private boolean bossFiresInCurrentFrame() {
        return Math.random() < fireRate;
    }

    private boolean shieldShouldBeActivated() {
        return Math.random() < BossShieldProbability;
    }

    private boolean shieldExhausted() {
        return framesWithShieldActivated >= maxFramesWithShield;
    }

    private void activateShield() {
        isShielded = true;
        framesSinceLastShield = 0;
        shield.showShield();
    }

    private void deactivateShield() {
        isShielded = false;
        framesWithShieldActivated = 0;
        shield.hideShield();
    }
}
