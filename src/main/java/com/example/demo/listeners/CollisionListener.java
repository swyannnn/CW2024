package com.example.demo.listeners;

import com.example.demo.actor.ActiveActor;
import com.example.demo.actor.plane.UserPlane;

public interface CollisionListener {
    void onExplosionStarted();
    void onExplosionFinished();
    /**
     * Called when a user's projectile hits an enemy.
     *
     * @param userPlane The user plane associated with the projectile.
     * @param enemy     The enemy that was hit.
     */
    void onProjectileHitEnemy(UserPlane userPlane, ActiveActor enemy);
}
