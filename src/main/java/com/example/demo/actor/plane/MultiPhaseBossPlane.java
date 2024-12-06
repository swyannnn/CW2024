package com.example.demo.actor.plane;

import com.example.demo.actor.ActiveActor;
import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.PlaneFactory;
import com.example.demo.actor.PlaneFactory.PlaneType;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ActorManager;
import com.example.demo.strategy.MultiPhaseBossMovementStrategy;
import com.example.demo.util.GameConstant;
import com.example.demo.util.PlaneConfig;

/**
 * MultiPhaseBossPlane class representing a boss aircraft with multiple phases.
 */
public class MultiPhaseBossPlane extends FighterPlane {
    private static final long SUMMON_COOLDOWN = GameConstant.MultiPhaseBossPlane.SUMMON_COOLDOWN;
    private ActorSpawner actorSpawner;
    private PlaneFactory planeFactory;
    private long lastSummonTime;
    private int currentPhase;
    private int remainingHealthPhase2;
    private int remainingHealthPhase3;

    public MultiPhaseBossPlane(Controller controller, PlaneConfig config, ActorSpawner actorSpawner) {
        super(controller, config);
        this.planeFactory = new PlaneFactory(controller, controller.getGameStateManager().getActorManager());
        this.actorSpawner = actorSpawner;
        this.currentPhase = 1;
        this.lastSummonTime = System.nanoTime();
        this.remainingHealthPhase2 = GameConstant.MultiPhaseBossPlane.REMAINING_HEALTH_PHASE2;
        this.remainingHealthPhase3 = GameConstant.MultiPhaseBossPlane.REMAINING_HEALTH_PHASE3;
    }

    /**
     * Updates the boss plane's state each frame.
     *
     * @param now The current timestamp in nanoseconds.
     */
    @Override
    public void update(long now) {
        super.update(now); // Handles movement and firing via strategies
        performAdditionalUpdates(now);
    }

    /**
     * Performs additional updates such as summoning minions and handling phase transitions.
     *
     * @param now The current timestamp in nanoseconds.
     */
    protected void performAdditionalUpdates(long now) {
        performPhaseAttacks(now);
        checkPhaseTransition(now);
    }

    /**
     * Handles attacks specific to the current phase.
     *
     * @param now The current timestamp in nanoseconds.
     */
    private void performPhaseAttacks(long now) {
        // Basic firing is handled by the firing strategy
        // Summon minions in phases 2 and 3
        if (currentPhase >= 2) {
            summonMinions(now);
        }
    }

    /**
     * Summons minions if the cooldown period has elapsed.
     *
     * @param now The current timestamp in nanoseconds.
     */
    private void summonMinions(long now) {
        if ((now - lastSummonTime) >= SUMMON_COOLDOWN) {
            ActiveActor minion1 = planeFactory.createPlane(PlaneType.ENEMY_PLANE4);
            ActiveActor minion2 = planeFactory.createPlane(PlaneType.ENEMY_PLANE4);
            System.out.println("Summoning minions: " + minion1 + " and " + minion2);

            actorSpawner.spawnActor(minion1);
            actorSpawner.spawnActor(minion2);

            lastSummonTime = now;
        }
    }

    /**
     * Checks and handles phase transitions based on the plane's current health.
     *
     * @param now The current timestamp in nanoseconds.
     */
    private void checkPhaseTransition(long now) {
        int currentHealth = getHealth();
        if (currentHealth <= 0) {
            // Boss defeated
            System.out.println("Boss defeated");
            this.destroy();
            return;
        }
        switch (currentPhase) {
            case 1:
                if (currentHealth <= remainingHealthPhase2) {
                    currentPhase = 2;
                    transitionToPhase2(now);
                }
                break;
            case 2:
                if (currentHealth <= remainingHealthPhase3) {
                    currentPhase = 3;
                    transitionToPhase3(now);
                }
                break;
            case 3:
                break;
            default:
                break;
        }
    }

    /**
     * Transitions the boss to phase 2 by updating necessary properties.
     *
     * @param now The current timestamp in nanoseconds.
     */
    private void transitionToPhase2(long now) {
        System.out.println("Transitioning to Phase 2");
        if (movementStrategy instanceof MultiPhaseBossMovementStrategy) {
            ((MultiPhaseBossMovementStrategy) movementStrategy).updatePhase(2);
        }
    }

    /**
     * Transitions the boss to phase 3 by updating necessary properties.
     *
     * @param now The current timestamp in nanoseconds.
     */
    private void transitionToPhase3(long now) {
        System.out.println("Transitioning to Phase 3");
        if (movementStrategy instanceof MultiPhaseBossMovementStrategy) {
            ((MultiPhaseBossMovementStrategy) movementStrategy).updatePhase(3);
        }
    }

    /**
     * Handles damage taken by the boss plane.
     *
     * @param damage The amount of damage inflicted.
     */
    public void onDamage(int damage) {
        setHealth(getHealth() - damage);
        checkPhaseTransition(System.nanoTime());
    }
}
