package com.example.demo.level;

import com.example.demo.controller.Controller;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * LevelFactory creates instances of levels using a map of registered level constructors.
 */
public class LevelFactory {
    // Map for registered level constructors
    private static final Map<Integer, Function<Controller, LevelParent>> levelMap = new HashMap<>();

    static {
        // Register levels with appropriate constructors
        levelMap.put(1, controller -> new Level001(controller, 1));
        levelMap.put(2, controller -> new Level002(controller, 2));
        levelMap.put(3, controller -> new Level003(controller, 3));
        levelMap.put(4, controller -> new Level004(controller, 4));
        // Add more levels as needed
    }

    /**
     * Creates and returns an instance of the specified level.
     *
     * @param levelNumber The level number to create.
     * @param controller The Controller instance for managing game flow.
     * @return An instance of the specified LevelParent.
     */
    public static Optional<LevelParent> createLevel(int levelNumber, Controller controller) {
    Function<Controller, LevelParent> constructor = levelMap.get(levelNumber);
    if (constructor != null) {
        return Optional.of(constructor.apply(controller));
    }
    return Optional.empty();
    }

    /**
     * Registers a new level dynamically.
     *
     * @param levelNumber The level number to register.
     * @param constructor The constructor function for the level.
     */
    public static void registerLevel(int levelNumber, Function<Controller, LevelParent> constructor) {
        levelMap.put(levelNumber, constructor);
    }
}
