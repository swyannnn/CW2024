package com.example.demo.level;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Factory class for creating different levels in the game.
 * It uses a map to store level constructors and provides methods to create levels and register new levels.
 */
public class LevelFactory {
    private static final Map<Integer, Function<LevelConfig, LevelParent>> levelMap = new HashMap<>();

    static {
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
     * Creates a level based on the provided level number and configuration.
     *
     * @param levelNumber the number of the level to create
     * @param config the configuration for the level
     * @return an Optional containing the created LevelParent if the level number is valid, otherwise an empty Optional
     */
    public static Optional<LevelParent> createLevel(int levelNumber, LevelConfig config) {
        Function<LevelConfig, LevelParent> constructor = levelMap.get(levelNumber);
        return constructor != null ? Optional.of(constructor.apply(config)) : Optional.empty();
    }

    /**
     * Registers a level with a specified level number and constructor function.
     *
     * @param levelNumber the unique identifier for the level
     * @param constructor a function that takes a LevelConfig and returns a LevelParent
     */
    public static void registerLevel(int levelNumber, Function<LevelConfig, LevelParent> constructor) {
        levelMap.put(levelNumber, constructor);
    }
}
