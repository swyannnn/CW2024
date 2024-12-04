package com.example.demo.strategy;

import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.plane.FighterPlane;
import com.example.demo.actor.projectile.UserProjectile;
import com.example.demo.util.GameConstant;
import com.example.demo.actor.plane.UserPlane;

public class UserFiringStrategy implements FiringStrategy {
    private final ActorSpawner actorSpawner;
    private final long fireIntervalNanoseconds;
    private long lastFireTime;

    public UserFiringStrategy(ActorSpawner actorSpawner, long fireIntervalNanoseconds) {
        this.actorSpawner = actorSpawner;
        this.fireIntervalNanoseconds = fireIntervalNanoseconds;
    }

    @Override
    public void fire(FighterPlane plane, long now) {
        if (now - lastFireTime >= fireIntervalNanoseconds) {
            double projectileX = plane.getProjectileXPosition(GameConstant.UserProjectile.PROJECTILE_X_POSITION_OFFSET);
            double projectileY = plane.getProjectileYPosition(GameConstant.UserProjectile.PROJECTILE_Y_POSITION_OFFSET);
    
            UserProjectile projectile = new UserProjectile(projectileX, projectileY, ((UserPlane) plane));
            actorSpawner.spawnActor(projectile);
    
            lastFireTime = now; // Update the last fire time
        }
    }
}
