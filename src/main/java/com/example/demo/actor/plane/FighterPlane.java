package com.example.demo.actor.plane;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.controller.Controller;
import com.example.demo.manager.AudioManager;
import com.example.demo.manager.ImageManager;

/**
 * FighterPlane class representing a generic fighter plane in the game.
 * Both EnemyPlane and UserPlane extend this class.
 */
public abstract class FighterPlane extends ActiveActorDestructible {
    private AudioManager audioManager;
    private ImageManager imageManager;
    protected int health;
    private long lastFireTime = 0;
    private final long fireIntervalNanoseconds;
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
    public FighterPlane(Controller controller, String imageName, int imageHeight, double initialXPos, double initialYPos, int health, long fireIntervalNanoseconds) {
        super(imageName, imageHeight, initialXPos, initialYPos);
        this.health = health;
        this.audioManager = controller.getGameStateManager().getAudioManager();
        this.imageManager = controller.getGameStateManager().getImageManager();
        this.fireIntervalNanoseconds = fireIntervalNanoseconds;
        this.fireRate = 0.5; // Default firing rate, can be overridden by subclasses

        setTranslateX(initialXPos);
        setTranslateY(initialYPos);

        // translate properties are set to zero
        setTranslateX(0);
        setTranslateY(0);
    }

    /**
     * Abstract method to fire a projectile.
     * Subclasses implement this to define projectile behavior.
     */
    public abstract void fireProjectile();

    @Override
    public boolean takeDamage() {
        health--;
        System.out.println(getClass().getSimpleName() + " took damage. Health: " + health);
        System.out.println(getClass().getSimpleName() + " took damage. Health: " + getHealth());
        if (healthAtZero()) {
            audioManager.playSoundEffect(1);
            this.destroy();
            System.out.println(getClass().getSimpleName() + " destroyed.");
            return true; // Indicates that destruction occurred
        }
        return false; // Damage was applied, but not necessarily destruction
    }

    protected double getProjectileXPosition(double xPositionOffset) {
        System.out.println("getLayoutX() + getTranslateX() + xPositionOffset: " + (getLayoutX() + getTranslateX() + xPositionOffset));
        return getLayoutX() + getTranslateX() + xPositionOffset;
    }

    protected double getProjectileYPosition(double yPositionOffset) {
        return getLayoutY() + getTranslateY() + yPositionOffset;
    }

    public boolean healthAtZero() {
        return health <= 0;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    protected void setImage(String imageName) {
        imageManager.getImage(imageName);
    }
    

    public boolean CanFire(FighterPlane plane) {
        return (plane instanceof UserPlane) || (plane instanceof EnemyPlane)|| (plane instanceof BossPlane);
    }

    /**
     * Template method to update the FighterPlane's state.
     * Handles firing logic and delegates movement to subclasses.
     *
     * @param now The current timestamp in nanoseconds.
     */
    public final void update(long now) {
        if (CanFire(this)) {
            handleFiring(now);
        }
        performMovement(now);
        performAdditionalUpdates(now); // Hook for subclasses to add extra behavior
    }

    /**
     * Handles firing logic based on the elapsed time.
     *
     * @param now The current timestamp in nanoseconds.
     */
    private void handleFiring(long now) {
        if (now - lastFireTime >= fireIntervalNanoseconds) {
            fireProjectile();
            lastFireTime = now;
        }
    }

    /**
     * Abstract method for performing movement.
     * Subclasses implement their specific movement logic.
     *
     * @param now The current timestamp in nanoseconds.
     */
    protected abstract void performMovement(long now);

    /**
     * Hook method for subclasses to perform additional updates.
     * Subclasses can override this method to add behavior without altering the template.
     *
     * @param now The current timestamp in nanoseconds.
     */
    protected void performAdditionalUpdates(long now) {
        // Default implementation does nothing.
    }
}
