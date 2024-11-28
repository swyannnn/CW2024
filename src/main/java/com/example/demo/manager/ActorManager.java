// package com.example.demo.manager;

// import javafx.scene.Group;
// import javafx.scene.Node;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.stream.Collectors;

// import com.example.demo.actor.ActiveActorDestructible;
// import com.example.demo.actor.plane.BossPlane;
// import com.example.demo.actor.plane.CanFire;
// import com.example.demo.actor.plane.UserPlane;

// /**
//  * ActorManager handles all actors within a level, including updating and managing their lifecycle.
//  */
// public class ActorManager {
//     private static ActorManager instance;
//     private final List<ActiveActorDestructible> player;
//     private final List<ActiveActorDestructible> friendlyUnits;
//     private final List<ActiveActorDestructible> enemyUnits;
//     private final List<ActiveActorDestructible> bossUnits;
//     private final List<ActiveActorDestructible> userProjectiles;
//     private final List<ActiveActorDestructible> enemyProjectiles;
//     private final List<ActiveActorDestructible> bossProjectiles;
//     private Group root;

//     // Private constructor to enforce Singleton pattern
//     private ActorManager(Group root) {
//         this.root = root;
//         this.friendlyUnits = new ArrayList<>();
//         this.bossUnits = new ArrayList<>();
//         this.enemyUnits = new ArrayList<>();
//         this.userProjectiles = new ArrayList<>();
//         this.enemyProjectiles = new ArrayList<>();
//         this.bossProjectiles = new ArrayList<>();
//         this.player = new ArrayList<>();
//     }

//     /**
//      * Retrieves the singleton instance of ActorManager.
//      *
//      * @param root  The root group to which actors are added.
//      * @param user  The user-controlled plane.
//      * @return The singleton instance of ActorManager.
//      */
//     public static synchronized ActorManager getInstance(Group root) {
//         if (instance == null) {
//             instance = new ActorManager(root);
//         }
//         return instance;
//     }

//     // public static synchronized ActorManager getInstance() {}


//     public void updateRoot(Group newRoot) {
//         System.out.println("ActorManager: Updating root to " + newRoot);
//         this.root = newRoot;
//     }
    

//     public Group getRoot() {
//         return this.root;
//     }

//     // Method to add a friendly unit to the scene and list
//     public void addFriendlyUnit(ActiveActorDestructible unit) {
//         if (!this.root.getChildren().contains(unit)) {
//             this.root.getChildren().add(unit);
//             friendlyUnits.add(unit);
//         }
//     }

//     public void addPlayer(UserPlane user) {
//         if (!this.root.getChildren().contains(user)) {
//             this.root.getChildren().add(user);
//             player.add(user);
//             System.out.println("Added player: " + user + "to" + this.root);
//         }
//     }    

//     // Method to add an enemy unit to the scene and list
//     public void addBossUnit(ActiveActorDestructible boss) {
//         this.root.getChildren().add(boss);
//         bossUnits.add(boss);
//         System.out.println("Added bossUnit: " + boss + "to the root" + this.root);
// }

//     // Method to add an enemy unit to the scene and list
//     public void addEnemyUnit(ActiveActorDestructible enemy) {
//             this.root.getChildren().add(enemy);
//             enemyUnits.add(enemy);
//             System.out.println("Added enemyUnit: " + enemy + "to the root" + this.root);
//     }

//     // Method to add a user projectile to the scene and list
//     public void addUserProjectile(ActiveActorDestructible projectile) {
//         this.root.getChildren().add(projectile);
//         userProjectiles.add(projectile);
//     }

//     // Method to add an enemy projectile to the scene and list
//     public void addEnemyProjectile(ActiveActorDestructible projectile) {
//         enemyProjectiles.add(projectile);
//         this.root.getChildren().add(projectile);
//         System.out.println("Added enemyProjectiles: " + projectile + "to the root" + this.root);
//     }

//     // Method to add an enemy projectile to the scene and list
//     public void addBossProjectile(ActiveActorDestructible projectile) {
//         bossProjectiles.add(projectile);
//         this.root.getChildren().add(projectile);
//     }

