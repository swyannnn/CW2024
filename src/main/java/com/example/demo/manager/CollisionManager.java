package com.example.demo.manager;

import com.example.demo.actor.ActiveActor;
import com.example.demo.actor.plane.UserPlane;
import com.example.demo.actor.projectile.UserProjectile;
import com.example.demo.effect.ExplosionEffect;
import com.example.demo.handler.CollisionHandler;
import com.example.demo.util.GameConstant;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

import java.util.List;


/**
 * The CollisionManager class is responsible for handling collisions between various actors in the game.
 * It follows the singleton pattern to ensure only one instance is created.
 * The class manages collision detection and processing, including handling user projectiles, enemy projectiles,
 * and player collisions with enemies.
 * It also integrates with the AudioManager for sound effects and the ActorManager for managing game actors.
 * 
 * <p>Key responsibilities include:</p>
 * <ul>
 *   <li>Managing collision handlers and processing collisions between different types of actors.</li>
 *   <li>Calculating shrunken bounds for collision detection to provide a more accurate collision area.</li>
 *   <li>Handling the effects of collisions, such as applying damage, creating explosions, and playing sound effects.</li>
 * </ul>
 * 
 * @see CollisionHandler
 * @see AudioManager
 * @see ActorManager
 * @see ActiveActor
 */
public class CollisionManager {
    private static CollisionManager instance;
    private CollisionHandler collisionHandler;
    private AudioManager audioManager;
    private ActorManager actorManager;
    private final double shrinkPercentage = GameConstant.GameSettings.COLLISION_SHRINK_PERCENTAGE;
    
    /**
     * Private constructor for the CollisionManager class.
     * Initializes the CollisionManager instance and sets up the AudioManager.
     * This constructor is private to enforce the singleton pattern.
     */
    private CollisionManager() {
        this.audioManager = AudioManager.getInstance();
    }

    /**
     * Returns the singleton instance of the CollisionManager.
     * This method is synchronized to ensure thread safety.
     * If the instance is null, it initializes a new CollisionManager.
     *
     * @return the singleton instance of CollisionManager
     */
    public static synchronized CollisionManager getInstance() {
        if (instance == null) {
            instance = new CollisionManager();
        }
        return instance;
    }

    /**
     * Sets the collision handler for managing collision events.
     *
     * @param handler the CollisionHandler to be set
     */
    public void setCollisionHandler(CollisionHandler handler) {
        this.collisionHandler = handler;
    }

    /**
     * Handles all types of collisions in the game.
     * 
     * This method processes the following collision scenarios:
     * 1. User projectiles hitting all enemies (including bosses).
     * 2. Enemy projectiles hitting players.
     * 3. Boss projectiles hitting players.
     * 4. Players colliding with all enemies (including bosses).
     * 
     * @param actorManager The ActorManager instance containing all actors in the game.
     */
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

    /**
     * Calculates and returns the shrunken bounds of the given actor.
     * The bounds are shrunk by a percentage defined by the shrinkPercentage field.
     *
     * @param actor the ActiveActor whose bounds are to be shrunken
     * @return a BoundingBox representing the shrunken bounds of the actor
     */
    private Bounds getShrunkenBounds(ActiveActor actor) {
        Bounds original = actor.getBoundsInParent();
        
        double width = original.getWidth() * shrinkPercentage;
        double height = original.getHeight() * shrinkPercentage;
        
        double minX = original.getMinX() + (original.getWidth() - width) / 2;
        double minY = original.getMinY() + (original.getHeight() - height) / 2;
        
        return new BoundingBox(minX, minY, width, height);
    }
    
    /**
     * Handles collisions between two lists of active actors.
     * 
     * This method takes two lists of active actors, `sourceActors` and `targetActors`,
     * and checks for collisions between each pair of actors from the two lists. 
     * If a collision is detected, it processes the collision.
     * 
     * @param sourceActors the list of source active actors to check for collisions
     * @param targetActors the list of target active actors to check for collisions
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

    /**
     * Processes a collision between two active actors.
     *
     * @param pair The collision pair containing the source and target actors.
     */
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
                collisionHandler.onProjectileHitEnemy(userPlane, target);
                actorManager.removeActor(projectile);
            }
        } else {
            audioManager.playSoundEffect(GameConstant.SoundEffect.PLAYER_HIT.ordinal());
        }
    }

    /**
     * Creates an explosion effect at the location of the specified target actor.
     * 
     * This method calculates the explosion coordinates based on the target's layout
     * and translation properties, then creates an ExplosionEffect at those coordinates.
     * The explosion effect is added to the UI and a sound effect is played. 
     * 
     * The collision handler is notified when the explosion starts and finishes.
     * 
     * @param target the ActiveActor at whose location the explosion will be created
     */
    private void createExplosionAt(ActiveActor target) {
        double explosionX = target.getLayoutX() + target.getTranslateX();
        double explosionY = target.getLayoutY() + target.getTranslateY() + target.getImageHeight() / 2;
        ExplosionEffect explosion = new ExplosionEffect(explosionX, explosionY);
        actorManager.addUIElement(explosion.getExplosionView());
        audioManager.playSoundEffect(GameConstant.SoundEffect.EXPLOSION.ordinal());
        // Notify that an explosion has started
        collisionHandler.onExplosionStarted();
        // Set a callback to notify when the explosion has finished
        explosion.setOnFinished(event -> {
            actorManager.removeUIElement(explosion.getExplosionView());
            collisionHandler.onExplosionFinished();
        });
        explosion.play();
    }

    /**
     * Represents a pair of colliding actors.
     * This class is used to store the source and target actors involved in a collision.
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
