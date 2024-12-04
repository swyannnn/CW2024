package com.example.demo.actor.plane;

import com.example.demo.actor.PlaneConfig;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ActorManager;
import com.example.demo.ui.Shield;
import com.example.demo.util.GameConstant;

/**
 * BossPlane class representing the boss enemy in the game.
 */
public class BossPlane extends FighterPlane {
    private Shield shield;
    private final Controller controller;

    // Shield-related constants
    private static final double BOSS_SHIELD_PROBABILITY = GameConstant.BossShield.BOSS_SHIELD_PROBABILITY;

    /**
     * Constructs a BossPlane instance.
     *
     * @param controller The game controller managing the state.
     * @param config     The PlaneConfig containing configuration.
     */
    public BossPlane(Controller controller, PlaneConfig config) {
        super(controller, config);
        this.controller = controller;
        initializeShield();
    }

    private void initializeShield() {
        shield = new Shield(controller, BOSS_SHIELD_PROBABILITY);
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
