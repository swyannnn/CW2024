package com.example.demo.level;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.EnemyPlane;
import com.example.demo.controller.Controller;
import com.example.demo.util.GameConstant;

/**
 * Level001 defines the behavior and configuration for the first level of the game.
 */
public class Level001 extends LevelParent {
    private static final String BACKGROUND_IMAGE_NAME = "background1.jpg";
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 2;
    private static final double ENEMY_SPAWN_PROBABILITY = 0.20;
    private static final int PLAYER_INITIAL_HEALTH = 5;
    
    private int currentLevelNumber;

    /**
     * Constructor for Level001.
     *
     * @param controller     The game controller.
     * @param gameStateManager The GameStateManager instance.
     * @param levelNumber    The level number for this level.
     */
    public Level001(Controller controller, int levelNumber) {
        super(controller, BACKGROUND_IMAGE_NAME, PLAYER_INITIAL_HEALTH);
        this.currentLevelNumber = levelNumber;
    }

    @Override
    protected void initializeFriendlyUnits() {
        // Add the user's plane to the list of friendly units using ActorManager
        getActorManager().addFriendlyUnit(getUser());
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (userHasReachedKillTarget()) {
            // Transition to the next level or handle level completion
            goToNextLevel(currentLevelNumber + 1); // Example transition logic
        }
    }

    @Override
    public boolean userHasReachedKillTarget() {
        // Check if the user's kill count has reached the target to advance
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
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
    protected void spawnEnemyUnits() {
        // Get the current number of enemies from ActorManager
        int currentNumberOfEnemies = getActorManager().getEnemyUnits().size();

        // Loop to spawn new enemies until the total number of enemies reaches TOTAL_ENEMIES
        while (currentNumberOfEnemies < TOTAL_ENEMIES) {
            // Spawn a new enemy with a probability defined by ENEMY_SPAWN_PROBABILITY
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActorDestructible newEnemy = new EnemyPlane(GameConstant.SCREEN_WIDTH, newEnemyInitialYPosition);
                getActorManager().addEnemyUnit(newEnemy); // Use ActorManager to add the enemy unit
                currentNumberOfEnemies++;
            } else {
                break; // Exit the loop if the probability condition is not met
            }
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        // Instantiate LevelView with the ActorManager and initial player health
        return new LevelView(getActorManager(), PLAYER_INITIAL_HEALTH);
    }
}
