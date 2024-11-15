package com.example.demo.level;

import com.example.demo.level.LevelParent;
import com.example.demo.GameControl;
import com.example.demo.level.Level001;
import com.example.demo.level.Level002;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * LevelFactory creates instances of levels using a map of registered level constructors.
 */
public class LevelFactory {
    // Use BiFunction<GameControl, Integer, LevelParent> for constructors that take two arguments
    private static final Map<Integer, BiFunction<GameControl, Integer, LevelParent>> levelMap = new HashMap<>();

    static {
        // Register levels with BiFunction
        levelMap.put(1, Level001::new);
        levelMap.put(2, Level002::new);
        // Continue registering all levels here
    }

    /**
     * Creates and returns an instance of the specified level.
     * @param levelNumber The level number to create.
     * @param gameControl The GameControl instance for managing game flow.
     * @return An instance of the specified LevelParent.
     */
    public static LevelParent createLevel(Integer levelNumber, GameControl gameControl) {
        try {
            System.out.println("Creating level: " + levelNumber);
            BiFunction<GameControl, Integer, LevelParent> constructor = levelMap.get(levelNumber);
            if (constructor != null) {
                LevelParent level = constructor.apply(gameControl, levelNumber);
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
     * @param levelNumber The level number to register.
     * @param constructor The constructor function for the level.
     */
    public static void registerLevel(Integer levelNumber, BiFunction<GameControl, Integer, LevelParent> constructor) {
        levelMap.put(levelNumber, constructor);
    }
}
