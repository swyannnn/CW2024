package com.example.demo.ui;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.GameOverImage;
import com.example.demo.HeartDisplay;
import com.example.demo.ShieldImage;
import com.example.demo.WinImage;
import com.example.demo.actors.planes.UserPlane;
import com.example.demo.listener.HealthChangeListener;
import com.example.demo.manager.ActorManager;

public class LevelView001 implements HealthChangeListener {
	private final Map<UserPlane, HeartDisplay> heartDisplays = new HashMap<>();
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final int WIN_IMAGE_X_POSITION = 355;
	private static final int WIN_IMAGE_Y_POSITION = 175;
	private static final int LOSS_SCREEN_X_POSITION = -160;
	private static final int LOSS_SCREEN_Y_POSITION = -375;
	private static final int SHIELD_X_POSITION = 355;
	private static final int SHIELD_Y_POSITION = 175;
	private final ActorManager actorManager;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final ShieldImage shield;
	
	public LevelView001(ActorManager actorManager, int heartsToDisplay) {
		if (actorManager == null) {
			throw new IllegalArgumentException("ActorManager cannot be null.");
		}
		this.actorManager = actorManager;
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);
		this.shield = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
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
            System.out.println("Added heart display for player: " + player);
        }
    }
	
	public void showWinImage() {
		actorManager.addUIElement(winImage); // Add winImage to the scene using ActorManager
		winImage.showWinImage(); // Call any additional methods needed to display the win image
	}
	
	public void showGameOverImage() {
		actorManager.addUIElement(gameOverImage); // Add gameOverImage to the scene using ActorManager
		gameOverImage.showGameOverImage(); // Call any additional methods needed to display the game over image
	}
	
	public void showShield() {
		actorManager.addUIElement(shield); // Add shield to the scene using ActorManager
		shield.showShield(); // Call any additional methods needed to display the shield
	}
	
	public void updateView() {
		// Initialize LevelView
	}
}
