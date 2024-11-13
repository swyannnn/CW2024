package com.example.demo.controller;

import java.lang.reflect.Constructor;
import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.example.demo.LevelParent;
import com.example.demo.AudioManager;
import com.example.demo.HomeMenu;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Controller {
    private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
    private final Stage stage;
    private final AudioManager audioManager;
    private HomeMenu homeMenu;
    private final PropertyChangeSupport support;

    public Controller(Stage stage) {
        this.stage = stage;
        this.audioManager = new AudioManager(); // Initialize AudioManager
        this.support = new PropertyChangeSupport(this); // Initialize PropertyChangeSupport
    }

    // Method to add a listener
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    // Method to remove a listener
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Launches the game by displaying the HomeMenu.
     */
    public void launchGame() {
        stage.show();
        homeMenu = new HomeMenu(stage, this); // Pass the stage and controller to HomeMenu
        stage.setScene(homeMenu.getHomeMenuScene()); // Set the home menu scene
    }

    /**
     * Starts the game and transitions from the home menu to Level One.
     */
    public void startGame() {
        // Create a fade-out transition
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), stage.getScene().getRoot());
        fadeTransition.setFromValue(1.0); // Start fully opaque
        fadeTransition.setToValue(0.0);   // Fade to fully transparent
        fadeTransition.setOnFinished(event -> {
            // Stop menu music and go to Level One after the fade-out effect
            audioManager.stopMusic();
            try {
                goToLevel(LEVEL_ONE_CLASS_NAME);
            } catch (Exception e) {
                System.err.println("Error launching game: " + e.getMessage());
                e.printStackTrace();
            }
        });

        // Play the fade-out transition
        fadeTransition.play();
    }

    /**
     * Loads and transitions to the specified level.
     *
     * @param className The fully qualified class name of the level to load.
     * @throws Exception If there is an error loading the level.
     */
    private void goToLevel(String className) throws Exception {
        Class<?> myClass = Class.forName(className);
        Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
        LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
        Scene scene = myLevel.initializeScene();
        stage.setScene(scene);

        // Create a fade-in transition for Level One
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), scene.getRoot());
        fadeIn.setFromValue(0.0); // Start fully transparent
        fadeIn.setToValue(1.0);   // Fade to fully opaque
        fadeIn.play(); // Play the fade-in effect
        
        support.firePropertyChange("level", null, className);
        myLevel.startGame();
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }
}
