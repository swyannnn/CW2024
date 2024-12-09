package com.example.demo.level;

import com.example.demo.actor.ActorSpawner;
import com.example.demo.manager.AudioManager;
import com.example.demo.manager.GameLoopManager;


/**
 * The LevelConfig class encapsulates the configuration settings for a game level.
 * It includes the number of players, actor spawner, audio manager, and game loop manager.
 * Flexible configurations can be set for different levels by adjusting these parameters.
 * When more customization is needed, the LevelConfig class can be extended to include additional settings.
 */
public class LevelConfig {
    private final int numberOfPlayers;
    private final ActorSpawner actorSpawner;
    private final AudioManager audioManager;
    private final GameLoopManager gameLoopManager;

    /**
     * Constructs a new LevelConfig with the specified parameters.
     *
     * @param numberOfPlayers the number of players in the level
     * @param actorSpawner the actor spawner responsible for spawning game actors
     * @param audioManager the audio manager responsible for handling game audio
     * @param gameLoopManager the game loop manager responsible for managing the game loop
     */
    public LevelConfig(int numberOfPlayers, ActorSpawner actorSpawner, AudioManager audioManager, GameLoopManager gameLoopManager) {
        this.numberOfPlayers = numberOfPlayers;
        this.actorSpawner = actorSpawner;
        this.audioManager = audioManager;
        this.gameLoopManager = gameLoopManager;
    }

    /**
     * Retrieves the number of players configured for the level.
     *
     * @return the number of players
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Retrieves the ActorSpawner instance.
     *
     * @return the ActorSpawner instance associated with this LevelConfig.
     */
    public ActorSpawner getActorSpawner() {
        return actorSpawner;
    }

    /**
     * Retrieves the AudioManager instance.
     *
     * @return the current AudioManager instance.
     */
    public AudioManager getAudioManager() {
        return audioManager;
    }

    /**
     * Retrieves the GameLoopManager instance.
     *
     * @return the GameLoopManager instance associated with this level configuration.
     */
    public GameLoopManager getGameLoopManager() {
        return gameLoopManager;
    }
}
