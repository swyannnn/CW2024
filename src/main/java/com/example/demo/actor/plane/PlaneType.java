// PlaneType.java
package com.example.demo.actor.plane;

import com.example.demo.strategy.firing.*;
import com.example.demo.strategy.movement.*;
import com.example.demo.util.GameConstant;

import java.util.function.BiFunction;

/**
 * Enum representing different types of planes in the game.
 */
public enum PlaneType {
    ENEMY_PLANE(
        GameConstant.EnemyPlane.IMAGE_NAME,
        GameConstant.EnemyPlane.IMAGE_HEIGHT,
        GameConstant.EnemyPlane.X_LOWER_BOUND,
        GameConstant.EnemyPlane.Y_UPPER_BOUND,
        GameConstant.EnemyPlane.Y_LOWER_BOUND,
        GameConstant.EnemyPlane.INITIAL_HEALTH,
        GameConstant.EnemyProjectile.FIRE_RATE,
        GameConstant.EnemyProjectile.FIRE_INTERVAL_NANOSECONDS,
        GameConstant.EnemyPlane.HORIZONTAL_VELOCITY,
        (factory, config) -> new EnemyMovementStrategy(config.speed),
        (factory, config) -> new EnemyFiringStrategy(factory.getActorSpawner(), config.fireRate)
    ),
    
    ENEMY_PLANE1(
        GameConstant.EnemyPlane1.IMAGE_NAME,
        GameConstant.EnemyPlane1.IMAGE_HEIGHT,
        GameConstant.EnemyPlane1.X_LOWER_BOUND,
        GameConstant.EnemyPlane1.Y_UPPER_BOUND,
        GameConstant.EnemyPlane1.Y_LOWER_BOUND,
        GameConstant.EnemyPlane1.INITIAL_HEALTH,
        GameConstant.EnemyProjectile.FIRE_RATE,
        GameConstant.EnemyProjectile.FIRE_INTERVAL_NANOSECONDS,
        GameConstant.EnemyPlane1.HORIZONTAL_VELOCITY,
        (factory, config) -> new EnemyMovementStrategy(config.speed),
        (factory, config) -> new EnemyFiringStrategy(factory.getActorSpawner(), config.fireRate)
    ),
    
    ENEMY_PLANE2(
        GameConstant.EnemyPlane2.IMAGE_NAME,
        GameConstant.EnemyPlane2.IMAGE_HEIGHT,
        GameConstant.EnemyPlane2.X_LOWER_BOUND,
        GameConstant.EnemyPlane2.Y_UPPER_BOUND,
        GameConstant.EnemyPlane2.Y_LOWER_BOUND,
        GameConstant.EnemyPlane2.INITIAL_HEALTH,
        GameConstant.EnemyProjectile.FIRE_RATE,
        GameConstant.EnemyProjectile.FIRE_INTERVAL_NANOSECONDS,
        GameConstant.EnemyPlane2.HORIZONTAL_VELOCITY,
        (factory, config) -> new EnemyMovementStrategy(config.speed),
        (factory, config) -> new EnemyFiringStrategy(factory.getActorSpawner(), config.fireRate)
    ),

    ENEMY_PLANE3(
        GameConstant.EnemyPlane3.IMAGE_NAME,
        GameConstant.EnemyPlane3.IMAGE_HEIGHT,
        GameConstant.EnemyPlane3.X_LOWER_BOUND,
        GameConstant.EnemyPlane3.Y_UPPER_BOUND,
        GameConstant.EnemyPlane3.Y_LOWER_BOUND,
        GameConstant.EnemyPlane3.INITIAL_HEALTH,
        GameConstant.EnemyProjectile.FIRE_RATE,
        GameConstant.EnemyProjectile.FIRE_INTERVAL_NANOSECONDS,
        GameConstant.EnemyPlane3.HORIZONTAL_VELOCITY,
        (factory, config) -> new EnemyMovementStrategy(config.speed),
        (factory, config) -> new EnemyFiringStrategy(factory.getActorSpawner(), config.fireRate)
    ),

    ENEMY_PLANE4(
        GameConstant.EnemyPlane4.IMAGE_NAME,
        GameConstant.EnemyPlane4.IMAGE_HEIGHT,
        GameConstant.EnemyPlane4.X_LOWER_BOUND,
        GameConstant.EnemyPlane4.Y_UPPER_BOUND,
        GameConstant.EnemyPlane4.Y_LOWER_BOUND,
        GameConstant.EnemyPlane4.INITIAL_HEALTH,
        GameConstant.EnemyProjectile.FIRE_RATE,
        GameConstant.EnemyProjectile.FIRE_INTERVAL_NANOSECONDS,
        GameConstant.EnemyPlane4.HORIZONTAL_VELOCITY,
        (factory, config) -> new EnemyMovementStrategy(config.speed),
        (factory, config) -> new EnemyFiringStrategy(factory.getActorSpawner(), config.fireRate)
    ),

