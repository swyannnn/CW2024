package com.example.demo.actor.plane;

import java.util.*;

import com.example.demo.ShieldImage;
import com.example.demo.actor.projectile.BossProjectile;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ActorManager;

public class BossPlane extends FighterPlane {
    private ActorManager actorManager;
    private final ShieldImage shield;
    private static final String IMAGE_NAME = "bossplane.png";
    private static final double INITIAL_X_POSITION = 1000.0;
    private static final double INITIAL_Y_POSITION = 125;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 120.0;
    private static final double BOSS_FIRE_RATE = 1;
    private static final double BOSS_SHIELD_PROBABILITY = 0.04;
    private static final int IMAGE_HEIGHT = 300;
    private static final int VERTICAL_VELOCITY = 4;
    private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
    private static final int ZERO = 0;
    private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
    private static final int Y_POSITION_UPPER_BOUND = -50;
    private static final int Y_POSITION_LOWER_BOUND = 300;
    private static final int MAX_FRAMES_WITH_SHIELD = 500;
    private static final int MAX_FRAMES_WITHOUT_SHIELD = 500; 
    private static final int INITIAL_HEALTH = 1;
    private static final long FIRE_INTERVAL_NANOSECONDS = 1_000_000_000;

    // Dynamic bounds and position based on screen size
    private final List<Integer> movePattern;
    private boolean isShielded;
    private int consecutiveMovesInSameDirection;
    private int indexOfCurrentMove;
    private int framesWithShieldActivated;
    private int framesSinceLastShield;

    public BossPlane(Controller controller) {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, INITIAL_HEALTH, FIRE_INTERVAL_NANOSECONDS);
        this.actorManager = controller.getGameStateManager().getActorManager();

        movePattern = new ArrayList<>();
        consecutiveMovesInSameDirection = 0;
        indexOfCurrentMove = 0;
        framesWithShieldActivated = 0;
        framesSinceLastShield = MAX_FRAMES_WITHOUT_SHIELD;
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
            double projectileY = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            BossProjectile projectile = new BossProjectile(projectileY);
            actorManager.addBossProjectile(projectile);
        }
    }

    @Override
    public void updatePosition() {
        double initialTranslateY = getTranslateY();
        moveVertically(getNextMove());
        double currentPosition = getLayoutY() + getTranslateY();
        if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
            setTranslateY(initialTranslateY); // Revert to initial position if out of bounds
        }
    }   
    
    @Override
    public void updateActor() {
        updatePosition();
        updateShield();
        
        // Update shield position to follow BossPlane
        if (shield.isVisible()) {
            shield.setTranslateX(getTranslateX());
            shield.setTranslateY(getTranslateY());
        }
    }

    @Override
    public void takeDamage() {
        if (!isShielded) {
            super.takeDamage();
        }
    }

    private void initializeMovePattern() {
        for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
            movePattern.add(VERTICAL_VELOCITY);
            movePattern.add(-VERTICAL_VELOCITY);
            movePattern.add(ZERO);
        }
        Collections.shuffle(movePattern);
    }

    private void updateShield() {
        if (isShielded) {
            framesWithShieldActivated++;
            if (shieldExhausted()) {
                deactivateShield();
            }
        } else {
            if (framesSinceLastShield < MAX_FRAMES_WITHOUT_SHIELD) {
                framesSinceLastShield++;
            }
            if (shieldShouldBeActivated() && framesSinceLastShield >= MAX_FRAMES_WITHOUT_SHIELD) {
                activateShield();
            }
        }
    }    

    private int getNextMove() {
        int currentMove = movePattern.get(indexOfCurrentMove);
        consecutiveMovesInSameDirection++;
        if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
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
        return Math.random() < BOSS_FIRE_RATE;
    }

    private boolean shieldShouldBeActivated() {
        return Math.random() < BOSS_SHIELD_PROBABILITY;
    }

    private boolean shieldExhausted() {
        return framesWithShieldActivated >= MAX_FRAMES_WITH_SHIELD;
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
