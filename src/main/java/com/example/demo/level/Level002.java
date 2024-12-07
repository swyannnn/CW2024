package com.example.demo.level;

import java.util.List;

import com.example.demo.actor.ActiveActor;
import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.plane.PlaneFactory;
import com.example.demo.actor.plane.PlaneFactory.PlaneType;
import com.example.demo.manager.AudioManager;
import com.example.demo.util.GameConstant;


/**
 * Level002 is a subclass of LevelParent that represents the second level of the game.
 * It initializes the level with specific settings and handles the spawning of enemy units
 * and checking if the user has reached the target by destroying all boss planes.
 * 
 * <p>This class uses the following components:
 * <ul>
 *   <li>ActorSpawner: Responsible for spawning game actors.</li>
 *   <li>AudioManager: Responsible for handling game audio.</li>
 *   <li>PlaneFactory: Used to create different types of planes.</li>
 * </ul>
 * 
 * <p>Key functionalities include:
 * <ul>
 *   <li>Initializing friendly units.</li>
 *   <li>Checking if all boss planes are destroyed to determine if the user has reached the target.</li>
 *   <li>Spawning enemy units, specifically boss planes, if none are present.</li>
 * </ul>
 * 
 * @see LevelParent
 * @see ActorSpawner
 * @see AudioManager
 * @see PlaneFactory
 */
public class Level002 extends LevelParent {
    private PlaneFactory planeFactory;
    private final ActorSpawner actorSpawn;
    private ActiveActor bossPlane;
    
    /**
     * Constructs a new Level002 instance.
     *
     * @param numberOfPlayers the number of players in the game level
     * @param actorSpawner the actor spawner responsible for spawning game actors
     * @param audioManager the audio manager responsible for handling game audio
     */
    public Level002(int numberOfPlayers, ActorSpawner actorSpawner, AudioManager audioManager) {
        super(2, numberOfPlayers, actorSpawner, audioManager);
        this.actorSpawn = actorSpawner;
        this.planeFactory = new PlaneFactory(actorSpawn);
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
     * Spawns enemy units in the game level. If there are no boss units currently spawned,
     * this method will create and spawn a boss plane using the plane factory and assign it
     * to the bossPlane field.
     */
    @Override
    public void spawnEnemyUnits() {
        if (actorSpawn.getBossUnits().isEmpty()) {
            ActiveActor bossPlane = planeFactory.createPlane(PlaneType.BOSS_PLANE);
            actorSpawn.spawnActor(bossPlane);
            this.bossPlane = bossPlane;
        }
    }
}
