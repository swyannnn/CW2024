package com.example.demo.level;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.BossPlane;
import com.example.demo.controller.Controller;
import com.example.demo.util.GameConstant;

public class Level002 extends LevelParent {
    private static final String BACKGROUND_IMAGE_NAME = "background2.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private int currentLevelNumber;

    public Level002(Controller controller, int levelNumber) {
        super(controller, BACKGROUND_IMAGE_NAME, PLAYER_INITIAL_HEALTH);
        this.controller = controller;
        this.currentLevelNumber = levelNumber;
        initializeFriendlyUnits();
    }

    @Override
    protected void initializeFriendlyUnits() {
        ActiveActorDestructible bossPlane = new BossPlane(controller);
        controller.getGameStateManager().getActorManager().addEnemyUnit(bossPlane);
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
        BossPlane bossPlane = controller.getGameStateManager().getActorManager().getBossPlane();
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
        if (controller.getGameStateManager().getActorManager().getEnemyUnits().size() == 0) {
            // Create and add the boss plane
            ActiveActorDestructible bossPlane = new BossPlane(controller);
            controller.getGameStateManager().getActorManager().addEnemyUnit(bossPlane); // Use ActorManager to add the boss enemy
        }
    }

    @Override
    public LevelView instantiateLevelView() {
        return new LevelViewLevelTwo(controller.getGameStateManager().getActorManager(), PLAYER_INITIAL_HEALTH);
    }
}
