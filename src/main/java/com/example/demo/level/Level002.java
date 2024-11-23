package com.example.demo.level;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.planes.BossPlane;
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
        ActiveActorDestructible bossPlane = new BossPlane(controller);
        actorManager.addEnemyUnit(bossPlane);
    }

    @Override
    public void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (userHasReachedKillTarget()) {
            // onLevelComplete();
        }
    }

    @Override
    public boolean userHasReachedKillTarget() {
        BossPlane bossPlane = actorManager.getBossPlane();
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
        if (actorManager.getEnemyUnits().size() == 0) {
            // Create and add the boss plane
            ActiveActorDestructible bossPlane = new BossPlane(controller);
            actorManager.addEnemyUnit(bossPlane); // Use ActorManager to add the boss enemy
        }
    }

    @Override
    public LevelView001 instantiateLevelView() {
        return new LevelView002(controller.getGameStateManager().getActorManager(), PLAYER_INITIAL_HEALTH);
    }
}
