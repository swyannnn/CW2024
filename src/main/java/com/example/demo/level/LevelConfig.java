package com.example.demo.level;

import com.example.demo.actor.ActorSpawner;
import com.example.demo.manager.AudioManager;
import com.example.demo.manager.GameLoopManager;

/**
 * Encapsulates configuration parameters for creating a level.
 */
public class LevelConfig {
    private final int numberOfPlayers;
    private final ActorSpawner actorSpawner;
    private final AudioManager audioManager;
    private final GameLoopManager gameLoopManager;

    public LevelConfig(int numberOfPlayers, ActorSpawner actorSpawner, AudioManager audioManager, GameLoopManager gameLoopManager) {
        this.numberOfPlayers = numberOfPlayers;
        this.actorSpawner = actorSpawner;
        this.audioManager = audioManager;
        this.gameLoopManager = gameLoopManager;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public ActorSpawner getActorSpawner() {
        return actorSpawner;
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }

    public GameLoopManager getGameLoopManager() {
        return gameLoopManager;
    }
}
