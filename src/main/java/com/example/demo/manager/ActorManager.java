package com.example.demo.manager;

import com.example.demo.actor.ActiveActorDestructible;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * ActorManager manages all active actors in the game.
 * It handles updating, rendering, and cleaning up actors.
 */
public class ActorManager {
    private static ActorManager instance;
    private final List<ActiveActorDestructible> actors;
    private Group root;

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

    public void updateRoot(Group newRoot) {
        System.out.println("ActorManager: Updating root to " + newRoot);
        this.root = newRoot;
    }

    /**
     * Adds an actor to the manager and the scene graph.
     *
     * @param actor The actor to add.
     */
    public void addActor(ActiveActorDestructible actor) {
        actors.add(actor);
        root.getChildren().add(actor);
        if (actor instanceof com.example.demo.actor.projectile.BossProjectile) {
            System.out.println("Added actor: " + actor + " to the root " + this.root);
        }
        // System.out.println("Added actor: " + actor + " to the root " + this.root);
    }

    // Method to add UI elements to the scene
    public void addUIElement(Node element) {
        if (!this.root.getChildren().contains(element)) {
            this.root.getChildren().add(element);
        }
    }

    // Method to remove UI elements from the scene
    public void removeUIElement(Node element) {
        if (this.root.getChildren().contains(element)) {
            this.root.getChildren().remove(element);
        }
    }

    public void removeActor(ActiveActorDestructible actor) {
        Platform.runLater(() -> {
            actors.remove(actor);
            root.getChildren().remove(actor);
        });
    }
    

    /**
     * Updates all actors by calling their update methods with the current time.
     *
     * @param now The current timestamp in nanoseconds.
     */
    public void updateAllActors(long now) {
        // Create a copy to avoid ConcurrentModificationException
        List<ActiveActorDestructible> actorsCopy = new ArrayList<>(actors);
        for (ActiveActorDestructible actor : actorsCopy) {
            actor.update(now);
        }
    }

    /**
     * Removes all actors that have been destroyed or marked for removal.
     */
    public void removeDestroyedActors() {
        actors.removeIf(actor -> {
            if (actor.isDestroyed()) {
                System.out.println("Removing destroyed actor: " + actor + " from the root " + this.root);
                root.getChildren().remove(actor);
                return true;
            }
            return false;
        });
    }

    /**
     * Cleans up all actors by removing them from the manager and scene graph.
     */
    public void cleanup() {
        for (ActiveActorDestructible actor : new ArrayList<>(actors)) {
            actor.destroy();
            root.getChildren().remove(actor);
            actors.remove(actor);
        }
    }

    /**
     * Retrieves the list of players.
     *
     * @return The list of players.
     */
    public List<com.example.demo.actor.plane.UserPlane> getPlayers() {
        List<com.example.demo.actor.plane.UserPlane> players = new ArrayList<>();
        for (ActiveActorDestructible actor : actors) {
            if (actor instanceof com.example.demo.actor.plane.UserPlane) {
                players.add((com.example.demo.actor.plane.UserPlane) actor);
            }
        }
        return players;
    }

    /**
     * Retrieves the list of user projectiles.
     *
     * @return The list of user projectiles.
     */
    public List<ActiveActorDestructible> getUserProjectiles() {
        List<ActiveActorDestructible> userProjectiles = new ArrayList<>();
        for (ActiveActorDestructible actor : actors) {
            if (actor instanceof com.example.demo.actor.projectile.UserProjectile) {
                userProjectiles.add(actor);
            }
        }
        return userProjectiles;
    }

    /**
     * Retrieves the list of enemy units.
     *
     * @return The list of enemy units.
     */
    public List<ActiveActorDestructible> getEnemyUnits() {
        List<ActiveActorDestructible> enemyUnits = new ArrayList<>();
        for (ActiveActorDestructible actor : actors) {
            if (actor instanceof com.example.demo.actor.plane.FighterPlane && !(actor instanceof com.example.demo.actor.plane.UserPlane)) {
                enemyUnits.add(actor);
            }
        }
        return enemyUnits;
    }

    /**
     * Retrieves the list of enemy projectiles.
     *
     * @return The list of enemy projectiles.
     */
    public List<ActiveActorDestructible> getEnemyProjectiles() {
        List<ActiveActorDestructible> enemyProjectiles = new ArrayList<>();
        for (ActiveActorDestructible actor : actors) {
            if (actor instanceof com.example.demo.actor.projectile.EnemyProjectile) {
                enemyProjectiles.add(actor);
            }
        }
        return enemyProjectiles;
    }

    /**
     * Retrieves the list of boss units.
     *
     * @return The list of boss units.
     */
    public List<ActiveActorDestructible> getBossUnits() {
        List<ActiveActorDestructible> bossUnits = new ArrayList<>();
        for (ActiveActorDestructible actor : actors) {
            if (actor instanceof com.example.demo.actor.plane.BossPlane) {
                bossUnits.add(actor);
            }
        }
        return bossUnits;
    }

    /**
     * Retrieves the list of boss projectiles.
     *
     * @return The list of boss projectiles.
     */
    public List<ActiveActorDestructible> getBossProjectiles() {
        List<ActiveActorDestructible> bossProjectiles = new ArrayList<>();
        for (ActiveActorDestructible actor : actors) {
            if (actor instanceof com.example.demo.actor.projectile.BossProjectile) {
                bossProjectiles.add(actor);
            }
        }
        return bossProjectiles;
        }
    }
