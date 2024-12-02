package com.example.demo.actor.plane;

// import com.example.demo.effect.AreaEffect;
import com.example.demo.actor.projectile.BossProjectile;
import com.example.demo.controller.Controller;
import com.example.demo.level.Level004;
import com.example.demo.manager.ActorManager;
import com.example.demo.util.GameConstant;

/**
 * MultiPhaseBossPlane class representing a boss aircraft with multiple phases.
 */
public class MultiPhaseBossPlane extends FighterPlane {
private static final String imageName = GameConstant.MultiPhaseBossPlane.IMAGE_NAME;
    private static final int imageHeight = GameConstant.MultiPhaseBossPlane.IMAGE_HEIGHT;
    private static final int remainingHealthPhase1 = GameConstant.MultiPhaseBossPlane.REMAINING_HEALTH_PHASE1;
    private static final int totalHealth = remainingHealthPhase1;
    private static final int remainingHealthPhase2 = GameConstant.MultiPhaseBossPlane.REMAINING_HEALTH_PHASE2;
    private static final int remainingHealthPhase3 = GameConstant.MultiPhaseBossPlane.REMAINING_HEALTH_PHASE3;
    private static final long fireIntervalNanoseconds = GameConstant.MultiPhaseBossPlane.FIRE_INTERVAL_NANOSECONDS;
    private static final double fireRate = GameConstant.MultiPhaseBossPlane.FIRE_RATE;
    private static final double yPosition = GameConstant.MultiPhaseBossPlane.Y_POSITION;
    private static final double xPosition = GameConstant.MultiPhaseBossPlane.X_POSITION;
    private static final double projectileXPositionOffset = GameConstant.MultiPhaseBossPlane.PROJECTILE_X_POSITION_OFFSET;
    private static final double projectileYPositionOffset = GameConstant.MultiPhaseBossPlane.PROJECTILE_Y_POSITION_OFFSET;
    private static final double horizontalVelocityPhase1 = GameConstant.MultiPhaseBossPlane.HORIZONTAL_VELOCITY_PHASE1;
    private static final double horizontalVelocityPhase2 = GameConstant.MultiPhaseBossPlane.HORIZONTAL_VELOCITY_PHASE2;
    private static final double horizontalVelocityPhase3 = GameConstant.MultiPhaseBossPlane.HORIZONTAL_VELOCITY_PHASE3;
    private static final double yUpperBound = GameConstant.MultiPhaseBossPlane.Y_UPPER_BOUND;
    private static final double yLowerBound = GameConstant.MultiPhaseBossPlane.Y_LOWER_BOUND;
    private static final double xUpperBound = GameConstant.MultiPhaseBossPlane.X_UPPER_BOUND;
    private static final double xLowerBound = GameConstant.MultiPhaseBossPlane.X_LOWER_BOUND;
    private static final long summonCooldown = GameConstant.MultiPhaseBossPlane.SUMMON_COOLDOWN;
    private static final int maxFramWithSameMove = GameConstant.MultiPhaseBossPlane.MAX_FRAMES_WITH_SAME_MOVE;

    private Controller controller;
    private ActorManager actorManager;
    private long spawnTime;
    private double horizontalVelocity; 
    private double verticalVelocity; 
    private int currentPhase;
    private int currentHealth;
    private long lastFireTime;
    private long lastSummonTime;
    private double sineWaveBaseX;
    private static double ySpeedPhase2 = 100; // Pixels per second,

    private int movementFrameCount;
    private MovementState movementState;

    // Enum to represent movement states
    private enum MovementState {
        HORIZONTAL,
        SINE
    }
            
        /**
         * Constructs a MultiPhaseBossPlane at the specified position.
         *
         * @param controller The game controller.
         */
        public MultiPhaseBossPlane(Controller controller, Level004 level) {
            super(controller, imageName, imageHeight, xPosition, yPosition, totalHealth, fireIntervalNanoseconds);
            this.controller = controller;
            this.actorManager = controller.getGameStateManager().getActorManager();
            this.currentPhase = 1;
            this.lastFireTime = System.nanoTime();
            this.lastSummonTime = System.nanoTime();
            this.spawnTime = System.nanoTime();
            this.movementState = MovementState.HORIZONTAL;
            this.movementFrameCount = 0;
            this.horizontalVelocity = horizontalVelocityPhase1;
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
            switch (currentPhase) {
                case 1:
                    moveHorizontally(horizontalVelocity);
                    break;
                case 2:
                    moveInSineWavePattern(now);
                    break;
                case 3:
                moveHorizontally(horizontalVelocity);
                    if (movementState == MovementState.HORIZONTAL) {
                        moveHorizontally(horizontalVelocity);
                    } else if (movementState == MovementState.SINE) {
                        moveInSineWavePattern(now);
                    }
                    System.out.println("Current movement state: " + movementState + "movementFrameCount: " + movementFrameCount);

                    // Increment frame counter
                    movementFrameCount++;

                    // Check if it's time to switch movement state
                    if (movementState == MovementState.HORIZONTAL && movementFrameCount >= maxFramWithSameMove) {
                        movementState = MovementState.SINE;
                        movementFrameCount = 0;
                    } else if (movementState == MovementState.SINE && movementFrameCount >= maxFramWithSameMove) {
                        movementState = MovementState.HORIZONTAL;
                        movementFrameCount = 0;
                    }
                default:
                    break;
            }
        
            // Boundary checking
            if (isOutOfBounds()) {
                constrainWithinBounds();
            }
        }
        
    
        @Override
        protected void performAdditionalUpdates(long now) {
            performPhaseAttacks(now);
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
    
                    BossProjectile projectile = new BossProjectile(projectileX, projectileY);
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
            // System.out.println("Performing phase attacks for phase " + currentPhase);
            switch (currentPhase) {
                case 1:
                    // Additional behaviors for phase 1
                    break;
                case 2:
                    summonMinions(now);
                    break;
                case 3:
                    summonMinions(now);
                    break;
                default:
                    break;
            }
        }
    
