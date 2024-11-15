package com.example.demo.level;

import com.example.demo.BossPlane;
import com.example.demo.GameControl;
import com.example.demo.util.ScreenConstants;

public class Level002 extends LevelParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private final BossPlane bossPlane;
    private int currentLevelNumber;

    public Level002(GameControl gameControl, int levelNumber) {
        super(gameControl, BACKGROUND_IMAGE_NAME, PLAYER_INITIAL_HEALTH);
        this.currentLevelNumber = levelNumber;
        bossPlane = new BossPlane(ScreenConstants.SCREEN_HEIGHT, ScreenConstants.SCREEN_WIDTH);
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (bossPlane.isDestroyed()) {
            // onLevelComplete();
        }
    }

    @Override
    public boolean userHasReachedKillTarget() {
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
    protected void spawnEnemyUnits() {
        if (getCurrentNumberOfEnemies() == 0) {
            addEnemyUnit(bossPlane);
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
    }
}
