package com.example.demo.level;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.EnemyPlane;
import com.example.demo.GameControl;
import com.example.demo.manager.GameStateManager;
import com.example.demo.util.GameConstant;

public class Level001 extends LevelParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 2;
    private static final double ENEMY_SPAWN_PROBABILITY = 0.20;
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private int currentLevelNumber;

    public Level001(GameControl gameControl, int levelNumber) {
        super(gameControl, BACKGROUND_IMAGE_NAME, PLAYER_INITIAL_HEALTH);
        this.currentLevelNumber = levelNumber;
    }

    @Override
    protected void initializeFriendlyUnits() {
        getActorManager().addFriendlyUnit(getUser());
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (userHasReachedKillTarget()) {
            // onLevelComplete();
        }
    }

    public boolean userIsDestroyed() {
        return getUser().isDestroyed(); // Ensure that UserPlane has an isDestroyed() method returning a boolean
    }

    @Override
    public boolean userHasReachedKillTarget() {
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
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            // Spawn a new enemy with a probability defined by ENEMY_SPAWN_PROBABILITY
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActorDestructible newEnemy = new EnemyPlane(GameConstant.SCREEN_WIDTH, newEnemyInitialYPosition);
                getActorManager().addEnemyUnit(newEnemy); // Use ActorManager to add the enemy unit
            }
        }
    }


    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getActorManager(), PLAYER_INITIAL_HEALTH);
    }
}


