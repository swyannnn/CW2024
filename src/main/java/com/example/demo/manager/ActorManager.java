package com.example.demo.manager;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.BossPlane;
import com.example.demo.UserPlane;
import javafx.scene.Group;
import javafx.scene.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ActorManager handles all actors within a level, including updating and managing their lifecycle.
 */
public class ActorManager {
    private static ActorManager instance;
    private final List<ActiveActorDestructible> player;
    private final List<ActiveActorDestructible> friendlyUnits;
    private final List<ActiveActorDestructible> enemyUnits;
    private final List<ActiveActorDestructible> userProjectiles;
    private final List<ActiveActorDestructible> enemyProjectiles;
    private final List<ActiveActorDestructible> bossProjectiles;
    private Group root;

    // Private constructor to enforce Singleton pattern
    private ActorManager(Group root) {
        this.root = root;
        this.friendlyUnits = new ArrayList<>();
        this.enemyUnits = new ArrayList<>();
        this.userProjectiles = new ArrayList<>();
        this.enemyProjectiles = new ArrayList<>();
        this.bossProjectiles = new ArrayList<>();
        this.player = new ArrayList<>();
    }

    /**
     * Retrieves the singleton instance of ActorManager.
     *
     * @param root  The root group to which actors are added.
     * @param user  The user-controlled plane.
     * @return The singleton instance of ActorManager.
     */
    public static synchronized ActorManager getInstance(Group root) {
        if (instance == null) {
            instance = new ActorManager(root);
        }
        return instance;
    }

    // Method to update the root
    public void updateRoot(Group newRoot) {
        this.root = newRoot;
    }

    public Group getRoot() {
        return this.root;
    }

    public void addPlayer(UserPlane user) {
        if (!this.root.getChildren().contains(user)) {
            this.root.getChildren().add(user);
            player.add(user);
            System.out.println("Added player: " + user);
        }
    }    

    // Method to add a friendly unit to the scene and list
    public void addFriendlyUnit(ActiveActorDestructible unit) {
        if (!this.root.getChildren().contains(unit)) {
            this.root.getChildren().add(unit);
            friendlyUnits.add(unit);
        }
    }

    // Method to add an enemy unit to the scene and list
    public void addEnemyUnit(ActiveActorDestructible enemy) {
            this.root.getChildren().add(enemy);
            enemyUnits.add(enemy);
            System.out.println("Added enemyUnit: " + enemy + "to the root" + this.root);
    }

    // Method to remove an enemy unit to the scene and list
    public void removeEnemyUnit(ActiveActorDestructible enemy) {
        if (this.root.getChildren().contains(enemy)) {
            this.root.getChildren().remove(enemy);
            enemyUnits.remove(enemy);
            System.out.println("Removed enemy: " + enemy);
        } else {
            System.out.println("Attempted to remove non-existent enemy: " + enemy);
        }
    }

    // Method to add a user projectile to the scene and list
    public void addUserProjectile(ActiveActorDestructible projectile) {
        this.root.getChildren().add(projectile);
        userProjectiles.add(projectile);
    }

    // Method to add an enemy projectile to the scene and list
    public void addEnemyProjectile(ActiveActorDestructible projectile) {
        enemyProjectiles.add(projectile);
        this.root.getChildren().add(projectile);
    }

    // Method to add an enemy projectile to the scene and list
    public void addBossProjectile(ActiveActorDestructible projectile) {
        bossProjectiles.add(projectile);
        this.root.getChildren().add(projectile);
    }

    /**
     * Removes an enemy projectile from the scene and tracking list.
     *
     * @param projectile The enemy projectile to remove.
     */
    public void removeEnemyProjectile(ActiveActorDestructible projectile) {
        if (this.root.getChildren().contains(projectile)) {
            this.root.getChildren().remove(projectile);
            enemyProjectiles.remove(projectile);
            System.out.println("Removed enemy projectile: " + projectile);
        }
    }

    /**
     * Removes a user projectile from the scene and tracking list.
     *
     * @param projectile The user projectile to remove.
     */
    public void removeUserProjectile(ActiveActorDestructible projectile) {
        if (this.root.getChildren().contains(projectile)) {
            this.root.getChildren().remove(projectile);
            userProjectiles.remove(projectile);
            System.out.println("Removed user projectile: " + projectile);
        }
    }

    /**
     * Removes a boss projectile from the scene and tracking list.
     *
     * @param projectile The boss projectile to remove.
     */
    public void removeBossProjectile(ActiveActorDestructible projectile) {
        if (this.root.getChildren().contains(projectile)) {
            this.root.getChildren().remove(projectile);
            bossProjectiles.remove(projectile);
            System.out.println("Removed user projectile: " + projectile);
        }
    }

    // Method to add UI elements to the scene
    public void addUIElement(Node element) {
        if (!this.root.getChildren().contains(element)) {
            this.root.getChildren().add(element);
        }
    }

    /**
     * Updates all actors in the game.
     */
    public void updateAllActors() {
        // Update players
        for (ActiveActorDestructible p : new ArrayList<>(player)) {
            p.updateActor();
        }
    
        // Update friendly units
        for (ActiveActorDestructible friendly : new ArrayList<>(friendlyUnits)) {
            friendly.updateActor();
        }
    
        // Update enemy units
        for (ActiveActorDestructible enemy : new ArrayList<>(enemyUnits)) {
            enemy.updateActor();
        }
    
        // Update user projectiles
        for (ActiveActorDestructible userProj : new ArrayList<>(userProjectiles)) {
            userProj.updateActor();
        }
    
        // Update enemy projectiles
        for (ActiveActorDestructible enemyProj : new ArrayList<>(enemyProjectiles)) {
            enemyProj.updateActor();
            if (enemyProj.isDestroyed()) {
                removeEnemyProjectile(enemyProj);
            }
        }
    }
    

    // Remove actors that have been destroyed
    public void removeDestroyedActors() {
        removeDestroyedActorsFromList(friendlyUnits);
        removeDestroyedActorsFromList(enemyUnits);
        removeDestroyedActorsFromList(userProjectiles);
        removeDestroyedActorsFromList(enemyProjectiles);
    }

    // Helper method to remove destroyed actors from a list
    private void removeDestroyedActorsFromList(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyed = actors.stream()
            .filter(ActiveActorDestructible::isDestroyed)
            .collect(Collectors.toList());
        this.root.getChildren().removeAll(destroyed);
        actors.removeAll(destroyed);
    }

    // Getters for actor lists
    public List<ActiveActorDestructible> getFriendlyUnits() {
        return friendlyUnits;
    }

    public List<ActiveActorDestructible> getEnemyUnits() {
        return enemyUnits;
    }

    public List<ActiveActorDestructible> getUserProjectiles() {
        return userProjectiles;
    }

    public List<ActiveActorDestructible> getEnemyProjectiles() {
        return enemyProjectiles;
    }

    /**
     * Retrieves a list of players.
     *
     * @return A list of players, currently containing only one player.
     */
    public List<UserPlane> getPlayers() {
        return player.stream()
                    .filter(UserPlane.class::isInstance) // Ensure only UserPlane instances are returned
                    .map(UserPlane.class::cast)         // Cast to UserPlane
                    .collect(Collectors.toList());
    }


    // Optionally, add a getBossPlane() method if needed
    public BossPlane getBossPlane() {
        for (ActiveActorDestructible enemy : enemyUnits) {
            if (enemy instanceof BossPlane) {
                return (BossPlane) enemy;
            }
        }
        return null;
    }
}
