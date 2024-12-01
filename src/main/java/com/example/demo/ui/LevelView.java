package com.example.demo.ui;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.HeartDisplay;
import com.example.demo.actor.plane.UserPlane;
import com.example.demo.listener.HealthChangeListener;
import com.example.demo.manager.ImageManager;
import com.example.demo.util.GameConstant;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

public class LevelView implements HealthChangeListener {
	private final Map<UserPlane, HeartDisplay> heartDisplays = new HashMap<>();
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 10;
	private final Group root;
    protected ImageView[] backgrounds;
	
	public LevelView(Group root) {
		this.root = root;
	}

    /**
     * Initializes the background images for scrolling effect.
     *
     * @param backgroundImageName The path to the background image.
     */
    public void initializeBackground(String backgroundImageName) {
        ImageView img1 = new ImageView(ImageManager.getInstance().getImage(backgroundImageName));
        ImageView img2 = new ImageView(ImageManager.getInstance().getImage(backgroundImageName));

        img1.setFitHeight(GameConstant.GameSettings.SCREEN_HEIGHT);
        img1.setFitWidth(GameConstant.GameSettings.SCREEN_WIDTH);
        img1.setOpacity(0.7);
        img2.setFitHeight(GameConstant.GameSettings.SCREEN_HEIGHT);
        img2.setFitWidth(GameConstant.GameSettings.SCREEN_WIDTH);
        img2.setOpacity(0.7);
        // Position the second image right after the first
        img1.setTranslateX(0);
        img2.setTranslateX(GameConstant.GameSettings.SCREEN_WIDTH);

        backgrounds = new ImageView[] { img1, img2 };

        // Add both images to the UI layer
        root.getChildren().addAll(backgrounds);
    }

    /**
     * Updates the background positions to create a scrolling effect.
     *
     * @param scrollSpeed The speed at which the background scrolls.
     */
    public void updateBackground(double scrollSpeed) {
        for (ImageView img : backgrounds) {
            img.setTranslateX(img.getTranslateX() - scrollSpeed);

            // If the image has moved completely out of view, reset its position
            if (img.getTranslateX() + img.getFitWidth() <= 0) {
                img.setTranslateX(GameConstant.GameSettings.SCREEN_WIDTH - scrollSpeed);
            }
        }
    }
	
    @Override
    public void onHealthChange(UserPlane player, int newHealth) {
        HeartDisplay hd = heartDisplays.get(player);
        if (hd != null) {
            hd.setHearts(newHealth);
            System.out.println("Updated heart display for player: " + player + " to " + newHealth + " hearts.");
        }
    }

	public void showHeartDisplay(UserPlane player, int playerIndex) {
        if (!heartDisplays.containsKey(player)) {
            double xPosition = HEART_DISPLAY_X_POSITION;
            double yPosition = calculateYPosition(playerIndex);; // You can keep y fixed or adjust as needed
            HeartDisplay hd = new HeartDisplay(xPosition, yPosition, player.getHealth());
            heartDisplays.put(player, hd);
            root.getChildren().add(hd.getContainer());
        }
    }
    
    private double calculateYPosition(int playerIndex) {
        double baseX = HEART_DISPLAY_Y_POSITION;
        double offset = 25; // Adjust the offset as needed to prevent overlapping
        return baseX + (playerIndex * offset);
    }
	
	public void updateView() {
		// Initialize LevelView
	}
}
