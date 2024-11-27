package com.example.demo.actor.projectile;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ActorManager;

/**
 * Abstract Projectile class representing a generic projectile in the game.
 */
public abstract class Projectile extends ActiveActorDestructible {
	private ActorManager actorManager;
    protected double horizontalVelocity;
    protected double screenWidth = 800.0; // Default screen width, adjust as needed

    /**
     * Constructor for Projectile.
     *
     * @param imageName         The image file name for the projectile.
     * @param imageHeight       The height of the projectile image.
     * @param initialXPos       The initial X position of the projectile.
     * @param initialYPos       The initial Y position of the projectile.
     * @param horizontalVelocity The horizontal velocity of the projectile.
     */
    public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos, double horizontalVelocity, Controller controller) {
        super(imageName, imageHeight, initialXPos, initialYPos);
        this.horizontalVelocity = horizontalVelocity;
		this.actorManager = controller.getGameStateManager().getActorManager();
    }

    @Override
    public void takeDamage() {
        this.destroy();
    }

    /**
     * Moves the projectile horizontally and checks for out-of-bounds conditions.
     */
    public void update(long now) {
        moveHorizontally(horizontalVelocity);
        if (isOutOfBounds()) {
            removeProjectile();
        }
    }

    /**
     * Checks if the projectile has moved out of the screen bounds.
     *
     * @return True if out of bounds, false otherwise.
     */
    private boolean isOutOfBounds() {
        double currentX = getLayoutX() + getTranslateX();
        double currentScreenWidth = screenWidth; // Default value

        if (getScene() != null) {
            currentScreenWidth = getScene().getWidth();
        }

        return currentX < 0 || currentX > currentScreenWidth;
    }

    /**
     * Removes the projectile from the scene graph and the ActorManager.
     */
    private void removeProjectile() {
		if (this instanceof EnemyProjectile) {
			actorManager.removeEnemyProjectile((EnemyProjectile) this);
		} else if (this instanceof UserProjectile) {
			actorManager.removeUserProjectile((UserProjectile) this);
		} else if (this instanceof BossProjectile) {
			actorManager.removeBossProjectile((BossProjectile) this);
		}
    }

    /**
     * Sets the screen width dynamically.
     *
     * @param screenWidth The width of the game screen.
     */
    public void setScreenWidth(double screenWidth) {
        this.screenWidth = screenWidth;
    }
}
