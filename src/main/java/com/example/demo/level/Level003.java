package com.example.demo.level;

import java.util.Timer;
import java.util.TimerTask;

import com.example.demo.actor.ActiveActor;
import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.plane.PlaneFactory;
import com.example.demo.actor.plane.PlaneType;
import com.example.demo.manager.AudioManager;
import com.example.demo.manager.GameLoopManager;
import com.example.demo.util.GameConstant;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;



/**
 * Level003 is a subclass of LevelParent that represents the third level of the game.
 * It initializes the level with specific settings and handles the spawning of enemy units,
 * updating the remaining time, and checking if the user has survived the required duration.
 *
 * <p>This class uses the following components:
 * <ul>
 *   <li>ActorSpawner: Responsible for spawning game actors.</li>
 *   <li>AudioManager: Responsible for handling game audio.</li>
 *   <li>PlaneFactory: Used to create different types of planes.</li>
 *   <li>GameLoopManager: Manages the game loop and checks if the game is paused.</li>
 * </ul>
 *
 * <p>Key functionalities include:
 * <ul>
 *   <li>Initializing friendly units.</li>
 *   <li>Displaying the remaining survival time.</li>
 *   <li>Spawning enemy units at fixed intervals with increasing difficulty.</li>
 *   <li>Updating the remaining time display every second.</li>
 *   <li>Checking if the user has survived the required duration to complete the level.</li>
 * </ul>
 *
 * @see LevelParent
 * @see ActorSpawner
 * @see AudioManager
 * @see PlaneFactory
 * @see GameLoopManager
 */
public class Level003 extends LevelParent {
    private static final int survivalTime = GameConstant.Level003.SURVIVAL_TIME; // in seconds
    private static final int enemySpawnInterval = GameConstant.Level003.ENEMY_SPAWN_INTERVAL; // in milliseconds

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

    /**
     * Constructs a new Level003 instance.
     *
     * @param numberOfPlayers the number of players in the game level
     * @param actorSpawner the actor spawner responsible for spawning game actors
     * @param audioManager the audio manager responsible for handling game audio
     * @param gameLoopManager the game loop manager responsible for managing the game loop
     */
    public Level003(int numberOfPlayers, ActorSpawner actorSpawner, AudioManager audioManager, GameLoopManager gameLoopManager) {
        super(3, numberOfPlayers, actorSpawner, audioManager);
        this.actorSpawn = actorSpawner;
        this.gameLoopManager = gameLoopManager;
        this.planeFactory = new PlaneFactory(actorSpawner);
        this.levelCompleted = false;
        this.root = super.getRoot();
        initializeTimeLabel();
        startLevelTimers();
    }

    /**
     * Initializes the time label and sets its properties.
     * This method runs on the JavaFX Application Thread using Platform.runLater.
     * The label is positioned at the top-right corner of the screen with some padding.
     * The text of the label displays the remaining survival time in seconds.
     * The label is styled with white text, Arial font of size 24, and bold font weight.
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
     * Starts the timers for the level, including:
     * - Recording the start time of the level.
     * - A timer for spawning enemy units at fixed intervals.
     * - A timer for the overall level duration, which marks the level as completed when time is up.
     * - A timer for updating the remaining time display every second.
     * 
     * The timers are daemon threads, meaning they will not prevent the JVM from exiting.
     * The timers will only execute their tasks if the game is not paused.
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

    /**
     * Checks if the user has reached the target for the current level.
     * If the level is completed, it performs cleanup operations and returns true.
     * Otherwise, it returns false.
     *
     * @return true if the level is completed and cleanup is performed, false otherwise.
     */
    @Override
    public boolean userHasReachedTarget() {
        if (levelCompleted) {
            cleanup();
            return true;
        }
        return false;
    }

    /**
     * Spawns enemy units based on the elapsed time and a random factor.
     * The spawn rate increases over time, normalized between 1 and 3.
     * 
     * The method calculates a spawn factor based on the elapsed time and survival time.
     * It then generates a random value to determine which type of enemy plane to spawn.
     * 
     * The enemy planes are spawned on the JavaFX application thread using Platform.runLater.
     * 
     * The probabilities for spawning each type of enemy plane are as follows:
     * - ENEMY_PLANE1: 0.003 * spawnFactor
     * - ENEMY_PLANE2: 0.006 * spawnFactor
     * - ENEMY_PLANE3: 0.009 * spawnFactor
     */
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

    /**
     * Cleans up resources by canceling the enemy spawn timer and level timer if they are not null.
     * This method ensures that any ongoing timers are properly terminated to prevent resource leaks.
     */
    public void cleanup() {
        if (enemySpawnTimer != null) {
            enemySpawnTimer.cancel();
        }
        if (levelTimer != null) {
            levelTimer.cancel();
        }
    }
}
