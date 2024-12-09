package com.example.demo.screen;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.actor.plane.UserPlane;
import com.example.demo.actor.plane.component.HeartDisplay;
import com.example.demo.handler.HealthChangeHandler;
import com.example.demo.manager.ImageManager;
import com.example.demo.util.GameConstant;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The LevelScreen class is responsible for displaying the current level's background,
 * instructions, and heart displays for players. It implements the HealthChangeHandler
 * interface to update the heart display when a player's health changes.
 * 
 * <p>This class provides methods to initialize the background, show level instructions,
 * update the background for a scrolling effect, and manage heart displays for players.</p>
 *
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/screen/LevelScreen.java">Github Source Code</a>
 * @see HealthChangeHandler
 * @see UserPlane
 * @see HeartDisplay
 */
public class LevelScreen implements HealthChangeHandler {
	private final Map<UserPlane, HeartDisplay> heartDisplays = new HashMap<>();
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 10;
    private final double scrollSpeed = 1.0; 
	private final Group root;
    protected ImageView[] backgrounds;
    private final int currentLevelNumber;
	
    /**
     * Constructs a new LevelView instance.
     *
     * @param root the root group node for the scene graph
     * @param currentLevelNumber the current level number to be displayed
     */
	public LevelScreen(Group root, int currentLevelNumber) {
		this.root = root;
        this.currentLevelNumber = currentLevelNumber;
        initializeBackground();
        showInstructions();
	}

    /**
     * Initializes the background for the level view by creating two ImageView objects
     * with the background image for the current level. The images are positioned side
     * by side to create a seamless scrolling effect. The images are set to the screen
     * height and width, and their opacity is set to 0.7. The images are then added to
     * the UI layer.
     */
    public void initializeBackground() {
        String backgroundImageName = GameConstant.LevelBackground.getBackgroundImageForLevel(currentLevelNumber);
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

    /**
     * Displays the instructions for the current level on the screen.
     * The instructions are displayed as a text element with a specific font,
     * size, and color. The position and wrapping width of the text are also set.
     * The text is added to the root node of the scene graph.
     */
    public void showInstructions() {
        System.out.println("Showing instructions for level: " + currentLevelNumber);
        Text instructionText = new Text(getInstructionsForLevel(currentLevelNumber));
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
     * Updates the background images by moving them to the left based on the scroll speed.
     * If an image moves completely out of view, it resets its position to the right edge of the screen.
     */
    public void updateBackground() {
        for (ImageView img : backgrounds) {
            img.setTranslateX(img.getTranslateX() - scrollSpeed);

            // If the image has moved completely out of view, reset its position
            if (img.getTranslateX() + img.getFitWidth() <= 0) {
                img.setTranslateX(GameConstant.GameSettings.SCREEN_WIDTH - scrollSpeed);
            }
        }
    }
	
    /**
     * Updates the heart display for the given player when their health changes.
     *
     * @param player The player whose health has changed.
     * @param newHealth The new health value of the player.
     */
    @Override
    public void onHealthChange(UserPlane player, int newHealth) {
        HeartDisplay hd = heartDisplays.get(player);
        if (hd != null) {
            hd.setHearts(newHealth);
            System.out.println("Updated heart display for player: " + player + " to " + newHealth + " hearts.");
        }
    }

    /**
     * Displays the heart display for the given player if it does not already exist.
     *
     * @param player The UserPlane object representing the player.
     * @param playerIndex The index of the player.
     */
	public void showHeartDisplay(UserPlane player, int playerIndex) {
        if (!heartDisplays.containsKey(player)) {
            double xPosition = HEART_DISPLAY_X_POSITION;
            double yPosition = calculateYPosition(playerIndex);; 
            HeartDisplay hd = new HeartDisplay(playerIndex, xPosition, yPosition, player.getHealth());
            heartDisplays.put(player, hd);
            root.getChildren().add(hd.getContainer());
        }
    }
    
    /**
     * Calculates the Y position for a player based on their index.
     *
     * @param playerIndex the index of the player
     * @return the calculated Y position for the player
     */
    private double calculateYPosition(int playerIndex) {
        double baseX = HEART_DISPLAY_Y_POSITION;
        double offset = 40; // Adjust the offset as needed to prevent overlapping
        return baseX + (playerIndex * offset);
    }
}
