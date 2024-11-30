package com.example.demo.manager;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.plane.BossPlane;
import com.example.demo.actor.plane.EnemyPlane;
import com.example.demo.actor.plane.UserPlane;
import com.example.demo.actor.projectile.UserProjectile;
import com.example.demo.effect.ExplosionEffect;
import com.example.demo.listener.CollisionListener;

import java.util.List;

/**
 * CollisionManager handles all collision detections and responses between actors.
 */
public class CollisionManager {
    private static CollisionManager instance;
    private CollisionListener collisionListener;
    private ActorManager actorManager;
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
        this.actorManager = actorManager;
    
        // Handle user projectiles hitting all enemies (including bosses)
        handleCollisions(actorManager.getUserProjectiles(), actorManager.getEnemyUnits());
    
        // Handle enemy projectiles hitting players
        handleCollisions(actorManager.getEnemyProjectiles(), actorManager.getPlayers());
    
        // Handle boss projectiles hitting players
        handleCollisions(actorManager.getBossProjectiles(), actorManager.getPlayers());
    
        // Handle player collisions with enemy units
        handleCollisions(actorManager.getPlayers(), actorManager.getEnemyUnits());
    
        // Handle player collisions with bosses
        handleCollisions(actorManager.getPlayers(), actorManager.getBossUnits());
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

    private void processCollision(CollisionPair pair) {
        ActiveActorDestructible source = pair.source;
        ActiveActorDestructible target = pair.target;
        System.out.println("Collision detected: " + source + " hit " + target);
    
        boolean targetDamaged = false;
        source.takeDamage();
        targetDamaged = target.takeDamage();
        System.out.println("isEnemy(target) = " + isEnemy(target) + ", targetDamaged = " + targetDamaged);
        
        if (targetDamaged && !(target instanceof UserPlane)){
            createExplosionAt(target);
            if (source instanceof UserProjectile) {
                UserProjectile projectile = (UserProjectile) source;
                UserPlane userPlane = projectile.getOwner();
                collisionListener.onProjectileHitEnemy(userPlane, target);
            }
        }
    }

    private void createExplosionAt(ActiveActorDestructible target) {
        double explosionX = target.getLayoutX() + target.getTranslateX();
        double explosionY = target.getLayoutY() + target.getTranslateY() + target.getImageHeight() / 2;
        ExplosionEffect explosion = new ExplosionEffect(explosionX, explosionY);
        actorManager.addUIElement(explosion.getExplosionView());
        // Notify that an explosion has started
        collisionListener.onExplosionStarted();
        // Set a callback to notify when the explosion has finished
        explosion.setOnFinished(event -> {
            actorManager.removeUIElement(explosion.getExplosionView());
            collisionListener.onExplosionFinished();
        });
        explosion.play();
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
}
