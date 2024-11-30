package com.example.demo.actor.plane;

import java.util.List;

import com.example.demo.actor.projectile.BossProjectile;
import com.example.demo.controller.Controller;
import com.example.demo.level.Level004;
import com.example.demo.manager.ActorManager;
import com.example.demo.util.GameConstant;
import javafx.geometry.Point2D;

/**
 * MultiPhaseBossPlane class representing a boss aircraft with multiple phases.
 */
public class MultiPhaseBossPlane extends FighterPlane {
    private static final String imageNamePhase1 = GameConstant.MultiPhaseBossPlane.IMAGE_NAME_PHASE1;
    private static final String imageNamePhase2 = GameConstant.MultiPhaseBossPlane.IMAGE_NAME_PHASE2;
    private static final String imageNamePhase3 = GameConstant.MultiPhaseBossPlane.IMAGE_NAME_PHASE3;
    private static final int imageHeight = GameConstant.MultiPhaseBossPlane.IMAGE_HEIGHT;
    private static final int initialHealthPhase1 = GameConstant.MultiPhaseBossPlane.INITIAL_HEALTH_PHASE1;
    private static final int initialHealthPhase2 = GameConstant.MultiPhaseBossPlane.INITIAL_HEALTH_PHASE2;
    private static final int initialHealthPhase3 = GameConstant.MultiPhaseBossPlane.INITIAL_HEALTH_PHASE3;
    private static final long fireIntervalNanoseconds = GameConstant.MultiPhaseBossPlane.FIRE_INTERVAL_NANOSECONDS;
    private static final double fireRate = GameConstant.MultiPhaseBossPlane.FIRE_RATE;
    private static final double yPosition = GameConstant.MultiPhaseBossPlane.Y_POSITION;
    private static final double xPosition = GameConstant.MultiPhaseBossPlane.X_POSITION;
    private static final double projectileXPositionOffset = GameConstant.BossProjectile.PROJECTILE_X_POSITION_OFFSET;
    private static final double projectileYPositionOffset = GameConstant.BossProjectile.PROJECTILE_Y_POSITION_OFFSET;
    private static final double horizontalVelocityPhase1 = GameConstant.MultiPhaseBossPlane.HORIZONTAL_VELOCITY_PHASE1;
    private static final double horizontalVelocityPhase2 = GameConstant.MultiPhaseBossPlane.HORIZONTAL_VELOCITY_PHASE2;
    private static final double horizontalVelocityPhase3 = GameConstant.MultiPhaseBossPlane.HORIZONTAL_VELOCITY_PHASE3;
    private static final double yUpperBound = GameConstant.MultiPhaseBossPlane.Y_UPPER_BOUND;
    private static final double yLowerBound = GameConstant.MultiPhaseBossPlane.Y_LOWER_BOUND;
    private static final double xUpperBound = GameConstant.MultiPhaseBossPlane.X_UPPER_BOUND;
    private static final double xLowerBound = GameConstant.MultiPhaseBossPlane.X_LOWER_BOUND;

    private Controller controller;
    private ActorManager actorManager;
    private long spawnTime;
    private double horizontalVelocity; 
    private int currentPhase;
    private boolean weaknessExposed;
    private long lastFireTime;
    private long lastAreaAttackTime;
    private long lastSummonTime;
    private long areaAttackCooldown;
    private long summonCooldown;
    private Level004 level; // Reference to Level004

        
    /**
     * Constructs a MultiPhaseBossPlane at the specified position.
     *
     * @param controller The game controller.
     */
    public MultiPhaseBossPlane(Controller controller, Level004 level) {
        super(controller, imageNamePhase1, imageHeight, xPosition, yPosition, initialHealthPhase1, fireIntervalNanoseconds);
        this.controller = controller;
        this.level = level;
        this.actorManager = controller.getGameStateManager().getActorManager();
        this.currentPhase = 1;
        this.weaknessExposed = false;
        this.lastFireTime = 0;
        this.lastAreaAttackTime = 0;
        this.lastSummonTime = 0;
        this.spawnTime = System.nanoTime();
        this.horizontalVelocity = horizontalVelocityPhase1;
        this.areaAttackCooldown = GameConstant.MultiPhaseBossPlane.AREA_ATTACK_COOLDOWN_PHASE1;
        this.summonCooldown = GameConstant.MultiPhaseBossPlane.SUMMON_COOLDOWN_PHASE1;

        setHorizontalBounds(xUpperBound, xLowerBound);
        setVerticalBounds(yUpperBound, yLowerBound);
    }

