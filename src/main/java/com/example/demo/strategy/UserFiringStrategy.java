package com.example.demo.strategy;

import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.plane.FighterPlane;
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
    private final ActorSpawner actorSpawner;
    private final long fireIntervalNanoseconds;
    private final AudioManager audioManager;
    private long lastFireTime;

    /**
     * Constructs a UserFiringStrategy with the specified actor spawner and fire interval.
     *
     * @param actorSpawner the actor spawner responsible for spawning actors
     * @param fireIntervalNanoseconds the interval between fires in nanoseconds
     */
    public UserFiringStrategy(ActorSpawner actorSpawner, long fireIntervalNanoseconds) {
        this.actorSpawner = actorSpawner;
        this.fireIntervalNanoseconds = fireIntervalNanoseconds;
        this.audioManager = AudioManager.getInstance();
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
            double projectileX = plane.getProjectileXPosition(GameConstant.UserProjectile.PROJECTILE_X_POSITION_OFFSET);
            double projectileY = plane.getProjectileYPosition(GameConstant.UserProjectile.PROJECTILE_Y_POSITION_OFFSET);
    
            UserProjectile projectile = new UserProjectile(projectileX, projectileY, ((UserPlane) plane));
            actorSpawner.spawnActor(projectile);
            audioManager.playSoundEffect(GameConstant.SoundEffect.PLAYER_SHOOT.ordinal());
    
            lastFireTime = now; // Update the last fire time
        }
    }
}
