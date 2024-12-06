package com.example.demo.actor.plane;

import com.example.demo.actor.ActorSpawner;
import com.example.demo.ui.Shield;
import com.example.demo.util.GameConstant;
import com.example.demo.util.PlaneConfig;

/**
 * BossPlane class representing the boss enemy in the game.
 */
public class BossPlane extends FighterPlane {
    private Shield shield;
    private static final double BOSS_SHIELD_PROBABILITY = GameConstant.BossShield.BOSS_SHIELD_PROBABILITY;

    /**
     * Constructs a BossPlane instance.
     *
     * @param controller The game controller managing the state.
     * @param config     The PlaneConfig containing configuration.
     */
    public BossPlane(PlaneConfig config, ActorSpawner actorSpawner) {
        super(config);
        initializeShield(actorSpawner);
    }

    /**
     * Initializes BossPlane-specific configurations.
     *
     * @param config The PlaneConfig containing configuration.
     */
    private void initializeShield(ActorSpawner actorSpawner) {
        // Initialize Shield
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
     * Method to take damage when the boss plane is not shielded.
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
