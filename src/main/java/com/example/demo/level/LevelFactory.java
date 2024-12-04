package com.example.demo.level;

import com.example.demo.controller.Controller;
import com.example.demo.actor.ActorSpawner;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * LevelFactory creates instances of levels using a map of registered level constructors.
 */
public class LevelFactory {
    // Map for registered level constructors
    private static final Map<Integer, BiFunction<Controller, ActorSpawner, LevelParent>> levelMap = new HashMap<>();

    static {
        // Register levels with appropriate constructors
        levelMap.put(1, (controller, actorSpawner) -> new Level001(controller, 1, actorSpawner));
        levelMap.put(2, (controller, actorSpawner) -> new Level002(controller, 2, actorSpawner));
        levelMap.put(3, (controller, actorSpawner) -> new Level003(controller, 3, actorSpawner));
        levelMap.put(4, (controller, actorSpawner) -> new Level004(controller, 4, actorSpawner));
        // Add more levels as needed
    }

    /**
     * Creates and returns an instance of the specified level.
     *
     * @param levelNumber The level number to create.
     * @param controller  The Controller instance for managing game flow.
     * @param actorSpawner The ActorSpawner instance for spawning actors.
     * @return An Optional containing the LevelParent instance if found, or empty if not.
     */
    public static Optional<LevelParent> createLevel(int levelNumber, Controller controller, ActorSpawner actorSpawner) {
        BiFunction<Controller, ActorSpawner, LevelParent> constructor = levelMap.get(levelNumber);
        if (constructor != null) {
            return Optional.of(constructor.apply(controller, actorSpawner));
        }
        return Optional.empty();
    }

    /**
     * Registers a new level dynamically.
     *
     * @param levelNumber The level number to register.
     * @param constructor The constructor function for the level.
     */
    public static void registerLevel(int levelNumber, BiFunction<Controller, ActorSpawner, LevelParent> constructor) {
        levelMap.put(levelNumber, constructor);
    }
}
