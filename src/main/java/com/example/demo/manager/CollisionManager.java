package com.example.demo.manager;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.planes.EnemyPlane;
import com.example.demo.actors.planes.UserPlane;
import com.example.demo.actors.planes.BossPlane;
import com.example.demo.actors.projectile.UserProjectile;
import com.example.demo.listener.CollisionListener;

import java.util.List;

/**
 * CollisionManager handles all collision detections and responses between actors.
 */
public class CollisionManager {
    private static CollisionManager instance;
    private CollisionListener collisionListener;
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

    public void setCollisionListener(CollisionListener listener) {
        this.collisionListener = listener;
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
                    
                    if (collisionListener != null && source instanceof UserProjectile && ((target instanceof EnemyPlane) || (target instanceof BossPlane))) {
                        UserProjectile projectile = (UserProjectile) source; 
                        UserPlane userPlane = getUserPlaneForProjectile(projectile);
                        System.out.println("Collision detected haha: " + projectile + " hit " + target);
                    
                        if (userPlane != null) {
                            collisionListener.onProjectileHitEnemy(userPlane, target);
                        }
                    }  
                }              
            }
        }
    }


    private UserPlane getUserPlaneForProjectile(UserProjectile projectile) {
        return projectile.getOwner();
    }

    /**
     * Handles collisions between projectiles and enemy units.
     *
     * @param projectiles The list of projectiles.
     * @param enemies     The list of enemy units.
     */
    public void handleUserProjectileEnemyCollisions(List<ActiveActorDestructible> userProjectiles, List<ActiveActorDestructible> enemies) {
        handleCollisions(userProjectiles, enemies);
    }

    /**
     * Handles collisions between enemy projectiles and player entities.
     *
     * @param enemyProjectiles The list of enemy projectiles.
     * @param players          The list of player entities.
     */
    public void handlePlayerEnemyProjectileCollisions(List<ActiveActorDestructible> enemyProjectiles, List<UserPlane> players) {
        handleCollisions(players, enemyProjectiles);
    }

    /**
     * Handles collisions between enemy projectiles and player entities.
     *
     * @param enemyProjectiles The list of enemy projectiles.
     * @param players          The list of player entities.
     */
    public void handlePlayerBossProjectileCollisions(List<ActiveActorDestructible> bossProjectiles, List<UserPlane> players) {
        handleCollisions(players, bossProjectiles);
    }

    /**
     * Handles collisions between enemy projectiles and player entities.
     *
     * @param enemyProjectiles The list of enemy.
     * @param players          The list of player entities.
     */
    public void handleEnemyPlayerCollisions(List<ActiveActorDestructible> enemies, List<UserPlane> players) {
        handleCollisions(enemies, players);
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
