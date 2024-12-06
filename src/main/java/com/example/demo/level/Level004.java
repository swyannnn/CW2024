package com.example.demo.level;

import com.example.demo.actor.ActiveActor;
import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.PlaneFactory;
import com.example.demo.actor.PlaneFactory.PlaneType;
import com.example.demo.manager.AudioManager;
import com.example.demo.util.GameConstant;

/**
 * Level004: Multi-Phase Boss Battle.
 */
public class Level004 extends LevelParent {
    private static final String backgroundImageName = GameConstant.Level004.BACKGROUND_IMAGE_NAME;
    private static final String backgroundMusicName = GameConstant.Level004.BACKGROUND_MUSIC;
    private static final int playerInitialHealth = GameConstant.Level004.PLAYER_INITIAL_HEALTH;

    private int currentLevelNumber;
    private PlaneFactory planeFactory;
    private final ActorSpawner actorSpawn;
    private ActiveActor bossPlane;

    public Level004(int numberOfPlayers, ActorSpawner actorSpawner, AudioManager audioManager) {
        super(4, numberOfPlayers, backgroundImageName, backgroundMusicName, playerInitialHealth, actorSpawner, audioManager);
        this.actorSpawn = actorSpawner;
        this.planeFactory = new PlaneFactory(actorSpawner);
        initializeFriendlyUnits();
    }

    @Override
    public boolean userHasReachedTarget() {
        // return bossDefeated;
        return bossPlane.isDestroyed();
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
        if (actorSpawn.getEnemyUnits().isEmpty()) {
            ActiveActor bossPlane = planeFactory.createPlane(PlaneType.MULTI_PHASE_BOSS_PLANE);
            actorSpawn.spawnActor(bossPlane);
            this.bossPlane = bossPlane;
        }
    }
}
