package com.example.demo;

import com.example.demo.manager.ImageManager;
import com.example.demo.util.GameConstant;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class HeartDisplay {

    private static final String HEART_IMAGE_NAME = GameConstant.Heart.IMAGE_NAME;
    private static final int HEART_HEIGHT = GameConstant.Heart.IMAGE_HEIGHT;
    private HBox container;
    private double containerXPosition;
    private double containerYPosition;

    public HeartDisplay(double xPosition, double yPosition, int initialHearts) {
        this.containerXPosition = xPosition;
        this.containerYPosition = yPosition;
        initializeContainer();
        setHearts(initialHearts); // Set initial hearts dynamically
    }

    private void initializeContainer() {
        container = new HBox();
        container.setLayoutX(containerXPosition);
        container.setLayoutY(containerYPosition);
    }

    /**
     * Updates the heart display to match the number of hearts remaining.
     *
     * @param heartsRemaining The number of hearts to display.
     */
    public void setHearts(int heartsRemaining) {
        container.getChildren().clear();
        ImageManager imageManager = ImageManager.getInstance(); // Get the singleton instance

        for (int i = 0; i < heartsRemaining; i++) {
            Image heartImage = imageManager.getImage(HEART_IMAGE_NAME);
            ImageView heart = new ImageView(heartImage);
            heart.setFitHeight(HEART_HEIGHT);
            heart.setPreserveRatio(true);
            container.getChildren().add(heart);}
    }

    public HBox getContainer() {
        return container;
    }
}
