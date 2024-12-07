package com.example.demo.actor.plane;

import com.example.demo.actor.ActorSpawner;
import com.example.demo.strategy.BossFiringStrategy;
import com.example.demo.strategy.BossMovementStrategy;
import com.example.demo.strategy.EnemyFiringStrategy;
import com.example.demo.strategy.EnemyMovementStrategy;
import com.example.demo.strategy.MultiPhaseBossFiringStrategy;
import com.example.demo.strategy.MultiPhaseBossMovementStrategy;
import com.example.demo.strategy.UserFiringStrategy;
import com.example.demo.util.GameConstant;
import com.example.demo.util.PlaneConfig;



/**
 * The PlaneFactory class is responsible for creating various types of planes in the game.
 * It uses the ActorSpawner to spawn different plane instances based on the specified PlaneType.
 * 
 * The class supports creating different types of enemy planes, user planes, boss planes, and multi-phase boss planes.
 * It provides methods to create planes with specific configurations and strategies for movement and firing.
 * 
 * The PlaneFactory class includes the following functionalities:
 * - Creating different types of planes based on the PlaneType enum.
 * - Creating user planes with specific player configurations.
 * - Setting up configurations for enemy planes, including image, position, health, fire rate, and strategies.
 * - Setting up configurations for boss planes and multi-phase boss planes.
 * - Calculating initial Y positions for planes within specified bounds.
 * 
 * The PlaneType enum represents the different types of planes that can be created:
 * - ENEMY_PLANE
 * - ENEMY_PLANE1
 * - ENEMY_PLANE2
 * - ENEMY_PLANE3
 * - ENEMY_PLANE4
 * - BOSS_PLANE
 * - USER_PLANE
 * - MULTI_PHASE_BOSS_PLANE
 * 
 * The createPlane method creates a FighterPlane based on the specified PlaneType.
 * The createPlane method with playerId creates a UserPlane for the specified player.
 * The createUserPlane method creates a UserPlane instance for a given player.
 * The getPlayerImageName method retrieves the image name associated with the given player ID.
 * The getUserInitialYPosition method returns the initial Y position for a user based on their player ID.
 * The createEnemyPlane, createEnemy1Plane, createEnemy2Plane, createEnemy3Plane, createEnemy4Plane methods create instances of EnemyPlane with specific properties.
 * The createBossPlane method creates and configures a new instance of BossPlane.
 * The createMultiPhaseBossPlane method creates and configures a new instance of MultiPhaseBossPlane.
 * The calculateInitialYPos method calculates the initial Y position of a plane within the specified bounds.
 */
public class PlaneFactory {
    private final ActorSpawner actorSpawner;

    /**
     * Constructs a PlaneFactory with the specified ActorSpawner.
     *
     * @param actorSpawner the ActorSpawner to be used by this PlaneFactory
     */
    public PlaneFactory(ActorSpawner actorSpawner) {
        this.actorSpawner = actorSpawner;
    }

    /**
     * Enum representing different types of planes in the game.
     */
    public enum PlaneType {
        ENEMY_PLANE,
        ENEMY_PLANE1,
        ENEMY_PLANE2,
        ENEMY_PLANE3,
        ENEMY_PLANE4,
        BOSS_PLANE,
        USER_PLANE,
        MULTI_PHASE_BOSS_PLANE
    }

    /**
     * Creates a FighterPlane based on the specified PlaneType.
     *
     * @param type the type of plane to create
     * @return a FighterPlane instance corresponding to the specified type
     * @throws IllegalArgumentException if the specified plane type is unknown
     */
    public FighterPlane createPlane(PlaneType type) {
        switch (type) {
            case ENEMY_PLANE:
                return createEnemyPlane();
            case ENEMY_PLANE1:
                return createEnemy1Plane();
            case ENEMY_PLANE2:
                return createEnemy2Plane();
            case ENEMY_PLANE3:
                return createEnemy3Plane();
            case ENEMY_PLANE4:
                return createEnemy4Plane();
            case BOSS_PLANE:
                return createBossPlane();
            case MULTI_PHASE_BOSS_PLANE:
                return createMultiPhaseBossPlane();
            default:
                throw new IllegalArgumentException("Unknown plane type: " + type);
        }
    }

