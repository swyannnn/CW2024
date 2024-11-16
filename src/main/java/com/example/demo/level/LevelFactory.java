package com.example.demo.level;

import com.example.demo.controller.Controller;
import java.util.HashMap;
import java.util.Map;
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
        // Add more levels as needed
    }

    /**
     * Creates and returns an instance of the specified level.
     *
     * @param levelNumber The level number to create.
     * @param controller The Controller instance for managing game flow.
     * @return An instance of the specified LevelParent.
     */
    public static LevelParent createLevel(int levelNumber, Controller controller) {
        try {
            System.out.println("Creating level: " + levelNumber);
            Function<Controller, LevelParent> constructor = levelMap.get(levelNumber);
            if (constructor != null) {
                LevelParent level = constructor.apply(controller);
                System.out.println("Level " + levelNumber + " created successfully.");
                return level;
            }
            throw new IllegalArgumentException("Unknown level: " + levelNumber);
        } catch (Exception e) {
            System.err.println("Error creating level " + levelNumber + ": " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
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
