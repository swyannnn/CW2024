package com.example.demo.level;

import java.util.Timer;
import java.util.TimerTask;

import com.example.demo.actor.ActiveActor;
import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.PlaneFactory;
import com.example.demo.actor.PlaneFactory.PlaneType;
import com.example.demo.manager.AudioManager;
import com.example.demo.manager.GameLoopManager;
import com.example.demo.util.GameConstant;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Level003: Timed Survival Challenge.
 */
public class Level003 extends LevelParent {
    private static final String backgroundImageName = GameConstant.Level003.BACKGROUND_IMAGE_NAME;
    private static final String backgroundMusicName = GameConstant.Level003.BACKGROUND_MUSIC;
    private static final int playerInitialHealth = GameConstant.Level003.PLAYER_INITIAL_HEALTH;
    private static final int survivalTime = GameConstant.Level003.SURVIVAL_TIME; // in seconds
    private static final int enemySpawnInterval = GameConstant.Level003.ENEMY_SPAWN_INTERVAL; // in milliseconds

    private int currentLevelNumber;
    private PlaneFactory planeFactory;
    private final ActorSpawner actorSpawn;
    private final GameLoopManager gameLoopManager;
    private Timer enemySpawnTimer;
    private Timer levelTimer;
    private Timer timeUpdateTimer;
    private Group root;
    private double startTime; 
    private boolean levelCompleted;
    private Label timeLabel;

    public Level003(int numberOfPlayers, ActorSpawner actorSpawner, AudioManager audioManager, GameLoopManager gameLoopManager) {
        super(3, numberOfPlayers, backgroundImageName, backgroundMusicName, playerInitialHealth, actorSpawner, audioManager);
        this.actorSpawn = actorSpawner;
        this.gameLoopManager = gameLoopManager;
        this.planeFactory = new PlaneFactory(actorSpawner);
        this.levelCompleted = false;
        this.root = super.getRoot();
        initializeFriendlyUnits();
        initializeTimeLabel();
        startLevelTimers();
    }

    /**
     * Initializes the time display label and adds it to the UI root.
     */
    private void initializeTimeLabel() {
        Platform.runLater(() -> {
            timeLabel = new Label();
            timeLabel.setTextFill(Color.WHITE);
            timeLabel.setFont(new Font("Arial", 24));
            timeLabel.setStyle("-fx-font-weight: bold;");
            // Position the label at the top-right corner with some padding
            timeLabel.setLayoutX(GameConstant.GameSettings.SCREEN_WIDTH - 180); // Adjust width as needed
            timeLabel.setLayoutY(8); // 10 pixels from the top
            timeLabel.setText("Time Left: " + survivalTime + "s");
            root.getChildren().add(timeLabel);
        });
    }

    /**
     * Starts the enemy spawn timer and level countdown timer.
     */
    private void startLevelTimers() {
        this.startTime = System.currentTimeMillis(); // Record the start time

        // Timer for spawning enemies
        enemySpawnTimer = new Timer(true); // Daemon thread
        enemySpawnTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!gameLoopManager.isPaused() && !levelCompleted) {
                    spawnEnemyUnits();
                }
            }
        }, 0, enemySpawnInterval);

        // Timer for level duration
        levelTimer = new Timer(true); // Daemon thread
        levelTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!gameLoopManager.isPaused()) {
                    levelCompleted = true;
                    enemySpawnTimer.cancel(); // Stop spawning enemies
                }
            }
        }, survivalTime * 1000);

        // Timer for updating the remaining time display every second
        timeUpdateTimer = new Timer(true); // Daemon thread
        timeUpdateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!gameLoopManager.isPaused()) {
                    updateRemainingTime();
                }
            }
        }, 0, 1000);
    }

    /**
     * Updates the remaining time label.
     */
    private void updateRemainingTime() {
        if (levelCompleted) {
            Platform.runLater(() -> {
                if (timeLabel != null) {
                    timeLabel.setText("Time Left: 0s");
                }
            });
            return;
        }

        double elapsedTime = (System.currentTimeMillis() - startTime) / 1000.0; // in seconds
        double remainingTime = survivalTime - elapsedTime;
        remainingTime = Math.max(remainingTime, 0); // Ensure it doesn't go below zero
    
        final double finalRemainingTime = remainingTime; 
    
        Platform.runLater(() -> {
            if (timeLabel != null) {
                // Optionally, format to display as integer seconds
                timeLabel.setText("Time Left: " + (int) finalRemainingTime + "s");
            }
        });
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
        double spawnFactor = elapsedTime / survivalTime * (3 - 1) + 1;
        double randomValue = Math.random(); // Generates a number between 0.0 and 1.0
    
        Platform.runLater(() -> {
            if (randomValue < 0.003 * spawnFactor) {
                ActiveActor newEnemy = planeFactory.createPlane(PlaneType.ENEMY_PLANE1);
                actorSpawn.spawnActor(newEnemy);
            } else if (randomValue < 0.006 * spawnFactor) {
                ActiveActor newEnemy = planeFactory.createPlane(PlaneType.ENEMY_PLANE2);
                actorSpawn.spawnActor(newEnemy);
            } else if (randomValue < 0.009 * spawnFactor) {
                ActiveActor newEnemy = planeFactory.createPlane(PlaneType.ENEMY_PLANE3);
                actorSpawn.spawnActor(newEnemy);
            }
        });
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
