// PlaneType.java
package com.example.demo.actor.plane;

import com.example.demo.strategy.firing.*;
import com.example.demo.strategy.movement.*;
import com.example.demo.util.GameConstant;

import java.util.function.BiFunction;


/**
 * The PlaneType enum represents different types of planes in the game, each with its own unique properties and behaviors.
 * Each plane type is defined with various attributes such as image name, image height, initial position, health, fire rate,
 * projectile offsets, speed, and strategies for movement and firing.
 * 
 * <p>Each enum constant in PlaneType corresponds to a specific type of plane and provides the necessary configuration
 * for creating and managing that plane type within the game.</p>
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/actor/plane/PlaneType.java">Github Source Code</a>
 */
public enum PlaneType {
    ENEMY_PLANE(
        GameConstant.EnemyPlane.IMAGE_NAME,
        GameConstant.EnemyPlane.IMAGE_HEIGHT,
        GameConstant.EnemyPlane.X_LOWER_BOUND,
        GameConstant.EnemyPlane.Y_UPPER_BOUND,
        GameConstant.EnemyPlane.Y_LOWER_BOUND,
        GameConstant.EnemyPlane.INITIAL_HEALTH,
        GameConstant.EnemyPlane.FIRE_RATE,
        0, // fireIntervalNanoseconds not applicable
        GameConstant.EnemyPlane.PROJECTILE_X_POSITION_OFFSET,
        GameConstant.EnemyPlane.PROJECTILE_Y_POSITION_OFFSET,
        GameConstant.EnemyPlane.HORIZONTAL_VELOCITY,
        (factory, config) -> new EnemyMovementStrategy(config.speed),
        (factory, config) -> new EnemyFiringStrategy(factory.getActorSpawner(), config.fireRate, config.projectileOffsetX, config.projectileOffsetY)
    ),
    
    ENEMY_PLANE1(
        GameConstant.EnemyPlane1.IMAGE_NAME,
        GameConstant.EnemyPlane1.IMAGE_HEIGHT,
        GameConstant.EnemyPlane1.X_LOWER_BOUND,
        GameConstant.EnemyPlane1.Y_UPPER_BOUND,
        GameConstant.EnemyPlane1.Y_LOWER_BOUND,
        GameConstant.EnemyPlane1.INITIAL_HEALTH,
        GameConstant.EnemyPlane1.FIRE_RATE,
        0,
        GameConstant.EnemyPlane1.PROJECTILE_X_POSITION_OFFSET,
        GameConstant.EnemyPlane1.PROJECTILE_Y_POSITION_OFFSET,
        GameConstant.EnemyPlane1.HORIZONTAL_VELOCITY,
        (factory, config) -> new EnemyMovementStrategy(config.speed),
        (factory, config) -> new EnemyFiringStrategy(factory.getActorSpawner(), config.fireRate, config.projectileOffsetX, config.projectileOffsetY)
    ),
    
    ENEMY_PLANE2(
        GameConstant.EnemyPlane2.IMAGE_NAME,
        GameConstant.EnemyPlane2.IMAGE_HEIGHT,
        GameConstant.EnemyPlane2.X_LOWER_BOUND,
        GameConstant.EnemyPlane2.Y_UPPER_BOUND,
        GameConstant.EnemyPlane2.Y_LOWER_BOUND,
        GameConstant.EnemyPlane2.INITIAL_HEALTH,
        GameConstant.EnemyPlane2.FIRE_RATE,
0,
        GameConstant.EnemyPlane2.PROJECTILE_X_POSITION_OFFSET,
        GameConstant.EnemyPlane2.PROJECTILE_Y_POSITION_OFFSET,
        GameConstant.EnemyPlane2.HORIZONTAL_VELOCITY,
        (factory, config) -> new EnemyMovementStrategy(config.speed),
        (factory, config) -> new EnemyFiringStrategy(factory.getActorSpawner(), config.fireRate, config.projectileOffsetX, config.projectileOffsetY)
    ),

    ENEMY_PLANE3(
        GameConstant.EnemyPlane3.IMAGE_NAME,
        GameConstant.EnemyPlane3.IMAGE_HEIGHT,
        GameConstant.EnemyPlane3.X_LOWER_BOUND,
        GameConstant.EnemyPlane3.Y_UPPER_BOUND,
        GameConstant.EnemyPlane3.Y_LOWER_BOUND,
        GameConstant.EnemyPlane3.INITIAL_HEALTH,
        GameConstant.EnemyPlane3.FIRE_RATE,
        0,
        GameConstant.EnemyPlane3.PROJECTILE_X_POSITION_OFFSET,
        GameConstant.EnemyPlane3.PROJECTILE_Y_POSITION_OFFSET,
        GameConstant.EnemyPlane3.HORIZONTAL_VELOCITY,
        (factory, config) -> new EnemyMovementStrategy(config.speed),
        (factory, config) -> new EnemyFiringStrategy(factory.getActorSpawner(), config.fireRate, config.projectileOffsetX, config.projectileOffsetY)
    ),

