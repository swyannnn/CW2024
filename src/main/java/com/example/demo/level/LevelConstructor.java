package com.example.demo.level;

/**
 * Interface for constructing levels.
 * Implementations of this interface are responsible for creating instances of LevelParent
 * based on the provided LevelConfig configuration.
 */
public interface LevelConstructor {
    /**
     * Creates a new instance of LevelParent based on the provided LevelConfig.
     *
     * @param config the configuration object containing the necessary parameters
     *               to construct a LevelParent instance
     * @return a new instance of LevelParent configured according to the provided
     *         LevelConfig
     */
    LevelParent create(LevelConfig config);
}