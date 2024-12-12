package com.example.demo.actor.plane.component;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShieldTest {

    private Shield shield;
    private static final double ACTIVATION_PROBABILITY = 0.5;

    @BeforeEach
    public void setUp() {
        shield = new Shield(ACTIVATION_PROBABILITY);
    }

    @Test
    public void testActivateShield() {
        shield.activateShield();
        shield.deactivateShield();
        assertFalse(shield.isShielded());
        assertFalse(shield.isVisible());
    }

    @Test
    public void testDeactivateShield() {
        shield.activateShield();
        shield.deactivateShield();
        assertFalse(shield.isShielded());
        assertFalse(shield.isVisible());
    }
}