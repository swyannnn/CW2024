package com.example.demo.manager;

import com.example.demo.actor.ActiveActor;
import com.example.demo.actor.plane.UserPlane;
import com.example.demo.actor.projectile.UserProjectile;
import com.example.demo.effect.ExplosionEffect;
import com.example.demo.interfaces.CollisionListener;
import com.example.demo.util.GameConstant;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

import java.util.List;

/**
 * CollisionManager handles all collision detections and responses between actors.
 */
public class CollisionManager {
    private static CollisionManager instance;
    private CollisionListener collisionListener;
    private AudioManager audioManager;
    private ActorManager actorManager;
    private final double shrinkPercentage = GameConstant.GameSettings.COLLISION_SHRINK_PERCENTAGE;
    
    private CollisionManager() {
        this.audioManager = AudioManager.getInstance();
    }

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
    
        // Handle player collisions with all enemies (including bosses)
        handleCollisions(actorManager.getPlayers(), actorManager.getEnemyUnits());
    }

    private Bounds getShrunkenBounds(ActiveActor actor) {
        Bounds original = actor.getBoundsInParent();
        
        double width = original.getWidth() * shrinkPercentage;
        double height = original.getHeight() * shrinkPercentage;
        
        double minX = original.getMinX() + (original.getWidth() - width) / 2;
        double minY = original.getMinY() + (original.getHeight() - height) / 2;
        
        return new BoundingBox(minX, minY, width, height);
    }
    
    /**
     * Handles collisions between two lists of actors.
     *
     * @param sourceActors The list of source actors (e.g., projectiles).
     * @param targetActors The list of target actors (e.g., players).
     */
    private void handleCollisions(
        List<? extends ActiveActor> sourceActors, 
        List<? extends ActiveActor> targetActors) {
        
        sourceActors.stream()
            .flatMap(source -> targetActors.stream()
                .filter(target -> {
                    Bounds sourceBounds = getShrunkenBounds(source);
                    Bounds targetBounds = getShrunkenBounds(target);
                    return sourceBounds.intersects(targetBounds);
                })
                .map(target -> new CollisionPair(source, target)))
            .forEach(this::processCollision);
    }

    private void processCollision(CollisionPair pair) {
        ActiveActor source = pair.source;
        ActiveActor target = pair.target;
        System.out.println("Collision detected: " + source + " hit " + target);
    
        source.takeDamage();
        target.takeDamage();
        
        if (!(target instanceof UserPlane)){
            createExplosionAt(target);
            if (source instanceof UserProjectile) {
                UserProjectile projectile = (UserProjectile) source;
                UserPlane userPlane = projectile.getOwner();
                collisionListener.onProjectileHitEnemy(userPlane, target);
                actorManager.removeActor(projectile);
            }
        } else {
            audioManager.playSoundEffect(GameConstant.SoundEffect.PLAYER_HIT.ordinal());
        }
    }

    private void createExplosionAt(ActiveActor target) {
        double explosionX = target.getLayoutX() + target.getTranslateX();
        double explosionY = target.getLayoutY() + target.getTranslateY() + target.getImageHeight() / 2;
        ExplosionEffect explosion = new ExplosionEffect(explosionX, explosionY);
        actorManager.addUIElement(explosion.getExplosionView());
        audioManager.playSoundEffect(GameConstant.SoundEffect.EXPLOSION.ordinal());
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
     * A simple helper class to hold a pair of actors involved in a collision.
     */
    private static class CollisionPair {
        final ActiveActor source;
        final ActiveActor target;

        CollisionPair(ActiveActor source, ActiveActor target) {
            this.source = source;
            this.target = target;
        }
    }
}
