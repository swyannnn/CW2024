package com.example.demo.manager;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.example.demo.handler.GameLoopHandler;

import javafx.animation.AnimationTimer;

/**
 * The GameLoopManager class is responsible for managing the game loop using the singleton pattern.
 * It provides methods to start, stop, pause, and resume the game loop, as well as to set the game loop updater.
 * The class also supports property change listeners to notify other components of changes in the game loop state.
 * 
 * <p>Animation Timer:</p>
 * <p>The game loop is implemented using JavaFX's AnimationTimer, which calls the updater's update method on each frame.</p>
 *
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/manager/GameLoopManager.java">Github Source Code</a>
 * @see java.beans.PropertyChangeSupport
 * @see javafx.animation.AnimationTimer
 */
public class GameLoopManager{
    private static volatile GameLoopManager instance;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private AnimationTimer gameLoop;
    private GameLoopHandler updater;
    private boolean paused;

    /**
     * Private constructor for the GameLoopManager class.
     * Initializes the GameLoopManager instance and sets the paused state to false.
     */
    private GameLoopManager() {
        this.paused = false;
    }

    /**
     * Retrieves the singleton instance of GameLoopManager.
     * Implements double-checked locking for thread safety and performance.
     *
     * @return The singleton instance of GameLoopManager.
     */
    public static GameLoopManager getInstance() {
        if (instance == null) {
            synchronized (GameLoopManager.class) {
                if (instance == null) {
                    instance = new GameLoopManager();
                }
            }
        }
        return instance;
    }

    /**
     * Adds a PropertyChangeListener to the listener list.
     * The listener is registered for all properties.
     *
     * @param listener the PropertyChangeListener to be added
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * Removes a PropertyChangeListener from the listener list.
     * This method should be called to unregister a listener that was previously added.
     *
     * @param listener the PropertyChangeListener to be removed
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    /**
     * Sets the GameLoopHandler updater and initializes the game loop.
     *
     * @param updater the GameLoopHandler to be set
     */
    public void setUpdater(GameLoopHandler updater) {
        this.updater = updater;
        initializeGameLoop();
    }

    /**
     * Initializes the game loop using an AnimationTimer.
     * The game loop will call the updater's update method with the current time in nanoseconds
     * if the game is not paused and the updater is not null.
     */
    private void initializeGameLoop() {
        this.gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
            if (!paused && updater != null) {
                updater.update(now); // 'now' is in nanoseconds
            }
            }
        };
    }

    /**
     * Starts the game loop if it has been initialized.
     * If the game loop is not initialized, it logs a message indicating that.
     */
    public void startLoop() {
        if (gameLoop != null) {
            gameLoop.start();
            System.out.println("GameLoopManager: Game loop started.");
        } else {
            System.out.println("GameLoopManager: Game loop not initialized.");
        }
    }

    /**
     * Stops the game loop if it is currently running.
     * This method checks if the game loop is not null and then stops it.
     * It also prints a message to the console indicating that the game loop has been stopped.
     */
    public void stopLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
            System.out.println("GameLoopManager: Game loop stopped.");
        }
    }

    /**
     * Pauses the game if it is not already paused.
     * This method sets the paused state to true and fires a property change event
     * to notify listeners about the change in the paused state.
     */
    public void pauseGame() {
        if (!paused) {
            boolean oldState = this.paused;
            paused = true;
            pcs.firePropertyChange("paused", oldState, paused);
        }
    }

    /**
     * Resumes the game if it is currently paused. This method changes the 
     * paused state to false and fires a property change event to notify 
     * listeners about the change in the paused state.
     */
    public void resumeGame() {
        if (paused) {
            boolean oldState = this.paused;
            paused = false;
            pcs.firePropertyChange("paused", oldState, paused);
        }
    }

    /**
     * Checks if the game loop is currently paused.
     *
     * @return {@code true} if the game loop is paused, {@code false} otherwise.
     */
    public boolean isPaused() {
        return paused;
    }
}
