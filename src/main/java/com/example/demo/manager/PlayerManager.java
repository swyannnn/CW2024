// package com.example.demo.manager;

// import com.example.demo.UserPlane;
// import javafx.scene.Group;
// import java.util.ArrayList;
// import java.util.List;

// /**
//  * PlayerManager manages multiple players and their respective ActorManagers.
//  */
// public class PlayerManager {
//     private final List<UserPlane> userPlanes;
//     private final ActorManager actorManager; // Use singleton ActorManager

//     public PlayerManager(Group root, int numberOfPlayers, double stageHeight, double stageWidth, int initialHealth) {
//         this.userPlanes = new ArrayList<>();
//         this.actorManager = ActorManager.getInstance(root); // Use singleton instance

//         for (int i = 0; i < numberOfPlayers; i++) {
//             // Create a UserPlane for each player
//             UserPlane userPlane = new UserPlane(initialHealth);
            
//             // Add the UserPlane to the ActorManager
//             actorManager.addFriendlyUnit(userPlane);
            
//             // Store the UserPlane in the list
//             userPlanes.add(userPlane);
//         }
//     }

//     public ActorManager getActorManager() {
//         return actorManager;
//     }

//     public List<UserPlane> getUserPlanes() {
//         return userPlanes;
//     }
// }
