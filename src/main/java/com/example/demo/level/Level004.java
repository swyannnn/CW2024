package com.example.demo.level;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.plane.MultiPhaseBossPlane;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ActorManager;
import com.example.demo.util.GameConstant;

/**
 * Level004: Multi-Phase Boss Battle.
 */
public class Level004 extends LevelParent {
    private static final String BACKGROUND_IMAGE_NAME = GameConstant.Level004.BACKGROUND_IMAGE_NAME;
    private static final String BACKGROUND_MUSIC_NAME = GameConstant.Level004.BACKGROUND_MUSIC;
    private static final int PLAYER_INITIAL_HEALTH = GameConstant.Level004.PLAYER_INITIAL_HEALTH;

    private int currentLevelNumber;
    private ActorManager actorManager;
    private boolean bossDefeated;

    public Level004(Controller controller, int levelNumber) {
        super(controller, BACKGROUND_IMAGE_NAME, BACKGROUND_MUSIC_NAME, PLAYER_INITIAL_HEALTH);
        this.controller = controller;
        this.currentLevelNumber = levelNumber;
        this.actorManager = gameStateManager.getActorManager();
        this.bossDefeated = false;
        initializeFriendlyUnits();
    }

    @Override
    public boolean userHasReachedTarget() {
        return bossDefeated;
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
        if (actorManager.getBossUnits().isEmpty()) {
            MultiPhaseBossPlane bossPlane = new MultiPhaseBossPlane(controller, this);
            actorManager.addActor(bossPlane);
        }
    }

    /**
     * Called by the boss plane when it is defeated.
     */
    public void onBossDefeated() {
        bossDefeated = true;
    }
}
