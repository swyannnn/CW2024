package com.example.demo;

import java.util.*;

public class BossPlane extends FighterPlane {

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

	public BossPlane(double stageHeight, double stageWidth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, stageWidth * 0.75, stageHeight * 0.5, HEALTH);

		// Set initial positions and bounds dynamically based on stage dimensions
		this.initialXPosition = stageWidth * 0.75; // 75% of the screen width
		this.initialYPosition = stageHeight * 0.5; // Centered vertically
		this.yUpperBound = -100;
		this.yLowerBound = stageHeight - IMAGE_HEIGHT; // Adjust lower bound based on screen height

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
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
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

	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
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
