package com.example.demo;

import javafx.animation.AnimationTimer;

/**
 * FighterPlane class representing a generic fighter plane in the game.
 * Both EnemyPlane and UserPlane extend this class.
 */
public abstract class FighterPlane extends ActiveActorDestructible {

    private int health;
    private AnimationTimer fireTimer;
    private long lastFireTime = 0;

    // Firing interval in nanoseconds
    private final long fireIntervalNanoseconds;

    // Firing rate probability
    protected final double fireRate;

    /**
     * Constructs a FighterPlane with specified parameters.
     *
     * @param imageName             The image file name for the plane.
     * @param imageHeight           The height of the plane's image.
     * @param initialXPos           The initial X position.
     * @param initialYPos           The initial Y position.
     * @param health                The initial health.
     * @param fireIntervalNanoseconds The interval between fires in nanoseconds.
     */
    public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health, long fireIntervalNanoseconds) {
        super(imageName, imageHeight, initialXPos, initialYPos);
        this.health = health;
        this.fireIntervalNanoseconds = fireIntervalNanoseconds;
        this.fireRate = 0.5; // Default firing rate, can be overridden by subclasses

        // Set the initial position
        setLayoutX(0.0); // Base position
        setTranslateX(initialXPos); // Dynamic movement
        setTranslateY(initialYPos);

        // Start firing
        startFiring();
    }

    /**
     * Abstract method to fire a projectile.
     * Subclasses implement this to define projectile behavior.
     */
    public abstract void fireProjectile();

    @Override
    public void takeDamage() {
        health--;
        System.out.println(getClass().getSimpleName() + " took damage. Health: " + health);
        if (healthAtZero()) {
            this.destroy();
            stopFiring();
            System.out.println(getClass().getSimpleName() + " destroyed.");
        }
    }

    protected double getProjectileXPosition(double xPositionOffset) {
        return getLayoutX() + getTranslateX() + xPositionOffset;
    }

    protected double getProjectileYPosition(double yPositionOffset) {
        return getLayoutY() + getTranslateY() + yPositionOffset;
    }

    private boolean healthAtZero() {
        return health <= 0;
    }

    public int getHealth() {
        return health;
    }

    /**
     * Retrieves the width of the plane's image.
     *
     * @return The width of the plane's image.
     */
    public double getWidth() {
        return getImage().getWidth();
    }

    /**
     * Starts firing projectiles at regular intervals.
     */
    public void startFiring() {
        fireTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastFireTime >= fireIntervalNanoseconds) {
                    fireProjectile();
                    lastFireTime = now;
                }
            }
        };
        fireTimer.start();
        System.out.println(getClass().getSimpleName() + " started firing.");
    }

    /**
     * Stops the firing timer.
     */
    public void stopFiring() {
        if (fireTimer != null) {
            fireTimer.stop();
            System.out.println(getClass().getSimpleName() + " stopped firing.");
        }
    }
}