    /**
     * Creates a FighterPlane based on the specified PlaneType and playerId.
     *
     * @param type the type of the plane to be created
     * @param playerId the ID of the player for whom the plane is being created
     * @return a FighterPlane instance of the specified type for the given player
     */
    public FighterPlane createPlane(PlaneType type, int playerId) {
        if (type == PlaneType.USER_PLANE) {
            return createUserPlane(playerId);
        } else {
            return createPlane(type);
        }
    }

    /**
     * Creates a UserPlane instance for a given player.
     *
     * @param playerId the ID of the player for whom the UserPlane is being created
     * @return a UserPlane instance configured with the specified player's properties
     */
    private UserPlane createUserPlane (int playerId) {
        // Set up UserPlane-specific properties
        PlaneConfig config = new PlaneConfig();
        config.imageName = getPlayerImageName(playerId);
        config.imageHeight = GameConstant.UserPlane.IMAGE_HEIGHT;
        config.initialXPos = GameConstant.UserPlane.INITIAL_X_POSITION;
        config.initialYPos = getUserInitialYPosition(playerId);
        config.health = GameConstant.UserPlane.INITIAL_HEALTH;
        config.fireIntervalNanoseconds = GameConstant.UserProjectile.FIRE_INTERVAL_NANOSECONDS;
        config.firingStrategy = new UserFiringStrategy(actorSpawner, GameConstant.UserProjectile.FIRE_INTERVAL_NANOSECONDS);
        return new UserPlane(config, playerId);
    }

    /**
     * Retrieves the image name associated with the given player ID.
     *
     * @param playerId the ID of the player whose image name is to be retrieved
     * @return the image name corresponding to the player ID, or null if the player ID is not recognized
     */
    private String getPlayerImageName(int playerId) {
        switch (playerId) {
            case 1:
                return GameConstant.UserPlane.ID1_IMAGE_NAME;
            case 2:
                return GameConstant.UserPlane.ID2_IMAGE_NAME;
            default:
                return null;
        }
    }

    /**
     * Returns the initial Y position for a user based on their player ID.
     *
     * @param playerId the ID of the player (1 or 2)
     * @return the initial Y position for the player:
     *         - 200 if the player ID is 1
     *         - 400 if the player ID is 2
     *         - 0 for any other player ID
     */
    public static double getUserInitialYPosition(int playerId) {
        if (playerId == 1) {
            return 200;
        } else if (playerId == 2) {
            return 400;
        }      
        return 0;
    }

    /**
     * Creates an instance of EnemyPlane with specific properties and strategies.
     * 
     * This method sets up the configuration for an EnemyPlane, including its image,
     * initial position, health, fire rate, and movement and firing strategies.
     * 
     * @return a new EnemyPlane instance with the configured properties.
     */
    private EnemyPlane createEnemyPlane() {
        // Set up EnemyPlane-specific properties
        PlaneConfig config = new PlaneConfig();
        config.imageName = GameConstant.EnemyPlane.IMAGE_NAME;
        config.imageHeight = GameConstant.EnemyPlane.IMAGE_HEIGHT;
        config.initialXPos = GameConstant.EnemyPlane.X_LOWER_BOUND;
        config.initialYPos = calculateInitialYPos(GameConstant.EnemyPlane.Y_UPPER_BOUND, GameConstant.EnemyPlane.Y_LOWER_BOUND);
        config.health = GameConstant.EnemyPlane.INITIAL_HEALTH;
        config.fireRate = GameConstant.EnemyProjectile.FIRE_RATE;
        config.fireIntervalNanoseconds = GameConstant.EnemyProjectile.FIRE_INTERVAL_NANOSECONDS;
        config.movementStrategy = new EnemyMovementStrategy();
        config.firingStrategy = new EnemyFiringStrategy(actorSpawner, config.fireRate);
        return new EnemyPlane(config);
    }

