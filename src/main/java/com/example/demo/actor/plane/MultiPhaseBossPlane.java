package com.example.demo.actor.plane;

import com.example.demo.actor.ActiveActor;
import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.plane.PlaneFactory.PlaneType;
import com.example.demo.strategy.MultiPhaseBossMovementStrategy;
import com.example.demo.util.GameConstant;

/**
 * The MultiPhaseBossPlane class represents a boss plane with multiple phases in a game.
 * It extends the FighterPlane class and adds functionality for phase transitions and summoning minions.
 * 
 * <p>This class handles the following functionalities:
 * <ul>
 *   <li>Updating the boss plane's state each frame.</li>
 *   <li>Performing additional updates such as summoning minions and handling phase transitions.</li>
 *   <li>Handling attacks specific to the current phase.</li>
 *   <li>Summoning minions if the cooldown period has elapsed.</li>
 *   <li>Checking and handling phase transitions based on the plane's current health.</li>
 *   <li>Transitioning the boss to phase 2 and phase 3 by updating necessary properties.</li>
 *   <li>Handling damage taken by the boss plane.</li>
 * </ul>
 * 
 * <p>Each phase transition updates the movement strategy if it is an instance of MultiPhaseBossMovementStrategy.
 * The boss plane can summon minions in phases 2 and 3, and transitions to the next phase based on its remaining health.
 * 
 * @see FighterPlane
 * @see PlaneFactory
 * @see ActorSpawner
 * @see GameConstant.MultiPhaseBossPlane
 * @see MultiPhaseBossMovementStrategy
 */
public class MultiPhaseBossPlane extends FighterPlane {
    private static final long SUMMON_COOLDOWN = GameConstant.MultiPhaseBossPlane.SUMMON_COOLDOWN;
    private ActorSpawner actorSpawner;
    private PlaneFactory planeFactory;
    private long lastSummonTime;
    private int currentPhase;
    private int remainingHealthPhase2 = GameConstant.MultiPhaseBossPlane.REMAINING_HEALTH_PHASE2;
    private int remainingHealthPhase3 = GameConstant.MultiPhaseBossPlane.REMAINING_HEALTH_PHASE3;

    public MultiPhaseBossPlane(PlaneConfig config, ActorSpawner actorSpawner) {
        super(config);
        this.planeFactory = new PlaneFactory(actorSpawner);
        this.actorSpawner = actorSpawner;
        this.currentPhase = 1;
        this.lastSummonTime = System.nanoTime();
    }

    /**
     * Updates the state of the MultiPhaseBossPlane.
     * This method overrides the update method from the superclass to include
     * additional updates specific to the MultiPhaseBossPlane.
     *
     * @param now The current timestamp in nanoseconds.
     */
    protected void performAdditionalUpdates(long now) {
        performPhaseAttacks(now);
        checkPhaseTransition(now);
    }

    /**
     * Executes the attacks for the current phase of the boss plane.
     * In phases 2 and 3, it will summon minions in addition to the basic firing strategy.
     *
     * @param now The current time in nanoseconds.
     */
    private void performPhaseAttacks(long now) {
        if (currentPhase >= 2) {
            summonMinions(now);
        }
    }

    /**
     * Summons two minion planes if the cooldown period has passed since the last summon.
     * The minions are created using the plane factory and then spawned using the actor spawner.
     * The last summon time is updated to the current time after summoning the minions.
     *
     * @param now The current time in milliseconds.
     */
    private void summonMinions(long now) {
        if ((now - lastSummonTime) >= SUMMON_COOLDOWN) {
            ActiveActor minion1 = planeFactory.createPlane(PlaneType.ENEMY_PLANE4);
            ActiveActor minion2 = planeFactory.createPlane(PlaneType.ENEMY_PLANE4);

            actorSpawner.spawnActor(minion1);
            actorSpawner.spawnActor(minion2);

            lastSummonTime = now;
        }
    }

    /**
     * Checks and handles the transition between different phases of the boss plane
     * based on its current health.
     *
     * @param now The current time in milliseconds.
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
     * Transitions the boss plane to Phase 2.
     * This method prints a message indicating the transition and updates the movement strategy to Phase 2
     *
     * @param now The current time in milliseconds.
     */
    private void transitionToPhase2(long now) {
        if (movementStrategy instanceof MultiPhaseBossMovementStrategy) {
            ((MultiPhaseBossMovementStrategy) movementStrategy).updatePhase(2);
        }
    }

    /**
     * Transitions the boss plane to Phase 3.
     * This method prints a message indicating the transition and updates the movement strategy to Phase 3
     *
     * @param now The current time in milliseconds.
     */
    private void transitionToPhase3(long now) {
        if (movementStrategy instanceof MultiPhaseBossMovementStrategy) {
            ((MultiPhaseBossMovementStrategy) movementStrategy).updatePhase(3);
        }
    }

    /**
     * Handles the damage inflicted on the plane by reducing its health and checking for phase transitions.
     *
     * @param damage the amount of damage to be inflicted on the plane
     */
    public void onDamage(int damage) {
        setHealth(getHealth() - damage);
        checkPhaseTransition(System.nanoTime());
    }
}
