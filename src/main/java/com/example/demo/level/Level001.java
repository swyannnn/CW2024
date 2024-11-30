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
    private static final String BACKGROUND_IMAGE_NAME = GameConstant.Level001.BACKGROUND_IMAGE_NAME;
    private static final String BACKGROUND_MUSIC_NAME = GameConstant.Level001.BACKGROUND_MUSIC;
    private static final int TOTAL_ENEMIES = GameConstant.Level001.TOTAL_ENEMIES;
    private static final int KILLS_TO_ADVANCE = GameConstant.Level001.KILLS_TO_ADVANCE;
    private static final double ENEMY_SPAWN_PROBABILITY = GameConstant.Level001.ENEMY_SPAWN_PROBABILITY;
    private static final int PLAYER_INITIAL_HEALTH = GameConstant.Level001.PLAYER_INITIAL_HEALTH;

    private int currentLevelNumber;
    private ActorManager actorManager;

    /**
     * Constructor for Level001.
     *
     * @param controller       The game controller.
     * @param levelNumber      The level number for this level.
     */
    public Level001(Controller controller, int levelNumber) {
        super(controller, BACKGROUND_IMAGE_NAME, BACKGROUND_MUSIC_NAME, PLAYER_INITIAL_HEALTH);
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
    public boolean userHasReachedKillTarget() {
        return  actorManager.getPlayers().stream()
                .allMatch(player -> player.getNumberOfKills() >= KILLS_TO_ADVANCE);
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
        while (actorManager.getEnemyUnits().size() < TOTAL_ENEMIES) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                ActiveActorDestructible newEnemy = new EnemyPlane(controller);
                // System.out.println("Enemy spawned at X: " + newEnemy.getTranslateX() + ", Y: " + newEnemy.getTranslateY());
                actorManager.addActor(newEnemy);
            }
        }
    }
}
