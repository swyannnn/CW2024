package com.example.demo.level;

import com.example.demo.actor.ActiveActor;
import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.plane.PlaneFactory;
import com.example.demo.actor.plane.PlaneType;
import com.example.demo.manager.AudioManager;


/**
 * Level004 is a subclass of LevelParent that represents the fourth level of the game.
 * It initializes the level with specific settings and handles the spawning of enemy units
 * and checking if the user has reached the target by destroying the boss plane.
 *
 * <p>Key functionalities include:
 * <ul>
 *   <li>Initializing friendly units.</li>
 *   <li>Checking if the user has reached the target by destroying the boss plane.</li>
 *   <li>Spawning a multi-phase boss plane if no enemy units are currently spawned.</li>
 * </ul>
 *
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/level/Level004.java">Github Source Code</a>
 * @see LevelParent
 * @see ActorSpawner
 * @see PlaneFactory
 */
public class Level004 extends LevelParent {
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
        super(4, numberOfPlayers, actorSpawner, audioManager);
        this.actorSpawn = actorSpawner;
        this.planeFactory = new PlaneFactory(actorSpawner);
    }

    /**
     * Checks if the user has reached the target by determining if the boss plane is destroyed.
     *
     * @return true if the boss plane is destroyed, false otherwise.
     */
    @Override
    public boolean userHasReachedTarget() {
        if (bossPlane == null) {
            return false;
        }
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
            actorSpawn.addActor(bossPlane);
            this.bossPlane = bossPlane;
        }
    }
}