    /**
     * Creates an instance of EnemyPlane with specific properties for Enemy1.
     * 
     * This method sets up the configuration for an EnemyPlane using predefined
     * constants from GameConstant for EnemyPlane1. It initializes the plane's
     * image, position, health, fire rate, fire interval, movement strategy, and
     * firing strategy.
     * 
     * @return a new EnemyPlane instance configured for Enemy1.
     */
    private EnemyPlane createEnemy1Plane() {
        PlaneConfig config = new PlaneConfig();
        config.imageName = GameConstant.EnemyPlane1.IMAGE_NAME;
        config.imageHeight = GameConstant.EnemyPlane1.IMAGE_HEIGHT;
        config.initialXPos = GameConstant.EnemyPlane1.X_LOWER_BOUND;
        config.initialYPos = calculateInitialYPos(GameConstant.EnemyPlane1.Y_UPPER_BOUND, GameConstant.EnemyPlane1.Y_LOWER_BOUND);
        config.health = GameConstant.EnemyPlane1.INITIAL_HEALTH;
        config.fireRate = GameConstant.EnemyProjectile.FIRE_RATE;
        config.fireIntervalNanoseconds = GameConstant.EnemyProjectile.FIRE_INTERVAL_NANOSECONDS;
        config.movementStrategy = new EnemyMovementStrategy();
        config.firingStrategy = new EnemyFiringStrategy(actorSpawner, config.fireRate);

        return new EnemyPlane(config);
    }

    /**
     * Creates an instance of EnemyPlane with specific properties for Enemy2.
     * 
     * This method sets up the configuration for an EnemyPlane, including its image,
     * initial position, health, fire rate, and strategies for movement and firing.
     * 
     * @return a new EnemyPlane instance configured for Enemy2.
     */
    private EnemyPlane createEnemy2Plane() {
        PlaneConfig config = new PlaneConfig();
        config.imageName = GameConstant.EnemyPlane2.IMAGE_NAME;
        config.imageHeight = GameConstant.EnemyPlane2.IMAGE_HEIGHT;
        config.initialXPos = GameConstant.EnemyPlane2.X_LOWER_BOUND;
        config.initialYPos = calculateInitialYPos(GameConstant.EnemyPlane2.Y_UPPER_BOUND, GameConstant.EnemyPlane2.Y_LOWER_BOUND);
        config.health = GameConstant.EnemyPlane2.INITIAL_HEALTH;
        config.fireRate = GameConstant.EnemyProjectile.FIRE_RATE;
        config.fireIntervalNanoseconds = GameConstant.EnemyProjectile.FIRE_INTERVAL_NANOSECONDS;
        config.movementStrategy = new EnemyMovementStrategy();
        config.firingStrategy = new EnemyFiringStrategy(actorSpawner, config.fireRate);
        return new EnemyPlane(config);
    }

    /**
     * Creates an instance of EnemyPlane with specific properties for EnemyPlane3.
     * 
     * This method sets up the configuration for EnemyPlane3, including its image,
     * initial position, health, fire rate, fire interval, movement strategy, and
     * firing strategy.
     * 
     * @return a new EnemyPlane instance configured for EnemyPlane3.
     */
    private EnemyPlane createEnemy3Plane() {
        PlaneConfig config = new PlaneConfig();
        config.imageName = GameConstant.EnemyPlane3.IMAGE_NAME;
        config.imageHeight = GameConstant.EnemyPlane3.IMAGE_HEIGHT;
        config.initialXPos = GameConstant.EnemyPlane3.X_LOWER_BOUND;
        config.initialYPos = calculateInitialYPos(GameConstant.EnemyPlane3.Y_UPPER_BOUND, GameConstant.EnemyPlane3.Y_LOWER_BOUND);
        config.health = GameConstant.EnemyPlane3.INITIAL_HEALTH;
        config.fireRate = GameConstant.EnemyProjectile.FIRE_RATE;
        config.fireIntervalNanoseconds = GameConstant.EnemyProjectile.FIRE_INTERVAL_NANOSECONDS;
        config.movementStrategy = new EnemyMovementStrategy();
        config.firingStrategy = new EnemyFiringStrategy(actorSpawner, config.fireRate);
        return new EnemyPlane(config);
    }

