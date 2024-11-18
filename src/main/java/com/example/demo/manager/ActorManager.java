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

    // Method to add a player to the scene and list
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
        // if (!root.getChildren().contains(enemy)) {
            this.root.getChildren().add(enemy);
            enemyUnits.add(enemy);
            System.out.println("Added enemyUnit: " + enemy);
        // }
    }

    // Method to remove an enemy unit to the scene and list
    public void removeEnemyUnit(ActiveActorDestructible enemy) {
        // if (!root.getChildren().contains(enemy)) {
            this.root.getChildren().remove(enemy);
            enemyUnits.remove(enemy);
            System.out.println("Removed enemy: " + enemy);
        // }
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

    // Method to add UI elements to the scene
    public void addUIElement(Node element) {
        if (!this.root.getChildren().contains(element)) {
            this.root.getChildren().add(element);
        }
    }

    // Update all actors in the game
    public void updateAllActors() {
        player.forEach(ActiveActorDestructible::updateActor);
        friendlyUnits.forEach(ActiveActorDestructible::updateActor);
        enemyUnits.forEach(ActiveActorDestructible::updateActor);
        userProjectiles.forEach(ActiveActorDestructible::updateActor);
        enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
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

    // Add this method to return the player as a UserPlane
    public UserPlane getPlayer() {
        if (!player.isEmpty() && player.get(0) instanceof UserPlane) {
            return (UserPlane) player.get(0); // Cast to UserPlane
        }
        return null; // Handle the case when there is no player
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
