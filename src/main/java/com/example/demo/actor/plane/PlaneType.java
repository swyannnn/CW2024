// PlaneType.java
package com.example.demo.actor.plane;

import com.example.demo.strategy.firing.*;
import com.example.demo.strategy.movement.*;
import com.example.demo.util.GameConstant;

import java.util.function.BiFunction;


/**
 * Enum representing different types of planes in the game.
 * Each plane type has specific attributes such as image name, image height,
 * initial position, health, fire rate, speed, and strategies for movement and firing.
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


    /**
     * Retrieves the image name associated with this plane type.
     *
     * @return the image name as a String.
     */
    public String getImageName() { 
        return imageName; 
    }

    /**
     * Returns the height of the image associated with the plane type.
     *
     * @return the height of the image in pixels
     */
    public int getImageHeight() {
        return imageHeight;
    }

    /**
     * Returns the initial X position of the plane.
     *
     * @return the initial X position as a double.
     */
    public double getInitialXPos() {
        return initialXPos; 
    }

    /**
     * Retrieves the upper bound of the Y coordinate.
     *
     * @return the upper bound of the Y coordinate.
     */
    public double getYUpperBound() {
         return yUpperBound; 
    }

    /**
     * Retrieves the lower bound of the Y coordinate.
     *
     * @return the lower bound of the Y coordinate.
     */
    public double getYLowerBound() {
         return yLowerBound; 
    }
    /**
     * Returns the health of the plane.
     *
     * @return the current health value of the plane.
     */
    public int getHealth() {
        return health; 
    }

    /**
     * Retrieves the fire rate of the plane.
     *
     * @return the fire rate as a double.
     */
    public double getFireRate() {
         return fireRate; 
    }

    /**
     * Returns the fire interval in nanoseconds.
     *
     * @return the fire interval in nanoseconds.
     */
    public long getFireIntervalNanoseconds() {
         return fireIntervalNanoseconds; 
    }

    /**
     * Retrieves the speed of the plane.
     *
     * @return the speed of the plane as an integer.
     */
    public int getSpeed() {
         return speed; 
    }

    /**
     * Returns a BiFunction that creates a MovementStrategy based on the provided PlaneFactory and PlaneConfig.
     *
     * @return a BiFunction that takes a PlaneFactory and PlaneConfig as inputs and produces a MovementStrategy.
     */
    public BiFunction<PlaneFactory, PlaneConfig, MovementStrategy> getMovementStrategyCreator() {
        return movementStrategyCreator; 
    }
    
    /**
     * Returns the BiFunction that creates a FiringStrategy based on the given PlaneFactory and PlaneConfig.
     *
     * @return a BiFunction that takes a PlaneFactory and PlaneConfig as parameters and returns a FiringStrategy.
     */
    public BiFunction<PlaneFactory, PlaneConfig, FiringStrategy> getFiringStrategyCreator() {
         return firingStrategyCreator; 
    }
}
