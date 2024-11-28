package com.example.demo.manager;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.plane.BossPlane;
import com.example.demo.actor.plane.EnemyPlane;
import com.example.demo.actor.plane.UserPlane;
import com.example.demo.actor.projectile.UserProjectile;
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

    public void handleAllCollisions(ActorManager actorManager) {
        handleUserProjectileEnemyCollisions(actorManager.getUserProjectiles(), actorManager.getEnemyUnits());
        handlePlayerEnemyProjectileCollisions(actorManager.getEnemyProjectiles(), actorManager.getPlayers());
        handlePlayerBossProjectileCollisions(actorManager.getBossProjectiles(), actorManager.getPlayers());
        handleEnemyPlayerCollisions(actorManager.getEnemyUnits(), actorManager.getPlayers());
        handleUserProjectileBossCollisions(actorManager.getUserProjectiles(), actorManager.getBossUnits());
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
        
        sourceActors.stream()
            .flatMap(source -> targetActors.stream()
                .filter(target -> source.getBoundsInParent().intersects(target.getBoundsInParent()))
                .map(target -> new CollisionPair(source, target)))
            .forEach(this::processCollision);
    }

    /**
     * Processes a single collision between a source and a target actor.
     *
     * @param pair The collision pair containing source and target actors.
     */
    private void processCollision(CollisionPair pair) {
        ActiveActorDestructible source = pair.source;
        ActiveActorDestructible target = pair.target;
        System.out.println("Collision detected: " + source + " hit " + target);
        source.takeDamage();
        target.takeDamage();
        
        if (collisionListener != null && source instanceof UserProjectile projectile && isEnemy(target)) {
            UserPlane userPlane = getUserPlaneForProjectile(projectile);
            System.out.println("Collision detected haha: " + projectile + " hit " + target);
            
            if (userPlane != null) {
                collisionListener.onProjectileHitEnemy(userPlane, target);
            }
        }
    }

    /**
     * Determines if the target actor is an enemy (EnemyPlane or BossPlane).
     *
     * @param target The target actor to check.
     * @return True if the target is an EnemyPlane or BossPlane, otherwise false.
     */
    private boolean isEnemy(ActiveActorDestructible target) {
        return target instanceof EnemyPlane || target instanceof BossPlane;
    }

    /**
     * A simple helper class to hold a pair of actors involved in a collision.
     */
    private static class CollisionPair {
        final ActiveActorDestructible source;
        final ActiveActorDestructible target;

        CollisionPair(ActiveActorDestructible source, ActiveActorDestructible target) {
            this.source = source;
            this.target = target;
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
     * Handles collisions between projectiles and enemy units.
     *
     * @param projectiles The list of projectiles.
     * @param enemies     The list of enemy units.
     */
    public void handleUserProjectileBossCollisions(List<ActiveActorDestructible> userProjectiles, List<ActiveActorDestructible> boss) {
        handleCollisions(userProjectiles, boss);
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
