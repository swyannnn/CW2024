package com.example.demo.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import com.example.demo.actor.plane.UserPlane;
import com.example.demo.listener.HealthChangeListener;
import com.example.demo.manager.ImageManager;
import com.example.demo.util.GameConstant;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LevelView implements HealthChangeListener, PropertyChangeListener {
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
        ImageView img1 = new ImageView(ImageManager.getImage(backgroundImageName));
        ImageView img2 = new ImageView(ImageManager.getImage(backgroundImageName));

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

    public void showInstructions(int currentLevel) {
        System.out.println("Showing instructions for level: " + currentLevel);
        Text instructionText = new Text(getInstructionsForLevel(currentLevel));
        instructionText.setFont(Font.font("Comic Sans MS", 35));
        instructionText.setFill(Color.BLACK);
        instructionText.setLayoutX(GameConstant.GameSettings.SCREEN_WIDTH - 850); // Adjust width as needed
        instructionText.setLayoutY(35); // 10 pixels from the top
        instructionText.setWrappingWidth(GameConstant.GameSettings.SCREEN_WIDTH * 0.8); // Adjust width as needed
        root.getChildren().add(instructionText);
    }

    /**
     * Retrieves instructions based on the current level number.
     *
     * @param level The current level number.
     * @return A string containing instructions for the level.
     */
    private String getInstructionsForLevel(int level) {
        switch (level) {
            case 1:
                return String.format("Level %d: Kill %d enemies!.", level, GameConstant.Level001.KILLS_TO_ADVANCE);
            case 2:
                return String.format("Level %d: Kill the boss!", level, GameConstant.Level001.KILLS_TO_ADVANCE);
            case 3:
                return String.format("Level %d: Survive %d seconds!", level, GameConstant.Level003.SURVIVAL_TIME);
            case 4:
                return String.format("Level %d: Kill the ULTIMATE BOSS!", level);
            default:
                return "Good luck on your adventure!";
        }
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
            double yPosition = calculateYPosition(playerIndex);; 
            HeartDisplay hd = new HeartDisplay(playerIndex, xPosition, yPosition, player.getHealth());
            heartDisplays.put(player, hd);
            root.getChildren().add(hd.getContainer());
        }
    }
    
    private double calculateYPosition(int playerIndex) {
        double baseX = HEART_DISPLAY_Y_POSITION;
        double offset = 40; // Adjust the offset as needed to prevent overlapping
        return baseX + (playerIndex * offset);
    }

    /**
     * Handles property change events.
     *
     * @param evt The property change event.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "level":
                int newLevelNumber = (int) evt.getNewValue();
                showInstructions(newLevelNumber);
                break;
            // Handle other events if necessary
            default:
                break;
        }
    }
	
	public void updateView() {
		// Initialize LevelView
	}
}
