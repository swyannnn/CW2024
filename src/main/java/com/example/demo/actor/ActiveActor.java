
package com.example.demo.actor;

import com.example.demo.manager.ImageManager;
import javafx.scene.image.ImageView;

/**
 * The ActiveActor class represents an actor in a game or simulation that can move and be destroyed.
 * It extends the ImageView class to display an image and provides methods for movement and state management.
 * This class is abstract and requires subclasses to implement the update and takeDamage methods.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/actor/ActiveActor.java">Github Source Code</a>
 */
public abstract class ActiveActor extends ImageView{
    private boolean isDestroyed;
    private int imageHeight;
    
    /**
     * Constructs an ActiveActor with the specified image, position, and height.
     *
     * @param imageName    the name of the image file to be used for the actor
     * @param imageHeight  the height of the image
     * @param initialXPos  the initial X position of the actor
     * @param initialYPos  the initial Y position of the actor
     */
    public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        this.setImage(ImageManager.getImage(imageName));
        this.setLayoutX(initialXPos);
        this.setLayoutY(initialYPos);
        this.setFitHeight(imageHeight);
        this.setPreserveRatio(true);
        this.isDestroyed = false;
        this.imageHeight = imageHeight;
    }

    /**
     * Moves the actor by the specified delta values in the X and Y directions.
     *
     * @param deltaX the change in the X direction
     * @param deltaY the change in the Y direction
     */
    public void move(double deltaX, double deltaY) {
        this.setTranslateX(getTranslateX() + deltaX);
        this.setTranslateY(getTranslateY() + deltaY);
    }

    /**
     * Moves the actor horizontally by the specified amount.
     *
     * @param horizontalMove the distance to move the actor horizontally. 
     *                       Positive values move the actor to the right, 
     *                       and negative values move the actor to the left.
     */
    protected void moveHorizontally(double horizontalMove) {
        this.setTranslateX(getTranslateX() + horizontalMove);
    }

    /**
     * Moves the actor vertically by the specified amount.
     *
     * @param verticalMove the amount to move the actor vertically. Positive values move the actor downwards,
     *                     while negative values move the actor upwards.
     */
    protected void moveVertically(double verticalMove) {
        this.setTranslateY(getTranslateY() + verticalMove);
    }

    /**
     * Returns the height of the image associated with this actor.
     *
     * @return the height of the image in pixels
     */
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
     * Abstract method to handle the actor taking damage.
     * Implementations of this method should define the specific behavior
     * when the actor takes damage, such as reducing health points.
     *
     * @return true if the actor successfully takes damage, false otherwise.
     */
    public abstract boolean takeDamage();

    /**
     * Marks the actor as destroyed by setting the isDestroyed flag to true.
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
