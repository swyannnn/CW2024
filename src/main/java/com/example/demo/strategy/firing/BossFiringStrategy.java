package com.example.demo.strategy.firing;

import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.plane.FighterPlane;
import com.example.demo.actor.projectile.BossProjectile;
import com.example.demo.actor.projectile.Projectile;
import com.example.demo.actor.projectile.ProjectileFactory;
import com.example.demo.actor.projectile.ProjectileType;
import com.example.demo.util.GameConstant;


/**
 * The BossFiringStrategy class implements the FiringStrategy interface and defines the firing behavior for a boss character.
 * It uses an ActorSpawner to spawn projectiles at a specified fire rate.
 * 
 * <p>This class is responsible for determining when and where the boss fires projectiles based on a random condition
 * and the provided fire rate.</p>
 * 
 * @see FiringStrategy
 * @see ActorSpawner
 * @see FighterPlane
 * @see BossProjectile
 */
public class BossFiringStrategy implements FiringStrategy {
    private final ActorSpawner actorSpawner;
    private final double fireRate;
    private final ProjectileFactory projectileFactory = new ProjectileFactory();

    /**
     * Constructs a new BossFiringStrategy with the specified actor spawner and fire rate.
     *
     * @param actorSpawner the actor spawner responsible for spawning actors
     * @param fireRate the rate at which the boss fires
     */
    public BossFiringStrategy(ActorSpawner actorSpawner, double fireRate) {
        this.actorSpawner = actorSpawner;
        this.fireRate = fireRate;
    }

    /**
     * Fires a projectile from the given fighter plane if the random condition meets the fire rate.
     *
     * @param plane the fighter plane from which the projectile is fired
     * @param now the current time in nanoseconds
     */
    @Override
    public void fire(FighterPlane plane, long now) {
        if (Math.random() < fireRate) {
            double projectileX = plane.getProjectileXPosition(GameConstant.EnemyProjectile.PROJECTILE_X_POSITION_OFFSET);
            double projectileY = plane.getProjectileYPosition(GameConstant.EnemyProjectile.PROJECTILE_Y_POSITION_OFFSET);

            Projectile projectile = projectileFactory.createProjectile(
                ProjectileType.BOSS,
                projectileX,
                projectileY
            );
            actorSpawner.addActor(projectile);
        }
    }
}
