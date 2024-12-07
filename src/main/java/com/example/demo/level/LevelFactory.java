package com.example.demo.level;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * The LevelFactory class is responsible for creating instances of levels based on a given level number
 * and configuration. It maintains a map of registered level constructors, allowing for dynamic level
 * creation. Levels can be registered with their corresponding constructors, and the factory can create
 * levels by looking up the appropriate constructor in the map.
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * LevelConfig config = new LevelConfig(...);
 * Optional<LevelParent> level = LevelFactory.createLevel(1, config);
 * if (level.isPresent()) {
 *     // Use the created level
 * }
 * }</pre>
 *
 * <p>To register a new level:</p>
 * <pre>{@code
 * LevelFactory.registerLevel(5, config -> new Level005(
 *     config.getNumberOfPlayers(), 
 *     config.getActorSpawner(), 
 *     config.getAudioManager()
 * ));
 * }</pre>
 *
 * <p>Note: The LevelFactory class uses a static initializer block to register predefined levels.</p>
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
     * Creates a level based on the provided level number and configuration.
     *
     * @param levelNumber the number of the level to create
     * @param config the configuration for the level
     * @return an Optional containing the created LevelParent if the level number is valid,
     *         otherwise an empty Optional
     */
    public static Optional<LevelParent> createLevel(int levelNumber, LevelConfig config) {
        LevelConstructor constructor = levelMap.get(levelNumber);
        if (constructor != null) {
            return Optional.of(constructor.create(config));
        }
        return Optional.empty();
    }

    /**
     * Registers a level with a specified level number and its corresponding constructor.
     *
     * @param levelNumber the number representing the level to be registered
     * @param constructor the constructor for creating instances of the level
     */
    public static void registerLevel(int levelNumber, LevelConstructor constructor) {
        levelMap.put(levelNumber, constructor);
    }
}
