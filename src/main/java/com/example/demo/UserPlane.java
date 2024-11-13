package com.example.demo;

import javafx.stage.Stage;

public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private double yUpperBound;
	private double yLowerBound;
	private double xUpperBound;
	private double xLowerBound;
	private double initialXPosition;
	private double initialYPosition;
	private static final int IMAGE_HEIGHT = 150;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HORIZONTAL_VELOCITY = 8;
	private static final int PROJECTILE_X_POSITION = 110;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	private int verticalVelocityMultiplier;
	private int horizontalVelocityMultiplier;
	private int numberOfKills;

	public UserPlane(double stageHeight, double stageWidth, int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, stageWidth * 0.01, stageHeight / 2, initialHealth);
	
		// Set bounds dynamically based on stage dimensions
		this.yUpperBound = -40;
		this.yLowerBound = stageHeight - 100; // Adjust this value to fit your needs
		this.xUpperBound = 5;
		this.xLowerBound = stageWidth - 100; // Adjust this value to fit your needs
		this.initialXPosition = stageWidth * 0.01;
		this.initialYPosition = stageHeight / 2;
	
		verticalVelocityMultiplier = 0;
		horizontalVelocityMultiplier = 0;
	}
	

	@Override
	public void updatePosition() {
		// Vertical movement
		if (verticalVelocityMultiplier != 0) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * verticalVelocityMultiplier);
			double newPositionY = getLayoutY() + getTranslateY();
			if (newPositionY < yUpperBound || newPositionY > yLowerBound) {
				this.setTranslateY(initialTranslateY);
			}
		}

		// Horizontal movement
		if (horizontalVelocityMultiplier != 0) {
			double initialTranslateX = getTranslateX();
			this.moveHorizontally(HORIZONTAL_VELOCITY * horizontalVelocityMultiplier);
			double newPositionX = getLayoutX() + getTranslateX();
			if (newPositionX < xUpperBound || newPositionX > xLowerBound) {
				this.setTranslateX(initialTranslateX);
			}
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
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		double currentX = getLayoutX() + getTranslateX() + PROJECTILE_X_POSITION; // Adjust x-position
		double currentY = getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET; // Adjust y-position
		return new UserProjectile(currentX, currentY);
	}

	public void moveUp() {
		verticalVelocityMultiplier = -1;
	}

	public void moveDown() {
		verticalVelocityMultiplier = 1;
	}

	public void moveRight() {
		horizontalVelocityMultiplier = 1;
	}

	public void moveLeft() {
		horizontalVelocityMultiplier = -1;
	}

	public void stopVertical() {
		verticalVelocityMultiplier = 0;
	}

	public void stopHorizontal() {
		horizontalVelocityMultiplier = 0;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}
}
