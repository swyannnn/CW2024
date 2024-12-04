package com.example.demo.actor.plane;

import com.example.demo.util.GameConstant;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.util.Duration;

import com.example.demo.actor.projectile.UserProjectile;
import com.example.demo.controller.Controller;
import com.example.demo.listener.HealthChangeListener;
import com.example.demo.manager.ActorManager;
import com.example.demo.manager.AudioManager;

public class UserPlane extends FighterPlane {
    private List<HealthChangeListener> healthChangeListeners = new ArrayList<>();
    private ActorManager actorManager;
    private AudioManager audioManager;

    private static final String ImageName1 = GameConstant.UserPlane.ID1_IMAGE_NAME;
    private static final String ImageName2 = GameConstant.UserPlane.ID2_IMAGE_NAME;
    private static final int imageHeight = GameConstant.UserPlane.IMAGE_HEIGHT; 
    private static double initialXPosition = GameConstant.UserPlane.INITIAL_X_POSITION;
    private static double initialYPosition = GameConstant.UserPlane.INITIAL_Y_POSITION;
    
    private static final double verticalVelocity = GameConstant.UserPlane.VERTICAL_VELOCITY;
    private static final double horizontalVelocity = GameConstant.UserPlane.HORIZONTAL_VELOCITY;
    private static final int projectileXPositionOffset = GameConstant.UserProjectile.PROJECTILE_X_POSITION_OFFSET;
    private static final int projectileYPositionOffset = GameConstant.UserProjectile.PROJECTILE_Y_POSITION_OFFSET;

    private final double yUpperBound = GameConstant.UserPlane.Y_UPPER_BOUND;
    private final double yLowerBound = GameConstant.UserPlane.Y_LOWER_BOUND;
    private final double xUpperBound = GameConstant.UserPlane.X_UPPER_BOUND;
    private final double xLowerBound = GameConstant.UserPlane.X_LOWER_BOUND;

    private int verticalVelocityMultiplier = GameConstant.UserPlane.VERTICAL_VELOCITY_MULTIPLIER;
    private int horizontalVelocityMultiplier = GameConstant.UserPlane.HORIZONTAL_VELOCITY_MULTIPLIER;
    private int numberOfKills = GameConstant.UserPlane.NUMBER_OF_KILLS;
    private int score;
    private boolean isFlickering = false; 
    private static int numberOfPlayers;
    
    /**
     * Constructs a UserPlane object with specified stage dimensions and initial health.
     * @param stageHeight The height of the stage.
     * @param stageWidth The width of the stage.
     * @param initialHealth The initial health of the user plane.
     */
    public UserPlane(int initialHealth, Controller controller, int playerId) {
        super(controller, getImageName(playerId), imageHeight, initialXPosition, getInitialYPosition(playerId) , initialHealth, GameConstant.UserProjectile.FIRE_INTERVAL_NANOSECONDS);
        this.health = initialHealth;
        this.actorManager = controller.getGameStateManager().getActorManager();
        this.audioManager = controller.getGameStateManager().getAudioManager();
        setHorizontalBounds(xUpperBound, xLowerBound);
        setVerticalBounds(yUpperBound, yLowerBound);
        System.out.println("Initial layout position: (" + getLayoutX() + ", " + getLayoutY() + ")");
        System.out.println("Initial translate position: (" + getTranslateX() + ", " + getTranslateY() + ")");
    }

    private static String getImageName (int playerId){
        switch(playerId){
            case 1:
                return ImageName1;
            case 2:
                return ImageName2;
            default:
                return null;
        }
    }
    
    public static double getInitialYPosition(int playerId) {
        if (numberOfPlayers == 1) {
            return initialYPosition;
        } else {
            if (playerId == 1) {
                return initialYPosition - 100;
            } else if (playerId == 2) {
                return initialYPosition + 100;
            }
            return initialYPosition;        
        }
    }

