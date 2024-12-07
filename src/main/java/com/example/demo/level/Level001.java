package com.example.demo.level;

import com.example.demo.actor.ActiveActor;
import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.plane.PlaneFactory;
import com.example.demo.actor.plane.PlaneFactory.PlaneType;
import com.example.demo.manager.AudioManager;
import com.example.demo.util.GameConstant;

/**
 * Level001 defines the behavior and configuration for the first level of the
 * game.
 */
public class Level001 extends LevelParent{
    private static final String backgroundImageName = GameConstant.Level001.BACKGROUND_IMAGE_NAME;
    private static final String backgroundMusicName = GameConstant.Level001.BACKGROUND_MUSIC;
    private static final int totalEnemies = GameConstant.Level001.TOTAL_ENEMIES;
    private static final int killsToAdvance = GameConstant.Level001.KILLS_TO_ADVANCE;
    private static final double enemySpawnProbability = GameConstant.Level001.ENEMY_SPAWN_PROBABILITY;
    private static final int playerInitialHealth = GameConstant.Level001.PLAYER_INITIAL_HEALTH;
    private PlaneFactory planeFactory;
    private final ActorSpawner actorSpawner;

    /**
     * Constructs a new Level001 instance.
     *
     * @param numberOfPlayers the number of players in the game level
     * @param actorSpawner the actor spawner responsible for spawning game actors
     * @param audioManager the audio manager responsible for handling game audio
     */
    public Level001(int numberOfPlayers, ActorSpawner actorSpawner, AudioManager audioManager) {
        super(1, numberOfPlayers, backgroundImageName, backgroundMusicName, playerInitialHealth, actorSpawner, audioManager);
        this.actorSpawner = actorSpawner;
        this.planeFactory = new PlaneFactory(actorSpawn);
        initializeFriendlyUnits();
    }

    /**
     * Checks if the user has reached the target number of kills to advance.
     *
     * This method calculates the total number of kills by all players and compares it
     * to the required number of kills to advance to the next level.
     *
     * @return true if the total number of kills is greater than or equal to the required kills to advance, false otherwise.
     */
    public boolean userHasReachedTarget() {
        int totalKills = actorSpawner.getPlayers().stream()
                .mapToInt(player -> player.getNumberOfKills())
                .sum();
        return totalKills >= killsToAdvance;
    }

    /**
     * Spawns enemy units until the total number of enemy units reaches the specified limit.
     * The spawning of each enemy unit is determined by a random probability.
     * 
     * This method overrides the spawnEnemyUnits method in the superclass.
     * It continuously checks the current number of enemy units and spawns new ones
     * if the number is less than the totalEnemies limit.
     * 
     * The probability of spawning a new enemy unit is determined by the enemySpawnProbability.
     * If a new enemy unit is to be spawned, it is created using the planeFactory and then
     * added to the game using the actorSpawn.
     */
    @Override
    public void spawnEnemyUnits() {
        while (actorSpawner.getEnemyUnits().size() < totalEnemies) {
            if (Math.random() < enemySpawnProbability) {
                ActiveActor newEnemy = planeFactory.createPlane(PlaneType.ENEMY_PLANE);
                actorSpawn.spawnActor(newEnemy);
            }
        }
    }
}
