package com.example.demo.interfaces;

import com.example.demo.actor.ActiveActor;
import com.example.demo.actor.plane.UserPlane;

/**
 * The CollisionListener interface provides methods to handle collision events
 * in the game. Implementations of this interface can define custom behavior
 * for when explosions start and finish, and when a user's projectile hits an enemy.
 */
public interface CollisionListener {
    /**
     * This method is called when an explosion event starts.
     * Implement this method to define the behavior that should occur
     * at the beginning of an explosion.
     */
    void onExplosionStarted();


    /**
     * This method is called when an explosion has finished.
     * Implement this method to define the behavior that should occur
     * after an explosion event is completed.
     */
    void onExplosionFinished();
    
    /**
     * Called when a user's projectile hits an enemy.
     *
     * @param userPlane The user plane associated with the projectile.
     * @param enemy     The enemy that was hit.
     */
    void onProjectileHitEnemy(UserPlane userPlane, ActiveActor enemy);
}
