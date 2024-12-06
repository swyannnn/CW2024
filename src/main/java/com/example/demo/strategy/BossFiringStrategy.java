package com.example.demo.strategy;

import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.plane.FighterPlane;
import com.example.demo.actor.projectile.BossProjectile;
import com.example.demo.util.GameConstant;

public class BossFiringStrategy implements FiringStrategy {
    private final ActorSpawner actorSpawner;
    private final double fireRate;

    public BossFiringStrategy(ActorSpawner actorSpawner, double fireRate) {
        this.actorSpawner = actorSpawner;
        this.fireRate = fireRate;
    }

    @Override
    public void fire(FighterPlane plane, long now) {
        if (Math.random() < fireRate) {
            double projectileX = plane.getProjectileXPosition(GameConstant.EnemyProjectile.PROJECTILE_X_POSITION_OFFSET);
            double projectileY = plane.getProjectileYPosition(GameConstant.EnemyProjectile.PROJECTILE_Y_POSITION_OFFSET);

            BossProjectile projectile = new BossProjectile(projectileX, projectileY);
            actorSpawner.spawnActor(projectile);
        }
    }
}