//     // Method to remove an enemy unit to the scene and list
//     public void removeEnemyUnit(ActiveActorDestructible enemy) {
//         if (this.root.getChildren().contains(enemy)) {
//             this.root.getChildren().remove(enemy);
//             enemyUnits.remove(enemy);
//             System.out.println("Removed enemy: " + enemy);
//         } else {
//             System.out.println("Attempted to remove non-existent enemy: " + enemy);
//         }
//     }

//     public void removeBossUnit(ActiveActorDestructible boss) {
//         if (this.root.getChildren().contains(boss)) {
//             this.root.getChildren().remove(boss);
//             bossUnits.remove(boss);
//             System.out.println("Removed boss: " + boss);
//         } else {
//             System.out.println("Attempted to remove non-existent boss: " + boss);
//         }
//     }

//     /**
//      * Removes a user projectile from the scene and tracking list.
//      *
//      * @param projectile The user projectile to remove.
//      */
//     public void removeUserProjectile(ActiveActorDestructible projectile) {
//         if (this.root.getChildren().contains(projectile)) {
//             this.root.getChildren().remove(projectile);
//             userProjectiles.remove(projectile);
//             // System.out.println("Removed user projectile: " + projectile);
//         }
//     }
    
//     /**
//      * Removes an enemy projectile from the scene and tracking list.
//      *
//      * @param projectile The enemy projectile to remove.
//      */
//     public void removeEnemyProjectile(ActiveActorDestructible projectile) {
//         if (this.root.getChildren().contains(projectile)) {
//             this.root.getChildren().remove(projectile);
//             enemyProjectiles.remove(projectile);
//             // System.out.println("Removed enemy projectile: " + projectile);
//         }
//     }

//     /**
//      * Removes a boss projectile from the scene and tracking list.
//      *
//      * @param projectile The boss projectile to remove.
//      */
//     public void removeBossProjectile(ActiveActorDestructible projectile) {
//         if (this.root.getChildren().contains(projectile)) {
//             this.root.getChildren().remove(projectile);
//             bossProjectiles.remove(projectile);
//             // System.out.println("Removed boss projectile: " + projectile);
//         }
//     }

//     // Method to add UI elements to the scene
//     public void addUIElement(Node element) {
//         if (!this.root.getChildren().contains(element)) {
//             this.root.getChildren().add(element);
//         }
//     }

//     /**
//      * Updates all actors in the game.
//      */
//     public void updateAllActors(long now) {
//         // Update players
//         for (ActiveActorDestructible user : new ArrayList<>(player)) {
//             user.update(now);
//         }
    
//         // Update friendly units
//         for (ActiveActorDestructible friendly : new ArrayList<>(friendlyUnits)) {
//             friendly.update(now);
//         }
    
//         // Update enemy units
//         for (ActiveActorDestructible enemy : new ArrayList<>(enemyUnits)) {
//             enemy.update(now);
//         }

//         // Update boss units
//         for (ActiveActorDestructible boss : new ArrayList<>(bossUnits)) {
//             boss.update(now);
//         }
    
//         // Update user projectiles
//         for (ActiveActorDestructible userProj : new ArrayList<>(userProjectiles)) {
//             userProj.update(now);
//         }
    
//         // Update enemy projectiles
//         for (ActiveActorDestructible enemyProj : new ArrayList<>(enemyProjectiles)) {
//             enemyProj.update(now);
//             if (enemyProj.isDestroyed()) {
//                 removeEnemyProjectile(enemyProj);
//             }
//         }

//         // Update boss projectiles
//         for (ActiveActorDestructible bossProj : new ArrayList<>(bossProjectiles)) {
//             bossProj.update(now);
//             if (bossProj.isDestroyed()) {
//                 removeBossProjectile(bossProj);
//             }
//         }
//     }
    

