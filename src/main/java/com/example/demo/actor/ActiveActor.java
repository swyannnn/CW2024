
package com.example.demo.actor;

import com.example.demo.manager.ImageManager;
import javafx.scene.image.ImageView;

public abstract class ActiveActor extends ImageView{
    private boolean isDestroyed;
    private int imageHeight;
    private double xUpperBound = Double.NEGATIVE_INFINITY;
    private double xLowerBound = Double.POSITIVE_INFINITY;
    private double yUpperBound = Double.NEGATIVE_INFINITY;
    private double yLowerBound = Double.POSITIVE_INFINITY;

    public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        this.setImage(ImageManager.getImage(imageName));
        this.setLayoutX(initialXPos);
        this.setLayoutY(initialYPos);
        this.setFitHeight(imageHeight);
        this.setPreserveRatio(true);
        this.isDestroyed = false;
        this.imageHeight = imageHeight;
    }

    protected void moveHorizontally(double horizontalMove) {
        this.setTranslateX(getTranslateX() + horizontalMove);
    }

    protected void moveVertically(double verticalMove) {
        this.setTranslateY(getTranslateY() + verticalMove);
    }

    public void setHorizontalBounds(double upperBound, double lowerBound) {
        this.xUpperBound = upperBound;
        this.xLowerBound = lowerBound;
    }

    public void setVerticalBounds(double upperBound, double lowerBound) {
        this.yUpperBound = upperBound;
        this.yLowerBound = lowerBound;
    }

    public boolean isOutOfBounds() {
        double currentX = getLayoutX() + getTranslateX();
        double currentY = getLayoutY() + getTranslateY();

        boolean outOfHorizontal = currentX < xUpperBound || currentX > xLowerBound;
        boolean outOfVertical = currentY < yUpperBound || currentY > yLowerBound;

        return outOfHorizontal || outOfVertical;
    }

    public int getImageHeight() {
        return this.imageHeight;
    }

    public int getImageWidth() {
        return (int) this.getBoundsInLocal().getWidth();
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
    public abstract boolean takeDamage();

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
