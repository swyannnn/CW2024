package com.example.demo.level;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.example.demo.listeners.LevelConstructor;

/**
 * LevelFactory creates instances of levels using a map of registered level constructors.
 */
public class LevelFactory {
    // Map for registered level constructors
    private static final Map<Integer, LevelConstructor> levelMap = new HashMap<>();

    static {
        // Register levels with appropriate constructors
        levelMap.put(1, config -> new Level001(
            config.getNumberOfPlayers(), 
            config.getActorSpawner(), 
            config.getAudioManager()
        ));
        levelMap.put(2, config -> new Level002(
            config.getNumberOfPlayers(), 
            config.getActorSpawner(), 
            config.getAudioManager()
        ));
        levelMap.put(3, config -> new Level003(
            config.getNumberOfPlayers(), 
            config.getActorSpawner(), 
            config.getAudioManager(),
            config.getGameLoopManager()
        ));        
        levelMap.put(4, config -> new Level004(
            config.getNumberOfPlayers(), 
            config.getActorSpawner(), 
            config.getAudioManager()
        ));
    }

    /**
     * Creates and returns an instance of the specified level.
     *
     * @param levelNumber The level number to create.
     * @param config      The LevelConfiguration containing necessary parameters.
     * @return An Optional containing the LevelParent instance if found, or empty if not.
     */
    public static Optional<LevelParent> createLevel(int levelNumber, LevelConfig config) {
        LevelConstructor constructor = levelMap.get(levelNumber);
        if (constructor != null) {
            return Optional.of(constructor.create(config));
        }
        return Optional.empty();
    }

    /**
     * Registers a new level dynamically.
     *
     * @param levelNumber The level number to register.
     * @param constructor The constructor function for the level.
     */
    public static void registerLevel(int levelNumber, LevelConstructor constructor) {
        levelMap.put(levelNumber, constructor);
    }
}
