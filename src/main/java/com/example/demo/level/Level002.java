package com.example.demo.level;

import java.util.List;

import com.example.demo.actor.ActiveActor;
import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.PlaneFactory;
import com.example.demo.actor.PlaneFactory.PlaneType;
import com.example.demo.controller.Controller;
import com.example.demo.util.GameConstant;

public class Level002 extends LevelParent {
    private static final String backgroundImageName = GameConstant.Level002.BACKGROUND_IMAGE_NAME;
    private static final String backgroundMusicName = GameConstant.Level002.BACKGROUND_MUSIC;
    private static final int playerInitialHealth = GameConstant.Level002.PLAYER_INITIAL_HEALTH;
    private PlaneFactory planeFactory;
    private final ActorSpawner actorSpawn;

    public Level002(Controller controller, int levelNumber, ActorSpawner actorSpawner) {
        super(controller, levelNumber, backgroundImageName, backgroundMusicName, playerInitialHealth, actorSpawner);
        this.controller = controller;
        this.actorSpawn = actorSpawner;
        this.planeFactory = new PlaneFactory(controller, actorManager);
        initializeFriendlyUnits();
    }

    @Override
    public boolean userHasReachedTarget() {
        List<ActiveActor> bossPlanes = actorManager.getBossUnits();
        if (bossPlanes.isEmpty()) {
            return false; // No boss planes present
        }
        
        // Option 1: Check if **all** BossPlanes are destroyed
        boolean allDestroyed = true;
        for (ActiveActor actor : bossPlanes) {
            if (!actor.isDestroyed()) {
                allDestroyed = false;
                break;
            }
        }
        return allDestroyed;
    }

    @Override
    public void spawnEnemyUnits() {
        // Check if there are no current enemies
        if (actorManager.getBossUnits().size() == 0) {
            // Create and add the boss plane
            ActiveActor newEnemy = planeFactory.createPlane(PlaneType.BOSS_PLANE);
            actorSpawn.spawnActor(newEnemy);
        }
    }
}
