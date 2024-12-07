package com.example.demo.actor.plane;

import com.example.demo.actor.ActorSpawner;
import com.example.demo.ui.Shield;
import com.example.demo.util.GameConstant;
import com.example.demo.util.PlaneConfig;


/**
 * The BossPlane class represents a specialized type of FighterPlane with an additional shield capability.
 * It extends the FighterPlane class and adds functionality for initializing and managing a shield.
 * The shield has a probability of being active, defined by the BOSS_SHIELD_PROBABILITY constant.
 * 
 * <p>This class includes methods for constructing a BossPlane, initializing its shield, performing
 * additional updates to the shield, checking if the plane is shielded, and applying damage to the plane.
 * If the shield is active, the plane does not take damage.</p>
 * 
 * <p>Constructor:</p>
 * <ul>
 *   <li>{@link #BossPlane(PlaneConfig, ActorSpawner)}: Constructs a new BossPlane with the specified configuration and actor spawner.</li>
 * </ul>
 * 
 * <p>Methods:</p>
 * <ul>
 *   <li>{@link #initializeShield(ActorSpawner)}: Initializes the shield for the BossPlane using the provided actor spawner.</li>
 *   <li>{@link #performAdditionalUpdates(long)}: Performs additional updates, specifically updating the shield state.</li>
 *   <li>{@link #isShielded()}: Checks if the BossPlane is currently shielded.</li>
 *   <li>{@link #takeDamage()}: Applies damage to the BossPlane. If the shield is active, no damage is taken.</li>
 * </ul>
 * 
 * <p>Fields:</p>
 * <ul>
 *   <li>{@link #shield}: The shield associated with the BossPlane.</li>
 *   <li>{@link #BOSS_SHIELD_PROBABILITY}: The probability that the shield is active, defined by a game constant.</li>
 * </ul>
 */
public class BossPlane extends FighterPlane {
    private Shield shield;
    private static final double BOSS_SHIELD_PROBABILITY = GameConstant.BossShield.BOSS_SHIELD_PROBABILITY;

    /**
     * Constructs a new BossPlane with the specified configuration and actor spawner.
     *
     * @param config the configuration for the plane
     * @param actorSpawner the actor spawner used to initialize the shield
     */
    public BossPlane(PlaneConfig config, ActorSpawner actorSpawner) {
        super(config);
        initializeShield(actorSpawner);
    }

    /**
     * Initializes the shield for the BossPlane.
     *
     * @param actorSpawner the actor spawner used to initialize the shield
     */
    private void initializeShield(ActorSpawner actorSpawner) {
        shield = new Shield(BOSS_SHIELD_PROBABILITY);
        actorSpawner.addUIElement(shield);
    }

    /**
     * Performs additional updates, specifically updating the shield.
     *
     * @param now The current time in nanoseconds.
     */
    @Override
    protected void performAdditionalUpdates(long now) {
        shield.updateShieldState(getLayoutX(), getLayoutY());
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
     * Applies damage to the BossPlane. If the BossPlane is shielded, no damage is taken.
     *
     * @return True if the BossPlane took damage, false otherwise.
     */
    @Override
    public boolean takeDamage() {
        if (shield.isShielded()) {
            System.out.println("BossPlane is shielded. No damage taken.");
            return false; // Damage not applied
        }
        return super.takeDamage(); // Delegate to base class
    }
}
