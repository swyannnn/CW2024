package com.example.demo.level;

import com.example.demo.GameOverImage;
import com.example.demo.HeartDisplay;
import com.example.demo.ShieldImage;
import com.example.demo.WinImage;
import com.example.demo.manager.ActorManager;

import javafx.scene.Group;

public class LevelView {
	
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
	private final HeartDisplay heartDisplay;
	private final ShieldImage shield;
	
	public LevelView(ActorManager actorManager, int heartsToDisplay) {
		if (actorManager == null) {
			throw new IllegalArgumentException("ActorManager cannot be null.");
		}
		this.actorManager = actorManager;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);
		this.shield = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
	}
	
	public void updateView() {
		// If there are additional elements like score or level-specific UI updates,
		// you can implement the logic here. For now, `updateView()` can be used
		// to refresh or animate elements as needed.
	}
	
	public void showHeartDisplay() {
		actorManager.addUIElement(heartDisplay.getContainer());
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
	
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

}
