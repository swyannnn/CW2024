
package com.example.demo.actor;

import com.example.demo.manager.ImageManager;
import javafx.scene.image.ImageView;

public abstract class ActiveActor extends ImageView{
    private boolean isDestroyed;
    private int imageHeight;
    
    public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        this.setImage(ImageManager.getImage(imageName));
        this.setLayoutX(initialXPos);
        this.setLayoutY(initialYPos);
        this.setFitHeight(imageHeight);
        this.setPreserveRatio(true);
        this.isDestroyed = false;
        this.imageHeight = imageHeight;
    }

    public void move(double deltaX, double deltaY) {
        this.setTranslateX(getTranslateX() + deltaX);
        this.setTranslateY(getTranslateY() + deltaY);
    }

    protected void moveHorizontally(double horizontalMove) {
        this.setTranslateX(getTranslateX() + horizontalMove);
    }

    protected void moveVertically(double verticalMove) {
        this.setTranslateY(getTranslateY() + verticalMove);
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