    ENEMY_PLANE4(
        GameConstant.EnemyPlane4.IMAGE_NAME,
        GameConstant.EnemyPlane4.IMAGE_HEIGHT,
        GameConstant.EnemyPlane4.X_LOWER_BOUND,
        GameConstant.EnemyPlane4.Y_UPPER_BOUND,
        GameConstant.EnemyPlane4.Y_LOWER_BOUND,
        GameConstant.EnemyPlane4.INITIAL_HEALTH,
        GameConstant.EnemyPlane4.FIRE_RATE,
        0,
        GameConstant.EnemyPlane4.PROJECTILE_X_POSITION_OFFSET,
        GameConstant.EnemyPlane4.PROJECTILE_Y_POSITION_OFFSET,
        GameConstant.EnemyPlane4.HORIZONTAL_VELOCITY,
        (factory, config) -> new EnemyMovementStrategy(config.speed),
        (factory, config) -> new EnemyFiringStrategy(factory.getActorSpawner(), config.fireRate, config.projectileOffsetX, config.projectileOffsetY)
    ),

    BOSS_PLANE(
        GameConstant.BossPlane.IMAGE_NAME,
        GameConstant.BossPlane.IMAGE_HEIGHT,
        GameConstant.BossPlane.INITIAL_X_POSITION,
        GameConstant.BossPlane.INITIAL_Y_POSITION,
        GameConstant.BossPlane.INITIAL_Y_POSITION, // Fixed Y position
        GameConstant.BossPlane.INITIAL_HEALTH,
        GameConstant.BossProjectile.FIRE_RATE,
        0,
        GameConstant.BossPlane.PROJECTILE_X_POSITION_OFFSET,
        GameConstant.BossPlane.PROJECTILE_Y_POSITION_OFFSET,
        GameConstant.BossPlane.VERTICAL_VELOCITY,
        (factory, config) -> new BossMovementStrategy(config.speed),
        (factory, config) -> new BossFiringStrategy(factory.getActorSpawner(), config.fireRate, config.projectileOffsetX, config.projectileOffsetY)
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
        GameConstant.MultiPhaseBossPlane.PROJECTILE_X_POSITION_OFFSET,
        GameConstant.MultiPhaseBossPlane.PROJECTILE_Y_POSITION_OFFSET,
        0, // Speed is handled by movement strategy
        (factory, config) -> new MultiPhaseBossMovementStrategy(config.speed),
        (factory, config) -> new MultiPhaseBossFiringStrategy(factory.getActorSpawner(), config.fireRate, config.projectileOffsetX, config.projectileOffsetY)
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
        GameConstant.UserPlane.PROJECTILE_X_POSITION_OFFSET,
        GameConstant.UserPlane.PROJECTILE_Y_POSITION_OFFSET,
        GameConstant.UserPlane.VELOCITY,
        null, // movementStrategyCreator handled separately
        (factory, config) -> new UserFiringStrategy(factory.getActorSpawner(), config.fireIntervalNanoseconds, config.projectileOffsetX, config.projectileOffsetY)
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
    private final double projectileOffsetX;
    private final double projectileOffsetY;
    private final int speed;
    private final BiFunction<PlaneFactory, PlaneConfig, MovementStrategy> movementStrategyCreator;
    private final BiFunction<PlaneFactory, PlaneConfig, FiringStrategy> firingStrategyCreator;

    /**
     * Constructs a new PlaneType with the specified parameters.
     *
     * @param imageName The name of the image representing the plane.
     * @param imageHeight The height of the image representing the plane.
     * @param initialXPos The initial X position of the plane.
     * @param yUpperBound The upper Y boundary for the plane's movement.
     * @param yLowerBound The lower Y boundary for the plane's movement.
     * @param health The health points of the plane.
     * @param fireRate The rate at which the plane can fire projectiles.
     * @param fireIntervalNanoseconds The interval between firing projectiles in nanoseconds.
     * @param projectileOffsetX The X offset for the projectile's initial position.
     * @param projectileOffsetY The Y offset for the projectile's initial position.
     * @param speed The speed of the plane.
     * @param movementStrategyCreator A BiFunction to create the movement strategy for the plane.
     * @param firingStrategyCreator A BiFunction to create the firing strategy for the plane.
     */
    PlaneType(
        String imageName,
        int imageHeight,
        double initialXPos,
        double yUpperBound,
        double yLowerBound,
        int health,
        double fireRate,
        long fireIntervalNanoseconds,
        double projectileOffsetX,
        double projectileOffsetY,
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
        this.projectileOffsetX = projectileOffsetX;
        this.projectileOffsetY = projectileOffsetY;
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
     * Retrieves the X offset for the projectile.
     *
     * @return the X offset for the projectile as a double.
     */
    public double getProjectileOffsetX() {
         return projectileOffsetX; 
    }

    /**
     * Retrieves the Y offset for the projectile.
     *
     * @return the Y offset for the projectile as a double.
     */
    public double getProjectileOffsetY() {
         return projectileOffsetY;
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
