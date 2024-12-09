package com.example.demo.strategy.firing;

import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.plane.FighterPlane;
import com.example.demo.actor.projectile.Projectile;
import com.example.demo.actor.projectile.ProjectileFactory;
import com.example.demo.actor.projectile.ProjectileType;
import com.example.demo.actor.projectile.UserProjectile;
import com.example.demo.manager.AudioManager;
import com.example.demo.util.GameConstant;
import com.example.demo.actor.plane.UserPlane;

/**
 * The UserFiringStrategy class implements the FiringStrategy interface and defines the firing behavior for user planes.
 * It uses an ActorSpawner to spawn projectiles at a specified fire rate.
 * 
 * <p>This class is responsible for determining when a user plane should fire a projectile based on the fire interval.
 * When the plane fires, a new UserProjectile is created and spawned at the plane's current position with an offset.</p>
 * 
 * @see FiringStrategy
 * @see ActorSpawner
 * @see FighterPlane
 * @see UserProjectile
 */
public class UserFiringStrategy implements FiringStrategy {
    private final ProjectileFactory projectileFactory = new ProjectileFactory();
    private final ActorSpawner actorSpawner;
    private final long fireIntervalNanoseconds;
    private final AudioManager audioManager;
    private long lastFireTime;
    private final double offsetX;
    private final double offsetY;

    /**
     * Constructs a UserFiringStrategy with the specified actor spawner and fire interval.
     *
     * @param actorSpawner the actor spawner responsible for spawning actors
     * @param fireIntervalNanoseconds the interval between fires in nanoseconds
     */
    public UserFiringStrategy(ActorSpawner actorSpawner, long fireIntervalNanoseconds, double offsetX, double offsetY) {
        this.actorSpawner = actorSpawner;
        this.fireIntervalNanoseconds = fireIntervalNanoseconds;
        this.audioManager = AudioManager.getInstance();
        this.offsetX = offsetX;
        this.offsetY = offsetX;
    }

    /**
     * Fires a projectile from the given fighter plane if the specified time interval has passed since the last fire.
     *
     * @param plane the fighter plane from which the projectile is fired
     * @param now the current time in nanoseconds
     */
    @Override
    public void fire(FighterPlane plane, long now) {
        if (now - lastFireTime >= fireIntervalNanoseconds) {
            double projectileX = plane.getProjectileXPosition(offsetX);
            double projectileY = plane.getProjectileYPosition(offsetY);
            
            Projectile projectile = projectileFactory.createProjectile(
                ProjectileType.USER,
                projectileX,
                projectileY,
                (UserPlane) plane
            );
            actorSpawner.addActor(projectile);
            audioManager.playSoundEffect(GameConstant.SoundEffect.PLAYER_SHOOT.ordinal());
    
            lastFireTime = now; // Update the last fire time
        }
    }
}
