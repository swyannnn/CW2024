package com.example.demo.level;

import com.example.demo.BossPlane;
import com.example.demo.GameControl;
import com.example.demo.util.ScreenConstants;

public class Level002 extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final BossPlane bossplane;
	private LevelViewLevelTwo levelView;

	public Level002(GameControl gameControl, Integer levelNumber) {
		super(gameControl, BACKGROUND_IMAGE_NAME, PLAYER_INITIAL_HEALTH);
		bossplane = new BossPlane(ScreenConstants.SCREEN_HEIGHT, ScreenConstants.SCREEN_WIDTH);
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (bossplane.isDestroyed()) {
			winGame();
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(bossplane);
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelView;
	}

}