    /**
     * Updates the boss plane's state.
     *
     * @param now The current timestamp in nanoseconds.
     */
    @Override
    protected void performMovement(long now) {
        // Movement logic based on the current phase
        switch (currentPhase) {
            case 1:
                moveHorizontally(horizontalVelocityPhase1);
                break;
            case 2:
                moveInSineWavePattern(now);
                break;
            case 3:
                moveTowardsPlayer();
                break;
            default:
                break;
        }

        // Boundary checking
        if (isOutOfBounds()) {
            // Keep the boss within the screen bounds
            constrainWithinBounds();
        }
    }

    @Override
    protected void performAdditionalUpdates(long now) {
        // Perform attacks based on the current phase
        performPhaseAttacks(now);

        // Handle phase transitions
        checkPhaseTransition(now);
    }

    /**
     * Fires a projectile from the boss plane's current position.
     */
    @Override
    public void fireProjectile() {
        if (System.nanoTime() - lastFireTime >= fireIntervalNanoseconds) {
            if (Math.random() < fireRate) {
                double projectileX = getProjectileXPosition(projectileXPositionOffset);
                double projectileY = getProjectileYPosition(projectileYPositionOffset);

                BossProjectile projectile = new BossProjectile(projectileX, projectileY, controller);
                actorManager.addActor(projectile);
                lastFireTime = System.nanoTime();
            }
        }
    }

    /**
     * Performs attacks specific to the current phase.
     *
     * @param now The current timestamp in nanoseconds.
     */
    private void performPhaseAttacks(long now) {
        fireProjectile(); // Basic attack available in all phases

        switch (currentPhase) {
            case 1:
                // Additional behaviors for phase 1
                break;
            case 2:
                performAreaOfEffectAttack(now);
                break;
            case 3:
                performAreaOfEffectAttack(now);
                summonMinions(now);
                break;
            default:
                break;
        }
    }

    /**
     * Performs an area-of-effect attack if cooldown allows.
     *
     * @param now The current timestamp in nanoseconds.
     */
    private void performAreaOfEffectAttack(long now) {
        if (now - lastAreaAttackTime >= areaAttackCooldown) {
            // Create and add the area effect
            // Implement the AreaEffect class accordingly
            // Example:
            // AreaEffect areaEffect = new AreaEffect(getTranslateX(), getTranslateY(), controller);
            // actorManager.addActor(areaEffect);

            lastAreaAttackTime = now;
        }
    }

    /**
     * Summons minions if cooldown allows.
     *
     * @param now The current timestamp in nanoseconds.
     */
    private void summonMinions(long now) {
        if (now - lastSummonTime >= summonCooldown) {
            // Create and add minion planes
            EnemyPlane minion1 = new EnemyPlane(controller);
            EnemyPlane minion2 = new EnemyPlane(controller);

            // Position minions relative to the boss
            minion1.setTranslateX(getTranslateX() - 100);
            minion1.setTranslateY(getTranslateY() + getImageHeight());
            minion2.setTranslateX(getTranslateX() + getImageWidth() + 100);
            minion2.setTranslateY(getTranslateY() + getImageHeight());

            actorManager.addActor(minion1);
            actorManager.addActor(minion2);

            lastSummonTime = now;
        }
    }

    /**
     * Checks if the boss should transition to the next phase.
     */
    private void checkPhaseTransition(long now) {
        if (getHealth() <= 0) {
            if (currentPhase < 3) {
                currentPhase++;
                transitionToNextPhase(now);
            } else {
                // Boss defeated
                actorManager.removeActor(this);
                level.onBossDefeated();
            }
        }
    }

    /**
     * Transitions the boss to the next phase.
     */
    private void transitionToNextPhase(long now) {
        switch (currentPhase) {
            case 2:
                setImageName(imageNamePhase2);
                setHealth(initialHealthPhase2);
                setHorizontalVelocity(horizontalVelocityPhase2);
                areaAttackCooldown = GameConstant.MultiPhaseBossPlane.AREA_ATTACK_COOLDOWN_PHASE2;
                summonCooldown = GameConstant.MultiPhaseBossPlane.SUMMON_COOLDOWN_PHASE2;
                break;
            case 3:
                setImageName(imageNamePhase3);
                setHealth(initialHealthPhase3);
                setHorizontalVelocity(horizontalVelocityPhase3);
                areaAttackCooldown = GameConstant.MultiPhaseBossPlane.AREA_ATTACK_COOLDOWN_PHASE3;
                summonCooldown = GameConstant.MultiPhaseBossPlane.SUMMON_COOLDOWN_PHASE3;
                break;
            default:
                break;
        }
        // Reset timers
        lastAreaAttackTime = System.nanoTime();
        lastSummonTime = System.nanoTime();
    }

