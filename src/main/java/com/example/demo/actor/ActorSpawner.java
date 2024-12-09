package com.example.demo.actor;

import java.util.List;

import com.example.demo.actor.plane.UserPlane;

import javafx.scene.Group;
import javafx.scene.Node;


/**
 * The ActorSpawner interface provides methods for spawning and managing actors in the game world.
 * It includes methods for spawning active actors, updating the root group, adding UI elements,
 * and retrieving lists of players, enemy units, and boss units.
 */
public interface ActorSpawner {
    /**
     * Spawns the given active actor in the game world.
     *
     * @param actor the active actor to be spawned
     */
    void addActor(ActiveActor actor);

    /**
     * Updates the given root group with new actors or changes.
     *
     * @param root the root group to be updated
     */
    void updateRoot(Group root);

    /**
     * Adds a UI element to the scene.
     *
     * @param node the UI element to be added
     */
    void addUIElement(Node node);

    // get player list
    /**
     * Retrieves a list of UserPlane objects representing the players.
     *
     * @return a List of UserPlane objects.
     */
    List<UserPlane> getPlayers(); 

    /**
     * Retrieves a list of active enemy units.
     *
     * @return a list of {@link ActiveActor} representing the enemy units.
     */
    List<ActiveActor> getEnemyUnits();

    /**
     * Retrieves a list of boss units.
     *
     * @return a list of {@link ActiveActor} representing the boss units.
     */
    List<ActiveActor> getBossUnits();

}
