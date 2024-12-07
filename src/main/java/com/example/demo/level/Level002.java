package com.example.demo.level;

import java.util.List;

import com.example.demo.actor.ActiveActor;
import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.plane.PlaneFactory;
import com.example.demo.actor.plane.PlaneFactory.PlaneType;
import com.example.demo.manager.AudioManager;
import com.example.demo.util.GameConstant;

/**
 * Level002 is a specific game level that extends the LevelParent class.
 * It initializes the level with specific background image, music, and player health.
 * It also manages the spawning of friendly and enemy units, and checks if the user has reached the target.
 * 
 * <p>This class includes the following functionalities:
 * <ul>
 *   <li>Constructing a new Level002 instance with the specified number of players, actor spawner, and audio manager.</li>
 *   <li>Checking if the user has reached the target by verifying if all boss planes are destroyed.</li>
 *   <li>Spawning enemy units in the game level, specifically creating and adding a new boss plane if there are no current boss units.</li>
 * </ul>
 * </p>
 * 
 * @see LevelParent
 * @see GameConstant.Level002
 * @see PlaneFactory
 * @see ActorSpawner
 * @see AudioManager
 * @see ActiveActor
 * @see PlaneType
 */
public class Level002 extends LevelParent {
    private static final String backgroundImageName = GameConstant.Level002.BACKGROUND_IMAGE_NAME;
    private static final String backgroundMusicName = GameConstant.Level002.BACKGROUND_MUSIC;
    private static final int playerInitialHealth = GameConstant.Level002.PLAYER_INITIAL_HEALTH;
    private PlaneFactory planeFactory;
    private final ActorSpawner actorSpawn;
    
    /**
     * Constructs a new Level002 instance.
     *
     * @param numberOfPlayers the number of players in the game level
     * @param actorSpawner the actor spawner responsible for spawning game actors
     * @param audioManager the audio manager responsible for handling game audio
     */
    public Level002(int numberOfPlayers, ActorSpawner actorSpawner, AudioManager audioManager) {
        super(2, numberOfPlayers, backgroundImageName, backgroundMusicName, playerInitialHealth, actorSpawner, audioManager);
        this.actorSpawn = actorSpawner;
        this.planeFactory = new PlaneFactory(actorSpawn);
        initializeFriendlyUnits();
    }

    /**
     * Checks if the user has reached the target by verifying if all boss planes are destroyed.
     *
     * @return {@code true} if all boss planes are destroyed, {@code false} otherwise.
     */
    @Override
    public boolean userHasReachedTarget() {
        List<ActiveActor> bossPlanes = actorSpawn.getBossUnits();
        if (bossPlanes.isEmpty()) {
            return false; // No boss planes present
        }
        
        // Check if **all** BossPlanes are destroyed
        boolean allDestroyed = true;
        for (ActiveActor actor : bossPlanes) {
            if (!actor.isDestroyed()) {
                allDestroyed = false;
                break;
            }
        }
        return allDestroyed;
    }

    /**
     * Spawns enemy units in the game level. If there are no current boss units,
     * this method creates and adds a new boss plane to the actor spawn.
     * Overrides the spawnEnemyUnits method in the superclass.
     */
    @Override
    public void spawnEnemyUnits() {
        // Check if there are no current enemies
        if (actorSpawn.getBossUnits().size() == 0) {
            // Create and add the boss plane
            ActiveActor newEnemy = planeFactory.createPlane(PlaneType.BOSS_PLANE);
            actorSpawn.spawnActor(newEnemy);
        }
    }
}
