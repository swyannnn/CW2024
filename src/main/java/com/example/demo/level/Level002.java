package com.example.demo.level;

import java.util.List;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.plane.BossPlane;
import com.example.demo.actor.plane.UserPlane;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ActorManager;
import com.example.demo.ui.LevelView001;
import com.example.demo.ui.LevelView002;

public class Level002 extends LevelParent {
    private static final String BACKGROUND_IMAGE_NAME = "background2.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private int currentLevelNumber;
    private ActorManager actorManager;

    public Level002(Controller controller, int levelNumber) {
        super(controller, BACKGROUND_IMAGE_NAME, PLAYER_INITIAL_HEALTH);
        this.controller = controller;
        this.actorManager = gameStateManager.getActorManager();
        this.currentLevelNumber = levelNumber;
        initializeFriendlyUnits();
    }

    @Override
    protected void initializeFriendlyUnits() {
        // Re-add the UserPlane for Level 2
        List<UserPlane> players = actorManager.getPlayers();
        if (players.isEmpty()) {
            UserPlane user = new UserPlane(PLAYER_INITIAL_HEALTH, controller);
            user.addHealthChangeListener(levelView);
            actorManager.addPlayer(user);
            levelView.showHeartDisplay(user); // Ensure heart display is updated
        } else {
            // Update existing player's state if already present
            for (UserPlane player : players) {
                levelView.showHeartDisplay(player);
            }
        }
    }

    @Override
    public boolean userHasReachedKillTarget() {
        BossPlane bossPlane = actorManager.getBossPlane();
        System.out.println("bossPlane.isDestroyed()" + bossPlane.isDestroyed());
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
        // Check if there are no current enemies
        if (actorManager.getBossUnits().size() == 0) {
            // Create and add the boss plane
            ActiveActorDestructible bossPlane = new BossPlane(controller);
            actorManager.addBossUnit(bossPlane); // Use ActorManager to add the boss enemy
            System.out.println("bossPlane.isDestroyed() in the beginning" + bossPlane.isDestroyed());
        }
    }

    @Override
    public LevelView001 instantiateLevelView() {
        return new LevelView002(controller.getGameStateManager().getActorManager(), PLAYER_INITIAL_HEALTH);
    }
}
