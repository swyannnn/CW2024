package com.example.demo.strategy.firing;

import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.plane.FighterPlane;
import com.example.demo.actor.projectile.BossProjectile;
import com.example.demo.actor.projectile.ProjectileFactory;
import com.example.demo.actor.projectile.ProjectileType;
import com.example.demo.util.GameConstant;

/**
 * The MultiPhaseBossFiringStrategy class implements the FiringStrategy interface
 * and defines the firing behavior for a multi-phase boss in a game.
 * It uses a ProjectileFactory to create projectiles and an ActorSpawner to spawn them.
 * The firing rate is determined by a specified fire rate.
 *
 * <p>This class is responsible for:
 * <ul>
 *   <li>Constructing a new MultiPhaseBossFiringStrategy with the specified actor spawner and fire rate.</li>
 *   <li>Firing a projectile from the given fighter plane if the random condition based on fireRate is met.</li>
 * </ul>
 *
 * @see FiringStrategy
 * @see ProjectileFactory
 * @see ActorSpawner
 * @see FighterPlane
 * @see BossProjectile
 * @see ProjectileType
 * @see GameConstant
 */
public class MultiPhaseBossFiringStrategy implements FiringStrategy {
    private final ProjectileFactory projectileFactory = new ProjectileFactory();
    private final ActorSpawner actorSpawner;
    private final double fireRate;

    /**
     * Constructs a new MultiPhaseBossFiringStrategy with the specified actor spawner and fire rate.
     *
     * @param actorSpawner the actor spawner used to spawn actors
     * @param fireRate the rate at which the boss fires
     */
    public MultiPhaseBossFiringStrategy(ActorSpawner actorSpawner, double fireRate) {
        this.actorSpawner = actorSpawner;
        this.fireRate = fireRate;
    }

    /**
     * Fires a projectile from the given fighter plane if the random condition based on fireRate is met.
     * The projectile's initial position is determined by the plane's projectile position offsets.
     * The projectile is then spawned using the actorSpawner.
     *
     * @param plane the fighter plane from which the projectile is fired
     * @param now the current time in milliseconds
     */
    @Override
    public void fire(FighterPlane plane, long now) {
        if (Math.random() < fireRate) {
            double projectileX = plane.getProjectileXPosition(GameConstant.EnemyProjectile.PROJECTILE_X_POSITION_OFFSET);
            double projectileY = plane.getProjectileYPosition(GameConstant.EnemyProjectile.PROJECTILE_Y_POSITION_OFFSET);

            BossProjectile projectile = (BossProjectile) projectileFactory.createProjectile(
                ProjectileType.BOSS,
                projectileX,
                projectileY
            );
            actorSpawner.addActor(projectile);
        }
    }
}

