package com.example.demo.level;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

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

    public static Optional<LevelParent> createLevel(int levelNumber, LevelConfig config) {
        Function<LevelConfig, LevelParent> constructor = levelMap.get(levelNumber);
        return constructor != null ? Optional.of(constructor.apply(config)) : Optional.empty();
    }

    public static void registerLevel(int levelNumber, Function<LevelConfig, LevelParent> constructor) {
        levelMap.put(levelNumber, constructor);
    }
}
