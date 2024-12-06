package com.example.demo.actor.plane;

import com.example.demo.util.GameConstant;
import com.example.demo.util.PlaneConfig;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.util.Duration;

import com.example.demo.controller.Controller;
import com.example.demo.listeners.HealthChangeListener;
import com.example.demo.manager.AudioManager;

public class UserPlane extends FighterPlane {
    private List<HealthChangeListener> healthChangeListeners = new ArrayList<>();
    private AudioManager audioManager;
    private int numberOfKills = GameConstant.UserPlane.NUMBER_OF_KILLS;
    private int score;
    private boolean isFlickering = false; 


    public UserPlane(Controller controller, PlaneConfig config,int playerId) {
        super(controller, config);
        this.health = config.health;
        this.audioManager = controller.getGameStateManager().getAudioManager();
        System.out.println("Initial layout position: (" + getLayoutX() + ", " + getLayoutY() + ")");
        System.out.println("Initial translate position: (" + getTranslateX() + ", " + getTranslateY() + ")");
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
            Platform.runLater(() -> flicker(3)); // Flicker three times
        }

        if (healthAtZero()) {
            System.out.println("UserPlane destroyed because health zero.");
            destroy();
            return true; // Destruction occurred
        }
        return true; // Damage applied
    }

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


    // Kill count methods
    public int getNumberOfKills() {
        return numberOfKills;
    }

    public void incrementKillCount() {
        numberOfKills++;
    }
}
