package com.example.demo.actors.planes;

import java.util.*;

import com.example.demo.actors.projectile.BossProjectile;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ActorManager;

public class BossPlane extends FighterPlane {
	private final Controller controller;
	private ActorManager actorManager;
	private static final String IMAGE_NAME = "bossplane.png";
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	private static final double BOSS_FIRE_RATE = 0.04;
	private static final double BOSS_SHIELD_PROBABILITY = 0.002;
	private static final int IMAGE_HEIGHT = 300;
	private static final int VERTICAL_VELOCITY = 4;
	private static final int HEALTH = 100;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int MAX_FRAMES_WITH_SHIELD = 500;
	private static final int Y_POSITION_UPPER_BOUND = -100;
	private static final int Y_POSITION_LOWER_BOUND = 475;
	private static final double INITIAL_X_POSITION = 1000.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final int INITIAL_HEALTH = 10;
	private static final long FIRE_INTERVAL_NANOSECONDS = 1_000_000_000;

	// Dynamic bounds and position based on screen size
	private double initialXPosition;
	private double initialYPosition;
	private double yUpperBound;
	private double yLowerBound;
	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;

	public BossPlane(Controller controller) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, INITIAL_HEALTH, FIRE_INTERVAL_NANOSECONDS);
		this.controller = controller;
		this.actorManager = controller.getGameStateManager().getActorManager();
		// super(IMAGE_NAME, IMAGE_HEIGHT, stageWidth * 0.75, stageHeight * 0.5, HEALTH);

		// // Set initial positions and bounds dynamically based on stage dimensions
		// this.initialXPosition = stageWidth * 0.75; // 75% of the screen width
		// this.initialYPosition = stageHeight * 0.5; // Centered vertically
		// this.yUpperBound = -100;
		// this.yLowerBound = stageHeight - IMAGE_HEIGHT; // Adjust lower bound based on screen height

		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		initializeMovePattern();
	}

	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < yUpperBound || currentPosition > yLowerBound) {
			setTranslateY(initialTranslateY); // Revert to initial position if out of bounds
		}
	}

	// Reset position using initialXPosition and initialYPosition
	public void resetPosition() {
		setLayoutX(initialXPosition);
		setLayoutY(initialYPosition);
	}
	
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	@Override
	public void fireProjectile() {
		if (bossFiresInCurrentFrame()) {
			double projectileY = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			BossProjectile projectile = new BossProjectile(projectileY);
			ActorManager.getInstance(actorManager.getRoot()).addBossProjectile(projectile);
		}
	}

	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
	}

	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	private void updateShield() {
		if (isShielded) framesWithShieldActivated++;
		else if (shieldShouldBeActivated()) activateShield();
		if (shieldExhausted()) deactivateShield();
	}

	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	private void activateShield() {
		isShielded = true;
	}

	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
	}
}
