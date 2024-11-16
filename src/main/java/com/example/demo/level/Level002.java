package com.example.demo.level;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.BossPlane;
import com.example.demo.EnemyPlane;
import com.example.demo.GameControl;
import com.example.demo.util.ScreenConstant;

public class Level002 extends LevelParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private final BossPlane bossPlane;
    private int currentLevelNumber;

    public Level002(GameControl gameControl, int levelNumber) {
        super(gameControl, BACKGROUND_IMAGE_NAME, PLAYER_INITIAL_HEALTH);
        this.currentLevelNumber = levelNumber;
        bossPlane = new BossPlane(ScreenConstant.SCREEN_HEIGHT, ScreenConstant.SCREEN_WIDTH);
    }

    @Override
    protected void initializeFriendlyUnits() {
        getActorManager().addFriendlyUnit(getUser()); 
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (bossPlane.isDestroyed()) {
            // onLevelComplete();
        }
    }

    public boolean userIsDestroyed() {
        return getUser().isDestroyed(); // Ensure that UserPlane has an isDestroyed() method returning a boolean
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
        // Check if there are no current enemies
        if (getActorManager().getEnemyUnits().size() == 0) {
            // Create and add the boss plane
            ActiveActorDestructible bossPlane = new EnemyPlane(ScreenConstant.SCREEN_WIDTH, ScreenConstant.SCREEN_HEIGHT / 2);
            getActorManager().addEnemyUnit(bossPlane); // Use ActorManager to add the boss enemy
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelViewLevelTwo(getActorManager(), PLAYER_INITIAL_HEALTH);
    }
}