    BOSS_PLANE(
        GameConstant.BossPlane.IMAGE_NAME,
        GameConstant.BossPlane.IMAGE_HEIGHT,
        GameConstant.BossPlane.INITIAL_X_POSITION,
        GameConstant.BossPlane.INITIAL_Y_POSITION,
        GameConstant.BossPlane.INITIAL_Y_POSITION, // Fixed Y position
        GameConstant.BossPlane.INITIAL_HEALTH,
        GameConstant.BossProjectile.FIRE_RATE,
        GameConstant.BossPlane.FIRE_INTERVAL_NANOSECONDS,
        GameConstant.BossPlane.VERTICAL_VELOCITY,
        (factory, config) -> new BossMovementStrategy(config.speed),
        (factory, config) -> new BossFiringStrategy(factory.getActorSpawner(), config.fireRate)
    ),

    MULTI_PHASE_BOSS_PLANE(
        GameConstant.MultiPhaseBossPlane.IMAGE_NAME,
        GameConstant.MultiPhaseBossPlane.IMAGE_HEIGHT,
        GameConstant.MultiPhaseBossPlane.X_POSITION,
        GameConstant.MultiPhaseBossPlane.Y_POSITION,
        GameConstant.MultiPhaseBossPlane.Y_POSITION, // Fixed Y position
        GameConstant.MultiPhaseBossPlane.REMAINING_HEALTH_PHASE1,
        GameConstant.MultiPhaseBossPlane.FIRE_RATE,
        GameConstant.MultiPhaseBossPlane.FIRE_INTERVAL_NANOSECONDS,
        0, // Speed is handled by movement strategy
        (factory, config) -> new MultiPhaseBossMovementStrategy(config.speed),
        (factory, config) -> new MultiPhaseBossFiringStrategy(factory.getActorSpawner(), config.fireRate)
    ),
    
    USER_PLANE(
        null, // imageName handled separately based on playerId
        GameConstant.UserPlane.IMAGE_HEIGHT,
        GameConstant.UserPlane.INITIAL_X_POSITION,
        0, // yUpperBound not applicable
        0, // yLowerBound not applicable
        GameConstant.UserPlane.INITIAL_HEALTH,
        0, // fireRate not applicable
        GameConstant.UserProjectile.FIRE_INTERVAL_NANOSECONDS,
        GameConstant.UserPlane.VELOCITY,
        null, // movementStrategyCreator handled separately
        (factory, config) -> new UserFiringStrategy(factory.getActorSpawner(), config.fireIntervalNanoseconds)
    )
    ;

    private final String imageName;
    private final int imageHeight;
    private final double initialXPos;
    private final double yUpperBound;
    private final double yLowerBound;
    private final int health;
    private final double fireRate;
    private final long fireIntervalNanoseconds;
    private final int speed;
    private final BiFunction<PlaneFactory, PlaneConfig, MovementStrategy> movementStrategyCreator;
    private final BiFunction<PlaneFactory, PlaneConfig, FiringStrategy> firingStrategyCreator;

    PlaneType(
        String imageName,
        int imageHeight,
        double initialXPos,
        double yUpperBound,
        double yLowerBound,
        int health,
        double fireRate,
        long fireIntervalNanoseconds,
        int speed,
        BiFunction<PlaneFactory, PlaneConfig, MovementStrategy> movementStrategyCreator,
        BiFunction<PlaneFactory, PlaneConfig, FiringStrategy> firingStrategyCreator
    ) {
        this.imageName = imageName;
        this.imageHeight = imageHeight;
        this.initialXPos = initialXPos;
        this.yUpperBound = yUpperBound;
        this.yLowerBound = yLowerBound;
        this.health = health;
        this.fireRate = fireRate;
        this.fireIntervalNanoseconds = fireIntervalNanoseconds;
        this.speed = speed;
        this.movementStrategyCreator = movementStrategyCreator;
        this.firingStrategyCreator = firingStrategyCreator;
    }

    // Getters for all properties
    public String getImageName() { return imageName; }
    public int getImageHeight() { return imageHeight; }
    public double getInitialXPos() { return initialXPos; }
    public double getYUpperBound() { return yUpperBound; }
    public double getYLowerBound() { return yLowerBound; }
    public int getHealth() { return health; }
    public double getFireRate() { return fireRate; }
    public long getFireIntervalNanoseconds() { return fireIntervalNanoseconds; }
    public int getSpeed() { return speed; }
    public BiFunction<PlaneFactory, PlaneConfig, MovementStrategy> getMovementStrategyCreator() { return movementStrategyCreator; }
    public BiFunction<PlaneFactory, PlaneConfig, FiringStrategy> getFiringStrategyCreator() { return firingStrategyCreator; }
}
