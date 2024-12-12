package com.example.demo.actor.plane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserPlaneTest {

    private UserPlane userPlane;
    private PlaneConfig config;

    @BeforeEach
    void setUp() {
        config = new PlaneConfig();
        config.health = 5;
        config.fireRate = 1.0;
        config.projectileOffsetX = 0.0;
        config.projectileOffsetY = 0.0;
        config.speed = 1;
        userPlane = new UserPlane(config, 1);
    }

    @Test
    void testIsDestroyedWhenHealthIsZero() {
        userPlane.setHealth(0); // Assuming a setter exists
        assertTrue(userPlane.isDestroyed());
    }

    @Test
    void testIsDestroyedWhenHealthIsAboveZero() {
        userPlane.setHealth(1); // Assuming a setter exists
        assertFalse(userPlane.isDestroyed());
    }

    @Test
    void testIncrementKillCount() {
        int initialKills = userPlane.getNumberOfKills();
        userPlane.incrementKillCount();
        assertEquals(initialKills + 1, userPlane.getNumberOfKills());
    }
}
