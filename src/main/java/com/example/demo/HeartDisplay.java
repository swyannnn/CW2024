package com.example.demo;

import com.example.demo.manager.ImageManager;
import com.example.demo.util.GameConstant;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class HeartDisplay {

    private static final String HEART_IMAGE_NAME = GameConstant.Heart.IMAGE_NAME;
    private static final int HEART_HEIGHT = GameConstant.Heart.IMAGE_HEIGHT;
    private ImageManager imageManager = ImageManager.getInstance();
    private HBox container;
    private int playerIndex;
    private double containerXPosition;
    private double containerYPosition;

    public HeartDisplay(int playerIndex, double xPosition, double yPosition, int initialHearts) {
        this.containerXPosition = xPosition;
        this.containerYPosition = yPosition;
        this.playerIndex = playerIndex;
        initializeContainer();
        setHearts(initialHearts); // Set initial hearts dynamically
    }

    private void initializeContainer() {
        container = new HBox(5);
        container.setLayoutX(containerXPosition);
        container.setLayoutY(containerYPosition);

        String planeIconImageName = getImageName(playerIndex);
        Image planeIconImage = imageManager.getImage(planeIconImageName);
        ImageView planeIcon = new ImageView(planeIconImage);
        planeIcon.setFitHeight(HEART_HEIGHT);
        planeIcon.setPreserveRatio(true);
        container.getChildren().add(planeIcon);
    }

    private String getImageName(int playerIndex) {
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
                Image heartImage = imageManager.getImage(HEART_IMAGE_NAME);
                ImageView heart = new ImageView(heartImage);
                heart.setFitHeight(HEART_HEIGHT);
                heart.setPreserveRatio(true);
                container.getChildren().add(heart);
            }
        });
    }

    public HBox getContainer() {
        return container;
    }
}
