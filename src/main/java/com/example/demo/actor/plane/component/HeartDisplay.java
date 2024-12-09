package com.example.demo.actor.plane.component;

import com.example.demo.manager.ImageManager;
import com.example.demo.util.GameConstant;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


/**
 * The HeartDisplay class represents a graphical component that displays a player's hearts
 * in a game. It uses an HBox container to arrange the hearts and the player's plane icon.
 * The number of hearts displayed can be dynamically updated.
 * 
 * <p>This class provides methods to initialize the heart display, set the number of hearts,
 * and retrieve the container for the heart display.</p>
 * 
 * <p>Note: This class relies on ImageManager for retrieving image names and images.</p>
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/actor/plane/component/HeartDisplay.java">Github Source Code</a>
 * @see ImageManager
 */
public class HeartDisplay {
    private static final String HEART_IMAGE_NAME = GameConstant.Heart.IMAGE_NAME;
    private static final int HEART_HEIGHT = GameConstant.Heart.IMAGE_HEIGHT;
    private HBox container;
    private int playerIndex;
    private double containerXPosition;
    private double containerYPosition;

    /**
     * Constructs a HeartDisplay instance with specified parameters.
     *
     * @param playerIndex The index of the player.
     * @param xPosition   The x-coordinate of the heart display.
     * @param yPosition   The y-coordinate of the heart display.
     * @param initialHearts The initial number of hearts to display.
     */
    public HeartDisplay(int playerIndex, double xPosition, double yPosition, int initialHearts) {
        this.containerXPosition = xPosition;
        this.containerYPosition = yPosition;
        this.playerIndex = playerIndex;
        initializeContainer();
        setHearts(initialHearts); // Set initial hearts dynamically
    }

    /**
     * Initializes the container for the heart display.
     * This method sets up an HBox with a specified spacing, positions it at the given coordinates,
     * and adds an ImageView containing the player's plane icon image to the container.
     */
    private void initializeContainer() {
        container = new HBox(5);
        container.setLayoutX(containerXPosition);
        container.setLayoutY(containerYPosition);

        String planeIconImageName = getImageName(playerIndex);
        Image planeIconImage = ImageManager.getImage(planeIconImageName);
        ImageView planeIcon = new ImageView(planeIconImage);
        planeIcon.setFitHeight(HEART_HEIGHT);
        planeIcon.setPreserveRatio(true);
        container.getChildren().add(planeIcon);
    }

    /**
     * Returns the image name corresponding to the given player index.
     *
     * @param playerIndex the index of the player (0 or 1)
     * @return the image name for the player if the index is 0 or 1, otherwise null
     */
    public String getImageName(int playerIndex) {
        System.out.println("playerIndex: " + playerIndex);
        switch (playerIndex) {
            case 0:
                return GameConstant.UserPlane.ID1_IMAGE_NAME_DISPLAY;
            case 1:
                return  GameConstant.UserPlane.ID2_IMAGE_NAME_DISPLAY;
            default:
                return null;
        }
    }

    /**
     * Updates the heart display to match the number of hearts remaining.
     *
     * @param heartsRemaining The number of hearts to display.
     */
    public void setHearts(int heartsRemaining) {
        Platform.runLater(() -> {
            // Remove all heart icons, keep the plane icon
            while (container.getChildren().size() > 1) {
                container.getChildren().remove(1);
            }

            // Add heart icons
            for (int i = 0; i < heartsRemaining; i++) {
                Image heartImage = ImageManager.getImage(HEART_IMAGE_NAME);
                ImageView heart = new ImageView(heartImage);
                heart.setFitHeight(HEART_HEIGHT);
                heart.setPreserveRatio(true);
                container.getChildren().add(heart);
            }
        });
    }

    /**
     * Returns the container for the heart display.
     * @return The container for the heart display.
     */
    public HBox getContainer() {
        return container;
    }
}
