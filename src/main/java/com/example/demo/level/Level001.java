package com.example.demo.level;
import com.example.demo.ActiveActorDestructible;
import com.example.demo.EnemyPlane;
import com.example.demo.GameControl;
import com.example.demo.util.ScreenConstants;

public class Level001 extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 2;
    private static final double ENEMY_SPAWN_PROBABILITY = 0.20;
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private final double screenHeight;
    private final double screenWidth;
    private final Integer currentLevelNumber;


    public Level001(GameControl gameControl, Integer levelNumber) {
        super(gameControl, BACKGROUND_IMAGE_NAME, PLAYER_INITIAL_HEALTH);
        this.screenHeight = ScreenConstants.SCREEN_HEIGHT;
        this.screenWidth = ScreenConstants.SCREEN_WIDTH;
        this.currentLevelNumber = levelNumber;
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }
	

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (userHasReachedKillTarget()) {
            goToNextLevel(currentLevelNumber + 1);
        }
    }

    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
                addEnemyUnit(newEnemy);
            }
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }
}
