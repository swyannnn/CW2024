package com.example.demo.manager;

import com.example.demo.ActiveActorDestructible;
import java.util.List;

/**
 * CollisionManager handles all collision detections and responses between actors.
 */
public class CollisionManager {
    private static CollisionManager instance;

    // Private constructor to enforce Singleton pattern
    private CollisionManager() {}

    /**
     * Retrieves the singleton instance of CollisionManager.
     *
     * @return The singleton instance of CollisionManager.
     */
    public static synchronized CollisionManager getInstance() {
        if (instance == null) {
            instance = new CollisionManager();
        }
        return instance;
    }

    /**
     * Handles collisions between two lists of actors.
     *
     * @param actors1 The first list of actors.
     * @param actors2 The second list of actors.
     */
    public void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor : actors2) {
            for (ActiveActorDestructible otherActor : actors1) {
                if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
                    actor.takeDamage();
                    otherActor.takeDamage();
                }
            }
        }
    }

    /**
     * Handles collisions between projectiles and enemy units.
     *
     * @param projectiles The list of projectiles.
     * @param enemies     The list of enemy units.
     */
    public void handleProjectileCollisions(List<ActiveActorDestructible> projectiles, List<ActiveActorDestructible> enemies) {
        handleCollisions(projectiles, enemies);
    }

    /**
     * Handles collisions between enemy projectiles and friendly units.
     *
     * @param enemyProjectiles The list of enemy projectiles.
     * @param friendlyUnits    The list of friendly units.
     */
    public void handleEnemyProjectileCollisions(List<ActiveActorDestructible> enemyProjectiles, List<ActiveActorDestructible> friendlyUnits) {
        handleCollisions(enemyProjectiles, friendlyUnits);
    }

    /**
     * Handles collisions between friendly and enemy units.
     *
     * @param friendlyUnits The list of friendly units.
     * @param enemyUnits    The list of enemy units.
     */
    public void handleUnitCollisions(List<ActiveActorDestructible> friendlyUnits, List<ActiveActorDestructible> enemyUnits) {
        handleCollisions(friendlyUnits, enemyUnits);
    }
}
