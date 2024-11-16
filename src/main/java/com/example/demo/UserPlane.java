package com.example.demo;

import javafx.scene.shape.Rectangle;

public class UserPlane extends FighterPlane {
    private static final String IMAGE_NAME = "userplane.png";
    private static final int IMAGE_HEIGHT = 150;
    private static final int VERTICAL_VELOCITY = 8;
    private static final int HORIZONTAL_VELOCITY = 8;
    private static final int PROJECTILE_X_POSITION = 110;
    private static final int PROJECTILE_Y_POSITION_OFFSET = 20;

    private final double yUpperBound;
    private final double yLowerBound;
    private final double xUpperBound;
    private final double xLowerBound;
    private final double initialXPosition;
    private final double initialYPosition;

    private int verticalVelocityMultiplier = 0;
    private int horizontalVelocityMultiplier = 0;
    private int numberOfKills = 0;
	private int health;
    private int score;

    /**
     * Constructs a UserPlane object with specified stage dimensions and initial health.
     * @param stageHeight The height of the stage.
     * @param stageWidth The width of the stage.
     * @param initialHealth The initial health of the user plane.
     */
    public UserPlane(double stageHeight, double stageWidth, int initialHealth) {
        super(IMAGE_NAME, IMAGE_HEIGHT, stageWidth * 0.01, stageHeight / 2, initialHealth);
        // Set dynamic bounds based on stage dimensions
        this.yUpperBound = -40;
        this.yLowerBound = stageHeight - 100;
        this.xUpperBound = 5;
        this.xLowerBound = stageWidth - 100;
        this.initialXPosition = stageWidth * 0.01;
        this.initialYPosition = stageHeight / 2;
        this.health = initialHealth;
        this.score = 0;
    }

    /**
     * Updates the position of the user plane based on velocity multipliers.
     */
    @Override
    public void updatePosition() {
        // Handle vertical movement
        if (verticalVelocityMultiplier != 0) {
            double initialTranslateY = getTranslateY();
            moveVertically(VERTICAL_VELOCITY * verticalVelocityMultiplier);
            if (isOutOfVerticalBounds()) {
                setTranslateY(initialTranslateY); // Revert to the previous position if out of bounds
                System.out.println("UserPlane out of vertical bounds, reverting Y position.");
            }
        }

        // Handle horizontal movement
        if (horizontalVelocityMultiplier != 0) {
            double initialTranslateX = getTranslateX();
            moveHorizontally(HORIZONTAL_VELOCITY * horizontalVelocityMultiplier);
            if (isOutOfHorizontalBounds()) {
                setTranslateX(initialTranslateX); // Revert to the previous position if out of bounds
                System.out.println("UserPlane out of horizontal bounds, reverting X position.");

            }
        }

        Rectangle testRect = new Rectangle(100, 100, javafx.scene.paint.Color.RED);
        testRect.setX(getLayoutX() + getTranslateX());
        testRect.setY(getLayoutY() + getTranslateY());
}

    // Health management methods
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    // Score management methods
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // Method to reduce the user's health (for when the user takes damage)
    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0; // Ensure health doesn't go below zero
        }
    }

    // Method to check if the user is destroyed
    public boolean isDestroyed() {
        return health <= 0;
    }

    /**
     * Checks if the user plane is out of vertical bounds.
     * @return True if out of bounds, otherwise false.
     */
    private boolean isOutOfVerticalBounds() {
        double newPositionY = getLayoutY() + getTranslateY();
        return newPositionY < yUpperBound || newPositionY > yLowerBound;
    }

    /**
     * Checks if the user plane is out of horizontal bounds.
     * @return True if out of bounds, otherwise false.
     */
    private boolean isOutOfHorizontalBounds() {
        double newPositionX = getLayoutX() + getTranslateX();
        return newPositionX < xUpperBound || newPositionX > xLowerBound;
    }

    /**
     * Resets the user plane's position to the initial coordinates.
     */
    public void resetPosition() {
        setLayoutX(initialXPosition);
        setLayoutY(initialYPosition);
    }

    /**
     * Updates the actor's state, called in each frame of the game loop.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * Fires a projectile from the user plane.
     * @return A new UserProjectile object.
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        double currentX = getLayoutX() + getTranslateX() + PROJECTILE_X_POSITION;
        double currentY = getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
        return new UserProjectile(currentX, currentY);
    }

    // Movement methods
    public void moveUp() {
        verticalVelocityMultiplier = -1;
        System.out.println("UserPlane moving up. Current position: X=" + (getLayoutX() + getTranslateX()) + ", Y=" + (getLayoutY() + getTranslateY()));
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

    // Kill count methods
    public int getNumberOfKills() {
        return numberOfKills;
    }

    public void incrementKillCount() {
        numberOfKills++;
    }
}
