package com.example.demo.level;

import java.util.List;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.plane.BossPlane;
import com.example.demo.actor.plane.UserPlane;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ActorManager;
import com.example.demo.ui.LevelView001;
import com.example.demo.ui.LevelView002;
import com.example.demo.util.GameConstant;

public class Level002 extends LevelParent {
    private static final String BACKGROUND_IMAGE_NAME = GameConstant.Level002.BACKGROUND_IMAGE_NAME;
    private static final int PLAYER_INITIAL_HEALTH = GameConstant.Level002.PLAYER_INITIAL_HEALTH;
    private int currentLevelNumber;
    private ActorManager actorManager;

    public Level002(Controller controller, int levelNumber) {
        super(controller, BACKGROUND_IMAGE_NAME, PLAYER_INITIAL_HEALTH);
        this.controller = controller;
        this.actorManager = gameStateManager.getActorManager();
        this.currentLevelNumber = levelNumber;
        initializeFriendlyUnits();
        System.out.println("Controller is not null in Level002");
    }

    @Override
    protected void initializeFriendlyUnits() {
        // Re-add the UserPlane for Level 2
        List<UserPlane> players = actorManager.getPlayers();
        if (players.isEmpty()) {
            UserPlane user = new UserPlane(PLAYER_INITIAL_HEALTH, controller);
            user.addHealthChangeListener(levelView);
            actorManager.addActor(user);
            levelView.showHeartDisplay(user); // Ensure heart display is updated
        } else {
            // Update existing player's state if already present
            for (UserPlane player : players) {
                levelView.showHeartDisplay(player);
            }
        }
    }

    // @Override
    // public boolean userHasReachedKillTarget() {
    //     BossPlane bossPlane = actorManager.getBossUnits();
    //     if (bossPlane == null) {
    //         return false;
    //     }
    //     return bossPlane.isDestroyed();
    // }

    @Override
    public boolean userHasReachedKillTarget() {
        List<ActiveActorDestructible> bossPlanes = actorManager.getBossUnits();
        if (bossPlanes.isEmpty()) {
            return false; // No boss planes present
        }
        
        // Option 1: Check if **all** BossPlanes are destroyed
        boolean allDestroyed = true;
        for (ActiveActorDestructible actor : bossPlanes) {
            if (!actor.isDestroyed()) {
                allDestroyed = false;
                break;
            }
        }
        return allDestroyed;
        
        // Option 2: Check if **any** BossPlane is destroyed
        // boolean anyDestroyed = false;
        // for (ActiveActorDestructible actor : bossPlanes) {
        //     if (actor.isDestroyed()) {
        //         anyDestroyed = true;
        //         break;
        //     }
        // }
        // return anyDestroyed;
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
        // Check if there are no current enemies
        if (actorManager.getBossUnits().size() == 0) {
            // Create and add the boss plane
            ActiveActorDestructible bossPlane = new BossPlane(controller);
            actorManager.addActor(bossPlane); // Use ActorManager to add the boss enemy
        }
    }

    @Override
    public LevelView001 instantiateLevelView() {
        return new LevelView002(controller.getGameStateManager().getActorManager(), PLAYER_INITIAL_HEALTH);
    }
}
