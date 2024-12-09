package com.example.demo.strategy.firing;

import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.plane.FighterPlane;
import com.example.demo.actor.projectile.BossProjectile;
import com.example.demo.actor.projectile.Projectile;
import com.example.demo.actor.projectile.ProjectileFactory;
import com.example.demo.actor.projectile.ProjectileType;


/**
 * The BossFiringStrategy class implements the FiringStrategy interface and defines the firing behavior for a boss character.
 * It uses an ActorSpawner to spawn projectiles at a specified fire rate.
 * 
 * <p>This class is responsible for determining when and where the boss fires projectiles based on a random condition
 * and the provided fire rate.</p>
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/strategy/firing/BossFiringStrategy.java">Github Source Code</a>
 * @see FiringStrategy
 * @see ActorSpawner
 * @see BossProjectile
 */
public class BossFiringStrategy implements FiringStrategy {
    private final ActorSpawner actorSpawner;
    private final double fireRate;
    private final ProjectileFactory projectileFactory = new ProjectileFactory();
    private final double offsetX;
    private final double offsetY;

    /**
     * Constructs a new BossFiringStrategy with the specified parameters.
     *
     * @param actorSpawner the ActorSpawner used to spawn actors
     * @param fireRate the rate at which the boss fires
     * @param offsetX the horizontal offset for the firing position
     * @param offsetY the vertical offset for the firing position
     */
    public BossFiringStrategy(ActorSpawner actorSpawner, double fireRate, double offsetX, double offsetY) {
        this.actorSpawner = actorSpawner;
        this.fireRate = fireRate;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
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
            double projectileX = plane.getProjectileXPosition(offsetX);
            double projectileY = plane.getProjectileYPosition(offsetY);

            Projectile projectile = projectileFactory.createProjectile(
                ProjectileType.BOSS,
                projectileX,
                projectileY
            );
            actorSpawner.addActor(projectile);
        }
    }
}