        /**
         * Summons minions if cooldown allows.
         *
         * @param now The current timestamp in nanoseconds.
         */
        private void summonMinions(long now) {
            if ((now - lastSummonTime) >= summonCooldown) {
                EnemyPlane4 minion1 = new EnemyPlane4(controller);
                EnemyPlane4 minion2 = new EnemyPlane4(controller);
                System.out.println("Summoning minions" + minion1 + " and " + minion2);
    
                actorManager.addActor(minion1);
                actorManager.addActor(minion2);
    
                lastSummonTime = now;
            }
        }
    
        /**
         * Checks if the boss should transition to the next phase.
         */
        private void checkPhaseTransition(long now) {
            // System.out.println("getHealth() = " + getHealth() + "at phase " + currentPhase);
            currentHealth = getHealth();
            if (healthAtZero()){
                // Boss defeated
                System.out.println("Boss defeated");
                this.destroy();
            } else {
                switch (currentPhase) {
                    case 1:
                        if (currentHealth <= remainingHealthPhase2) {
                            currentPhase++;
                            transitionToNextPhase(now);
                        }
                        break;
                    case 2:
                        if (currentHealth <= remainingHealthPhase3) {
                            currentPhase++;
                            transitionToNextPhase(now);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    
        /**
         * Transitions the boss to the next phase.
         */
        private void transitionToNextPhase(long now) {
            switch (currentPhase) {
                case 2:
                    horizontalVelocity = horizontalVelocityPhase2;
                    verticalVelocity = ySpeedPhase2;
                    // areaAttackCooldown = GameConstant.MultiPhaseBossPlane.AREA_ATTACK_COOLDOWN_PHASE2;
                    sineWaveBaseX = getTranslateX(); // Store current X position
                    spawnTime = now; // Reset spawnTime to current time for accurate sine wave calculation
                break;
            case 3:
                horizontalVelocity = horizontalVelocityPhase3;
                // areaAttackCooldown = GameConstant.MultiPhaseBossPlane.AREA_ATTACK_COOLDOWN_PHASE3;
                break;
            default:
                break;
        }
        // Reset timers
        // lastAreaAttackTime = now;
        lastSummonTime = now;
    }

    /**
     * Moves the boss in a sine wave pattern (Phase 2).
     *
     * @param now The current timestamp in nanoseconds.
     */
    private void moveInSineWavePattern(long now) {
        // Calculate time elapsed since Phase 2 started
        double timeInSeconds = (now - spawnTime) / 1_000_000_000.0;
    
        double amplitude = 100; // Horizontal oscillation amplitude in pixels
        double frequency = 0.5; // Oscillation frequency in Hz
    
        // Calculate new X position based on sine wave
        double sineValue = Math.sin(2 * Math.PI * frequency * timeInSeconds);
        double newX = sineWaveBaseX + amplitude * sineValue;
    
        // Calculate new Y position based on vertical velocity
        double deltaTime = 0.016; // Approximate time between frames (assuming ~60 FPS)
        double newY = getTranslateY() + verticalVelocity * deltaTime;
        setTranslateX(newX);
        setTranslateY(newY);
    }    

    /**
     * Constrains the boss within the screen bounds and reverses direction if needed.
     */
    private void constrainWithinBounds() {
        double currentX = getLayoutX() + getTranslateX();
        double currentY = getLayoutY() + getTranslateY();
        
        boolean outOfHorizontal = currentX < xUpperBound || currentX > xLowerBound;
        boolean outOfVertical = currentY < yUpperBound || currentY > yLowerBound;
        
        if (outOfHorizontal) {
            horizontalVelocity = -horizontalVelocity; // Reverse horizontal direction
        }
        
        if (outOfVertical) {
            verticalVelocity = -verticalVelocity; // Reverse vertical direction
        }
    }

    public void onDamage(int damage) {
        setHealth(getHealth() - damage);
        // Check for phase transitions or boss defeat
        checkPhaseTransition(System.nanoTime());
    }
}
