package com.example.demo.level;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.plane.EnemyPlane;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ActorManager;
import com.example.demo.util.GameConstant;

/**
 * Level001 defines the behavior and configuration for the first level of the
 * game.
 */
public class Level001 extends LevelParent {
    private static final String backgroundImageName = GameConstant.Level001.BACKGROUND_IMAGE_NAME;
    private static final String backgroundMusicName = GameConstant.Level001.BACKGROUND_MUSIC;
    private static final int totalEnemies = GameConstant.Level001.TOTAL_ENEMIES;
    private static final int killsToAdvance = GameConstant.Level001.KILLS_TO_ADVANCE;
    private static final double enemySpawnProbability = GameConstant.Level001.ENEMY_SPAWN_PROBABILITY;
    private static final int playerInitialHealth = GameConstant.Level001.PLAYER_INITIAL_HEALTH;

    private int currentLevelNumber;
    private ActorManager actorManager;

    /**
     * Constructor for Level001.
     *
     * @param controller       The game controller.
     * @param levelNumber      The level number for this level.
     */
    public Level001(Controller controller, int levelNumber) {
        super(controller, levelNumber, backgroundImageName, backgroundMusicName, playerInitialHealth);
        this.controller = controller;
        this.currentLevelNumber = levelNumber;
        this.actorManager = gameStateManager.getActorManager();
        initializeFriendlyUnits();
    }

    /**
     * Checks if all users have reached the kill target to advance.
     *
     * @return true if all players' kill counts are greater than or equal to the kill target; false otherwise.
     */
    public boolean userHasReachedTarget() {
        return  actorManager.getPlayers().stream()
                .allMatch(player -> player.getNumberOfKills() >= killsToAdvance);
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
        while (actorManager.getEnemyUnits().size() < totalEnemies) {
            if (Math.random() < enemySpawnProbability) {
                ActiveActorDestructible newEnemy = new EnemyPlane(controller);
                // System.out.println("Enemy spawned at X: " + newEnemy.getTranslateX() + ", Y: " + newEnemy.getTranslateY());
                actorManager.addActor(newEnemy);
            }
        }
    }
}
