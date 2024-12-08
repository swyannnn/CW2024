// PlaneFactory.java
package com.example.demo.actor.plane;

import com.example.demo.actor.ActorSpawner;
import com.example.demo.strategy.firing.*;
import com.example.demo.strategy.movement.*;
import com.example.demo.util.GameConstant;

import java.util.function.BiFunction;

/**
 * The PlaneFactory class is responsible for creating various types of planes in the game.
 * It uses the ActorSpawner to spawn different plane instances based on the specified PlaneType.
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
     * Creates a FighterPlane based on the specified PlaneType.
     *
     * @param type the type of plane to create
     * @return a FighterPlane instance corresponding to the specified type
     * @throws IllegalArgumentException if the specified plane type is unknown
     */
    public FighterPlane createPlane(PlaneType type) {
        PlaneConfig config = new PlaneConfig();
        config.imageName = type.getImageName();
        config.imageHeight = type.getImageHeight();
        config.initialXPos = type.getInitialXPos();

        // Calculate Y position if applicable
        if (type.getYLowerBound() != type.getYUpperBound()) {
            config.initialYPos = calculateInitialYPos(type.getYUpperBound(), type.getYLowerBound());
        } else {
            config.initialYPos = type.getYUpperBound(); // Fixed Y position
        }

        config.health = type.getHealth();
        config.fireRate = type.getFireRate();
        config.fireIntervalNanoseconds = type.getFireIntervalNanoseconds();
        config.speed = type.getSpeed();

        // Create movement and firing strategies using lambda expressions from PlaneType
        BiFunction<PlaneFactory, PlaneConfig, MovementStrategy> movementCreator = type.getMovementStrategyCreator();
        BiFunction<PlaneFactory, PlaneConfig, FiringStrategy> firingCreator = type.getFiringStrategyCreator();

        config.movementStrategy = movementCreator.apply(this, config);
        config.firingStrategy = firingCreator.apply(this, config);

        // Instantiate the appropriate FighterPlane subclass based on PlaneType
        switch (type) {
            case ENEMY_PLANE:
            case ENEMY_PLANE1:
            case ENEMY_PLANE2:
            case ENEMY_PLANE3:
            case ENEMY_PLANE4:
                return new EnemyPlane(config);
            case BOSS_PLANE:
                return new BossPlane(config, actorSpawner);
            case MULTI_PHASE_BOSS_PLANE:
                return new MultiPhaseBossPlane(config, actorSpawner);
            case USER_PLANE:
                throw new UnsupportedOperationException("Use createUserPlane method for USER_PLANE type.");
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
    private UserPlane createUserPlane(int playerId) {
        PlaneConfig config = new PlaneConfig();
        config.imageName = getPlayerImageName(playerId);
        config.imageHeight = GameConstant.UserPlane.IMAGE_HEIGHT;
        config.initialXPos = GameConstant.UserPlane.INITIAL_X_POSITION;
        config.initialYPos = getUserInitialYPosition(playerId);
        config.health = GameConstant.UserPlane.INITIAL_HEALTH;
        config.fireIntervalNanoseconds = GameConstant.UserProjectile.FIRE_INTERVAL_NANOSECONDS;
        config.speed = GameConstant.UserPlane.VELOCITY; // Set speed from constants

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
     * Retrieves the initial Y position for a user plane based on the player ID.
     *
     * @param playerId the ID of the player (1 or 2)
     * @return the initial Y position for the specified player ID
     *         - 0 if the playerId is not 1 or 2
     */
    public static double getUserInitialYPosition(int playerId) {
        if (playerId == 1) {
            return GameConstant.UserPlane.ID1_INITIAL_Y_POSITION;
        } else if (playerId == 2) {
            return GameConstant.UserPlane.ID2_INITIAL_Y_POSITION;
        }
        return 0;
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

    /**
     * Retrieves the ActorSpawner instance.
     *
     * @return the ActorSpawner instance.
     */
    public ActorSpawner getActorSpawner() {
        return actorSpawner;
    }
}
