package com.example.demo.manager;

import com.example.demo.actor.ActiveActor;
import com.example.demo.actor.ActorSpawner;
import com.example.demo.actor.plane.BossPlane;
import com.example.demo.actor.plane.FighterPlane;
import com.example.demo.actor.plane.UserPlane;
import com.example.demo.actor.projectile.BossProjectile;
import com.example.demo.actor.projectile.EnemyProjectile;
import com.example.demo.actor.projectile.UserProjectile;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;


/**
 * The ActorManager class is responsible for managing and updating all active actors in the game.
 * It follows the singleton pattern to ensure only one instance of the manager exists.
 * The manager handles adding, removing, and updating actors, as well as managing UI elements.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/manager/ActorManager.java">Github Source Code</a>
 */
public class ActorManager implements ActorSpawner {
    private static ActorManager instance;
    private final List<ActiveActor> actors;
    private Group root;

    /**
     * Constructs an ActorManager with the specified root group.
     *
     * @param root the root group to which actors will be added
     */
    private ActorManager(Group root) {
        this.root = root;
        this.actors = new ArrayList<>();
    }

    /**
     * Retrieves the singleton instance of ActorManager.
     *
     * @param root The root group to which actors are added.
     * @return The singleton instance of ActorManager.
     */
    public static synchronized ActorManager getInstance(Group root) {
        if (instance == null) {
            instance = new ActorManager(root);
        }
        return instance;
    }

    /**
     * Updates the root group to the specified new root group.
     *
     * @param newRoot the new root group to be set
     */
    public void updateRoot(Group newRoot) {
        System.out.println("ActorManager: Updating root to " + newRoot);
        this.root = newRoot;
    }

    /**
     * Adds an actor to the manager and the scene graph.
     *
     * @param actor The actor to add.
     */
    public void addActor(ActiveActor actor) {
        actors.add(actor);
        root.getChildren().add(actor);
    }

    /**
     * Adds a UI element to the root node if it is not already present.
     *
     * @param element the UI element to be added
     */
    public void addUIElement(Node element) {
        if (!this.root.getChildren().contains(element)) {
            this.root.getChildren().add(element);
        }
    }

    /**
     * Removes the specified UI element from the root node's children if it exists.
     *
     * @param element the UI element to be removed
     */
    public void removeUIElement(Node element) {
        if (this.root.getChildren().contains(element)) {
            this.root.getChildren().remove(element);
        }
    }

    /**
     * Removes the specified actor from the list of active actors and from the UI.
     * This method ensures that the removal is performed on the JavaFX Application Thread.
     *
     * @param actor the ActiveActor to be removed
     */
    public void removeActor(ActiveActor actor) {
        Platform.runLater(() -> {
            actors.remove(actor);
            root.getChildren().remove(actor);
        });
    }
    
    /**
     * Updates all active actors with the current time.
     * <p>
     * This method creates a copy of the list of actors to avoid
     * Each actor's {@code update} method is called with the provided timestamp.
     *
     * @param now the current time in milliseconds
     */
    public void updateAllActors(long now) {
        // Create a copy to avoid ConcurrentModificationException
        List<ActiveActor> actorsCopy = new ArrayList<>(actors);
        for (ActiveActor actor : actorsCopy) {
            actor.update(now);
        }
    }

    /**
     * Removes all destroyed actors from the list of actors and from the root's children.
     * An actor is considered destroyed if its `isDestroyed` method returns true.
     * This method iterates through the list of actors and removes each actor that is destroyed.
     */
    public void removeDestroyedActors() {
        actors.removeIf(actor -> {
            if (actor.isDestroyed()) {
                root.getChildren().remove(actor);
                return true;
            }
            return false;
        });
    }

    /**
     * Cleans up the active actors by destroying each actor, removing it from the root's children,
     * and then removing it from the list of actors.
     * This method iterates over a copy of the actors list to avoid 
     * ConcurrentModificationException during the removal process.
     */
    public void cleanup() {
        for (ActiveActor actor : new ArrayList<>(actors)) {
            actor.destroy();
            root.getChildren().remove(actor);
            actors.remove(actor);
        }
    }

    /**
     * Retrieves a list of all actors that are instances of UserPlane.
     *
     * @return a list of UserPlane objects representing the players.
     */
    public List<UserPlane> getPlayers() {
        List<UserPlane> players = new ArrayList<>();
        for (ActiveActor actor : actors) {
            if (actor instanceof UserPlane) {
                players.add((UserPlane) actor);
            }
        }
        return players;
    }

    /**
     * Retrieves a list of user projectiles from the collection of actors.
     *
     * @return a list of ActiveActor objects that are instances of UserProjectile.
     */
    public List<ActiveActor> getUserProjectiles() {
        List<ActiveActor> userProjectiles = new ArrayList<>();
        for (ActiveActor actor : actors) {
            if (actor instanceof UserProjectile) {
                userProjectiles.add(actor);
            }
        }
        return userProjectiles;
    }

    /**
     * Retrieves a list of enemy units from the collection of actors.
     * An enemy unit is defined as an instance of FighterPlane that is not an instance of UserPlane.
     *
     * @return a list of ActiveActor objects representing enemy units.
     */
    public List<ActiveActor> getEnemyUnits() {
        List<ActiveActor> enemyUnits = new ArrayList<>();
        for (ActiveActor actor : actors) {
            if (actor instanceof FighterPlane && !(actor instanceof UserPlane)) {
                enemyUnits.add(actor);
            }
        }
        return enemyUnits;
    }


    /**
     * Retrieves a list of enemy projectiles from the current actors.
     *
     * @return a list of ActiveActor objects that are instances of EnemyProjectile.
     */
    public List<ActiveActor> getEnemyProjectiles() {
        List<ActiveActor> enemyProjectiles = new ArrayList<>();
        for (ActiveActor actor : actors) {
            if (actor instanceof EnemyProjectile) {
                enemyProjectiles.add(actor);
            }
        }
        return enemyProjectiles;
    }

    /**
     * Retrieves a list of all boss units from the collection of actors.
     * 
     * @return a list of {@link ActiveActor} objects that are instances of {@link BossPlane}.
     */
    public List<ActiveActor> getBossUnits() {
        List<ActiveActor> bossUnits = new ArrayList<>();
        for (ActiveActor actor : actors) {
            if (actor instanceof BossPlane) {
                bossUnits.add(actor);
            }
        }
        return bossUnits;
    }

    /**
     * Retrieves a list of all active boss projectiles.
     *
     * @return a list of ActiveActor objects that are instances of BossProjectile.
     */
    public List<ActiveActor> getBossProjectiles() {
        List<ActiveActor> bossProjectiles = new ArrayList<>();
        for (ActiveActor actor : actors) {
            if (actor instanceof BossProjectile) {
                bossProjectiles.add(actor);
            }
        }
        return bossProjectiles;
        }
    }
