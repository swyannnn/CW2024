package com.example.demo.level;

import java.util.List;

import com.example.demo.actor.ActiveActor;
import com.example.demo.actor.plane.BossPlane;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ActorManager;
import com.example.demo.util.GameConstant;

public class Level002 extends LevelParent {
    private static final String backgroundImageName = GameConstant.Level002.BACKGROUND_IMAGE_NAME;
    private static final String backgroundMusicName = GameConstant.Level002.BACKGROUND_MUSIC;
    private static final int playerInitialHealth = GameConstant.Level002.PLAYER_INITIAL_HEALTH;
    private ActorManager actorManager;

    public Level002(Controller controller, int levelNumber) {
        super(controller, levelNumber, backgroundImageName, backgroundMusicName, playerInitialHealth);
        this.controller = controller;
        this.actorManager = controller.getGameStateManager().getActorManager();
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
            ActiveActor bossPlane = new BossPlane(controller);
            actorManager.addActor(bossPlane); // Use ActorManager to add the boss enemy
        }
    }
}