//     // Remove actors that have been destroyed
//     public void removeDestroyedActors() {
//         removeDestroyedActorsFromList(friendlyUnits);
//         removeDestroyedActorsFromList(enemyUnits);
//         removeDestroyedActorsFromList(userProjectiles);
//         removeDestroyedActorsFromList(enemyProjectiles);
//     }

//     // Helper method to remove destroyed actors from a list
//     private void removeDestroyedActorsFromList(List<ActiveActorDestructible> actors) {
//         List<ActiveActorDestructible> destroyed = actors.stream()
//             .filter(ActiveActorDestructible::isDestroyed)
//             .collect(Collectors.toList());
//         this.root.getChildren().removeAll(destroyed);
//         actors.removeAll(destroyed);
//     }

//     // Getters for actor lists
//     public List<ActiveActorDestructible> getFriendlyUnits() {
//         return friendlyUnits;
//     }

//     public List<ActiveActorDestructible> getEnemyUnits() {
//         return enemyUnits;
//     }

//     public List<ActiveActorDestructible> getBossUnits() {
//         return bossUnits;
//     }

//     public List<ActiveActorDestructible> getUserProjectiles() {
//         return userProjectiles;
//     }

//     public List<ActiveActorDestructible> getEnemyProjectiles() {
//         return enemyProjectiles;
//     }

//     public List<ActiveActorDestructible> getBossProjectiles() {
//         return bossProjectiles;
//     }

//     /**
//      * Retrieves a list of players.
//      *
//      * @return A list of players, currently containing only one player.
//      */
//     public List<UserPlane> getPlayers() {
//         return player.stream()
//                     .filter(UserPlane.class::isInstance) // Ensure only UserPlane instances are returned
//                     .map(UserPlane.class::cast)         // Cast to UserPlane
//                     .collect(Collectors.toList());
//     }


//     // Optionally, add a getBossPlane() method if needed
//     public BossPlane getBossPlane() {
//         for (ActiveActorDestructible enemy : bossUnits) {
//             if (enemy instanceof BossPlane) {
//                 return (BossPlane) enemy;
//             }
//         }
//         return null;
//     }

//     /**
//      * Cleans up all actors, projectiles, and UI elements from the scene and internal lists.
//      */
//     public void cleanup() {
//         // Stop firing for all players
//         for (ActiveActorDestructible actor : new ArrayList<>(player)) {
//             if (actor instanceof CanFire) {
//                 System.out.println(actor + " can fire, stopping firing.");
//                 ((CanFire) actor).stopFiring();
//             }
//         }

//         // Stop firing for all enemy units
//         for (ActiveActorDestructible actor : new ArrayList<>(enemyUnits)) {
//             if (actor instanceof CanFire) {
//                 System.out.println(actor + " can fire, stopping firing.");
//                 ((CanFire) actor).stopFiring();
//             }
//         }

//         // Stop firing for all boss units
//         for (ActiveActorDestructible actor : new ArrayList<>(bossUnits)) {
//             if (actor instanceof CanFire) {
//                 System.out.println(actor + " can fire, stopping firing.");
//                 ((CanFire) actor).stopFiring();
//             }
//         }

//         // Clear nodes from root
//         root.getChildren().clear();
    
//         // Clear all internal lists
//         player.clear();
//         friendlyUnits.clear();
//         enemyUnits.clear();
//         bossUnits.clear();
//         userProjectiles.clear();
//         enemyProjectiles.clear();
//         bossProjectiles.clear();
    
//         System.out.println("ActorManager cleanup completed: All actors and projectiles removed.");
//     }
// }    


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
        System.out.println("Added actor: " + actor + " to the root " + this.root);
    }

    // Method to add UI elements to the scene
    public void addUIElement(Node element) {
        if (!this.root.getChildren().contains(element)) {
            this.root.getChildren().add(element);
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
            if (actor instanceof com.example.demo.actor.plane.EnemyPlane || actor instanceof com.example.demo.actor.plane.BossPlane) {
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
            if (actor instanceof com.example.demo.actor.projectile.EnemyProjectile || actor instanceof com.example.demo.actor.projectile.BossProjectile) {
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
    // Add other getter methods as needed
    }