    /**
     * Creates an instance of EnemyPlane with specific properties for EnemyPlane4.
     * 
     * This method sets up the configuration for EnemyPlane4, including its image,
     * initial position, health, fire rate, fire interval, movement strategy, and
     * firing strategy.
     * 
     * @return A new instance of EnemyPlane configured for EnemyPlane4.
     */
    private EnemyPlane createEnemy4Plane() {
        PlaneConfig config = new PlaneConfig();
        config.imageName = GameConstant.EnemyPlane4.IMAGE_NAME;
        config.imageHeight = GameConstant.EnemyPlane4.IMAGE_HEIGHT;
        config.initialXPos = GameConstant.EnemyPlane4.X_LOWER_BOUND;
        config.initialYPos = calculateInitialYPos(GameConstant.EnemyPlane4.Y_UPPER_BOUND, GameConstant.EnemyPlane4.Y_LOWER_BOUND);
        config.health = GameConstant.EnemyPlane4.INITIAL_HEALTH;
        config.fireRate = GameConstant.EnemyProjectile.FIRE_RATE;
        config.fireIntervalNanoseconds = GameConstant.EnemyProjectile.FIRE_INTERVAL_NANOSECONDS;
        config.movementStrategy = new EnemyMovementStrategy();
        config.firingStrategy = new EnemyFiringStrategy(actorSpawner, config.fireRate);
        return new EnemyPlane(config);
    }

    /**
     * Creates and configures a new instance of BossPlane with specific properties.
     * 
     * @return a new BossPlane instance with the configured properties.
     */
    private BossPlane createBossPlane() {
        PlaneConfig config = new PlaneConfig();
        config.imageName = GameConstant.BossPlane.IMAGE_NAME;
        config.imageHeight = GameConstant.BossPlane.IMAGE_HEIGHT;
        config.initialXPos = GameConstant.BossPlane.INITIAL_X_POSITION;
        config.initialYPos = GameConstant.BossPlane.INITIAL_Y_POSITION;
        config.health = GameConstant.BossPlane.INITIAL_HEALTH;
        config.fireRate = GameConstant.BossProjectile.FIRE_RATE;
        config.fireIntervalNanoseconds = GameConstant.BossPlane.FIRE_INTERVAL_NANOSECONDS;
        config.firingStrategy = new BossFiringStrategy(actorSpawner, config.fireRate);
        config.movementStrategy = new BossMovementStrategy();
        return new BossPlane(config, actorSpawner);
    }
    
    /**
     * Creates and configures a new instance of MultiPhaseBossPlane.
     * 
     * This method sets up the specific properties for the MultiPhaseBossPlane,
     * including its image, initial position, health, fire rate, firing strategy,
     * and movement strategy.
     * 
     * @return a new instance of MultiPhaseBossPlane configured with the specified properties.
     */
    private MultiPhaseBossPlane createMultiPhaseBossPlane() {
        PlaneConfig config = new PlaneConfig();
        config.imageName = GameConstant.MultiPhaseBossPlane.IMAGE_NAME;
        config.imageHeight = GameConstant.MultiPhaseBossPlane.IMAGE_HEIGHT;
        config.initialXPos = GameConstant.MultiPhaseBossPlane.X_POSITION;
        config.initialYPos = GameConstant.MultiPhaseBossPlane.Y_POSITION;
        config.health = GameConstant.MultiPhaseBossPlane.REMAINING_HEALTH_PHASE1;
        config.fireRate = GameConstant.MultiPhaseBossPlane.FIRE_RATE;
        config.fireIntervalNanoseconds = GameConstant.MultiPhaseBossPlane.FIRE_INTERVAL_NANOSECONDS;
        config.firingStrategy = new MultiPhaseBossFiringStrategy(actorSpawner, config.fireRate);
        config.movementStrategy = new MultiPhaseBossMovementStrategy();
        return new MultiPhaseBossPlane(config, actorSpawner);
    }

    /**
     * Calculates the initial Y position of a plane within the specified bounds.
     *
     * @param yUpperBound the upper bound of the Y position
     * @param yLowerBound the lower bound of the Y position
     * @return a random Y position between the upper and lower bounds
     */
    private double calculateInitialYPos(double yUpperBound, double yLowerBound) {
        return Math.random() * (yLowerBound - yUpperBound) + yUpperBound;
    }
}
