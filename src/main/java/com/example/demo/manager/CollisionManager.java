package com.example.demo.manager;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.UserPlane;

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
     * @param sourceActors The list of source actors (e.g., projectiles).
     * @param targetActors The list of target actors (e.g., players).
     */
    private void handleCollisions(
            List<? extends ActiveActorDestructible> sourceActors, 
            List<? extends ActiveActorDestructible> targetActors) {
        for (ActiveActorDestructible source : sourceActors) {
            for (ActiveActorDestructible target : targetActors) {
                if (source.getBoundsInParent().intersects(target.getBoundsInParent())) {
                    source.takeDamage();
                    target.takeDamage();
                    System.out.println("Collision detected: " + source + " hit " + target);
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
     * Handles collisions between enemy projectiles and player entities.
     *
     * @param enemyProjectiles The list of enemy projectiles.
     * @param players          The list of player entities.
     */
    public void handleEnemyProjectileCollisions(
            List<ActiveActorDestructible> enemyProjectiles, 
            List<UserPlane> players) {
        handleCollisions(enemyProjectiles, players);
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