    /**
     * Fires a projectile from the user plane's current position.
     */
    public void fireProjectile() {
        double currentX =  getProjectileXPosition(projectileXPositionOffset);
        double currentY = getProjectileYPosition(projectileYPositionOffset);

        UserProjectile projectile = new UserProjectile(currentX, currentY, this);
        actorManager.addActor(projectile);
        audioManager.playSoundEffect(3);
    }

    @Override
    protected void performMovement(long now) {
        boolean moved = false; // Flag to track if movement occurred
        double initialTranslateX = getTranslateX();
        double initialTranslateY = getTranslateY();
    
        // Handle vertical movement
        if (verticalVelocityMultiplier != 0) {
            moveVertically(verticalVelocity * verticalVelocityMultiplier);
            moved = true;
        }
    
        // Handle horizontal movement
        if (horizontalVelocityMultiplier != 0) {
            moveHorizontally(horizontalVelocity * horizontalVelocityMultiplier);
            moved = true;
        }
    
        // If any movement occurred, check for out-of-bounds
        if (moved && isOutOfBounds()) {
            setTranslateX(initialTranslateX);
            setTranslateY(initialTranslateY);
        }
    }
    

    @Override
    protected void performAdditionalUpdates(long now) {
        // Implement any additional updates specific to UserPlane if necessary
    }

    public void addHealthChangeListener(HealthChangeListener listener) {
        healthChangeListeners.add(listener);
    }

    public void removeHealthChangeListener(HealthChangeListener listener) {
        healthChangeListeners.remove(listener);
    }

    private void notifyHealthChange() {
        for (HealthChangeListener listener : healthChangeListeners) {
            listener.onHealthChange(this, this.health);
        }
    }

    @Override
    public boolean takeDamage() {
        health--;
        notifyHealthChange();
        System.out.println("notifyHealthChange() called in UserPlane.takeDamage().");

        if (!isDestroyed()) {
            audioManager.playSoundEffect(0);
            Platform.runLater(() -> flicker(3)); // Flicker two times
        }

        if (healthAtZero()) {
            System.out.println("UserPlane destroyed because health zero.");
            destroy();
            return true; // Destruction occurred
        }
        return true; // Damage applied
    }

        /**
     * Makes the UserPlane flicker a specified number of times.
     *
     * @param times The number of flickers.
     */
    private void flicker(int times) {
        if (isFlickering) {
            return; // Already flickering, skip
        }

        isFlickering = true;

        SequentialTransition flickerTransition = new SequentialTransition();

        for (int i = 0; i < times; i++) {
            // Fade out
            FadeTransition fadeOut = new FadeTransition(Duration.millis(100), this);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            // Fade in
            FadeTransition fadeIn = new FadeTransition(Duration.millis(100), this);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            // Add both fade transitions to the sequential transition
            flickerTransition.getChildren().addAll(fadeOut, fadeIn);
        }

        // Reset the flicker flag once the animation completes
        flickerTransition.setOnFinished(event -> isFlickering = false);
        flickerTransition.play();
    }

    public void setHealth(int health) {
        // notifyHealthChange();
        this.health = health;
    }

    // Score management methods
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // Method to check if the user is destroyed
    public boolean isDestroyed() {
        return health <= 0;
    }

    /**
     * Resets the user plane's position to the initial coordinates.
     */
    public void resetPosition() {
        setLayoutX(initialXPosition);
        setLayoutY(initialYPosition);
    }

    public int getCurrentXPosition() {
        return (int) getLayoutX();
    }

    public int getCurrentYPosition() {
        return (int) getLayoutY();
    }

    // Movement methods
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

    // Kill count methods
    public int getNumberOfKills() {
        // System.out.println("UserPlane.getNumberOfKills():" + numberOfKills);
        return numberOfKills;
    }

    public void incrementKillCount() {
        System.out.println("UserPlane.incrementKillCount(): " + numberOfKills);
        numberOfKills++;
    }
}
