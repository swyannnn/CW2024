package com.example.demo.listener;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.UserPlane;

public interface CollisionListener {
    /**
     * Called when a user's projectile hits an enemy.
     *
     * @param userPlane The user plane associated with the projectile.
     * @param enemy     The enemy that was hit.
     */
    void onProjectileHitEnemy(UserPlane userPlane, ActiveActorDestructible enemy);
}
