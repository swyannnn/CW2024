package com.example.demo.actor;

/**
 * Abstract class representing active actors that can be destroyed.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {
    private boolean isDestroyed;

    /**
     * Constructor for ActiveActorDestructible.
     *
     * @param imageName     The name of the image representing the actor.
     * @param imageHeight   The height of the image.
     * @param initialXPos   The initial X position.
     * @param initialYPos   The initial Y position.
     */
    public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        super(imageName, imageHeight, initialXPos, initialYPos);
        isDestroyed = false;
    }

    // @Override
    public abstract void update(long now);

    // @Override
    public abstract void takeDamage();

    // @Override
    public void destroy() {
        this.isDestroyed = true;
    }

    /**
     * Checks if the actor is destroyed.
     *
     * @return True if destroyed, else false.
     */
    public boolean isDestroyed() {
        return isDestroyed;
    }
}
