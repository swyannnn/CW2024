package com.example.demo.ui;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.HeartDisplay;
import com.example.demo.actor.plane.UserPlane;
import com.example.demo.listener.HealthChangeListener;
import com.example.demo.manager.ActorManager;

public class LevelView implements HealthChangeListener {
	private final Map<UserPlane, HeartDisplay> heartDisplays = new HashMap<>();
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private final ActorManager actorManager;
	
	public LevelView(ActorManager actorManager, int heartsToDisplay) {
		this.actorManager = actorManager;
	}
	
    @Override
    public void onHealthChange(UserPlane player, int newHealth) {
        HeartDisplay hd = heartDisplays.get(player);
        if (hd != null) {
            hd.setHearts(newHealth);
            System.out.println("Updated heart display for player: " + player + " to " + newHealth + " hearts.");
        }
    }

	public void showHeartDisplay(UserPlane player) {
        if (!heartDisplays.containsKey(player)) {
            HeartDisplay hd = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, player.getHealth());
            heartDisplays.put(player, hd);
            actorManager.addUIElement(hd.getContainer());
        }
    }
	
	public void updateView() {
		// Initialize LevelView
	}
}
