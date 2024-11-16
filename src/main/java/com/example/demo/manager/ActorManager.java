package com.example.demo.manager;

import com.example.demo.ActiveActorDestructible;
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
    private final List<ActiveActorDestructible> friendlyUnits;
    private final List<ActiveActorDestructible> enemyUnits;
    private final List<ActiveActorDestructible> userProjectiles;
    private final List<ActiveActorDestructible> enemyProjectiles;
    private final Group root;

    public ActorManager(Group root, UserPlane user) {
        this.root = root;
        this.friendlyUnits = new ArrayList<>();
        this.enemyUnits = new ArrayList<>();
        this.userProjectiles = new ArrayList<>();
        this.enemyProjectiles = new ArrayList<>();
        addFriendlyUnit(user); // Initialize with the user plane
    }

    // Method to add a friendly unit to the scene and list
    public void addFriendlyUnit(ActiveActorDestructible unit) {
        if (!root.getChildren().contains(unit)) {
            root.getChildren().add(unit);
            friendlyUnits.add(unit);
        }
    }

    // Method to add an enemy unit to the scene and list
    public void addEnemyUnit(ActiveActorDestructible enemy) {
        if (!root.getChildren().contains(enemy)) {
            root.getChildren().add(enemy);
            enemyUnits.add(enemy);
        }
    }

    // Method to add a user projectile to the scene and list
    public void addUserProjectile(ActiveActorDestructible projectile) {
        userProjectiles.add(projectile);
        root.getChildren().add(projectile);
    }

    // Method to add an enemy projectile to the scene and list
    public void addEnemyProjectile(ActiveActorDestructible projectile) {
        enemyProjectiles.add(projectile);
        root.getChildren().add(projectile);
    }

    // Method to add UI elements to the scene
    public void addUIElement(Node element) {
        if (!root.getChildren().contains(element)) {
            root.getChildren().add(element);
        }
    }  

    // Update all actors in the game
    public void updateAllActors() {
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
        root.getChildren().removeAll(destroyed);
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
}
