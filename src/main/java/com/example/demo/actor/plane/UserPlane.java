package com.example.demo.actor.plane;

import com.example.demo.interfaces.HealthChangeListener;
import com.example.demo.util.GameConstant;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.util.Duration;

public class UserPlane extends FighterPlane {
    private List<HealthChangeListener> healthChangeListeners = new ArrayList<>();
    private int numberOfKills = GameConstant.UserPlane.NUMBER_OF_KILLS;
    private int flickerCount = GameConstant.UserPlane.DAMAGE_FLICKER_COUNT;;
    private boolean isFlickering = false; 


    /**
     * Constructs a UserPlane object with the specified configuration and player ID.
     *
     * @param config the configuration settings for the plane
     * @param playerId the ID of the player controlling the plane
     */
    public UserPlane(PlaneConfig config,int playerId) {
        super(config);
        this.health = config.health;
    }

    /**
     * Performs any additional updates specific to the UserPlane.
     * This method is called during the update cycle and can be overridden
     * to implement custom behavior.
     *
     * @param now The current time in nanoseconds.
     */
    @Override
    protected void performAdditionalUpdates(long now) {
        // Implement any additional updates specific to UserPlane if necessary
    }

    /**
     * Adds a HealthChangeListener to the list of listeners that will be notified
     * when the health of the UserPlane changes.
     *
     * @param listener the HealthChangeListener to be added
     */
    public void addHealthChangeListener(HealthChangeListener listener) {
        healthChangeListeners.add(listener);
    }

    /**
     * Removes a HealthChangeListener from the list of listeners.
     *
     * @param listener the HealthChangeListener to be removed
     */
    public void removeHealthChangeListener(HealthChangeListener listener) {
        healthChangeListeners.remove(listener);
    }

    /**
     * Notifies all registered health change listeners about the current health status.
     * This method iterates through the list of health change listeners and calls
     * their onHealthChange method, passing the current instance and its health value.
     */
    private void notifyHealthChange() {
        for (HealthChangeListener listener : healthChangeListeners) {
            listener.onHealthChange(this, this.health);
        }
    }

    /**
     * Applies damage to the user plane by decrementing its health.
     * Notifies any listeners of the health change and triggers a visual flicker effect if the plane is not destroyed.
     * If the health reaches zero, the plane is destroyed.
     *
     * @return true if damage was applied or the plane was destroyed, false otherwise.
     */
    @Override
    public boolean takeDamage() {
        health--;
        notifyHealthChange();
        System.out.println("notifyHealthChange() called in UserPlane.takeDamage().");

        if (!isDestroyed()) {
            Platform.runLater(() -> flicker()); 
        }

        if (healthAtZero()) {
            System.out.println("UserPlane destroyed because health zero.");
            destroy();
            return true; // Destruction occurred
        }
        return true; // Damage applied
    }

    /**
     * Causes the plane to flicker a specified number of times.
     * The flicker effect is achieved by alternating fade out and fade in transitions.
     * If the plane is already flickering, this method will return immediately.
     *
     * @param times the number of times the plane should flicker
     */
    private void flicker() {
        if (isFlickering) {
            return; // Already flickering, skip
        }

        isFlickering = true;

        SequentialTransition flickerTransition = new SequentialTransition();

        for (int i = 0; i < flickerCount; i++) {
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

    /**
     * Checks if the user plane is destroyed.
     *
     * @return true if the health of the user plane is less than or equal to 0, false otherwise.
     */
    public boolean isDestroyed() {
        return health <= 0;
    }

    /**
     * Retrieves the number of kills made by the user plane.
     *
     * @return the number of kills
     */
    public int getNumberOfKills() {
        return numberOfKills;
    }

    /**
     * Increments the kill count for the user plane by one.
     */
    public void incrementKillCount() {
        numberOfKills++;
    }
}