    /**
     * Moves the boss in a sine wave pattern (Phase 2).
     *
     * @param now The current timestamp in nanoseconds.
     */
    private void moveInSineWavePattern(long now) {
        double timeInSeconds = (now - spawnTime) / 1_000_000_000.0;
        double amplitude = 100;
        double frequency = 0.5;

        double newX = xPosition + amplitude * Math.sin(2 * Math.PI * frequency * timeInSeconds);
        setTranslateX(newX);
    }

    /**
     * Moves the boss towards the player's position (Phase 3).
     */
    private void moveTowardsPlayer() {
        // Assuming you have a method to get the player's position
        // double playerX = controller.getGameStateManager().getActorManager().getPlayers().getCurrentXPosition();
        List<UserPlane> players = controller.getGameStateManager().getActorManager().getPlayers();
        double playerX = players.get(0).getCurrentXPosition();

        double deltaX = playerX - getTranslateX();

        if (Math.abs(deltaX) > 5) {
            double direction = Math.signum(deltaX);
            moveHorizontally(direction * horizontalVelocity);
        }
    }

    /**
     * Constrains the boss within the screen bounds.
     */
    private void constrainWithinBounds() {
        double x = getTranslateX();
        double y = getTranslateY();

        if (x < xLowerBound) {
            setTranslateX(xLowerBound);
        } else if (x > xUpperBound) {
            setTranslateX(xUpperBound);
        }

        if (y < yUpperBound) {
            setTranslateY(yUpperBound);
        } else if (y > yLowerBound) {
            setTranslateY(yLowerBound);
        }
    }

    /**
     * Sets the image name for the boss plane.
     *
     * @param imageName The new image name.
     */
    private void setImageName(String imageName) {
       setImage(imageName); // Use the method from FighterPlane
    }

    // onDamage to handle weakness exposure and increased damage
    public void onDamage(int damage, Point2D hitPoint) {
        if (weaknessExposed && isHitOnWeakPoint(hitPoint)) {
            // Deal increased damage
            setHealth(getHealth() - (damage * 3));
        } else {
            // Normal damage
            setHealth(getHealth() - damage);
        }

        // Optionally expose weakness based on health thresholds
        if (!weaknessExposed) {
            switch (currentPhase) {
                case 1:
                    if (getHealth() <= 700) { // Example threshold for phase 1
                        exposeWeakness();
                    }
                    break;
                case 2:
                    if (getHealth() <= 1000) { // Example threshold for phase 2
                        exposeWeakness();
                    }
                    break;
                default:
                    break;
            }
        }

        // Check for phase transitions or boss defeat
        if (getHealth() <= 0) {
            if (currentPhase < 3) {
                currentPhase++;
                transitionToNextPhase(System.nanoTime());
            } else {
                // Boss defeated
                actorManager.removeActor(this);
                level.onBossDefeated(); // Notify Level004 that the boss is defeated
            }
        }
    }

    /**
     * Determines if the hit was on the boss's weak point.
     *
     * @param hitPoint The point where the hit occurred.
     * @return True if hit on weak point, false otherwise.
     */
    private boolean isHitOnWeakPoint(Point2D hitPoint) {
        // Define weak point area based on current image or phase
        // Example coordinates (you may need to adjust based on your image)
        double weakPointX = getTranslateX() + getImageWidth() / 2 - 25;
        double weakPointY = getTranslateY() + getImageHeight() / 2 - 25;
        double weakPointWidth = 50;
        double weakPointHeight = 50;

        return (hitPoint.getX() >= weakPointX && hitPoint.getX() <= (weakPointX + weakPointWidth))
                && (hitPoint.getY() >= weakPointY && hitPoint.getY() <= (weakPointY + weakPointHeight));
    }

    /**
     * Exposes the boss's weakness, allowing the player to deal more damage.
     */
    private void exposeWeakness() {
        weaknessExposed = true;
        setImageName("boss_weakness_exposed.png"); // Replace with your weak point image
        // Optionally, add visual indicators for the weak point (e.g., flashing, highlighting)
    }

    /**
     * Hides the boss's weakness.
     */
    private void hideWeakness() {
        weaknessExposed = false;
        // Revert to the current phase's image
        switch (currentPhase) {
            case 1:
                setImageName(imageNamePhase1);
                break;
            case 2:
                setImageName(imageNamePhase2);
                break;
            case 3:
                setImageName(imageNamePhase3);
                break;
            default:
                break;
        }
    }

    /**
     * Sets the horizontal velocity for the boss plane.
     *
     * @param velocity The new horizontal velocity.
     */
    private void setHorizontalVelocity(double velocity) {
        this.horizontalVelocity = velocity;
    }
}
