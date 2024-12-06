package com.example.demo.actor;

import com.example.demo.actor.plane.*;
import com.example.demo.strategy.BossFiringStrategy;
import com.example.demo.strategy.BossMovementStrategy;
import com.example.demo.strategy.EnemyFiringStrategy;
import com.example.demo.strategy.EnemyMovementStrategy;
import com.example.demo.strategy.MultiPhaseBossFiringStrategy;
import com.example.demo.strategy.MultiPhaseBossMovementStrategy;
import com.example.demo.util.GameConstant;
import com.example.demo.util.PlaneConfig;


public class PlaneFactory {
    private final ActorSpawner actorSpawner;

    public PlaneFactory(ActorSpawner actorSpawner) {
        this.actorSpawner = actorSpawner;
    }

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

    public FighterPlane createPlane(PlaneType type, int playerId) {
        if (type == PlaneType.USER_PLANE) {
            return createUserPlane(playerId);
            // return new UserPlane(GameConstant.UserPlane.INITIAL_HEALTH, controller, playerId);
        } else {
            return createPlane(type);
        }
    }

    private UserPlane createUserPlane (int playerId) {
        // Set up UserPlane-specific properties
        PlaneConfig config = new PlaneConfig();
        config.imageName = getImageName(playerId);
        config.imageHeight = GameConstant.UserPlane.IMAGE_HEIGHT;
        config.initialXPos = GameConstant.UserPlane.INITIAL_X_POSITION;
        config.initialYPos = getUserInitialYPosition(playerId);
        config.health = GameConstant.UserPlane.INITIAL_HEALTH;
        config.fireIntervalNanoseconds = GameConstant.UserProjectile.FIRE_INTERVAL_NANOSECONDS;
        return new UserPlane(config, playerId);
    }

    private String getImageName(int playerId) {
        switch (playerId) {
            case 1:
                return GameConstant.UserPlane.ID1_IMAGE_NAME;
            case 2:
                return GameConstant.UserPlane.ID2_IMAGE_NAME;
            default:
                return null;
        }
    }

    public static double getUserInitialYPosition(int playerId) {
        if (playerId == 1) {
            return 200;
        } else if (playerId == 2) {
            return 400;
        }      
        return 0;
    }

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

    private EnemyPlane createEnemy1Plane() {
        // Set up EnemyPlane-specific properties
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

    private EnemyPlane createEnemy2Plane() {
        // Set up EnemyPlane-specific properties
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

    private EnemyPlane createEnemy3Plane() {
        // Set up EnemyPlane-specific properties
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

    private EnemyPlane createEnemy4Plane() {
        // Set up EnemyPlane-specific properties
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

    private BossPlane createBossPlane() {
        // Set up BossPlane-specific properties
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
    
    private MultiPhaseBossPlane createMultiPhaseBossPlane() {
        // Set up MultiPhaseBossPlane-specific properties
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

    // Helper method to calculate initial Y position
    private double calculateInitialYPos(double yUpperBound, double yLowerBound) {
        return Math.random() * (yLowerBound - yUpperBound) + yUpperBound;
    }
}
