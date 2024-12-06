package com.example.demo.manager;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.example.demo.listeners.GameLoopUpdater;
import com.example.demo.listeners.PauseHandler;

import javafx.animation.AnimationTimer;

/**
 * GameLoopManager is responsible for managing the game's update loop.
 * It also handles pausing and resuming the game.
 */
public class GameLoopManager implements PauseHandler {
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private AnimationTimer gameLoop;
    private GameLoopUpdater updater;
    private boolean paused;

    /**
     * Constructor for GameLoopManager.
     */
    public GameLoopManager() {
        this.paused = false;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    /**
     * Sets the GameLoopUpdater responsible for updating game logic.
     *
     * @param updater The GameLoopUpdater to set.
     */
    public void setUpdater(GameLoopUpdater updater) {
        this.updater = updater;
        initializeGameLoop();
    }

    /**
     * Initializes the AnimationTimer with the updater.
     */
    private void initializeGameLoop() {
        this.gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!paused && updater != null) {
                    updater.update(now);
                }
            }
        };
    }

    /**
     * Starts the game loop.
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
     * Stops the game loop.
     */
    public void stopLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
            System.out.println("GameLoopManager: Game loop stopped.");
        }
    }

    // PauseManager methods
    @Override
    public void pauseGame() {
        if (!paused) {
            boolean oldState = this.paused;
            paused = true;
            pcs.firePropertyChange("paused", oldState, paused);
        }
    }

    @Override
    public void resumeGame() {
        if (paused) {
            boolean oldState = this.paused;
            paused = false;
            pcs.firePropertyChange("paused", oldState, paused);
        }
    }

    @Override
    public boolean isPaused() {
        return paused;
    }
}