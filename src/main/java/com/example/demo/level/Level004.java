package com.example.demo.level;

import com.example.demo.actor.ActiveActor;
import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.plane.PlaneFactory;
import com.example.demo.actor.plane.PlaneFactory.PlaneType;
import com.example.demo.manager.AudioManager;
import com.example.demo.util.GameConstant;


/**
 * Level004 class represents the fourth level in the game, extending the LevelParent class.
 * It initializes the level with specific background image, music, and player health.
 * The class is responsible for spawning enemy units and checking if the user has reached the target.
 *
 * <p>Constructor:
 * <ul>
 *   <li>{@code Level004(int numberOfPlayers, ActorSpawner actorSpawner, AudioManager audioManager)}: 
 *   Initializes the level with the specified number of players, actor spawner, and audio manager.
 * </ul>
 *
 * <p>Methods:
 * <ul>
 *   <li>{@code boolean userHasReachedTarget()}: Checks if the user has reached the target by determining if the boss plane is destroyed.
 *   <li>{@code void spawnEnemyUnits()}: Spawns enemy units, specifically the boss plane, if no enemy units are present.
 * </ul>
 */
public class Level004 extends LevelParent {
    private static final int playerInitialHealth = GameConstant.Level004.PLAYER_INITIAL_HEALTH;

    private PlaneFactory planeFactory;
    private final ActorSpawner actorSpawn;
    private ActiveActor bossPlane;

    /**
     * Constructs a new Level004 instance.
     *
     * @param numberOfPlayers the number of players in the level
     * @param actorSpawner the actor spawner used to spawn actors in the level
     * @param audioManager the audio manager used to manage audio in the level
     */
    public Level004(int numberOfPlayers, ActorSpawner actorSpawner, AudioManager audioManager) {
        super(4, numberOfPlayers, playerInitialHealth, actorSpawner, audioManager);
        this.actorSpawn = actorSpawner;
        this.planeFactory = new PlaneFactory(actorSpawner);
        initializeFriendlyUnits();
    }

    /**
     * Checks if the user has reached the target by determining if the boss plane is destroyed.
     *
     * @return true if the boss plane is destroyed, false otherwise.
     */
    @Override
    public boolean userHasReachedTarget() {
        return bossPlane.isDestroyed();
    }

    /**
     * Spawns enemy units in the game level. If there are no enemy units currently spawned,
     * it creates and spawns a multi-phase boss plane using the plane factory and assigns it
     * to the bossPlane field.
     */
    @Override
    public void spawnEnemyUnits() {
        if (actorSpawn.getEnemyUnits().isEmpty()) {
            ActiveActor bossPlane = planeFactory.createPlane(PlaneType.MULTI_PHASE_BOSS_PLANE);
            actorSpawn.spawnActor(bossPlane);
            this.bossPlane = bossPlane;
        }
    }
}
