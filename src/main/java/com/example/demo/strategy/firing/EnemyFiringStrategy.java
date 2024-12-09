package com.example.demo.strategy.firing;

import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.plane.FighterPlane;
import com.example.demo.actor.projectile.Projectile;
import com.example.demo.actor.projectile.EnemyProjectile;
import com.example.demo.actor.projectile.ProjectileFactory;
import com.example.demo.actor.projectile.ProjectileType;

/**
 * The EnemyFiringStrategy class implements the FiringStrategy interface and defines the firing behavior for enemy planes.
 * It uses an ActorSpawner to spawn projectiles at a specified fire rate.
 * 
 * <p>This class is responsible for determining when an enemy plane should fire a projectile based on a random chance
 * influenced by the fire rate. When the plane fires, a new EnemyProjectile is created and spawned at the plane's
 * current position with an offset.</p>
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/strategy/firing/EnemyFiringStrategy.java">Github Source Code</a>
 * @see FiringStrategy
 * @see ActorSpawner
 * @see EnemyProjectile
 */
public class EnemyFiringStrategy implements FiringStrategy {
    private final ProjectileFactory projectileFactory = new ProjectileFactory();
    private final ActorSpawner actorSpawner;
    private final double fireRate;
    private final double offsetX;
    private final double offsetY;

    /**
     * Constructs an EnemyFiringStrategy with the specified parameters.
     *
     * @param actorSpawner the ActorSpawner instance responsible for spawning actors
     * @param fireRate the rate at which the enemy fires
     * @param offsetX the horizontal offset for the firing position
     * @param offsetY the vertical offset for the firing position
     */
    public EnemyFiringStrategy(ActorSpawner actorSpawner, double fireRate, double offsetX, double offsetY) {
        this.actorSpawner = actorSpawner;
        this.fireRate = fireRate;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        System.out.println("EnemyFiringStrategy: fireRate: " + fireRate + ", offsetX: " + offsetX + ", offsetY: " + offsetY);
    }

    /**
     * Fires a projectile from the given fighter plane if the random condition meets the fire rate.
     * 
     * @param plane The fighter plane from which the projectile is fired.
     * @param now The current time in nanoseconds.
     */
    @Override
    public void fire(FighterPlane plane, long now) {
        if (Math.random() < fireRate) {
            double projectileX = plane.getProjectileXPosition(offsetX);
            double projectileY = plane.getProjectileYPosition(offsetY);

            Projectile projectile = projectileFactory.createProjectile(
                ProjectileType.ENEMY,
                projectileX,
                projectileY
            );
            actorSpawner.addActor(projectile);
        }
    }
}
