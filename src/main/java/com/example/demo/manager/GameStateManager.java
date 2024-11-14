package com.example.demo.manager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.example.demo.GameControl;
import com.example.demo.level.LevelFactory;
import com.example.demo.level.LevelParent;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * GameStateManager handles game state changes and manages level loading.
 */
public class GameStateManager {
    private final PropertyChangeSupport support;
    private LevelParent currentLevel;
    private final Stage stage;
    private int currentLevelNumber; 

    public GameStateManager(Stage stage) {
        this.support = new PropertyChangeSupport(this);
        this.stage = stage;
    }

    /**
     * Adds a property change listener to listen for level changes.
     * @param listener The listener to add.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a property change listener.
     * @param listener The listener to remove.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Loads and initializes the specified level.
     * @param levelNumber The level number to load.
     * @param gameControl The GameControl instance for managing game flow.
     */
    public void loadLevel(int levelNumber, GameControl gameControl) {
        // Prevent reloading the same level
        if (levelNumber == currentLevelNumber) {
            return;
        }

        currentLevelNumber = levelNumber; // Update the current level number
        currentLevel = LevelFactory.createLevel(levelNumber, gameControl);

        if (currentLevel == null) {
            System.err.println("Error: currentLevel is null in loadLevel.");
            return;
        }
        
        System.out.println("Success: currentLevel initialized in loadLevel.");

        // Remove any existing listeners from the current level and add a new one
        currentLevel.removePropertyChangeListener(this::levelChangeListener);
        currentLevel.addPropertyChangeListener(this::levelChangeListener);

        // Initialize the scene and start the game
        Scene scene = currentLevel.initializeScene();
        stage.setScene(scene);
        currentLevel.startGame();

        // Notify listeners of the level change
        notifyLevelChange(levelNumber);
    }

    /**
     * Handles the level change event and loads the next level.
     * @param event The property change event.
     */
    private void levelChangeListener(PropertyChangeEvent event) {
        if ("level".equals(event.getPropertyName())) {
            int nextLevelNumber = (int) event.getNewValue();
            if (currentLevel != null) {
                loadLevel(nextLevelNumber, currentLevel.getGameControl());
            }
        }
    }

    /**
     * Notifies listeners of a level change event.
     * @param levelNumber The new level number.
     */
    private void notifyLevelChange(int levelNumber) {
        support.firePropertyChange("level", null, levelNumber);
    }

    /**
     * Gets the current level.
     * @return The current LevelParent object.
     */
    public LevelParent getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Calls loseGame on the current level.
     */
    public void loseCurrentLevel() {
        if (currentLevel != null) {
            currentLevel.loseGame();
        }
    }

    /**
     * Calls winGame on the current level.
     */
    public void winCurrentLevel() {
        if (currentLevel != null) {
            currentLevel.winGame();
        }
    }
}
