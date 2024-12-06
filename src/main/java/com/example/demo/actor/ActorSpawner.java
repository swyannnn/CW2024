package com.example.demo.actor;

import java.util.List;

import com.example.demo.actor.plane.UserPlane;

import javafx.scene.Group;
import javafx.scene.Node;

public interface ActorSpawner {
    void spawnActor(ActiveActor actor);

    void updateRoot(Group root);

    void addUIElement(Node node);

    // get player list
    List<UserPlane> getPlayers(); 

    List<ActiveActor> getEnemyUnits();

    List<ActiveActor> getBossUnits();

}
