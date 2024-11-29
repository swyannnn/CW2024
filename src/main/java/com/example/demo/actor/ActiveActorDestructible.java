package com.example.demo.actor;

/**
 * Abstract class representing active actors that can be destroyed.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {
    private boolean isDestroyed;
    private int imageHeight;
    private double xUpperBound = Double.NEGATIVE_INFINITY;
    private double xLowerBound = Double.POSITIVE_INFINITY;
    private double yUpperBound = Double.NEGATIVE_INFINITY;
    private double yLowerBound = Double.POSITIVE_INFINITY;

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
        this.imageHeight = imageHeight;
    }

    /**
     * Sets the horizontal bounds for the actor.
     *
     * @param upperBound The upper X boundary.
     * @param lowerBound The lower X boundary.
     */
    public void setHorizontalBounds(double upperBound, double lowerBound) {
        this.xUpperBound = upperBound;
        this.xLowerBound = lowerBound;
    }

    /**
     * Sets the vertical bounds for the actor.
     *
     * @param upperBound The upper Y boundary.
     * @param lowerBound The lower Y boundary.
     */
    public void setVerticalBounds(double upperBound, double lowerBound) {
        this.yUpperBound = upperBound;
        this.yLowerBound = lowerBound;
    }

    /**
     * Checks if the actor is out of bounds based on the set boundaries.
     *
     * @return True if out of bounds, otherwise false.
     */
    public boolean isOutOfBounds() {
        double currentX = getLayoutX() + getTranslateX();
        double currentY = getLayoutY() + getTranslateY();
        
        // System.out.println("Current position: " + currentX + ", " + currentY + "from " + this.getClass().getSimpleName());
        boolean outOfHorizontal = currentX < xUpperBound || currentX > xLowerBound;
        boolean outOfVertical = currentY < yUpperBound || currentY > yLowerBound;

        return outOfHorizontal || outOfVertical;
    }

    public int getImageHeight() {
        return this.imageHeight;
    }

    /**
     * Updates the actor.
     *
     * @param now The current time.
     */
    public abstract void update(long now);

    /**
     * Takes damage.
     */
    public abstract void takeDamage();

    /**
     * Destroys the actor.
     */
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
