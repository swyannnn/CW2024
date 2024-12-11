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
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/strategy/firing/UserFiringStrategy.java">Github Source Code</a>
 * @see FiringStrategy
 * @see ActorSpawner
 * @see UserProjectile
 */
public class UserFiringStrategy implements FiringStrategy {
    private final ProjectileFactory projectileFactory = new ProjectileFactory();
    private final ActorSpawner actorSpawner;
    private final long fireIntervalNanoseconds;
    private final AudioManager audioManager;
    private long lastFireTime;
    private static long lastSoundPlayedTime = 0; // Static to track across instances
    private final double offsetX;
    private final double offsetY;
    private static final long soundCooldown = GameConstant.UserProjectile.SOUND_COOL_DOWN;

    /**
     * Constructs a new UserFiringStrategy with the specified parameters.
     *
     * @param actorSpawner the ActorSpawner instance used to spawn actors
     * @param fireIntervalNanoseconds the interval between firing actions in nanoseconds
     * @param offsetX the horizontal offset for the firing position
     * @param offsetY the vertical offset for the firing position
     */
    public UserFiringStrategy(ActorSpawner actorSpawner, long fireIntervalNanoseconds, double offsetX, double offsetY) {
        this.actorSpawner = actorSpawner;
        this.fireIntervalNanoseconds = fireIntervalNanoseconds;
        this.audioManager = AudioManager.getInstance();
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    /**
     * Fires a projectile from the given fighter plane if the fire interval has passed.
     * Also plays a sound effect if the sound cooldown has passed.
     *
     * @param plane The fighter plane from which the projectile is fired.
     * @param now The current time in nanoseconds.
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

            // Play sound effect only if sufficient time has passed
            if (now - lastSoundPlayedTime >= soundCooldown) {
                audioManager.playSoundEffect(GameConstant.SoundEffect.PLAYER_SHOOT.ordinal());
                lastSoundPlayedTime = now;
            }

            lastFireTime = now; // Update the last fire time
        }
    }
}

