package com.example.demo.manager;

import com.example.demo.UserPlane;
import javafx.scene.Group;
import java.util.ArrayList;
import java.util.List;

/**
 * PlayerManager manages multiple players and their respective ActorManagers.
 */
public class PlayerManager {
    private final List<ActorManager> playerActorManagers;

    public PlayerManager(Group root, int numberOfPlayers) {
        this.playerActorManagers = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            // Create a UserPlane or other player-specific entities as needed
            // UserPlane userPlane = new UserPlane(); // Example: Create a new UserPlane for each player
            ActorManager actorManager = new ActorManager(root);
            // actorManager.addFriendlyUnit(userPlane); // Add the player’s UserPlane to the actor manager
            playerActorManagers.add(actorManager);
        }
    }

    public List<ActorManager> getPlayerActorManagers() {
        return playerActorManagers;
    }

    // Additional methods to manage players, update actors, handle input, etc.
}
