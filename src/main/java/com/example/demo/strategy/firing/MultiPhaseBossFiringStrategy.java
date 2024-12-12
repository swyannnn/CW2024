package com.example.demo.strategy.firing;

import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.plane.FighterPlane;
import com.example.demo.actor.projectile.BossProjectile;
import com.example.demo.actor.projectile.ProjectileFactory;
import com.example.demo.actor.projectile.ProjectileType;

/**
 * The MultiPhaseBossFiringStrategy class implements the FiringStrategy interface
 * and defines the firing behavior for a multi-phase boss in a game.
 * It uses a ProjectileFactory to create projectiles and an ActorSpawner to spawn them.
 * The firing rate is determined by a specified fire rate.
 *
 *
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/strategy/firing/MultiPhaseBossFiringStrategy.java">Github Source Code</a>
 * @see FiringStrategy
 * @see ActorSpawner
 * @see BossProjectile
 */
public class MultiPhaseBossFiringStrategy implements FiringStrategy {
    private final ProjectileFactory projectileFactory = new ProjectileFactory();
    private final ActorSpawner actorSpawner;
    private final double fireRate;
    private final double offsetX;
    private final double offsetY;

    /**
     * Constructs a new MultiPhaseBossFiringStrategy with the specified actor spawner and fire rate.
     *
     * @param actorSpawner the actor spawner used to spawn actors
     * @param fireRate the rate at which the boss fires
     */
    public MultiPhaseBossFiringStrategy(ActorSpawner actorSpawner, double fireRate, double offsetX, double offsetY) {
        this.actorSpawner = actorSpawner;
        this.fireRate = fireRate;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
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
            double projectileX = plane.getProjectileXPosition(offsetX);
            double projectileY = plane.getProjectileYPosition(offsetY);

            BossProjectile projectile = (BossProjectile) projectileFactory.createProjectile(
                ProjectileType.BOSS,
                projectileX,
                projectileY
            );
            actorSpawner.addActor(projectile);
        }
    }
}

