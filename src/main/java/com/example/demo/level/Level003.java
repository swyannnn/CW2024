package com.example.demo.level;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.plane.EnemyPlane1;
import com.example.demo.actor.plane.EnemyPlane2;
import com.example.demo.actor.plane.EnemyPlane3;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ActorManager;
import com.example.demo.util.GameConstant;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Level003: Timed Survival Challenge.
 */
public class Level003 extends LevelParent {
    private static final String BACKGROUND_IMAGE_NAME = GameConstant.Level003.BACKGROUND_IMAGE_NAME;
    private static final String BACKGROUND_MUSIC_NAME = GameConstant.Level003.BACKGROUND_MUSIC;
    private static final int PLAYER_INITIAL_HEALTH = GameConstant.Level003.PLAYER_INITIAL_HEALTH;
    private static final int SURVIVAL_TIME = GameConstant.Level003.SURVIVAL_TIME; // in seconds
    private static final int ENEMY_SPAWN_INTERVAL = GameConstant.Level003.ENEMY_SPAWN_INTERVAL; // in milliseconds

    private int currentLevelNumber;
    private ActorManager actorManager;
    private Timer enemySpawnTimer;
    private Timer levelTimer;
    private double startTime; // Start time in milliseconds
    ActiveActorDestructible newEnemy;
    private boolean levelCompleted;

    public Level003(Controller controller, int levelNumber) {
        super(controller, BACKGROUND_IMAGE_NAME, BACKGROUND_MUSIC_NAME, PLAYER_INITIAL_HEALTH);
        this.controller = controller;
        this.currentLevelNumber = levelNumber;
        this.actorManager = gameStateManager.getActorManager();
        this.levelCompleted = false;
        initializeFriendlyUnits();
        startLevelTimers();
    }

    /**
     * Starts the enemy spawn timer and level countdown timer.
     */
    private void startLevelTimers() {
        this.startTime = System.currentTimeMillis(); // Record the start time
        // Timer for spawning enemies
        enemySpawnTimer = new Timer();
        enemySpawnTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                spawnEnemyUnits();
            }
        }, 0, ENEMY_SPAWN_INTERVAL);

        // Timer for level duration
        levelTimer = new Timer();
        levelTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                levelCompleted = true;
                enemySpawnTimer.cancel(); // Stop spawning enemies
            }
        }, SURVIVAL_TIME * 1000);
    }

    @Override
    public boolean userHasReachedTarget() {
        if (levelCompleted) {
            cleanup();
            return true;
        }
        return false;
    }

    @Override
    public int getCurrentLevelNumber() {
        return currentLevelNumber;
    }

    @Override
    public void setCurrentLevelNumber(int levelNumber) {
        this.currentLevelNumber = levelNumber;
    }

    @Override
    public void spawnEnemyUnits() {
        double elapsedTime = (System.currentTimeMillis() - startTime) / 1000; // in seconds

        // normalize the elapsed time to a value between 1 and 3
        // input = (input - min) / (max - min) * (new_range_max - new_range_min) + new_range_min
        double spawnfactor = elapsedTime / SURVIVAL_TIME * (3 - 1) + 1;
        double randomValue = Math.random(); // Generates a number between 0.0 and 1.0
    
        if (randomValue < 0.003 * spawnfactor) {
            newEnemy = new EnemyPlane1(controller);
            actorManager.addActor(newEnemy);
        } else if (randomValue < 0.006 * spawnfactor) {
            newEnemy = new EnemyPlane2(controller);
        } else if (randomValue < 0.009 * spawnfactor) {
            newEnemy = new EnemyPlane3(controller);
            actorManager.addActor(newEnemy);
        }
    }    

    public void cleanup() {
        if (enemySpawnTimer != null) {
            enemySpawnTimer.cancel();
        }
        if (levelTimer != null) {
            levelTimer.cancel();
        }
    }
}
